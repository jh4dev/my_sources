package com.nongshim.next.nssm.api.xml.elements;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @title ColumnInfo Element 
 * @ParentElement Dataset
 * @ChildElement Column
 * */
@XmlRootElement(name="ColumnInfo")
@XmlAccessorType(XmlAccessType.NONE)
public class ColumnInfoElement {

	@XmlElement(name="Column")
	private List<ColumnElement> columnList;

	public List<ColumnElement> getColumnList() {
		return columnList;
	}

	public void setColumnList(List<ColumnElement> columnList) {
		this.columnList = columnList;
	}

	@Override
	public String toString() {
		return "ColumnInfoelement [columnList=" + columnList + "]";
	}

	
}
