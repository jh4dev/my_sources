package com.lottewellfood.sfa.common.customs;

import com.sap.conn.jco.ext.DestinationDataProvider;
import com.sap.conn.jco.ext.Environment;

public class JCoEnvironmentInitializer {

    private DestinationDataProvider destinationDataProvider;

    public void setDestinationDataProvider(DestinationDataProvider destinationDataProvider) {
        this.destinationDataProvider = destinationDataProvider;
    }

    public void initialize() {
    	if(!Environment.isDestinationDataProviderRegistered()) {
    		Environment.registerDestinationDataProvider(destinationDataProvider);
    	} else {
    		Environment.unregisterDestinationDataProvider(destinationDataProvider);
    		Environment.registerDestinationDataProvider(destinationDataProvider);
    	}
    }
    
    public void destroy() {
    	if(Environment.isDestinationDataProviderRegistered()) {
    		Environment.unregisterDestinationDataProvider(destinationDataProvider);
    	} 
    }
}