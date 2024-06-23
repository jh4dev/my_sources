/**
 * DBApproval_CONF_ENCLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.lottewellfood.sfa.common.service.webservice.elecpayment;

import com.mcnc.bizmob.common.config.SmartConfig;

public class DBApproval_CONF_ENCLocator extends org.apache.axis.client.Service implements com.lottewellfood.sfa.common.service.webservice.elecpayment.DBApproval_CONF_ENC {

    public DBApproval_CONF_ENCLocator() {
    }


    public DBApproval_CONF_ENCLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public DBApproval_CONF_ENCLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for DBApproval_CONF_ENCSoap
    //private java.lang.String DBApproval_CONF_ENCSoap_address = "https://wfeaidev.lotte.net/eai/dbapproval_conf_enc.asmx";
    //private java.lang.String DBApproval_CONF_ENCSoap_address = SmartConfig.getString("electronic.webservice.url");
    private java.lang.String DBApproval_CONF_ENCSoap_address = SmartConfig.getString("electronic.webservice.url." + System.getProperty("spring.profiles.active"));

    public java.lang.String getDBApproval_CONF_ENCSoapAddress() {
        return DBApproval_CONF_ENCSoap_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String DBApproval_CONF_ENCSoapWSDDServiceName = "DBApproval_CONF_ENCSoap";

    public java.lang.String getDBApproval_CONF_ENCSoapWSDDServiceName() {
        return DBApproval_CONF_ENCSoapWSDDServiceName;
    }

    public void setDBApproval_CONF_ENCSoapWSDDServiceName(java.lang.String name) {
        DBApproval_CONF_ENCSoapWSDDServiceName = name;
    }

    public com.lottewellfood.sfa.common.service.webservice.elecpayment.DBApproval_CONF_ENCSoap getDBApproval_CONF_ENCSoap() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(DBApproval_CONF_ENCSoap_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getDBApproval_CONF_ENCSoap(endpoint);
    }

    public com.lottewellfood.sfa.common.service.webservice.elecpayment.DBApproval_CONF_ENCSoap getDBApproval_CONF_ENCSoap(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            com.lottewellfood.sfa.common.service.webservice.elecpayment.DBApproval_CONF_ENCSoapStub _stub = new com.lottewellfood.sfa.common.service.webservice.elecpayment.DBApproval_CONF_ENCSoapStub(portAddress, this);
            _stub.setPortName(getDBApproval_CONF_ENCSoapWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setDBApproval_CONF_ENCSoapEndpointAddress(java.lang.String address) {
        DBApproval_CONF_ENCSoap_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (com.lottewellfood.sfa.common.service.webservice.elecpayment.DBApproval_CONF_ENCSoap.class.isAssignableFrom(serviceEndpointInterface)) {
                com.lottewellfood.sfa.common.service.webservice.elecpayment.DBApproval_CONF_ENCSoapStub _stub = new com.lottewellfood.sfa.common.service.webservice.elecpayment.DBApproval_CONF_ENCSoapStub(new java.net.URL(DBApproval_CONF_ENCSoap_address), this);
                _stub.setPortName(getDBApproval_CONF_ENCSoapWSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("DBApproval_CONF_ENCSoap".equals(inputPortName)) {
            return getDBApproval_CONF_ENCSoap();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://tempuri.org/", "DBApproval_CONF_ENC");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://tempuri.org/", "DBApproval_CONF_ENCSoap"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("DBApproval_CONF_ENCSoap".equals(portName)) {
            setDBApproval_CONF_ENCSoapEndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
