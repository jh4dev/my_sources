package com.nongshim.next.nssm.api.xml.elements;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;

@XmlRootElement(name="Col")
@XmlAccessorType(XmlAccessType.NONE)
public class ColElement {

	@XmlAttribute(name = "id")
	private String id;

	@XmlValue
	private String val;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getVal() {
		return val;
	}

	public void setVal(String val) {
		this.val = val;
	}

	@Override
	public String toString() {
		return "RequestParam [id=" + id + ", val=" + val + "]";
	}
	
	
}
