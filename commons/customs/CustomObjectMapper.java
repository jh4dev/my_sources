package com.lottewellfood.sfa.common.customs;

import java.math.BigDecimal;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

public class CustomObjectMapper extends ObjectMapper {

	private static final long serialVersionUID = -6505265486906028025L;

	/**
	 * @method create
	 * @description 커스텀 ObjectMapper create
	 * 	<br/>	1) DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES
	 *  <br/> 	- 역직렬화시, 선언되지 않은 속성이 있는경우 에러 발생하지 않도록 설정
	 *  <br/>	
	 *  <br/>	2) BigDecimalSerializer
	 *  <br/>	- 직렬화 시, BigDecimal to String 되도록 설정
	 *  
	 * */
	public static ObjectMapper create() {
		CustomObjectMapper objectMapper = new CustomObjectMapper();
		
		/* ObjectMapper에 필요한 설정 아래 적용 */
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        
        /* 직렬화 모듈 추가 Numeric to String */
        SimpleModule module = new SimpleModule();
        module.addSerializer(BigDecimal.class, new BigDecimalSerializer());
        module.addDeserializer(String.class, new StringDeserializer());
        
        objectMapper.registerModule(module);
        
        return objectMapper;
    }
}
