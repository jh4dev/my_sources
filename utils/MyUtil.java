package my.util.sources;

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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.mcnc.bizmob.common.util.JsonUtil;
import com.mcnc.bizmob.hybrid.common.server.JsonAdaptorObject;

public class MyUtil {
	
	private static Logger logger = LoggerFactory.getLogger(MyUtil.class);

	/**
	 * @method pusSessionAttribute
	 * @description bizMOB session META 데이터 셋팅
	 * */
	public static void putSessionAttribute(JsonAdaptorObject obj, String key, String value) {
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
	 * @method getDateString
	 * @description Date object를 sdf 형태의 문자열로 변환
	 * <br> JDK1.7이하
	 * */
	public static String getDateString(Date date, String sdf) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(sdf);
		return dateFormat.format(date);
	}
	
	/**
	 * @method getLocalDateTimeString
	 * @description LocalDateTime object를 dtf 형태의 문자열로 변환
	 * </br> JDK1.8이상
	 * </br> yyyy MM dd HH(hh) mm ss SSS
	 * */
	public static String getLocalDateTimeString(LocalDateTime localDate, String dtf) {
		
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(dtf);
		return localDate.format(dateTimeFormatter);
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
