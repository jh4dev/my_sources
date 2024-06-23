package com.lottewellfood.sfa.common.vo;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.lottewellfood.sfa.common.enums.AppType;

public class SessionUserVo implements Serializable{

	private static final long serialVersionUID = -6998750757939363876L;
	
	private String 	userId;			//MOIN ID
	private String	empId;			//사번 (HR)
	private String	ZPERNR;			//SAP 영업사원 코드
	private AppType appType;		//앱 타입
	
	private String	VKBUR;			//SAP 부문 코드
	private String	VKGRP;			//SAP 지사/팀 코드
	private String 	ZZVKGRP2;		//SAP 지점/담당 코드
	private String	ZPOSITION;		//SAP 직무 코드
	private String	ZACT_TYPE;		//SAP 활동 유형 코드
	private String	ZDUTY;			//SAP 직무 유형 코드
	
	private boolean	isSuperUser;	//시스템관리자여부
	private boolean isHeadUser;		//임원 or 지원부서 소속 여부
	private String	headUserOpt;	

	private String	DEPT_CODE;		//부서 코드
	private int		empUseGrade;	//사용 권한
	
	private String 	dfStartTimeStr;		//업무 시작 시간 문자열
	private String 	dfEndTimeStr;		//업무 종료 시간 문자열
	
	private String	tempSchYn;		//임시근무일정여부
	private String	holidayYn;		//공휴일여부
	
	private List<WorkingTimeInfo> overtimeList;	//휴일,연장 근무 시간 목록
	private List<WorkingTimeInfo> workOffList;	//업무 불가 일자,시간 목록
	
	private String	appVersion;
	private String 	contentsVersion;
	private String	appKey;
	
	public SessionUserVo() {
		
	}
	
	public SessionUserVo(String userId, String appKey, boolean isSuperUser) {
		
		this.userId 		= userId;
		this.isSuperUser 	= isSuperUser;
		this.setAppTypeByAppKey(appKey);
	}

	public void setAppTypeByAppKey(String appKey) {
		appType = StringUtils.equalsIgnoreCase(appKey, AppType.APP_TYPE_SLR_TAB.getAppKey()) ? AppType.APP_TYPE_SLR_TAB 
				: StringUtils.equalsIgnoreCase(appKey, AppType.APP_TYPE_SLR_HTT.getAppKey()) ? AppType.APP_TYPE_SLR_HTT
				: StringUtils.equalsIgnoreCase(appKey, AppType.APP_TYPE_PPN_MOB_AND.getAppKey()) ? AppType.APP_TYPE_PPN_MOB_AND
				: StringUtils.equalsIgnoreCase(appKey, AppType.APP_TYPE_PPN_MOB_IOS.getAppKey()) ? AppType.APP_TYPE_PPN_MOB_IOS
				: null;
	}
	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getEmpId() {
		return empId;
	}

	public void setEmpId(String empId) {
		this.empId = empId;
	}

	@JsonProperty("ZPERNR")
	public String getZPERNR() {
		return ZPERNR;
	}

	public void setZPERNR(String zPERNR) {
		ZPERNR = zPERNR;
	}

	public AppType getAppType() {
		return appType;
	}

	public void setAppType(AppType appType) {
		this.appType = appType;
	}
	
	@JsonProperty("VKBUR")
	public String getVKBUR() {
		return VKBUR;
	}

	public void setVKBUR(String vKBUR) {
		VKBUR = vKBUR;
	}
	@JsonProperty("VKGRP")
	public String getVKGRP() {
		return VKGRP;
	}

	public void setVKGRP(String vKGRP) {
		VKGRP = vKGRP;
	}
	@JsonProperty("ZZVKGRP2")
	public String getZZVKGRP2() {
		return ZZVKGRP2;
	}

	public void setZZVKGRP2(String zZVKGRP2) {
		ZZVKGRP2 = zZVKGRP2;
	}
	@JsonProperty("ZPOSITION")
	public String getZPOSITION() {
		return ZPOSITION;
	}

	public void setZPOSITION(String zPOSITION) {
		ZPOSITION = zPOSITION;
	}
	@JsonProperty("ZACT_TYPE")
	public String getZACT_TYPE() {
		return ZACT_TYPE;
	}

	public void setZACT_TYPE(String zACT_TYPE) {
		ZACT_TYPE = zACT_TYPE;
	}
	
	public boolean isSuperUser() {
		return isSuperUser;
	}

	public void setSuperUser(boolean isSuperUser) {
		this.isSuperUser = isSuperUser;
	}
	
	public boolean isHeadUser() {
		return isHeadUser;
	}

	public void setHeadUser(boolean isHeadUser) {
		this.isHeadUser = isHeadUser;
	}
	
	public String getHeadUserOpt() {
		return headUserOpt;
	}

	public void setHeadUserOpt(String headUserOpt) {
		this.headUserOpt = headUserOpt;
	}

	@JsonProperty("DEPT_CODE")
	public String getDEPT_CODE() {
		return DEPT_CODE;
	}

	public void setDEPT_CODE(String DEPT_CODE) {
		this.DEPT_CODE = DEPT_CODE;
	}

	public String getZDUTY() {
		return ZDUTY;
	}
	@JsonProperty("ZDUTY")
	public void setZDUTY(String ZDUTY) {
		this.ZDUTY = ZDUTY;
	}

	public int getEmpUseGrade() {
		return empUseGrade;
	}

	public void setEmpUseGrade(int empUseGrade) {
		this.empUseGrade = empUseGrade;
	}

	public List<WorkingTimeInfo> getOvertimeList() {
		return overtimeList;
	}

	public void setOvertimeList(List<WorkingTimeInfo> overtimeList) {
		this.overtimeList = overtimeList;
	}

	public List<WorkingTimeInfo> getWorkOffList() {
		return workOffList;
	}

	public void setWorkOffList(List<WorkingTimeInfo> workOffList) {
		this.workOffList = workOffList;
	}
	
	public String getTempSchYn() {
		return tempSchYn;
	}

	public void setTempSchYn(String tempSchYn) {
		this.tempSchYn = tempSchYn;
	}

	public String getHolidayYn() {
		return holidayYn;
	}

	public void setHolidayYn(String holidayYn) {
		this.holidayYn = holidayYn;
	}
	public String getDfStartTimeStr() {
		return dfStartTimeStr;
	}
	public void setDfStartTimeStr(String dfStartTimeStr) {
		this.dfStartTimeStr = dfStartTimeStr;
	}

	public String getDfEndTimeStr() {
		return dfEndTimeStr;
	}

	public void setDfEndTimeStr(String dfEndTimeStr) {
		this.dfEndTimeStr = dfEndTimeStr;
	}

	
	public String getAppVersion() {
		return appVersion;
	}

	public void setAppVersion(String appVersion) {
		this.appVersion = appVersion;
	}

	public String getContentsVersion() {
		return contentsVersion;
	}

	public void setContentsVersion(String contentsVersion) {
		this.contentsVersion = contentsVersion;
	}

	
	public String getAppKey() {
		return appKey;
	}

	public void setAppKey(String appKey) {
		this.appKey = appKey;
	}

	@Override
	public String toString() {
		return "SessionUserVo [userId=" + userId + ", empId=" + empId + ", ZPERNR=" + ZPERNR + ", appType=" + appType
				+ ", VKBUR=" + VKBUR + ", VKGRP=" + VKGRP + ", ZZVKGRP2=" + ZZVKGRP2 + ", ZPOSITION=" + ZPOSITION
				+ ", ZACT_TYPE=" + ZACT_TYPE + ", ZDUTY=" + ZDUTY + ", isSuperUser=" + isSuperUser + ", DEPT_CODE="
				+ DEPT_CODE + ", empUseGrade=" + empUseGrade + ", dfStartTimeStr=" + dfStartTimeStr + ", dfEndTimeStr="
				+ dfEndTimeStr + ", tempSchYn=" + tempSchYn + ", holidayYn=" + holidayYn + ", overtimeList="
				+ overtimeList + ", workOffList=" + workOffList + ", getUserId()=" + getUserId() + ", getEmpId()="
				+ getEmpId() + ", getZPERNR()=" + getZPERNR() + ", getAppType()=" + getAppType() + ", getVKBUR()="
				+ getVKBUR() + ", getVKGRP()=" + getVKGRP() + ", getZZVKGRP2()=" + getZZVKGRP2() + ", getZPOSITION()="
				+ getZPOSITION() + ", getZACT_TYPE()=" + getZACT_TYPE() + ", isSuperUser()=" + isSuperUser()
				+ ", getDEPT_CODE()=" + getDEPT_CODE() + ", getZDUTY()=" + getZDUTY() + ", getEmpUseGrade()="
				+ getEmpUseGrade() + ", getOvertimeList()=" + getOvertimeList() + ", getWorkOffList()="
				+ getWorkOffList() + ", getTempSchYn()=" + getTempSchYn() + ", getHolidayYn()=" + getHolidayYn()
				+ ", getDfStartTimeStr()=" + getDfStartTimeStr() + ", getDfEndTimeStr()=" + getDfEndTimeStr()
				+ ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()=" + super.toString()
				+ "]";
	}

	
}
