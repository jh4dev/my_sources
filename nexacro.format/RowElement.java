package com.nongshim.next.nssm.api.xml.elements;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @title Row Element 
 * @ParentElement Rows
 * @ChildElements Col
 * */
@XmlRootElement(name="Row")
@XmlAccessorType(XmlAccessType.NONE)
public class RowElement {

	@XmlAttribute(name = "type")
	private String type;
	
	@XmlElement(name="Col")
	private List<ColElement> cols;

	public List<ColElement> getCols() {
		return cols;
	}

	public void setCols(List<ColElement> cols) {
		this.cols = cols;
	}

	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "RowElement [type=" + type + ", cols=" + cols + "]";
	}

	
	
}
