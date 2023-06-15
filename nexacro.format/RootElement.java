package com.nongshim.next.nssm.api.xml.elements;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @title Root Element 
 * @ParentElement none
 * @ChildElements Parameters, Dataset
 * @Hiearachy 
 * <pre>
 * {@code
 *<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
 * <Root xmlns="http://www.nexacroplatform.com/platform/dataset">
 *	<Parameters>
 *		<Parameter id="" type=""></Parameter> <!-- response 처리 시 사용 -->
 *	</Parameters>
 *	<Dataset id=""> <!-- request/response data -->
 *		<ColumnInfo>
 *			<Column id="" type="" size=""/>	<!-- define request/response fields' name, type and size -->
 *		</ColumnInfo>
 *		<Rows>
 *			<Row>
 *				<Col id=""></Col> <!-- declare request/response fields' name and value-->
 *			</Row>
 *		</Rows>
 *	</Dataset>
 *</Root> 
 *}
 * </pre>
 * */
@XmlRootElement(name = "Root")
@XmlAccessorType(XmlAccessType.NONE)
public class RootElement {
	
	@XmlElement(name="Parameters")
	private ParametersElement parameters;

	@XmlElement(name="Dataset")
	private List<DatasetElement> dataset;
	
	public ParametersElement getParameters() {
		return parameters;
	}

	public void setParameters(ParametersElement parameters) {
		this.parameters = parameters;
	}

	public List<DatasetElement> getDataset() {
		return dataset;
	}

	public void setDataset(List<DatasetElement> dataset) {
		this.dataset = dataset;
	}

	@Override
	public String toString() {
		return "RootElement [parameters=" + parameters + "]";
	}

	
	
}
