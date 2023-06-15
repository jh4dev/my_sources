package com.nongshim.next.nssm.api.xml.elements;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;

/**
 * @title Parameter Element 
 * @ParentElements Parameter
 * @ChildElements none
 * */
@XmlRootElement(name="Parameter")
@XmlAccessorType(XmlAccessType.NONE)
public class ParameterElement {

	@XmlAttribute
	private String id;
	@XmlAttribute
	private String type;
	@XmlValue
	private String val;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getVal() {
		return val;
	}
	public void setVal(String val) {
		this.val = val;
	}
	
	
}
