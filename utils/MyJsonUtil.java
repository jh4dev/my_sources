package my.util.sources;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mcnc.bizmob.common.util.JsonUtil;
import com.mcnc.bizmob.db.type.DBMap;
import com.nongshim.next.nssm.common.code.CodesEx;
import com.nongshim.next.nssm.common.exception.NSException;
import com.nongshim.next.nssm.common.util.CustomObjectMapper;

public class MyJsonUtil {

	private static ObjectMapper objectMapper = CustomObjectMapper.create();
	
	/**
	 * @method makeJsonNodeToArrayList
	 * @description JsonNode to ArrayList<T>
	 * */
	public static <T> List<T> makeJsonNodeToArrayList(Class<T> clazz, JsonNode jn) throws Exception {
		
		JavaType type = objectMapper.getTypeFactory().
				  constructCollectionType(List.class, clazz);
		
		ArrayList<T> list = objectMapper.readValue(jn.toString(),  type);
		
		return list;
	}
	
	/***
	 * @method jsonToMap
	 * @description Json String to Map
	 */
	public static Map<String, Object> jsonToMap(String jsonString) {
	    Map<String, Object> result = new HashMap<>();
	    try {
	        JsonNode jsonNode = objectMapper.readTree(jsonString);
	        Iterator<Map.Entry<String, JsonNode>> fields = jsonNode.fields();
	        while (fields.hasNext()) {
	            Map.Entry<String, JsonNode> field = fields.next();
	            String key = field.getKey();
	            JsonNode value = field.getValue();
	            if (value.isObject()) {
	                result.put(key, jsonToMap(value.toString()));
	            } else if (value.isArray()) {
	                result.put(key, jsonArrayToList(value));
	            } else {
	                if (value.isTextual()) {
	                    result.put(key, value.textValue());
	                } else if (value.isNumber()) {
	                    result.put(key, value.numberValue());
	                } else if (value.isBoolean()) {
	                    result.put(key, value.booleanValue());
	                }
	            }
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return result;
	}

	public static List<Object> jsonArrayToList(JsonNode jsonArray) {
	    List<Object> list = new ArrayList<>();
	    for (JsonNode jsonNode : jsonArray) {
	        if (jsonNode.isObject()) {
	            list.add(jsonToMap(jsonNode.toString()));
	        } else if (jsonNode.isArray()) {
	            list.add(jsonArrayToList(jsonNode));
	        } else {
	            if (jsonNode.isTextual()) {
	                list.add(jsonNode.textValue());
	            } else if (jsonNode.isNumber()) {
	                list.add(jsonNode.numberValue());
	            } else if (jsonNode.isBoolean()) {
	                list.add(jsonNode.booleanValue());
	            }
	        }
	    }
	    return list;
	}
	
	
	public static <T> Map<String, Object> convertPojoToMap(T pojo) throws IllegalAccessException {
	    Map<String, Object> map = new HashMap<>();
	    Class<?> clazz = pojo.getClass();

	    for (Field field : clazz.getDeclaredFields()) {
	        field.setAccessible(true);
	        Object value = field.get(pojo);

	        if (value != null) {
	            if (field.getType().isAssignableFrom(List.class)) {
	                List<Map<String, Object>> list = new ArrayList<>();
	                for (Object obj : (List<?>) value) {
	                    list.add(convertPojoToMap(obj));
	                }
	                map.put(field.getName(), list);
	            } else if (field.getType().isAssignableFrom(Map.class)) {
	                Map<String, Object> nestedMap = new HashMap<>();
	                for (Map.Entry<?, ?> entry : ((Map<?, ?>) value).entrySet()) {
	                    nestedMap.put(entry.getKey().toString(), entry.getValue());
	                }
	                map.put(field.getName(), nestedMap);
	            } else {
	                map.put(field.getName(), value);
	            }
	        }
	    }
	    return map;
	}

	
	public static Map<String, Object> convertObjectToMap(Object object) throws NSException  {

		try {
			String objStr = JsonUtil.toJson(object);
			Map<String, Object> map = JsonUtil.toObject(objStr, DBMap.class);

			return map;
		} catch (Exception e) {
			throw new NSException(CodesEx.SYSTEM_EXCEPTION, "데이터 변환 중 오류 발생");
		}
		
	}
}
