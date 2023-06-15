package com.nongshim.next.nssm.api.xml.elements;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @title Rows Element 
 * @ParentElement Dataset
 * @ChildElements Row
 * */
@XmlRootElement(name="Rows")
@XmlAccessorType(XmlAccessType.NONE)
public class RowsElement {

	@XmlElement(name="Row")
	private List<RowElement> rowList;

	public List<RowElement> getRowList() {
		return rowList;
	}

	public void setRowList(List<RowElement> rowList) {
		this.rowList = rowList;
	}

	@Override
	public String toString() {
		return "RowsElement [rowList=" + rowList + "]";
	}


	
}
