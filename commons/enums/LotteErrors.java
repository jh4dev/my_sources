package com.lottewellfood.sfa.common.enums;

public enum LotteErrors {
	
	NO_SESSION_DATA				("ERR000")
	
	, BUSINESS_ERROR			("9000")
	, DB_SELECT_ERROR			("9001")
	, DB_INSERT_ERROR			("9002")
	, DB_UPDATE_ERROR			("9003")
	, DB_DELETE_ERROR			("9004")
	, DB_PROCESS_ERROR			("9005")
	, CONVERT_ERROR				("9006")
	, REQUEST_DATA_ERROR		("9007")
	, DUPLICATED_DATA_ERROR		("9008")
	, NO_SIGN_DATA_ERROR		("9009")
	, ABNORMAL_ERROR			("9010")
	
	, DEVICE_ID_ERROR			("9011")
	, MENU_PERMISSION_ERROR		("9012")
	, NO_DATA_EMP_BSC_INFO		("9013")
	, NO_LIST_PENDING_APPROVAL	("9014")
	, AD_PASSWORD_HAS_EXPIRED	("9015")
	, AD_CERTIFY_ERROR			("9016")
	
	, API_PROCESS_ERROR			("9101")
	, API_HTTP_STATUS_ERROR		("9102")
	, API_DEFINED_ERROR			("9103")
	, API_UNDEFINED_ERROR		("9104")
	
	, SAP_PROCESS_ERROR			("9201")
	, SAP_DEFINED_ERROR			("9202")
	, SAP_UNDEFINED_ERROR		("9203")
	
	, FILE_NOT_FOUND_ERROR		("9301")
	, FILE_NO_EXT_ERROR			("9302")
	
	, NO_EXIST_WORKING_TIME_ERROR("9401")
	, OFF_WORKING_ERROR			("9402")
	, NO_WORKING_TIME_ERROR		("9403")
	;
	
	private String errorCode;
	
	private LotteErrors(String errorCode) {
		this.errorCode = errorCode;
	}
	
	public String getErrorCode() {
		return this.errorCode;
	}
}
