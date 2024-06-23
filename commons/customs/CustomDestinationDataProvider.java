package com.lottewellfood.sfa.common.customs;

import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mcnc.bizmob.common.config.SmartConfig;
import com.sap.conn.jco.ext.DestinationDataEventListener;
import com.sap.conn.jco.ext.DestinationDataProvider;

public class CustomDestinationDataProvider implements DestinationDataProvider {

	private Logger logger = LoggerFactory.getLogger(CustomDestinationDataProvider.class);
	
	@Override
	public Properties getDestinationProperties(String poolName) {
		
		Properties prop = new Properties();
		
		try {
			
			//MSHOST 연결
			prop.setProperty(DestinationDataProvider.JCO_MSHOST	, SmartConfig.getString("jco.client.mshost." 	+ poolName));
			prop.setProperty(DestinationDataProvider.JCO_MSSERV	, SmartConfig.getString("jco.client.msserv." 	+ poolName));
			prop.setProperty(DestinationDataProvider.JCO_R3NAME	, SmartConfig.getString("jco.client.r3name."	+ poolName));
			prop.setProperty(DestinationDataProvider.JCO_USER	, SmartConfig.getString("jco.client.user." 		+ poolName));
			prop.setProperty(DestinationDataProvider.JCO_PASSWD	, SmartConfig.getString("jco.client.passwd." 	+ poolName));
			prop.setProperty(DestinationDataProvider.JCO_CLIENT	, SmartConfig.getString("jco.client.client." 	+ poolName));
			prop.setProperty(DestinationDataProvider.JCO_GROUP	, SmartConfig.getString("jco.client.group."		+ poolName));
			prop.setProperty(DestinationDataProvider.JCO_LANG	, SmartConfig.getString("jco.client.lang." 		+ poolName));
			
			//ASHOST 연결
//			prop.setProperty(DestinationDataProvider.JCO_ASHOST	, SmartConfig.getString("jco.client.ashost." 	+ poolName));
//			prop.setProperty(DestinationDataProvider.JCO_USER	, SmartConfig.getString("jco.client.user." 		+ poolName));
//			prop.setProperty(DestinationDataProvider.JCO_PASSWD	, SmartConfig.getString("jco.client.passwd." 	+ poolName));
//			prop.setProperty(DestinationDataProvider.JCO_CLIENT	, SmartConfig.getString("jco.client.client." 	+ poolName));
//			prop.setProperty(DestinationDataProvider.JCO_SYSNR	, SmartConfig.getString("jco.client.sysnr." 	+ poolName));
//			prop.setProperty(DestinationDataProvider.JCO_LANG	, SmartConfig.getString("jco.client.lang." 		+ poolName));
			
		} catch (Exception e) {
			logger.error("CustomDestinationDataProvider::getDestinationProperties::" + e.getClass().toString());
			throw new RuntimeException(e);
		}
		
		return prop;
	}

	@Override
	public void setDestinationDataEventListener(DestinationDataEventListener el) {
	}

	@Override
	public boolean supportsEvents() {
		return true;
	}

//	@Override
//	public void afterPropertiesSet() throws Exception {
//		logger.info("CustomDestinationDataProvider::InitializingBean::afterPropertiesSet");
//		if(!Environment.isDestinationDataProviderRegistered()) {
//			logger.info("Regist CustomDestinationDataProvider to SAP JCO Environment");
//			Environment.registerDestinationDataProvider(this);
//		} 
//	}
//
//	@Override
//	public void destroy() throws Exception {
//		logger.info("CustomDestinationDataProvider::DisposableBean::destroy");
//		if(Environment.isDestinationDataProviderRegistered()) {
//			logger.info("Unregist CustomDestinationDataProvider to SAP JCO Environment");
//			Environment.unregisterDestinationDataProvider(this);
//		}
//	}
	
}
