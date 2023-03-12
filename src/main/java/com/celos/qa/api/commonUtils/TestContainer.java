package com.celos.qa.api.commonUtils;

import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.databind.ObjectMapper;

public class TestContainer {
	
	/** Map to hold all global settings for this test run **/
	private static Map<String, Object> globalVars = new HashMap<String, Object>();

	/** Jackson object mapper for creating new JSON objects **/
	public static ObjectMapper mapper = new ObjectMapper();

	/** Single instance of Config to hold configuration file properties **/
	public static Config props = null;
	
	static {
		if (props == null) {
			props = Config.getInstance();
		}
	}

	/********************
	 * Accessor Methods *
	 ********************/

	/** Set global setting **/
	public static synchronized void setGlobal(String key, Object val) {
		globalVars.put(key, val);
	}

	/** Get global setting **/
	public static Object getGlobal(String key) {
		return globalVars.get(key);
	}

	/** Variable representing the environment under test **/
	public static void setEnvironment(String env) {
		setGlobal("environment", env);
	}

	/** Variable representing the environment under test **/
	public static String getEnvironment() {
		return globalVars.get("environment").toString();
	}

	/** Variable to hold test type such as **/
	public static void setDomain(String type) {
		setGlobal("domain", type);
	}

	/** Variable to hold test type such as **/
	public static String getDomain() {
		return globalVars.get("domain").toString();
	}

}
