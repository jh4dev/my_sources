package com.lottewellfood.sfa.common.service.call;

import java.nio.charset.Charset;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lottewellfood.sfa.common.enums.LotteErrors;
import com.lottewellfood.sfa.common.exception.LotteException;
import com.mcnc.bizmob.common.util.JsonUtil;

@Service("jsonApiCallService")
public class JSONApiCallService {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired 
	private ObjectMapper objectMapper;
	
	public <T>JsonNode call(HttpMethod method, HttpHeaders headers, String apiURL, T param) throws LotteException {
		
		/* 1. log4j 로그레벨이 DEBUG인 경우 API 세부로그를 생성 */
		long 			startTime	= 0L;
		StringBuffer 	buffer 		= null;
		boolean			isInfo		= logger.isInfoEnabled();
		boolean			isDebug		= logger.isDebugEnabled();
		
		try {

			startTime	= System.currentTimeMillis();

			if (isInfo || isDebug) {
				buffer 	= new StringBuffer();
				buffer.append("\n┌────────────────────────────────────────────────────────────────────────────────────────────");
				buffer.append("\n│ API Start process > ").append(apiURL);
				buffer.append("\n│ API Request Data > ").append(param);
				buffer.append("\n│ API Request Data(JSON) > ").append(JsonUtil.toJson(param));
			}

			//RestTemplate 설정
			restTemplate.getMessageConverters()
            .add(0, new StringHttpMessageConverter(Charset.forName("UTF-8")));
			
			
			if(headers == null) {
				headers = new HttpHeaders();
				Charset utf8 = Charset.forName("UTF-8");
//				MediaType mediaType = new MediaType(MediaType.APPLICATION_JSON, utf8);
				MediaType mediaType = new MediaType(MediaType.APPLICATION_FORM_URLENCODED, utf8);
				headers.setContentType(mediaType);
			}
			
			/* Set RequestEntity */
			HttpEntity<T> 	requestEntity =	new HttpEntity<T>(param, headers);
			
			/* Call by exchange */
			ResponseEntity<String> 	responseEntity = restTemplate.exchange(apiURL, method, requestEntity, String.class);
			
			/* Ger response body */
			String 		content	= responseEntity.getBody();
			
			if (isDebug) {
				buffer.append("\n│ API Response Data > ").append(content);
			}
			
			/* HTTP 응답코드 확인 */
			if (!responseEntity.getStatusCode().is2xxSuccessful()) {
				int resCode = responseEntity.getStatusCodeValue();
				logger.error("API Call Error!! {} : {}", apiURL, resCode);
				if (isInfo || isDebug) {
					buffer.append("\n│ API Connection Error > ").append(resCode);
				}
				throw new LotteException(LotteErrors.API_HTTP_STATUS_ERROR);
			}
			
			JsonNode	resultNode 	= objectMapper.readTree(content);
			
			return resultNode;
			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new LotteException(LotteErrors.API_HTTP_STATUS_ERROR);
		} finally {
			if (isInfo || isDebug) {
				long endTime	 	= System.currentTimeMillis();
				buffer.append("\n│ API URL > ").append(apiURL);
				buffer.append("\n│ API Call Time > ").append(endTime - startTime).append("ms");
				buffer.append("\n└────────────────────────────────────────────────────────────────────────────────────────────");
				logger.info(buffer.toString());
			}
		}
	}
}