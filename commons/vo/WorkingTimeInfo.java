package com.lottewellfood.sfa.common.vo;

import java.io.Serializable;

public class WorkingTimeInfo implements Serializable{
	
	
	private static final long serialVersionUID = 5643314747629029997L;
	
	private String startDateStr;
	private String endDateStr;
	private String startTimeStr;
	private String endTimeStr;
	
	public String getStartDateStr() {
		return startDateStr;
	}
	public void setStartDateStr(String startDateStr) {
		this.startDateStr = startDateStr;
	}
	public String getEndDateStr() {
		return endDateStr;
	}
	public void setEndDateStr(String endDateStr) {
		this.endDateStr = endDateStr;
	}
	public String getStartTimeStr() {
		return startTimeStr;
	}
	public void setStartTimeStr(String startTimeStr) {
		this.startTimeStr = startTimeStr;
	}
	public String getEndTimeStr() {
		return endTimeStr;
	}
	public void setEndTimeStr(String endTimeStr) {
		this.endTimeStr = endTimeStr;
	}
	
}
