package com.lottewellfood.sfa.common.service.utility;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lottewellfood.sfa.common.code.DBConstants;
import com.lottewellfood.sfa.common.code.LMessageConstants;
import com.lottewellfood.sfa.common.code.LotteConstants;
import com.lottewellfood.sfa.common.enums.LotteErrors;
import com.lottewellfood.sfa.common.exception.LotteException;
import com.lottewellfood.sfa.common.vo.LMessageVo;
import com.mcnc.bizmob.adapter.DBAdapter;
import com.mcnc.bizmob.common.config.SmartConfig;
import com.mcnc.bizmob.common.util.StringUtil;

@Service("lMessageService")
public class LMessageService {

	private static final Logger logger = LoggerFactory.getLogger(LMessageService.class);
	
	@Autowired
	private DBAdapter dbAdapter;
	
	
	/**
	 * @title sendLMessage
	 * @desc 알림톡 발송
	 * @caution 운영(prod) 시스템이 아닌 경우, 발송하지 않음
	 * 
	 * */
	public void sendLMessage(LMessageVo vo) throws LotteException {
		
		String env = System.getProperty("spring.profiles.active");
		try {
			//LMessage 개발계가 없으므로, 운영 환경이 아닌 경우 발송하지 않음
			if(!StringUtil.equalsIgnoreCase(LotteConstants._ENV_PROD, env)
				//특정 수신자 연락처인경우 발송
				&& !Arrays.asList(SmartConfig.getStringArray("alert.talk.force.receivers")).contains(vo.getDestPhone())) {
				
				logger.info("System Environment : {} >> LMessage 미발송", env);
				return;
			}
			
			//발송
			dbAdapter.insert(
					DBConstants.LMSGDS
					, DBConstants._MAPPER_NS_LMESSAGE + DBConstants._INSERT_SEND_LMESSAGE
					, vo);
			
		} catch(Exception e) {
			if (e instanceof LotteException) throw e;
			else throw new LotteException(LotteErrors.DB_INSERT_ERROR);
		}
	}
	

	public LMessageVo initLMessageVo(String templateKey, String...params) {
		
		LMessageVo vo = dbAdapter.selectOne(
				DBConstants.SFADS
				, DBConstants._MAPPER_NS_LMESSAGE + DBConstants._SELECT_SEND_LMESSAGE_VO
				, templateKey
				, LMessageVo.class);

		
		return vo;
	}
	
	public LMessageVo makeMessageBody(LMessageVo vo, String ... params) throws LotteException {
		
		try {
			String templateBody = vo.getMsgBody();
			
			int openIdx = 0;
			int closeIdx = 0;
			
			for(String param : params) {
				
				openIdx = templateBody.indexOf(LMessageConstants._LMESSAGE_PARAM_OPEN_STRING);
				closeIdx = templateBody.indexOf(LMessageConstants._LMESSAGE_PARAM_CLOSE_STRING) + 1;
				
				templateBody = templateBody.replace(templateBody.substring(openIdx, closeIdx), param);
			}
			
			vo.setMsgBody(templateBody);
			
			return vo;
			
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new LotteException();
		}
		
	}
	
	
}
