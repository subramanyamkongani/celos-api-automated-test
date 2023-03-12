package com.celos.qa.api.commonUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Properties;

import org.apache.logging.log4j.*;

import com.celos.qa.api.commonUtils.Log;

/*************************************************************
 * Reads file and loads values into Properties object.
 * Contains singleton instance of configuration properties.
 *
 * @author Subbu
 * @since Mar 12 2023
 *************************************************************/

public final class Config {
	
	private static Logger log = LogManager.getLogger(Config.class.getName());
	
	/** Contains Properties read from config file **/
	public Properties properties;
	
	/** Flag to have properties to read **/
	private Boolean valuesLoaded = false;
	
	/** Singleton instance of class **/
	private static final Config CONFIG_INSTANCE = new Config();

    /** Empty & private to control instantiation **/
    private Config() {
    }

    /**
     * Single accessor to singleton instance
     *
     * @return Config - singleton instance of this class
     **/
    public static synchronized Config getInstance() {
    	return CONFIG_INSTANCE;
    }
   
    /**
     * Reads a file and loads the properties found into the Properties object
     *
     * @param configFilePath - path to file containing properties
     **/
    private synchronized void loadProperties(String configFilePath) {
    	if (!valuesLoaded) {
    		try {
    			File file = new File(configFilePath);
    			if(file.exists()) {
    				properties = new Properties();
    				FileInputStream inputStream = null;
    				try {
    					inputStream = new FileInputStream(configFilePath);
    					properties.load(inputStream); 
    				} catch (FileNotFoundException e) {
    					Log.logFatal(log, "Configuration file - " + configFilePath + " - not found", e);
    				} catch (Exception e) {
    					Log.logFatal(log, "Exception occured when attemtping to load property file - " + configFilePath, e);
    				} finally {
    					if (inputStream != null) {
    						try {
    							inputStream.close();
    						} catch (Exception e) {
    							Log.logFatal(log, "Exception occured when attemtping to close input stream to property file - " + configFilePath, e);
    						}
    					}
    				}
    				valuesLoaded = true;
    				Log.logInfo(log, "Configuration file - " + configFilePath + " - successfully loaded");
    			}
    		} catch (Exception e) {
    			Log.logFatal(log, "Exception occured when attemtping to open input stream to property file - " + configFilePath, e);
    		}
    	}
    }

    /**
     * Lookup value of specified property in properties object read in from configuration file.
     * If a command line argument exists with the same key, return the command line version. 
     *
     * @param file - the path to the properties file
     * @param key - the key to the property to retrieve
     * @return String - the value of the property or null if property is not found
     **/
    public String getPropCmdLineOverWrite(String file, String key) {
    	loadProperties(file);
    	String prop = (System.getProperty(key) != null) ? System.getProperty(key) : properties.getProperty(key);
    	if (prop == null)
    		Log.logWarn(log, "Property value not found for key: " + key);
    	return prop;
    }

    /**
     * Lookup value of specified property in properties object read in from configuration file only.
     *
     * @param file - the path to the properties file
     * @param key - the key to the property to retrieve
     * @return String - the value of the property or null if property is not found
     **/
    public String getPropConfigOnly(String file, String key) {
    	loadProperties(file);
    	String prop = properties.getProperty(key);
    	if (prop == null)
    		Log.logWarn(log, "Property value not found for key: " + key);
    	return prop;
    }

}
