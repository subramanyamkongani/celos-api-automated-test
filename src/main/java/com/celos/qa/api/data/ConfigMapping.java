package com.celos.qa.api.data;

import java.io.File;

import com.celos.qa.api.commonUtils.TestContainer;
import com.celos.qa.api.commonUtils.TestCoreUtils;

/************************************************************************************
 * Configuration properties mapping used to retrieve values from a configuration file
 *
 * @author subramanyamkongani
 * @since Mar 12, 2023
 ************************************************************************************/
public class ConfigMapping {
	
	/** Configuration File Pathname */
    public static final String CONFIG_FILE = TestCoreUtils.determineCurrentPath() + "configuration" + File.separator + 
    		TestContainer.getEnvironment().toLowerCase() + "_" + TestContainer.getDomain().toLowerCase() +
    		"_config.properties";
    
    public static final String URL_REQ_RES = "reqres.url";
    
    public static String getConfigProperty(String key) {
    	String value = TestContainer.props.getPropCmdLineOverWrite(ConfigMapping.CONFIG_FILE, key);
    	return value == "null" ? null : value;
    }

}
