package com.lottewellfood.sfa.common.vo;

public class CommonCodeItemVo {

	private String codeItemId;
	private String codeItemDesc;
	private String codeItemVal;
	
	public String getCodeItemId() {
		return codeItemId;
	}
	public void setCodeItemId(String codeItemId) {
		this.codeItemId = codeItemId;
	}
	public String getCodeItemDesc() {
		return codeItemDesc;
	}
	public void setCodeItemDesc(String codeItemDesc) {
		this.codeItemDesc = codeItemDesc;
	}
	public String getCodeItemVal() {
		return codeItemVal;
	}
	public void setCodeItemVal(String codeItemVal) {
		this.codeItemVal = codeItemVal;
	}
	@Override
	public String toString() {
		return "CommonCodeItemVo [codeItemId=" + codeItemId + ", codeItemDesc=" + codeItemDesc + ", codeItemVal="
				+ codeItemVal + "]";
	}
}