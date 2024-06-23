package com.lottewellfood.sfa.common.vo;

import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class PushRequestVo {

	private String 			trxType;			/* 푸쉬 트랜잭션 타입 INSTANT, SCHEDULE */
	private String			scheduleDate;		/* SCHEDULE 인 경우, 발송 시간 */
	private String 			appName;			/* 앱 이름 */
	private List<String> 	toUsers;			/* 수신자 ID 리스트 */
	private boolean 		toAll;				/* 전체 발송 여부 */
	private String 			fromUser;			/* 발송 주체 */
	private String 			messageSubject;		/* 푸쉬 메시지 제목 */
	private String 			messageCategory;	/* 푸쉬 카테고리 */
	private String 			messageContent;		/* 푸쉬 컨텐츠 */
	private JsonNode		messagePayload;		/* 푸쉬 페이로드(클라이언트 전달 데이터) */
	private String			messageType;		/* 푸쉬 타입 DEF01 고정*/
	private String 			menuUri;			/* 푸쉬 연결 화면 ID */
	
	private String			fromUserIp;			/* 발송 시스템 IP */
	
	
	public String getFromUserIp() {
		return fromUserIp;
	}
	public void setFromUserIp(String fromUserIp) {
		this.fromUserIp = fromUserIp;
	}
	public String getMenuUri() {
		return menuUri;
	}
	public void setMenuUri(String menuUri) {
		this.menuUri = menuUri;
	}
	public String getTrxType() {
		return trxType;
	}
	public void setTrxType(String trxType) {
		this.trxType = trxType;
	}
	public String getScheduleDate() {
		return scheduleDate;
	}
	public void setScheduleDate(String scheduleDate) {
		this.scheduleDate = scheduleDate;
	}
	public String getAppName() {
		return appName;
	}
	public void setAppName(String appName) {
		this.appName = appName;
	}
	public List<String> getToUsers() {
		return toUsers;
	}
	public void setToUsers(List<String> toUsers) {
		this.toUsers = toUsers;
	}
	public boolean isToAll() {
		return toAll;
	}
	public void setToAll(boolean toAll) {
		this.toAll = toAll;
	}
	public String getFromUser() {
		return fromUser;
	}
	public void setFromUser(String fromUser) {
		this.fromUser = fromUser;
	}
	public String getMessageSubject() {
		return messageSubject;
	}
	public void setMessageSubject(String messageSubject) {
		this.messageSubject = messageSubject;
	}
	public String getMessageCategory() {
		return messageCategory;
	}
	public void setMessageCategory(String messageCategory) {
		this.messageCategory = messageCategory;
	}
	public String getMessageContent() {
		return messageContent;
	}
	public void setMessageContent(String messageContent) {
		this.messageContent = messageContent;
	}
	public JsonNode getMessagePayload() {
		return messagePayload;
	}
	public void setMessagePayload(JsonNode messagePayload) {
		this.messagePayload = messagePayload;
	}
	public String getMessageType() {
		return messageType;
	}
	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}
	
	@Override
	public String toString() {
		return "PushRequestDto [trxType=" + trxType + ", appName=" + appName + ", toUsers=" + toUsers + ", toAll="
				+ toAll + ", fromUser=" + fromUser + ", messageSubject=" + messageSubject + ", messageCategory="
				+ messageCategory + ", messageContent=" + messageContent + ", messagePayload=" + messagePayload
				+ ", messageType=" + messageType + ", menuUri=" + menuUri + ", fromUserIp=" + fromUserIp + "]";
	}
	//고정값 설정
	public void initPushRequest() {
		this.trxType 			= "INSTANT";
		this.messageCategory	= "def";
		this.toAll 				= false;
	}
	//custom
	public void setMessagePayloadByMenuUri() {
		ObjectMapper objectMapper = new ObjectMapper();
		this.messagePayload = objectMapper.createObjectNode().put("menuCode", this.menuUri);
	}
}
