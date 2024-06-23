package com.lottewellfood.sfa.common.service.call;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.lottewellfood.sfa.common.code.LotteConstants;
import com.lottewellfood.sfa.common.code.SAPConstants;
import com.lottewellfood.sfa.common.enums.LotteErrors;
import com.lottewellfood.sfa.common.exception.LotteException;
import com.lottewellfood.sfa.common.util.LotteUtil;
import com.mcnc.bizmob.common.config.SmartConfig;
import com.sap.conn.jco.JCoDestination;
import com.sap.conn.jco.JCoDestinationManager;
import com.sap.conn.jco.JCoException;
import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoParameterList;
import com.sap.conn.jco.JCoStructure;
import com.sap.conn.jco.JCoTable;

@Service("rfcCallService")
public class RFCCallService {

	@Autowired private ObjectMapper objectMapper;
	
	private Logger logger = LoggerFactory.getLogger(RFCCallService.class);
	
	public JCoDestination getJCoDestination() throws LotteException {
		JCoDestination 	jcoDestination 	= null;
		
		try {
			//Set Jco Destination
			//환경 체크
			String env 				= System.getProperty("spring.profiles.active");
			String destination 		= StringUtils.isEmpty(env) ? SmartConfig.getString("jco.default.connection") : env;
			
			try {
				jcoDestination 	= JCoDestinationManager.getDestination(destination);
			} catch (JCoException e) {
				//JCoDestination 설정 오류
				logger.error("========== OCCUR ERROR :: JCoDestination 설정 중 오류 발생");
				throw new LotteException(LotteErrors.SAP_PROCESS_ERROR, e);
			}
		
			return jcoDestination;
			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new LotteException(LotteErrors.SAP_PROCESS_ERROR, e);
		}
		
	}

	public JsonNode call(String rfcName, Map<String, Object> param) throws LotteException {
		
		/* 1. log4j 로그레벨이 DEBUG인 경우 RFC 세부로그를 생성 */
		long 			startTime	= 0L;
		boolean			isInfo		= logger.isInfoEnabled();
		boolean			isDebug		= logger.isDebugEnabled();
		String			destination = null;
		
		JCoDestination 	jcoDestination 	= null;
		JCoFunction		jcoFunction		= null;
		
		StringBuffer 	buffer 			= null;
		
		//response node  
		ObjectNode 		resNode 	= objectMapper.createObjectNode();
		
		try {
			startTime	= System.currentTimeMillis();
			
			if(StringUtils.isEmpty(rfcName)) throw new LotteException(LotteErrors.SAP_PROCESS_ERROR);

			buffer 	= new StringBuffer();
			if (isInfo || isDebug) {
				buffer.append("\n┌────────────────────────────────────────────────────────────────────────────────────────────");
				buffer.append("\n│ RFC Start process > ");
				buffer.append("\n│ RFC Name > ").append(rfcName);
			}
			
			jcoDestination 	= getJCoDestination();
			
			//get Function
			jcoFunction = getFunctionByRfcName(destination, rfcName, jcoDestination);
			if (isInfo || isDebug) {
				buffer.append("\n│ SAP ENV > ").append(jcoDestination.getR3Name());
				buffer.append("\n│ SAP CLIENT > ").append(jcoDestination.getClient());
			}
			
			//converting
			StringBuffer paramBuffers[] = convertRequestMapToJcoImportParams(param, jcoFunction);
			
			if (isDebug) {
				buffer.append("\n│ RFC Import Data > ").append(paramBuffers[0]);
				buffer.append("\n│ RFC Import Structure > ").append(paramBuffers[1]);
				buffer.append("\n│ RFC Import Tables > ").append(paramBuffers[2]);
			}
			
			jcoFunction.execute(jcoDestination);
			
			JsonNode exportDataNode 	= jcoFunction.getExportParameterList() == null ? 
					objectMapper.createObjectNode() : objectMapper.readTree(jcoFunction.getExportParameterList().toJSON());
			JsonNode exportTableNode 	= jcoFunction.getTableParameterList() == null ? 
					objectMapper.createObjectNode() : objectMapper.readTree(jcoFunction.getTableParameterList().toJSON());
			
			if (isDebug) {
				buffer.append("\n│ RFC Export Data > ").append(exportDataNode.toString().length() > LotteConstants._LOG_MAXIMUM_SIZE ? 
						exportDataNode.toString().substring(0, LotteConstants._LOG_MAXIMUM_SIZE) : exportDataNode.toString());
				buffer.append("\n│ RFC Export Tables > ").append(exportTableNode.toString().length() > LotteConstants._LOG_MAXIMUM_SIZE ? 
						exportTableNode.toString().substring(0, LotteConstants._LOG_MAXIMUM_SIZE) : exportTableNode.toString());
			}
			
			//SAP EV_RETURN 체크
			checkRfcReturnError(exportDataNode);
			
			resNode.set(SAPConstants._RFC_EXPORT_DATA_KEY	, exportDataNode);
			resNode.set(SAPConstants._RFC_EXPORT_TABLES_KEY	, exportTableNode);
			
			return resNode;
			
		} catch (LotteException le) {
			if(buffer != null) {
				buffer.append("\n│ Occured Exception > ").append(le.getCause());
			}
			throw le;
		} catch (Exception e) {
			if(buffer != null) {
				buffer.append("\n│ Occured Exception > ").append(e.getCause());
			}
			throw new LotteException(LotteErrors.SAP_PROCESS_ERROR, e);
		} finally {
			if(buffer != null) {
				if ((isInfo || isDebug)) {
					long endTime	 	= System.currentTimeMillis();
					buffer.append("\n│ RFC Call Time > ").append(endTime - startTime).append("ms");
					buffer.append("\n└────────────────────────────────────────────────────────────────────────────────────────────");
					logger.info(buffer.toString());
				}
			}
		}
	}
	
	/**
	 * @method checkRfcReturnError
	 * @description 
	 * </br> Check RFC common result fields (EV_RETURN, EV_MESSAGE)
	 * */
	private void checkRfcReturnError(JsonNode exportDataNode) throws LotteException {

		String EV_RETURN 	= exportDataNode.findPath(SAPConstants._RFC_RESULT_STATUS_FIELD).asText();
		String EV_MESSAGE 	= exportDataNode.findPath(SAPConstants._RFC_RESULT_MESSAGE_FIELD).asText();
		
		if(!StringUtils.equalsIgnoreCase(EV_RETURN, SAPConstants._RFC_RESULT_SUCCESS_CODE)) {
			//EV_RETURN = "S" 가 아니면 실패 ("E" or "F")
			if(StringUtils.isNotEmpty(EV_MESSAGE)) {
				//리턴된 메세지 있음
				throw new LotteException(LotteErrors.SAP_DEFINED_ERROR, EV_MESSAGE);
			} else {
				throw new LotteException(LotteErrors.SAP_UNDEFINED_ERROR);
			}
		}
	}
	
	/**
	 * @method getFunctionByRfcName
	 * @description 
	 * </br> Set JCoDestination with destination name
	 * </br> Set JCoFunction with RFC name
	 * */
	private JCoFunction getFunctionByRfcName(String destination, String rfcName, JCoDestination jcoDestination) throws LotteException {
		try {
			return jcoDestination.getRepository().getFunction(rfcName);
		} catch (JCoException e) {
			//JCoFunction 설정 오류
			logger.error("========== OCCUR ERROR :: JCoFunction 설정 중 오류 발생");
			throw new LotteException(LotteErrors.SAP_PROCESS_ERROR, e);
		}
	}
	
	/**
	 * @method convertRequestMapToJcoImportParams
	 * @description 
	 * </br> RFC 호출을 위한 파라미터 설정
	 * */
	@SuppressWarnings("unchecked")
	private StringBuffer[] convertRequestMapToJcoImportParams(Map<String, Object> param, JCoFunction jcoFunction) throws LotteException {
		
		StringBuffer importBf 			= null;
		StringBuffer importTableBf 		= null;
		StringBuffer importStructureBf 	= null;
		
		
        if (jcoFunction == null) {
        	logger.error("========== OCCUR ERROR :: JCoFunction 설정되지 않음");
			throw new LotteException(LotteErrors.SAP_PROCESS_ERROR, new NullPointerException());
        }
        try {
        	
        	importBf 			= new StringBuffer();
        	importTableBf 		= new StringBuffer();
        	importStructureBf 	= new StringBuffer(); 
        	
        	JCoParameterList importParams = jcoFunction.getImportParameterList();
        	
        	if(importParams != null || jcoFunction.getTableParameterList() != null) {
        		
	            for (Map.Entry<String, Object> entry : param.entrySet()) {
	                String key = entry.getKey();
	                Object value = entry.getValue();
	                
	                if (value instanceof List) {
	                    List<Map<String, Object>> tableData = (List<Map<String, Object>>) value;
	                    JCoTable table = jcoFunction.getTableParameterList().getTable(key);
	                    
	                    importTableBf.append("\n│───── TABLE NAME : " + key + " ─────");
	                    for (Map<String, Object> rowData : tableData) {
	                        table.appendRow();
	                        for (Map.Entry<String, Object> columnData : rowData.entrySet()) {
	                        	//TABLE
	                        	if(table.getMetaData().hasField(columnData.getKey())) {
	                        		table.setValue(columnData.getKey(), LotteUtil.toStringValue(columnData.getValue()));
	                        		importTableBf.append("\n││ " + columnData.getKey() + " : " + LotteUtil.toStringValue(columnData.getValue()));
	                        	}
	                        }
	                        importTableBf.append("\n│────────── end row ──────────");
	                    }
	                } else {
	                	if(importParams.getMetaData().hasField(key)) {
	                		
	                		if(importParams.getMetaData().isStructure(key)) {
	                			//STRUCTURE
		                		importStructureBf.append("\n│───── STRUCTURE NAME : " + key + " ─────");
		                		Map<String, Object> structureMap = (Map<String, Object>) value;
		                		JCoStructure structure = jcoFunction.getImportParameterList().getStructure(key);
		                		
		                		for (Map.Entry<String, Object> mapData : structureMap.entrySet()) {
		                        	//function에 정의된 필드인지 확인 후 SET
		                        	if(structure.getMetaData().hasField(mapData.getKey())) {
		                        		structure.setValue(mapData.getKey(), LotteUtil.toStringValue(mapData.getValue()));
		                        		importStructureBf.append("\n││ " + mapData.getKey() + " : " + LotteUtil.toStringValue(mapData.getValue()));
		                        	}
		                        }
		                	} else {
		                		//FIELD
	                			importParams.setValue(key, value);
	                			importBf.append("\n│ " + key + " : " + value);
		                	}
	                	}
	                }
	            }
            }
        	
        	return new StringBuffer[] {importBf, importStructureBf, importTableBf};
    	} catch (Exception e) {
    		logger.error("========== OCCUR ERROR :: RFC Import 데이터 설정 중 오류 발생");
			throw new LotteException(LotteErrors.SAP_PROCESS_ERROR, e);
			
		}
    }
	
}
