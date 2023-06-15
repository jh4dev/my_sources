package com.nongshim.next.nssm.api.xml.elements;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @title Column Element 
 * @ParentElement ColumnInfo
 * @ChildElement none
 * */
@XmlRootElement(name="Column")
@XmlAccessorType(XmlAccessType.NONE)
public class ColumnElement {

	@XmlAttribute
	private String id;
	
	@XmlAttribute
	private String type;
	
	@XmlAttribute
	private String size;
	
	
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
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	@Override
	public String toString() {
		return "Column [id=" + id + ", type=" + type + ", size=" + size + "]";
	}
	
	
}
