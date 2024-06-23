package com.lottewellfood.sfa.common.service.utility;

import java.io.File;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lottewellfood.sfa.common.code.VocConstants;
import com.lottewellfood.sfa.common.customs.CustomObjectMapper;
import com.lottewellfood.sfa.common.enums.LotteErrors;
import com.lottewellfood.sfa.common.exception.LotteException;
import com.lottewellfood.sfa.common.service.call.JSONApiCallService;
import com.lottewellfood.sfa.common.service.call.MultiPartCallService;
import com.lottewellfood.sfa.common.util.ConvertUtil;
import com.mcnc.bizmob.common.config.SmartConfig;
import com.mcnc.bizmob.hybrid.server.web.dao.LocalFileStorageAccessor;

@Service("vocService")
public class VocService {

	private static final Logger logger = LoggerFactory.getLogger(VocService.class);
	
	@Autowired
	private JSONApiCallService jsonApiCallService;
	
	@Autowired
	private MultiPartCallService multiPartCallService;
	
	@Autowired
	private LocalFileStorageAccessor uploadStorageAccessor;
	
	private HttpHeaders getVocHeader() {
		
		HttpHeaders headers = new HttpHeaders();
		Charset utf8 = Charset.forName("UTF-8");
		MediaType mediaType = new MediaType(MediaType.APPLICATION_JSON, utf8);
//		MediaType mediaType = new MediaType(MediaType.APPLICATION_FORM_URLENCODED, utf8);
		headers.setContentType(mediaType);
		
		return headers;
	}
	
	private HttpHeaders getVocFileHeader() {
		
		HttpHeaders headers = new HttpHeaders();
		Charset utf8 = Charset.forName("UTF-8");
		MediaType mediaType = new MediaType(MediaType.MULTIPART_FORM_DATA, utf8);
		headers.setContentType(mediaType);
		
		return headers;
	}
	
	public void test() {
		try {
			
			File file = new File("C:\\Workspace\\sfaServer\\SMART_HOME\\temp\\voctest.json");
			ObjectMapper mapper = CustomObjectMapper.create();
			JsonNode jn = mapper.readValue(file, JsonNode.class);
			Map<String, Object> reqMap = ConvertUtil.objectToMap(jn);
			
			System.out.println(reqMap);
			
			jn = jsonApiCallService.call(HttpMethod.POST, getVocHeader(), SmartConfig.getString("voc.api.url.get." + System.getProperty("spring.profiles.active")) + SmartConfig.getString("api.voc.list"), reqMap);
			
			System.out.println(jn);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public JsonNode call(String apiName, Object param) throws LotteException {
		Map<String, Object> reqMap = null;
		JsonNode			resMap = null;
		
		try {
			
			reqMap = ConvertUtil.objectToMap(param);
			resMap = jsonApiCallService.call(HttpMethod.POST, getVocHeader()
					, SmartConfig.getString("voc.api.url.get." + System.getProperty("spring.profiles.active")) + apiName
					, reqMap);

			if(!resMap.findPath(VocConstants._VOC_RESULT_STATUS_FIELD).asText().equals(VocConstants._VOC_RESULT_SUCCESS_CODE)){
				throw new Exception();
			}

			return resMap;
			
		} catch (LotteException le) {
			throw le;
		} catch (Exception e) {
			throw new LotteException(LotteErrors.API_PROCESS_ERROR, e);
		}
	}
	
	public JsonNode fileUpload(String apiName, Object param, String mobUID, String FileName, String filePath) throws LotteException {
		MultiValueMap<String, Object> 	reqMap 		= null;
		JsonNode						resMap 		= null;
		
		try {
			
			logger.info("filePath : {}", filePath);
			byte[] bytes = null;
			if(!StringUtils.isEmpty(mobUID)) {
				bytes = uploadStorageAccessor.load(mobUID);
			} else {
				bytes = Files.readAllBytes(new File(filePath).toPath());
			}
			
//			MultipartFile multipartFile = new MockMultipartFile(FileName, bytes);
			ByteArrayResource resource = new ByteArrayResource(bytes) {
				@Override
				public String getFilename() {
					return FileName;
				}
			};
			
			reqMap = ConvertUtil.objectToMultiValueMap(param);
			reqMap.add("uploadFile", resource);
			
			resMap = multiPartCallService.call(HttpMethod.POST, getVocFileHeader()
					, SmartConfig.getString("voc.api.url.get." + System.getProperty("spring.profiles.active")) + apiName
					, reqMap);

			if(!resMap.findPath(VocConstants._VOC_RESULT_STATUS_FIELD).asText().equals(VocConstants._VOC_RESULT_SUCCESS_CODE)){
				throw new Exception();
			}

			return resMap;
			
		} catch (LotteException le) {
			logger.error(le.getMessage());
			throw le;
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new LotteException(LotteErrors.API_PROCESS_ERROR, e);
		}
	}
	
	public JsonNode fileDelete(String apiName, Object param) throws LotteException {
		Map<String, Object> reqMap = null;
		JsonNode			resMap = null;
		
		try {
			
			reqMap = ConvertUtil.objectToMap(param);
			resMap = multiPartCallService.call(HttpMethod.POST, getVocFileHeader()
					, SmartConfig.getString("voc.api.url.get." + System.getProperty("spring.profiles.active")) + apiName
					, reqMap);

			if(!resMap.findPath(VocConstants._VOC_RESULT_STATUS_FIELD).asText().equals(VocConstants._VOC_RESULT_SUCCESS_CODE)){
				throw new Exception();
			}

			return resMap;
			
		} catch (LotteException le) {
			throw le;
		} catch (Exception e) {
			throw new LotteException(LotteErrors.API_PROCESS_ERROR, e);
		}
	}
	
	public JsonNode getFile(String apiName, Object param) throws LotteException {
		Map<String, Object> reqMap = null;
		JsonNode			resMap = null;
		
		try {
			
			reqMap = ConvertUtil.objectToMap(param);
			resMap = multiPartCallService.call(HttpMethod.POST, getVocFileHeader()
					, SmartConfig.getString("voc.api.url.get." + System.getProperty("spring.profiles.active")) + apiName
					, reqMap);

			if(!resMap.findPath(VocConstants._VOC_RESULT_STATUS_FIELD).asText().equals(VocConstants._VOC_RESULT_SUCCESS_CODE)){
				throw new Exception();
			}

			return resMap;
			
		} catch (LotteException le) {
			throw le;
		} catch (Exception e) {
			throw new LotteException(LotteErrors.API_PROCESS_ERROR, e);
		}
	}
	
}
