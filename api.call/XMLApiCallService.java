package com.nongshim.next.nssm.service.api;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.support.JdbcUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.mcnc.bizmob.common.config.SmartConfig;
import com.mcnc.bizmob.common.util.JsonUtil;
import com.mcnc.bizmob.common.util.StringUtil;
import com.mcnc.bizmob.hybrid.common.server.JsonAdaptorObject;
import com.nongshim.next.nssm.api.xml.elements.ColElement;
import com.nongshim.next.nssm.api.xml.elements.DatasetElement;
import com.nongshim.next.nssm.api.xml.elements.ParameterElement;
import com.nongshim.next.nssm.api.xml.elements.ParametersElement;
import com.nongshim.next.nssm.api.xml.elements.RootElement;
import com.nongshim.next.nssm.api.xml.elements.RowElement;
import com.nongshim.next.nssm.common.code.CodesEx;
import com.nongshim.next.nssm.common.code.NongshimCodesEx;
import com.nongshim.next.nssm.common.exception.NSException;
import com.nongshim.next.nssm.common.util.NSUtil;
import com.nongshim.next.nssm.common.util.NsXmlUtil;

@Service
public class XMLApiCallService {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private RestTemplate restTemplate;
	
	public JsonNode call(String module, String apiPath, String param, JsonAdaptorObject sessionObj) throws NSException {
		
		/* 1. log4j 로그레벨이 DEBUG인 경우 API 세부로그를 생성 */
		long 			startTime	= 0L;
		StringBuffer 	buffer 		= null;
		boolean			isInfo		= logger.isInfoEnabled();
		boolean			isDebug		= logger.isDebugEnabled();
		String 			apiURL 		= "";
		
		try {
			
			startTime	= System.currentTimeMillis();

			/* API URL 정보 세팅 */
			apiURL 		= SmartConfig.getString("api.base.url") + ":"							//API Server Url
						+ SmartConfig.getString(NongshimCodesEx._PROP_PREFIX_PORT + module)		//API Service Port
						+ apiPath;																//API Path
			
			if (isInfo || isDebug) {
				buffer 	= new StringBuffer();
				buffer.append("\n┌────────────────────────────────────────────────────────────────────────────────────────────");
				buffer.append("\n│ API Start process > ").append(apiURL);
				buffer.append("\n│ API Request For > ").append(module);
				buffer.append("\n│ API Request Data > \n").append(param);
			}
			
			/* Set RequestEntity */
			HttpEntity<String> 	requestEntity =	new HttpEntity<String>(param, this.setHttpHeaders(sessionObj, buffer));
			
			/* Call by exchange */
			ResponseEntity<String> 	responseEntity = restTemplate.exchange(apiURL, HttpMethod.POST, requestEntity, String.class);
			
			/* Ger response body */
			String 		content	= responseEntity.getBody();
						//content = HtmlUtils.htmlUnescape(content);

			/* log level 이 debug인 경우에만 api response 로깅 */
			if (isDebug) {
				buffer.append("\n│ API Response Data > \n").append(content);
			}
			
			/* HTTP 응답코드 확인 */
			if (!responseEntity.getStatusCode().is2xxSuccessful()) {
				
				int resCode = responseEntity.getStatusCodeValue();
				logger.error("API Call Error!! {} : {}", apiPath, resCode);
				
				if (isInfo || isDebug) {
					buffer.append("\n│ API Connection Error > ").append(resCode);
				}
				
				throw new NSException(CodesEx.API_EXCEPTION, "[API ERROR] HTTP STATUS : " + resCode);
			}
			
			RootElement response = (RootElement) NsXmlUtil.xmlStringToPojo(content, RootElement.class);
			
			/* API response 에러 여부 확인 */
			this.checkApiResponseError(response);
			
			return this.xmlResponseToJsonNodeByRootElement(response);
			
		} catch(HttpStatusCodeException he) {
			logger.error(he.getMessage(), he);
			throw new NSException(CodesEx.API_EXCEPTION, "OCCUR ERROR!! HTTP STATUS : " + he.getStatusCode().toString());
			
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new NSException(CodesEx.API_EXCEPTION, CodesEx.API_EXCEPTION_MSG);
		} finally {
			if (isInfo || isDebug) {
				long endTime	 	= System.currentTimeMillis();
				buffer.append("\n│ API Path  > ").append(apiPath).append(" [" + (endTime - startTime)).append("ms]");
				buffer.append("\n└────────────────────────────────────────────────────────────────────────────────────────────");
				logger.info(buffer.toString());
			}
		}
	}
	
	/**
	 * @desc Convert response(XML String) value to JsonNode(RootElement)
	 * @desc 응답값(XML)을 JsonNode로 변환. for Dataset List Format
	 * */
	private JsonNode xmlResponseToJsonNodeByRootElement(RootElement root) {
		
		ObjectNode objNode = JsonUtil.objectNode();
		
		if(root.getDataset() != null) {
			
			for(DatasetElement dataset : root.getDataset()) {
				
				List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
				
				List<RowElement> rowList = dataset.getRows().getRowList();
				if(rowList != null && rowList.size() > 0) {
					
					for(RowElement row : rowList) {
						Map<String, Object> map = new HashMap<String, Object>();
						List<ColElement> paramList = row.getCols();
						for(ColElement param : paramList) {
							map.put(JdbcUtils.convertUnderscoreNameToPropertyName(param.getId()), param.getVal());
						}
						mapList.add(map);
					}
				}
				
				ObjectMapper objectMapper = new ObjectMapper();                
				JsonNode jn = objectMapper.valueToTree(mapList);
				
				objNode.set(dataset.getId(), jn);
			}
		}
		
		return objNode;
	}
	
	/**
	 * @desc Check internal error of response data
	 * @descKor API 응답 데이터 내, 결과 코드 체크
	 * */
	private void checkApiResponseError(RootElement response) throws NSException {
	
		ParametersElement apiResult = response.getParameters();
		
		String resultCode 	= "";
		String resultMsg	= "";
		
		for(ParameterElement result : apiResult.getParameterList()) {
			
			resultCode = StringUtil.equalsIgnoreCase(NongshimCodesEx._ITRN_API_RESULT_FIELD_RESULT_CODE, result.getId()) ? result.getVal() : resultCode;
			resultMsg = StringUtil.equalsIgnoreCase(NongshimCodesEx._ITRN_APIRESULT_FIELD_RESULT_MSG, result.getId()) ? result.getVal() : resultMsg;
		}
		
		//PC에서, API 결과는 성공이나 에러코드를 설정하여 메시지 처리하는 부분이 있음
		//API별 성공처리에 대한 코드 값이 상이하여, 오류인 에러코드 체크
		if(StringUtil.contains(NongshimCodesEx.API_RESULT_CODE_ARR_ERROR, resultCode)) {
			
			throw new NSException(CodesEx.API_EXCEPTION, resultMsg);
		}
	}
	
	/**
	 * @title setHttpHeaders
	 * @desc 헤더 정보 설정 
	 * </br> set evUserId / evUserIp / evLangSe 
	 * </br> 주문관련 API인 경우, 모바일 등록 구분 추가
	 * */
	private HttpHeaders setHttpHeaders(JsonAdaptorObject sessionObj, StringBuffer buffer) throws NSException {
		
		HttpHeaders headers = new HttpHeaders();
		
		Charset utf8 = Charset.forName("UTF-8");
		MediaType mediaType = new MediaType(MediaType.APPLICATION_XML, utf8);
		headers.setContentType(mediaType);
		if(sessionObj != null) {
			String evUserId = NSUtil.getSessionAttribute(sessionObj, "empNo");
			if(StringUtil.isEmpty(evUserId)) {
				buffer.append("\n│ API Request Fail > No Sessiong Data");
				throw new NSException(CodesEx._SESSION_TIMEOUT, CodesEx._SESSION_TIMEOUT_TEXT);
			}
			String evUserIp = NSUtil.getSessionAttribute(sessionObj, "clientIp");
			try {
				evUserIp = StringUtil.isEmpty(evUserIp) ? InetAddress.getLocalHost().getHostAddress() : evUserIp;
			} catch (UnknownHostException e) {
				logger.error("IP정보 조회 중 오류 발생");
			}
			
			headers.add("evUserId", NSUtil.getSessionAttribute(sessionObj, "empNo"));
			headers.add("evUserIp", evUserIp);
			headers.add("evLangSe", "KOR");
		
			//2022.12.27 ISD 요청 > 주문 API 호출 시, 모바일 구분코드 추가
			String orderYn = NSUtil.toStringValue(NSUtil.getSessionAttribute(sessionObj, "orderYn"));
			if(StringUtil.equalsIgnoreCase("Y", orderYn)) {
				headers.add(NongshimCodesEx._ISD_ORDER_API_REQUEST_MOBILE_KEY, NongshimCodesEx._ISD_ORDER_API_REQUEST_MOBILE_VALUE);
			}
			
			buffer.append("\n│ API Request Header Data > ").append(headers.toString());
		}
		
		return headers;
	}
}