package com.lottewellfood.sfa.common.vo;

import java.io.File;

import org.apache.commons.lang.StringUtils;

import com.fasterxml.jackson.databind.JsonNode;
import com.lottewellfood.sfa.common.code.LotteConstants;
import com.lottewellfood.sfa.common.util.LotteUtil;
import com.mcnc.bizmob.common.config.SmartConfig;

public class CommonFileVo {

	private String atchFileIdxNo;
	private String atchFileOrd;
	private String workDvsCd;
	private String actlFileNm;
	private String srvrStrgFileNm;
	private String fileExt;
	private String fileSz;
	private String fileSvPth;
	private String sortOrd;
	private String drmYn;
	private String delYn;
	private String fileSvFullPth;
	private String atchFileUrl;
	private String atchFileDesc;
	
	private String rgtrId;
	private String mdfrId;
	

	public CommonFileVo() {
		
	}
	
	public CommonFileVo(File file, JsonNode fileNode) {
		
		this.actlFileNm 		= StringUtils.isEmpty(fileNode.findPath("actlFileNm").asText()) ? "" : fileNode.findPath("actlFileNm").asText();
		this.srvrStrgFileNm 	= file.getName();
		this.fileSvPth 			= file.getParent();
		this.fileExt 			= this.getFileExtension(file.getName());
		this.fileSz 			= String.valueOf(file.length());
		this.sortOrd			= StringUtils.isEmpty(fileNode.findPath("sortOrd").asText()) ? "1" : fileNode.findPath("sortOrd").asText(); 
		
		this.delYn				= "N";
		this.drmYn				= "N";
	}
	
	public CommonFileVo(String atchFileIdxNo, String delYn) {
		this.atchFileIdxNo 		= atchFileIdxNo;
		this.delYn 				= delYn;
	}
	
	public CommonFileVo(String atchFileIdxNo, String atchFileOrd, String delYn) {
		
		this.atchFileIdxNo 		= atchFileIdxNo;
		this.atchFileOrd 		= atchFileOrd;
		this.delYn 				= delYn;
	}
	public CommonFileVo(String atchFileIdxNo, String atchFileOrd, String sortOrd, String delYn) {
		
		this.atchFileIdxNo 		= atchFileIdxNo;
		this.atchFileOrd 		= atchFileOrd;
		this.sortOrd			= sortOrd;
		this.delYn 				= delYn;
	}
	
	private String getFileExtension(String fileName) {
		int index = fileName.lastIndexOf(".");
		if (index > 0) {  
			return fileName.substring(index + 1);   
		} else {
			return  "";
		}
	}
	
	public String getAtchFileIdxNo() {
		return atchFileIdxNo;
	}
	public void setAtchFileIdxNo(String atchFileIdxNo) {
		this.atchFileIdxNo = atchFileIdxNo;
	}
	public String getAtchFileOrd() {
		return atchFileOrd;
	}
	public void setAtchFileOrd(String atchFileOrd) {
		this.atchFileOrd = atchFileOrd;
	}
	public String getWorkDvsCd() {
		return workDvsCd;
	}
	public void setWorkDvsCd(String workDvsCd) {
		this.workDvsCd = workDvsCd;
	}
	public String getActlFileNm() {
		return actlFileNm;
	}
	public void setActlFileNm(String actlFileNm) {
		this.actlFileNm = actlFileNm;
	}
	public String getSrvrStrgFileNm() {
		return srvrStrgFileNm;
	}
	public void setSrvrStrgFileNm(String srvrStrgFileNm) {
		this.srvrStrgFileNm = srvrStrgFileNm;
	}
	public String getFileExt() {
		return fileExt;
	}
	public void setFileExt(String fileExt) {
		this.fileExt = fileExt;
	}
	public String getFileSz() {
		return fileSz;
	}
	public void setFileSz(String fileSz) {
		this.fileSz = fileSz;
	}
	public String getFileSvPth() {
		return fileSvPth;
	}
	public void setFileSvPth(String fileSvPth) {
		this.fileSvPth = fileSvPth;
	}
	public String getSortOrd() {
		return sortOrd;
	}
	public void setSortOrd(String sortOrd) {
		this.sortOrd = sortOrd;
	}
	public String getDrmYn() {
		return drmYn;
	}
	public void setDrmYn(String drmYn) {
		this.drmYn = drmYn;
	}
	public String getDelYn() {
		return delYn;
	}
	public void setDelYn(String delYn) {
		this.delYn = delYn;
	}

	public String getRgtrId() {
		return rgtrId;
	}

	public void setRgtrId(String rgtrId) {
		this.rgtrId = rgtrId;
	}

	public String getMdfrId() {
		return mdfrId;
	}

	public void setMdfrId(String mdfrId) {
		this.mdfrId = mdfrId;
	}

	public String getFileSvFullPth() {
		return fileSvFullPth;
	}

	public void setFileSvFullPth(String fileSvFullPth) {
		this.fileSvFullPth = fileSvFullPth;
		this.setAtchFileUrl();
	}
	
	public String getAtchFileUrl() {
		return atchFileUrl;
	}

	public void setAtchFileDesc (String atchFileDesc) {
		this.atchFileDesc = atchFileDesc;
	}
	
	public String getAtchFileDesc() {
		return atchFileDesc;
	}

	public void setAtchFileUrl() {
		StringBuffer buffer = new StringBuffer();
		buffer.append(SmartConfig.getString("server.url." + System.getProperty("spring.profiles.active")));
		buffer.append(LotteUtil.replaceStringWithParams(SmartConfig.getString("file.download.path.common"), this.atchFileIdxNo, this.atchFileOrd));
		
		this.atchFileUrl = buffer.toString();
	}

	@Override
	public String toString() {
		return "CommonFileVo [atchFileIdxNo=" + atchFileIdxNo + ", atchFileOrd=" + atchFileOrd + ", workDvsCd="
				+ workDvsCd + ", actlFileNm=" + actlFileNm + ", srvrStrgFileNm=" + srvrStrgFileNm + ", fileExt="
				+ fileExt + ", fileSz=" + fileSz + ", fileSvPth=" + fileSvPth + ", sortOrd=" + sortOrd + ", drmYn="
				+ drmYn + ", delYn=" + delYn + ", fileSvFullPth=" + fileSvFullPth + ", atchFileUrl=" + atchFileUrl + ", atchFileDesc=" + atchFileDesc
				+ ", rgtrId=" + rgtrId + ", mdfrId=" + mdfrId + "]";
	}

	
}
