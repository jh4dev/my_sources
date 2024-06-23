package com.lottewellfood.sfa.common.vo;

public class PushResponseVo {

	private String				messageType;
	private boolean 			result;
	private String 				resultCode;
	private String 				resultMessage;
	private ResponseBody 		body;
	
	
	public String getMessageType() {
		return messageType;
	}
	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}
	public boolean isResult() {
		return result;
	}
	public void setResult(boolean result) {
		this.result = result;
	}
	public String getResultCode() {
		return resultCode;
	}
	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}
	public String getResultMessage() {
		return resultMessage;
	}
	public void setResultMessage(String resultMessage) {
		this.resultMessage = resultMessage;
	}
	public ResponseBody getBody() {
		return body;
	}
	public void setBody(ResponseBody body) {
		this.body = body;
	}

	
	public class ResponseBody {
		
		private String trxId;
		private String trxDate;
		
		public String getTrxId() {
			return trxId;
		}
		public void setTrxId(String trxId) {
			this.trxId = trxId;
		}
		public String getTrxDate() {
			return trxDate;
		}
		public void setTrxDate(String trxDate) {
			this.trxDate = trxDate;
		}
		@Override
		public String toString() {
			return "ResponseBody [trxId=" + trxId + ", trxDate=" + trxDate + "]";
		}
	}
}
