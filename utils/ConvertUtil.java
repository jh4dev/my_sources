package com.lottewellfood.sfa.common.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lottewellfood.sfa.common.code.SAPConstants;
import com.lottewellfood.sfa.common.customs.CustomObjectMapper;
import com.lottewellfood.sfa.common.enums.LotteErrors;
import com.lottewellfood.sfa.common.exception.LotteException;
import com.lottewellfood.sfa.model.header.LCSampleHeader;
import com.mcnc.bizmob.db.type.DBMap;

/**
 * @description 롯데제과 SFA Converting Utility
 * </br> JSON(with Jackson) and JAVA Collection
 * @update 2023-03-13
 * @author jhahn
 * */
public class ConvertUtil {

	private static ObjectMapper objectMapper = CustomObjectMapper.create();

	private static Logger logger = LoggerFactory.getLogger(LotteUtil.class);
	
	/**
	 * @method objectToJsonString
	 * @description Pojo to Json format String
	 * @throws LotteException 
	 * */
	public static String objectToJsonString(Object obj) throws LotteException {
		try {
			return objectMapper.writeValueAsString(obj);
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new LotteException(LotteErrors.CONVERT_ERROR);
		}
	}
	
	/**
	 * @method anyObjectToJsonNode
	 * @description Any Object To JsonNode
	 * @throws LotteException 
	 * */
	public static JsonNode objectToJsonNode(Object obj) throws LotteException {
		try {
			return objectMapper.valueToTree(obj);			
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new LotteException(LotteErrors.CONVERT_ERROR);
		}
	}
	
	/**
	 * @method jsonStringToObject
	 * @description jsonString to POJO
	 * @throws LotteException 
	 * */
	public static <T> T jsonStringToObject(String jsonStr, Class<T> clazz) throws LotteException {
		try {
			return (T)objectMapper.readValue(jsonStr, clazz);
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new LotteException(LotteErrors.CONVERT_ERROR);
		}
	}
	
	/**
	 * @method jsonNodeToObject
	 * @description JsonNode to POJO
	 * @throws LotteException 
	 * */
	public static <T> T jsonNodeToObject(JsonNode jsonNode, Class<T> clazz) throws LotteException {
		try {
			return objectMapper.convertValue(jsonNode, clazz);
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new LotteException(LotteErrors.CONVERT_ERROR);
		}
	}
	

	/**
	 * @method makeJsonNodeToArrayList
	 * @description JsonNode to ArrayList<T>
	 * @throws LotteException 
	 * */
	public static <T> List<T> jsonNodeToArrayList(JsonNode jn, Class<T> clazz) throws LotteException {
		try {
			JavaType type = objectMapper.getTypeFactory().
					constructCollectionType(List.class, clazz);
			
			ArrayList<T> list = objectMapper.readValue(jn.toString(),  type);
			return list;
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new LotteException(LotteErrors.CONVERT_ERROR);
		}
	}
	
	/**
	 * @method objectToMap
	 * @description Pojo to Map
	 * @throws LotteException 
	 * */
	public static DBMap objectToMap(Object obj) throws LotteException {
		try {
			return jsonStringToObject(objectToJsonString(obj), DBMap.class);
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new LotteException(LotteErrors.CONVERT_ERROR);
		}
	}
	
	/**
	 * @method mapToPojo
	 * @description Map to POJO
	 * @throws LotteException 
	 * */
	public static <T> T mapToPojo(Map<String, Object> map, Class<T> clazz) throws LotteException {
		try {
			 return objectMapper.convertValue(map, clazz);
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new LotteException(LotteErrors.CONVERT_ERROR);
		}
	}
	
	/**************************** 필요에 따라 추가하는 메소드는 아래 기재 *********************************/
	/**
	 * @method objectToMapWithHeader
	 * @description Pojo to Map with HeaderInfo (동일변수명이 있을 경우, POJO 값 우선)
	 * @throws LotteException 
	 * */
	public static Map<String, Object> objectToMapWithHeaderForSap(Object obj, LCSampleHeader header) throws LotteException {
		try {
			Map<String, Object> objMap = jsonStringToObject(objectToJsonString(obj), DBMap.class);
			//Request Object에 이미 IV_ZPERNR 필드가 없다면, Header의 userId PUT
			if(!objMap.containsKey(SAPConstants._COMMON_IMPORT_VALUE_ZPERNR)) {
				objMap.put(SAPConstants._COMMON_IMPORT_VALUE_ZPERNR, header.getUserId());
			}
			
			return objMap;
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new LotteException(LotteErrors.CONVERT_ERROR);
		}
	}
}
