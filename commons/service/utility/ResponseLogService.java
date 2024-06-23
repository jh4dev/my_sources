package com.lottewellfood.sfa.common.service.utility;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.lottewellfood.sfa.common.code.DBConstants;
import com.lottewellfood.sfa.common.code.LotteConstants;
import com.lottewellfood.sfa.common.exception.LotteException;
import com.lottewellfood.sfa.common.util.LotteUtil;
import com.mcnc.bizmob.adapter.DBAdapter;
import com.mcnc.bizmob.common.config.SmartConfig;
import com.mcnc.bizmob.common.util.JsonUtil;
import com.mcnc.bizmob.hybrid.common.json.SimpleJsonResponse;
import com.mcnc.bizmob.hybrid.common.server.JsonAdaptorObject;
import com.mcnc.bizmob.hybrid.common.server.JsonAdaptorObject.TYPE;

@Component
public class ResponseLogService {
	
	private Logger logger = LoggerFactory.getLogger(ResponseLogService.class);
	
	@Autowired
	protected DBAdapter dbAdapter;
	
	@Autowired
	protected ObjectMapper objectMapper;
	
	@Autowired
	MessageSourceAccessor sessionManagementSourceAccessor;
	
	/**
	 * Adapter 처리 성공 Response & 로그 출력
	 * 
	 * @param obj
	 * @param resNode
	 * @param trCode
	 * @param startAdapter
	 * 
	 * @return JsonAdaptorObject
	 */
	public JsonAdaptorObject makeResponse(JsonAdaptorObject obj, JsonNode resNode, String trCode,
			long startAdapter) {
		if (!resNode.path("header").path("result").booleanValue()) {
			((ObjectNode) resNode.path("header")).put("result", true);
		}
		obj.put(TYPE.RESPONSE, resNode);
		obj.put(TYPE.RESULT, SimpleJsonResponse.getSimpleSuccessResponse().toJson());

		String reqJson = JsonUtil.toJson(obj.get(TYPE.REQUEST), false);
		long endAdapter = System.currentTimeMillis();
		
		String resJson = resNode.toString().length() > LotteConstants._LOG_MAXIMUM_SIZE ? resNode.toString().substring(0, LotteConstants._LOG_MAXIMUM_SIZE) : resNode.toString();
		
		StringBuffer strBf = new StringBuffer();
		strBf.append(":: Adapter Process Success !!").append("\n##############################################")
			.append("\n# APP TYPE : ").append(getAppType(obj, trCode))
			.append("\n# TRCODE : ").append(trCode)
			.append("\n# TRNAME : ").append(SmartConfig.getString(trCode))
			.append("\n# REQUEST : ").append(reqJson);
		if(logger.isDebugEnabled()) {
			strBf.append("\n# RESPONSE : ").append(resJson);
		}
			strBf.append("\n# TIME : ").append(endAdapter - startAdapter).append("ms")
			.append("\n##############################################");
		logger.info(strBf.toString());

		return obj;
	}

	/**
	 * Adapter 처리 실패 Response & 로그 출력
	 * 
	 * @param obj
	 * @param errorCode
	 * @param errorMessage
	 * @param trCode
	 * @param startAdapter
	 */
	public JsonAdaptorObject makeFailResponse(JsonAdaptorObject obj, LotteException le, String trCode, long startAdapter) {
		
		if(StringUtils.isEmpty(le.getErrorMessage())) le = setErrorMessageFromCamo(le);
		
		JsonAdaptorObject fail = new JsonAdaptorObject();
		
		fail.put(TYPE.RESULT, new SimpleJsonResponse(false, (le.getErrorCode().contains("ERR000") ? "" : trCode) + le.getErrorCode(), le.getErrorMessage(), null).toJson());

		String reqJson = JsonUtil.toJson(obj.get(TYPE.REQUEST), false);
		long endAdapter = System.currentTimeMillis();

		
		StringBuffer strBf = new StringBuffer();
		strBf.append(":: Adapter Process Fail !!").append("\n##############################################")
			.append("\n# APP TYPE : ").append(getAppType(obj, trCode))
			.append("\n# TRCODE : ").append(trCode)
			.append("\n# TRNAME : ").append(SmartConfig.getString(trCode))
			.append("\n# REQUEST : ").append(reqJson)
			.append("\n# TIME : ").append(endAdapter - startAdapter).append("ms")
			.append("\n# ERROR : [").append(trCode).append(le.getErrorCode()).append("] ")
			.append(le.getErrorMessage());
		if(logger.isDebugEnabled()) {
			strBf.append("\n# Exception : [").append(le.getMessage());
		}
		
		strBf.append("\n##############################################");
		logger.info(strBf.toString());

		return fail;
	}
	
	private LotteException setErrorMessageFromCamo(LotteException le) {
		String errorMessage = dbAdapter.selectOne(
				DBConstants.SFADS
				, DBConstants._MAPPER_NS_COMMON_CODE + DBConstants._SELECT_ERROR_MESSAGE
				, le.getErrorCode()
				, String.class);
		if(le.getErrorMessageParams() != null) {
			for(int i = 0; i < le.getErrorMessageParams().length; i++) {
				String replaceIdx = "$" + (i + 1);
				String replaceStr = le.getErrorMessageParams()[i];			
				errorMessage = errorMessage.replace(replaceIdx, replaceStr);
			}
			
			le.setErrorMessage(errorMessage);
		}
		
		return le;
	}
	
	private String getAppType(JsonAdaptorObject obj, String trCode) {
		try {
			String checkOption = sessionManagementSourceAccessor.getMessage(trCode, this.sessionManagementSourceAccessor.getMessage("default"));
			if(StringUtils.equalsIgnoreCase("on", checkOption)) {
				return LotteUtil.getSessionUserVo(obj).getAppType().toString();
			}
			
		} catch (LotteException e) {
			return "NO DATA";
		}
		return "NO DATA";
	}
}
