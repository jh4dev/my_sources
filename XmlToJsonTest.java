package adapter;


import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.junit.Test;
import org.springframework.jdbc.support.JdbcUtils;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.mcnc.bizmob.common.util.JsonUtil;
import com.nongshim.next.nssm.api.xml.elements.ColElement;
import com.nongshim.next.nssm.api.xml.elements.ColumnElement;
import com.nongshim.next.nssm.api.xml.elements.DatasetElement;
import com.nongshim.next.nssm.api.xml.elements.RootElement;
import com.nongshim.next.nssm.api.xml.elements.RowElement;
import com.nongshim.next.nssm.api.xml.elements.RowsElement;

public class XmlToJsonTest extends TestAdapter{

	
	@Test
	public void test() throws Exception {
		
		String strXml = getXmlString("sample_response.xml");
		System.out.println(strXml);
		
		JAXBContext jaxbContext = JAXBContext.newInstance(RootElement.class); // Response 타입으로 변경
		Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
		
		RootElement response = (RootElement) unmarshaller.unmarshal(new StringReader(strXml));
		
		/**
		 * key추출
		 * */
		for(ColumnElement col : response.getDataset().get(0).getColumnInfo().getColumnList()) {
			System.out.println(JdbcUtils.convertUnderscoreNameToPropertyName(col.getId()));
		}
		
		JsonNode jn = xmlResponseToJsonNodeRoot(response);
		
		System.out.println();
		System.out.println(jn.toString());
		ArrayNode arrNode = (ArrayNode) xmlResponseToJsonNodeRoot(response).findPath("output1");
		System.out.println(arrNode.size());
//		
//		
//		List<String> keys = new ArrayList<>();
//	    Iterator<String> iterator = jn.fieldNames();
//	    iterator.forEachRemaining(e -> keys.add(e));
//	    
//	    for(String key : keys) {
//	    	System.out.println(key);
//	    	if(key.contains("work")) {
//	    		System.out.println(jn.findPath(key).asText());
//	    	}
//	    }
//	    
//	    System.out.println(keys);
	}
	
	private JsonNode xmlResponseToJsonNode(RowsElement rows) {
		
		List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
		
		List<RowElement> rowList = rows.getRowList();
		if(rowList != null && rowList.size() > 0) {
			
			for(RowElement row : rowList) {
				Map<String, Object> map = new HashMap<String, Object>();
				List<ColElement> paramList = row.getCols();
				for(ColElement param : paramList) {
					map.put(JdbcUtils.convertUnderscoreNameToPropertyName(param.getId()), param.getVal());
				}
				mapList.add(map);
			}
		}
		
		ObjectMapper objectMapper = new ObjectMapper();                
		JsonNode jn = objectMapper.valueToTree(mapList);
		
		
		return jn;
	}
	
	private JsonNode xmlResponseToJsonNodeRoot(RootElement root) {
		
		ObjectNode objNode = JsonUtil.objectNode();
		
		for(DatasetElement dataset : root.getDataset()) {
			
			List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
			
			List<RowElement> rowList = dataset.getRows().getRowList();
			if(rowList != null && rowList.size() > 0) {
				
				for(RowElement row : rowList) {
					Map<String, Object> map = new HashMap<String, Object>();
					List<ColElement> paramList = row.getCols();
					for(ColElement param : paramList) {
						map.put(JdbcUtils.convertUnderscoreNameToPropertyName(param.getId()), param.getVal());
					}
					mapList.add(map);
				}
			}
			
			ObjectMapper objectMapper = new ObjectMapper();                
			JsonNode jn = objectMapper.valueToTree(mapList);
			
			objNode.set(dataset.getId(), jn);
		}
		
		return objNode;
	}
	
	private static String getXmlString(String fileName) throws Exception {
		
		File file = new File("D:\\DEV\\workspace\\TO_BE\\web\\SMART_HOME\\xml_sample\\" + fileName);
		
		InputStream targetStream = new FileInputStream(file);
		
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder parser = dbf.newDocumentBuilder();
		Document doc = parser.parse(new InputSource(targetStream));
		
		TransformerFactory tf = TransformerFactory.newInstance();
		Transformer transformer = tf.newTransformer();
		transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
		StringWriter writer = new StringWriter();
		transformer.transform(new DOMSource(doc), new StreamResult(writer));
		String output = writer.getBuffer().toString().replaceAll("\n|\r", "");
		
		return output;
	}
}
