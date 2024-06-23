package com.lottewellfood.sfa.common.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lottewellfood.sfa.common.code.SAPConstants;
import com.lottewellfood.sfa.common.customs.CustomObjectMapper;
import com.lottewellfood.sfa.common.enums.LotteErrors;
import com.lottewellfood.sfa.common.exception.LotteException;
import com.lottewellfood.sfa.model.header.LWFSHeader;
import com.mcnc.bizmob.db.type.DBMap;

/**
 * @description 롯데제과 SFA Converting Utility
 * </br> JSON(with Jackson) and JAVA Collection
 * @update 2023-03-13
 * @author jhahn
 * */
public class ConvertUtil {
	
	private ConvertUtil() {
		
	}

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
			throw new LotteException(LotteErrors.CONVERT_ERROR, e);
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
			throw new LotteException(LotteErrors.CONVERT_ERROR, e);
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
			throw new LotteException(LotteErrors.CONVERT_ERROR, e);
		}
	}
	
	/**
	 * @method jsonNodeToObject
	 * @description JsonNode to POJO
	 * @throws LotteException 
	 * */
	public static <T> T jsonNodeToObject(JsonNode jsonNode, Class<T> clazz) throws LotteException {
		try {
			if(jsonNode == null || jsonNode.isNull() || jsonNode.isMissingNode()) {return clazz.getDeclaredConstructor().newInstance();}
			return objectMapper.convertValue(jsonNode, clazz);
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new LotteException(LotteErrors.CONVERT_ERROR, e);
		}
	}
	

	/**
	 * @method makeJsonNodeToArrayList
	 * @description JsonNode to ArrayList<T>
	 * @throws LotteException 
	 * */
	public static <T> List<T> jsonNodeToArrayList(JsonNode jn, Class<T> clazz) throws LotteException {
		try {
			
			if(jn == null || jn.isNull() || jn.isMissingNode()) {return new ArrayList<T>();}
			
			JavaType type = objectMapper.getTypeFactory().
					constructCollectionType(List.class, clazz);
			
			ArrayList<T> list = objectMapper.readValue(jn.toString(),  type);
			return list;
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new LotteException(LotteErrors.CONVERT_ERROR, e);
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
			throw new LotteException(LotteErrors.CONVERT_ERROR, e);
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
			throw new LotteException(LotteErrors.CONVERT_ERROR, e);
		}
	}
	
	/**************************** 필요에 따라 추가하는 메소드는 아래 기재 *********************************/
	
	/**
	 * @method objectToMultiValueMap
	 * @description Pojo to MultiValueMap
	 * @throws LotteException 
	 * */
	public static MultiValueMap<String, Object> objectToMultiValueMap(Object obj) throws LotteException {
		MultiValueMap<String, Object> valueMap = new LinkedMultiValueMap<>();
		try {
			Map<String, Object> map = objectToMap(obj);
			valueMap.setAll(map);
			return valueMap;
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new LotteException(LotteErrors.CONVERT_ERROR, e);
		}
	}

	/**
	 * @method mergeJsonNodes
	 * @description merge JsonNodes
	 * @throws LotteException
	 * */
	public static JsonNode mergeJsonNodes(JsonNode node1, JsonNode node2) throws LotteException {
		ObjectNode mergedNode = new ObjectMapper().createObjectNode();
		try{
			if (node1.isArray() && node2.isArray()) {
				// 두 노드가 모두 배열인 경우 배열을 합침
				ArrayNode mergedArray = new ObjectMapper().createArrayNode();
				mergedArray.addAll((ArrayNode) node1);
				mergedArray.addAll((ArrayNode) node2);
				return mergedArray;
			} else if (node1.isObject() && node2.isObject()) {
				// 두 노드가 모두 객체인 경우 객체를 합침
				ObjectNode mergedObject = new ObjectMapper().createObjectNode();
				mergedObject.setAll((ObjectNode) node1);
				mergedObject.setAll((ObjectNode) node2);
				return mergedObject;
			} else {
				// 다른 경우는 그냥 node2 반환
				return node2;
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new LotteException(LotteErrors.CONVERT_ERROR, e);
		}
	}

	/**
	 * @method convertToMultiValueMap
	 * @description merge JsonNodes
	 * @throws LotteException
	 * */
	public static MultiValueMap<String, String> convertToMultiValueMap(Map<String, Object> map) throws LotteException {
		try{
			MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();
			map.forEach((key, value) -> multiValueMap.add(key, String.valueOf(value)));
			return multiValueMap;
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new LotteException(LotteErrors.CONVERT_ERROR, e);
		}
	}
}
