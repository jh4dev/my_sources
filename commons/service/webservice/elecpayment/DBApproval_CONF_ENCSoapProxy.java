package com.lottewellfood.sfa.common.service.webservice.elecpayment;

public class DBApproval_CONF_ENCSoapProxy implements com.lottewellfood.sfa.common.service.webservice.elecpayment.DBApproval_CONF_ENCSoap {
  private String _endpoint = null;
  private com.lottewellfood.sfa.common.service.webservice.elecpayment.DBApproval_CONF_ENCSoap dBApproval_CONF_ENCSoap = null;
  
  public DBApproval_CONF_ENCSoapProxy() {
    _initDBApproval_CONF_ENCSoapProxy();
  }
  
  public DBApproval_CONF_ENCSoapProxy(String endpoint) {
    _endpoint = endpoint;
    _initDBApproval_CONF_ENCSoapProxy();
  }
  
  private void _initDBApproval_CONF_ENCSoapProxy() {
    try {
      dBApproval_CONF_ENCSoap = (new com.lottewellfood.sfa.common.service.webservice.elecpayment.DBApproval_CONF_ENCLocator()).getDBApproval_CONF_ENCSoap();
      if (dBApproval_CONF_ENCSoap != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)dBApproval_CONF_ENCSoap)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)dBApproval_CONF_ENCSoap)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (dBApproval_CONF_ENCSoap != null)
      ((javax.xml.rpc.Stub)dBApproval_CONF_ENCSoap)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public com.lottewellfood.sfa.common.service.webservice.elecpayment.DBApproval_CONF_ENCSoap getDBApproval_CONF_ENCSoap() {
    if (dBApproval_CONF_ENCSoap == null)
      _initDBApproval_CONF_ENCSoapProxy();
    return dBApproval_CONF_ENCSoap;
  }
  
  public java.lang.String loadApproval(java.lang.String companyCode, java.lang.String authInfo, java.lang.String processId) throws java.rmi.RemoteException{
    if (dBApproval_CONF_ENCSoap == null)
      _initDBApproval_CONF_ENCSoapProxy();
    return dBApproval_CONF_ENCSoap.loadApproval(companyCode, authInfo, processId);
  }
  
  public java.lang.String loadApproval_TABLET(java.lang.String companyCode, java.lang.String authInfo, java.lang.String processId) throws java.rmi.RemoteException{
    if (dBApproval_CONF_ENCSoap == null)
      _initDBApproval_CONF_ENCSoapProxy();
    return dBApproval_CONF_ENCSoap.loadApproval_TABLET(companyCode, authInfo, processId);
  }
  
  public java.lang.String approvalRequest(java.lang.String companyCode, java.lang.String approvalType, java.lang.String authInfo, java.lang.String xmlData) throws java.rmi.RemoteException{
    if (dBApproval_CONF_ENCSoap == null)
      _initDBApproval_CONF_ENCSoapProxy();
    return dBApproval_CONF_ENCSoap.approvalRequest(companyCode, approvalType, authInfo, xmlData);
  }
  
  public java.lang.String approvalRequest_TABLET(java.lang.String companyCode, java.lang.String approvalType, java.lang.String authInfo, java.lang.String xmlData) throws java.rmi.RemoteException{
    if (dBApproval_CONF_ENCSoap == null)
      _initDBApproval_CONF_ENCSoapProxy();
    return dBApproval_CONF_ENCSoap.approvalRequest_TABLET(companyCode, approvalType, authInfo, xmlData);
  }
  
  
}