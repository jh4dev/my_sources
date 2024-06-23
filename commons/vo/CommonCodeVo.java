package com.lottewellfood.sfa.common.vo;

import java.util.List;

public class CommonCodeVo {

	private String categoryId;
	private String codeId;
	private String codeDesc;
	
	private List<CommonCodeItemVo> codeItemList;

	public String getCodeId() {
		return codeId;
	}

	public void setCodeId(String codeId) {
		this.codeId = codeId;
	}

	public String getCodeDesc() {
		return codeDesc;
	}

	public void setCodeDesc(String codeDesc) {
		this.codeDesc = codeDesc;
	}

	public List<CommonCodeItemVo> getCodeItemList() {
		return codeItemList;
	}

	public void setCodeItemList(List<CommonCodeItemVo> codeItemList) {
		this.codeItemList = codeItemList;
	}
	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	@Override
	public String toString() {
		return "CommonCodeVo [categoryId=" + categoryId + ", codeId=" + codeId + ", codeDesc=" + codeDesc
				+ ", codeItemList=" + codeItemList + "]";
	}
	
	
}
