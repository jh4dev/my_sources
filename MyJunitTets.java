package my.util.sources;

import java.io.File;
import java.io.StringWriter;
import java.time.LocalDateTime;
import java.util.Map;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mcnc.bizmob.common.util.IOUtil;
import com.nongshim.next.nssm.common.exception.NSException;
import com.nongshim.next.nssm.common.util.CustomObjectMapper;
import com.nongshim.next.nssm.model.MSD0618.MSD0618Request_Body;

public class MyJunitTets {

	@Test
	public void complexJsonToMapTest() {
		
		ObjectMapper objectMapper = CustomObjectMapper.create();
		
		try {
			File file = new File("C:\\Lotte_Project\\DEV\\workspaces\\sample\\SMART_HOME\\test\\sample.json");
			JsonNode json = objectMapper.readTree(file);
			
			System.out.println(json);
			
			Map<String, Object> jsonMap = MyJsonUtil.jsonToMap(json.toString());
			Map<String, Object> jsonMap2 = objectMapper.readValue(json.toString(), new TypeReference<Map<String, Object>>(){});
			
			System.out.println(jsonMap);
			System.out.println(jsonMap2);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void complexPojoToMap() throws NSException {
		
		ObjectMapper objectMapper = CustomObjectMapper.create();
		
		try {
			File file = new File("C:\\Lotte_Project\\DEV\\workspaces\\sample\\next-nssm-web\\test\\data\\MSD0618.json");
			JsonNode json = objectMapper.readTree(file);
			System.out.println(json);
			
			MSD0618Request_Body tmpBody = objectMapper.readValue(json.toString(), MSD0618Request_Body.class);
			String a = toJson(tmpBody, false);
			System.out.println(a);
			
			Map<String, Object> map = MyJsonUtil.convertPojoToMap(tmpBody);
			System.out.println(map);
			Map<String, Object> map2 = MyJsonUtil.convertObjectToMap(tmpBody);
			System.out.println(map2);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void javaTimePackageTest() {
		
		System.out.println(MyUtil.getLocalDateTimeString(LocalDateTime.now(), "yyyyMMddHH(hh)mmssSSS"));
	}
	
	public static String toJson(Object pojo, boolean prettyPrint) {
		ObjectMapper objectMapper = new ObjectMapper();
	    StringWriter sw = new StringWriter();
	    try {
	      JsonFactory jf = new JsonFactory();
	      JsonGenerator jg = jf.createGenerator(sw);
	      if (prettyPrint)
	        jg.useDefaultPrettyPrinter();
	      	objectMapper.writeValue(jg, pojo);
	    } catch (Exception e) {
	      return "{}";
	    } finally {
	      IOUtil.closeQuietly(sw);
	    } 
	    return sw.toString();
	  }
}
