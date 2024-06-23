/**
 * DBApproval_CONF_ENCSoap.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.lottewellfood.sfa.common.service.webservice.elecpayment;

public interface DBApproval_CONF_ENCSoap extends java.rmi.Remote {

    /**
     * 문서보기 전자결재 문서 URL을 제공한다.  - companyCode:웰푸드(010100), authInfo:로그인계정|사번|부서코드,
     * processId:전자결재문서고유키(pid)
     */
    public java.lang.String loadApproval(java.lang.String companyCode, java.lang.String authInfo, java.lang.String processId) throws java.rmi.RemoteException;

    /**
     * 문서보기 전자결재 문서 URL을 제공한다. 태블릿용  - companyCode:웰푸드(010100), authInfo:로그인계정|사번|부서코드,
     * processId:전자결재문서고유키(pid)
     */
    public java.lang.String loadApproval_TABLET(java.lang.String companyCode, java.lang.String authInfo, java.lang.String processId) throws java.rmi.RemoteException;

    /**
     * 연동결재 기안문서 페이지 URL을 제공한다. - companyCode:웰푸드(010100), approvalType:
     * 단건(1)/다건(2), authInfo:로그인계정|사번|부서코드, xmlData:xml로 구성된 인터페이스정보
     */
    public java.lang.String approvalRequest(java.lang.String companyCode, java.lang.String approvalType, java.lang.String authInfo, java.lang.String xmlData) throws java.rmi.RemoteException;

    /**
     * 연동결재 기안문서 페이지 URL을 제공한다. 태블릿용 - companyCode:웰푸드(010100), approvalType:
     * 단건(1)/다건(2), authInfo:로그인계정|사번|부서코드, xmlData:xml로 구성된 인터페이스정보
     */
    public java.lang.String approvalRequest_TABLET(java.lang.String companyCode, java.lang.String approvalType, java.lang.String authInfo, java.lang.String xmlData) throws java.rmi.RemoteException;
}
