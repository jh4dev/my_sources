package com.nongshim.next.nssm.api.xml.elements;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @title Parameters Element 
 * @ParentElement Root
 * @ChildElement Parameter
 * */
@XmlRootElement(name="Parameters")
@XmlAccessorType(XmlAccessType.NONE)
public class ParametersElement {

	@XmlElement(name="Parameter")
	List<ParameterElement> parameterList;

	public List<ParameterElement> getParameterList() {
		return parameterList;
	}

	public void setParameterList(List<ParameterElement> parameterList) {
		this.parameterList = parameterList;
	}

	@Override
	public String toString() {
		return "ParametersElement [parameterList=" + parameterList + "]";
	}
	
}
