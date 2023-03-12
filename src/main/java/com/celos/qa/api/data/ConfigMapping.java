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
    public static final String CONFIG_FILE = System.getProperty("user.dir")+"/src/main/java/com/celos/qa/api/config/config.properties";

    /** API Config Property Field Names **/
    public static final String URL_API_WDPRO_STARWAVE = "url.res.req";

    /**
     * Convenience method to retrieve a property from the default configuration file
     * @param key - name of property to retrieve
     * @return String - value of property, if not found null
     **/
    public static String getConfigProperty(String key) {
    	String value = TestContainer.props.getPropCmdLineOverWrite(ConfigMapping.CONFIG_FILE, key);
    	return value == "null" ? null : value;
    }

}
