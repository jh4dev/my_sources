package com.lottewellfood.sfa.common.enums;

public enum AppType {
	
	APP_TYPE_SLR_TAB	("LWSAANP0", "영업사원 태블릿", "SFA_TABLET")
	, APP_TYPE_SLR_HTT	("LWHTANP2", "영업사원 핸드터미널", "SFA_MOBILE")
	, APP_TYPE_PPN_MOB_AND	("LWPNANP3", "피플네트웍스 모바일 안드로이드", "PPN_MOBILE")
	, APP_TYPE_PPN_MOB_IOS	("LWPNIOP0", "피플네트웍스 모바일 iOS", "PPN_MOBILE");
	
	private String appName;
	private String appKey;
	private String menuAppType;
	
	private AppType(String appKey, String appName, String menuAppType) {
		this.appKey	 = appKey;
		this.appName = appName;
		this.menuAppType = menuAppType;
	}
	public String getAppName() {
		return appName;
	}
	public String getAppKey() {
		return appKey;
	}
	public String getMenuAppType() {
		return menuAppType;
	}
	
}