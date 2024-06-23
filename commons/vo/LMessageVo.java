package com.lottewellfood.sfa.common.vo;

import java.time.LocalDateTime;
import java.util.Random;

import com.lottewellfood.sfa.common.util.LotteUtil;

public class LMessageVo {

	private String empId;
	/* TEMPLATE KEY - 템플릿 코드 조회하기 위한 작업 */
	private String templateKey;
	
	/* PK */
	private String cmid;
	
	/**
	 * MSG_TYPE
	 * [6] 카카오톡 알림톡
	 * [0] SMS
	 * */
	private String msgType;
	
	/* 수신자 전화번호 */
	private String destPhone;
	
	/* 수신자 이름 */
	private String destName;
	
	/* 발신자 전화번호 */
	private String sendPhone;
	
	/* 발신자 이름 */
	private String sendName;
	
	/* 메시지 내용 */
	private String msgBody;
	
	/* 메시지 템플릿 코드*/
	private String templateCode;

	/* 국가코드 */
	private String nationCode;
	
	/* 발신 KEY */
	private String senderKey;
	
	/* 대체 발송 타입 */
	private String reType;
	
	

	public void makeCmid() {

		this.cmid = this.templateKey
				+ LotteUtil.getLocalDateTimeString(LocalDateTime.now(), "yyMMddHHmmssSSS")
				+ new Random().nextInt((int) Math.pow(10, 5));
	}


	public String getEmpId() {
		return empId;
	}


	public void setEmpId(String empId) {
		this.empId = empId;
	}


	public String getTemplateKey() {
		return templateKey;
	}


	public void setTemplateKey(String templateKey) {
		this.templateKey = templateKey;
		makeCmid();
	}


	public String getCmid() {
		return cmid;
	}


	public void setCmid(String cmid) {
		this.cmid = cmid;
	}


	public String getMsgType() {
		return msgType;
	}


	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}


	public String getDestPhone() {
		return destPhone;
	}


	public void setDestPhone(String destPhone) {
		this.destPhone = destPhone;
	}


	public String getDestName() {
		return destName;
	}


	public void setDestName(String destName) {
		this.destName = destName;
	}


	public String getSendPhone() {
		return sendPhone;
	}


	public void setSendPhone(String sendPhone) {
		this.sendPhone = sendPhone;
	}


	public String getSendName() {
		return sendName;
	}


	public void setSendName(String sendName) {
		this.sendName = sendName;
	}


	public String getMsgBody() {
		return msgBody;
	}


	public void setMsgBody(String msgBody) {
		this.msgBody = msgBody;
	}


	public String getTemplateCode() {
		return templateCode;
	}


	public void setTemplateCode(String templateCode) {
		this.templateCode = templateCode;
	}


	public String getNationCode() {
		return nationCode;
	}


	public void setNationCode(String nationCode) {
		this.nationCode = nationCode;
	}


	public String getSenderKey() {
		return senderKey;
	}


	public void setSenderKey(String senderKey) {
		this.senderKey = senderKey;
	}

	public String getReType() {
		return reType;
	}

	public void setReType(String reType) {
		this.reType = reType;
	}


	@Override
	public String toString() {
		return "LMessageVo [empId=" + empId + ", templateKey=" + templateKey + ", cmid=" + cmid + ", msgType=" + msgType
				+ ", destPhone=" + destPhone + ", destName=" + destName + ", sendPhone=" + sendPhone + ", sendName="
				+ sendName + ", msgBody=" + msgBody + ", templateCode=" + templateCode + ", nationCode=" + nationCode
				+ ", senderKey=" + senderKey + "]";
	}

	
}
