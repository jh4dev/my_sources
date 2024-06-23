package com.lottewellfood.sfa.common.customs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.lottewellfood.sfa.common.exception.LotteException;
import com.lottewellfood.sfa.common.service.call.RFCCallService;
import com.mcnc.bizmob.common.util.JsonUtil;
import com.sap.conn.jco.JCoException;
import com.sap.conn.jco.JCoField;
import com.sap.conn.jco.JCoFieldIterator;
import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoMetaData;
import com.sap.conn.jco.JCoParameterList;
import com.sap.conn.jco.JCoRecordMetaData;
import com.sap.conn.jco.JCoRepository;
import com.sap.conn.jco.JCoStructure;
import com.sap.conn.jco.JCoTable;

public class RFCParser {
	
	private Logger logger = LoggerFactory.getLogger(RFCParser.class);
	
	public ObjectNode cacheClear(String rfcName, boolean isall, RFCCallService rfcCallService) {
		boolean 				resultFlag 			= false;
		JCoRepository 			repository 			= null;
		ObjectNode 				resNode 			= JsonUtil.getNodeFactory().objectNode();
		
		try {
			repository = rfcCallService.getJCoDestination().getRepository();
			if(isall) {
				repository.clear();
			} else {
				repository.removeFunctionTemplateFromCache(rfcName);
			}
			
			resultFlag = true;
			
		} catch (JCoException e) {
			resNode.put("error", e.getMessage());
			logger.error(e.getMessage(), e);
		} catch (LotteException e) {
			resNode.put("error", e.getMessage());
			logger.error(e.getMessage(), e);
		} catch (Exception e) {
			resNode.put("error", e.getMessage());
			logger.error(e.getMessage(), e);
		}
		
		resNode.put("result", resultFlag);
		return resNode;
	}
	
	public ObjectNode parseFunction(String rfcName, RFCCallService rfcCallService) {
		JCoRepository 			repository 			= null;
		JCoFunction 			function			= null;
		
		ObjectNode 				resultNode 			= JsonUtil.objectNode();
		ObjectNode 				resNode 			= JsonUtil.objectNode();
		
		try {
			
			repository 		= rfcCallService.getJCoDestination().getRepository();
			
			function 		= repository.getFunction(rfcName);
			
			JCoParameterList 	importParameterList 	= function.getImportParameterList();
			JCoParameterList 	exportParameterList 	= function.getExportParameterList();
			JCoParameterList 	tableParameterList 		= function.getTableParameterList();
			

			Map<String, Object> rfcMap 					= new HashMap<String, Object>();
			
			parseJCoParameterList(importParameterList, rfcMap, false, "IMPORT");
			parseJCoParameterList(exportParameterList, rfcMap, false, "OUTPUT");
			parseJCoParameterList(tableParameterList, rfcMap, true, "TABLE");
			
			
			
			resNode = JsonUtil.toObjectNode(rfcMap);
			resultNode.set(rfcName, resNode);
			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		
		return resultNode;
	}
	
	private void parseJCoParameterList(JCoParameterList jcoParameterList, Map<String, Object> rfcMap, boolean isTable, String type) {
		
		if( jcoParameterList != null ) {
			if(isTable) {
				
				int count = jcoParameterList.getMetaData().getFieldCount();		
				Map<String, Object> 		tableMap = new HashMap<String, Object>();
				for(int i=0; i < count; i++) {
					
					String 						tableName 		= jcoParameterList.getMetaData().getName(i);
					JCoTable 					table 			= jcoParameterList.getTable(tableName);
					
					JCoRecordMetaData recordMetaData = table.getRecordMetaData();
					tableMap.put("recName", recordMetaData.getName());
					int fieldCount = recordMetaData.getFieldCount();
					
					Map<String, Object> dataMap = new HashMap<String, Object>();
					for(int j=0; j < fieldCount; j++) {
						
						String 	fieldName 		= recordMetaData.getName(j);
						String 	fieldType 		= recordMetaData.getTypeAsString(j);
						int 	fieldLength 	= recordMetaData.getLength(j);
						String 	description 	= recordMetaData.getDescription(j);
						
						dataMap.put(fieldName, fieldType + "(" + fieldLength + "," + description + ")");
						
					}
					List<Map<String, Object>> listMap = new ArrayList<Map<String, Object>>();
					listMap.add(dataMap);
					tableMap.put(tableName, listMap);
				}
				
				rfcMap.put("TABLE", tableMap);
				
			} else {
				JCoMetaData 			metaData 		= jcoParameterList.getMetaData();
				String 					metaName 		= metaData.getName();
				Map<String, Object> 	metaMap 		= new HashMap<String, Object>();
				
				
				JCoFieldIterator 		fieldIter 	= jcoParameterList.getFieldIterator();
				while(fieldIter.hasNextField()) {
					JCoField 	field 			= fieldIter.nextField();
					String 		fieldName 		= field.getName();				
					String 		fieldType 		= field.getTypeAsString();
					int			fieldLength		= field.getLength();
					String 		description 	= field.getDescription();
					
					if(field.isStructure()) 
					{
						Map<String, Object> 	structureMap 		= new HashMap<String, Object>();
						
						JCoStructure structure = field.getStructure();
						JCoFieldIterator structureFieldIter = structure.getFieldIterator();
						
						while(structureFieldIter.hasNextField()) {
							JCoField 	keyField = structureFieldIter.nextField();
							
							String 		sFieldName 		= keyField.getName();				
							String 		sFieldType 		= keyField.getTypeAsString();
							int			sFieldLength	= keyField.getLength();
							String 		sDescription 	= keyField.getDescription();
							
							structureMap.put(sFieldName, sFieldType + "(" + sFieldLength + "," + sDescription + ")");
						}
						
						metaMap.put(fieldName, structureMap);
						
					} else {
						metaMap.put(fieldName, fieldType + "(" + fieldLength + "," + description + ")");
					}
				}
				rfcMap.put(metaName, metaMap);
			}
		} else {
			rfcMap.put(type, "null");
		}
	}
}
