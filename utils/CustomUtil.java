package com.lottewellfood.sfa.common.util;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.apache.commons.lang.StringUtils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.lottewellfood.sfa.common.exception.LotteException;
import com.lottewellfood.sfa.common.vo.SessionUserVo;
import com.mcnc.bizmob.common.util.JsonUtil;
import com.mcnc.bizmob.hybrid.common.server.JsonAdaptorObject;
import com.mcnc.bizmob.hybrid.common.server.JsonAdaptorObject.TYPE;

/**
 * @description 롯데제과 SFA Common Utility
 * @update 2023-03-13
 * @author jhahn
 * */
public class LotteUtil {

	/**
     * 세션에 저장되어 있는 TRANSACTION_ID를 얻는다.
     * @param obj Biz Logic에서 Adapter로 전달한 객체
     * @return TRANSACTION_ID 값. 만약, 세션 data가 없거나, TRANSACTION_ID가 없으면 0
     */
    public static long getTransactionId(JsonAdaptorObject obj) {
        long id = 0;
        
        if(obj.containsKey(TYPE.META)) {
            JsonNode node = (JsonNode) obj.get(TYPE.META);
            
            try {
                id = (Long) JsonUtil.getValue(node, "TRANSACTION_ID");
            } catch(Exception e) {
                id = 0;
            }
        }
        
        return id;
    }
    
	/**
	 * @method pusSessionAttribute
	 * @description bizMOB session META 데이터 셋팅
	 * */
	public static void putSessionAttribute(JsonAdaptorObject obj, String key, Object value) {
		ObjectNode sessionNode =(ObjectNode) obj.get(JsonAdaptorObject.TYPE.META);
		JsonUtil.putValue(sessionNode, key, value);
		obj.put(JsonAdaptorObject.TYPE.META, sessionNode);
	}
	
	/**
	 * @method getSessionAttribute
	 * @description bizMOB session META 데이터 추출
	 * */
	public static String getSessionAttribute(JsonAdaptorObject obj, String key) {
		ObjectNode sessionNode =(ObjectNode) obj.get(JsonAdaptorObject.TYPE.META);
		return sessionNode.path(key).asText();
	}
	
	public static SessionUserVo getSessionUserVo(JsonAdaptorObject obj) throws LotteException {
		try {
			JsonNode sessionUserNode =(ObjectNode) obj.get(JsonAdaptorObject.TYPE.META).findPath("sessionUserVo");
			return ConvertUtil.jsonNodeToObject(sessionUserNode, SessionUserVo.class);
		} catch (Exception e) {
			throw new LotteException("ERR000", "세션이 만료되었습니다. 다시 로그인 해주세요.");
		}
	}
	
	/**
	 * @method replaceStringWithParams
	 * @description 문자열 내, {variable} 형태의 string을 순서대로 치환 
	 * */
	public static String replaceStringWithParams(String str, String ...params) {
		for(int i = 0; i < params.length; i++) {
			String replaceIdx = "{" + i + "}";
			String replaceStr = params[i];			
			str = str.replace(replaceIdx, replaceStr);
		}
		return str;
	}
	
	/**
	 * @method toStringValue
	 * @description Object가 null인 경우 ""로 치환 
	 * </br> Object가 null이 아닌 경우 String으로 형변환
	 * */
	public static String toStringValue(Object obj) {        
		return obj == null ? "" : String.valueOf(obj);    
	}
	
	/**
	 * @method checkValue
	 * @description 문자열이 null 또는 비어있는 경우, 치환
	 * </br> Object가 null이 아닌 경우 String으로 형변환
	 * */
	public static String checkValue(String param, Object defaultValue) {
		if(StringUtils.isEmpty(param)) {
			if(defaultValue == null) {
				return null;
			} else {
				return String.valueOf(defaultValue);
			}
		}
		return param;
	}
	
	/**
	 * @method getDateString
	 * @description Date object를 sdf 형태의 문자열로 변환
	 * <br> JDK1.7이하
	 * */
	public static String getDateString(Date date, String sdf) {
		if(date == null) return null;
		return new SimpleDateFormat(sdf).format(date);
	}
	
	/**
	 * @method getLocalDateTimeString
	 * @description LocalDateTime object를 dtf 형태의 문자열로 변환
	 * </br> JDK1.8이상
	 * </br> yyyy MM dd HH(hh) mm ss SSS
	 * */
	public static String getLocalDateTimeString(LocalDateTime localDate, String dtf) {
		if(localDate == null) return null;
		return localDate.format(DateTimeFormatter.ofPattern(dtf));
	}
	
	/**
	 * @method removeDuplicateItemFromList
	 * @description 제네릭 클래스의 특정 키 값으로 리스트 중복 항목 제거
	 * */
	public static <T> List<T> removeDuplicationDataFromList(List<T> list, Function<? super T, ?> key) {
		return list.stream().filter(setFilter(key)).collect(Collectors.toList());
	}
	private static <T> Predicate<T> setFilter(Function<? super T, ?> key) {
		Set<Object> set = ConcurrentHashMap.newKeySet();
		return predicate -> set.add(key.apply(predicate));
	}
	
	/**
	 * @method removeDuplicateMapFromList
	 * @description Map 리스트에서 key, value가 같은 항목 제거
	 * */
	public static List<Map<String, Object>> removeDuplicateMapFromList(List<Map<String, Object>> mapList) {
		
		return mapList.stream().distinct().collect(Collectors.toList());
	}
}
