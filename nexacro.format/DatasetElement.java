package com.nongshim.next.nssm.api.xml.elements;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @title Dataset Element 
 * @ParentElement Root
 * @ChildElements ColumnInfo, Rows
 * */
@XmlRootElement(name="Dataset")
@XmlAccessorType(XmlAccessType.NONE)
public class DatasetElement {

	@XmlAttribute(name = "id")
	private String id;
	
	@XmlElement(name = "ColumnInfo")
	private ColumnInfoElement columnInfo;

	@XmlElement(name = "Rows")
	private RowsElement rows;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public ColumnInfoElement getColumnInfo() {
		return columnInfo;
	}

	public void setColumnInfo(ColumnInfoElement columnInfo) {
		this.columnInfo = columnInfo;
	}

	public RowsElement getRows() {
		return rows;
	}

	public void setRows(RowsElement rows) {
		this.rows = rows;
	}
	
}
