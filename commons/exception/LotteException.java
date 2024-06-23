package com.lottewellfood.sfa.common.exception;

import java.util.Arrays;

import com.lottewellfood.sfa.common.enums.LotteErrors;

public class LotteException extends Exception {

	private static final long serialVersionUID = -7611094631104714588L;
	
	/*
	 * 에러코드
	 * */
	private String errorCode;
	
	/*
	 * 에러메시지
	 * */
	private String errorMessage;
	
	/*
	 * 에러메시지 치환 파라미터 배열
	 * */
	private String[] errorMessageParams;
    
    public LotteException() {
    	super();
    }
    public LotteException(Throwable e) {
    	super(e);
    }
    
    public LotteException(String errorCode, String errorMessage, Throwable e) {
    	this.errorCode = errorCode;
    	this.errorMessage = errorMessage;
    }
    
    public LotteException(LotteErrors error, String...errorMessageParams) {
    	this.errorCode = error.getErrorCode();
    	this.errorMessageParams = errorMessageParams;
    }
    
    public LotteException(LotteErrors error, Throwable e, String...errorMessageParams) {
    	super(e);
    	this.errorCode = error.getErrorCode();
    	this.errorMessageParams = errorMessageParams;
    }
	
	public String getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	public String[] getErrorMessageParams() {
		String[] copyArr = new String[errorMessageParams.length];
		System.arraycopy(errorMessageParams, 0, copyArr, 0, errorMessageParams.length);
		return copyArr;
	}
	public void setErrorMessageParams(String[] errorMessageParams) {
		this.errorMessageParams = Arrays.copyOf(errorMessageParams, errorMessageParams.length);
	}
	
	
}
