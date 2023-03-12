package com.celos.qa.api.restClient;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.celos.qa.api.commonUtils.Log;
import com.celos.qa.api.commonUtils.TestContainer;
import com.celos.qa.api.commonUtils.TestCoreUtils;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

/******************************************************************
 * Library of common methods, useful when manipulating JSON objects
 * 
 * @author subramanyamkongani
 * @since Mar 12, 2023
 ******************************************************************/
public class JsonBody {
	private static Logger log = LogManager.getLogger(JsonBody.class.getName());

    /**
     * Retrieve file and update template values based on session object values.
     * If session is null, the search and replace is skipped and the file is taken as-is.
     *
     * @param session - SessionData object containing values to replace
     * @param filePath - Path to file
     * @return String - file contents
     **/
    public static String retrieveTemplateFile(Session session, String filePath) {
    	String payload = null;
    	try {
    		payload = TestCoreUtils.readResourceFile(filePath);
    		if (session != null)
    			payload = TestCoreUtils.searchAndReplaceTemplate(session.getSession(), payload);
    	} catch (Exception e) {
    		Log.logFatal(log, "Error when attempting to read from file - " + filePath, e);
    	}
    	return payload;
    } 

    /**
     * Flattens a formatted JSON object and outputs it to a single log line
     *
     * @param body - JSON object
     **/
	public static void logJsonSingleLine(String body, String precedingMsg) {
		if (body != null)
			Log.logInfo(log, precedingMsg + TestCoreUtils.convertJsonToSingleLine(body));
	}

	/**
	 * Creates an empty, mutable, JSON object
	 *
	 * @return ObjectNode - empty JSON object
	 **/
	public static ObjectNode createEmptyJson() {
		return TestContainer.mapper.createObjectNode();
	}

	/**
	 * Creates an empty, mutable, JSON array
	 *
	 * @return ArrayNode - empty JSON array
	 **/
	public static ArrayNode createEmptyJsonArray() {
		return TestContainer.mapper.createArrayNode();
	}

	/**
	 * Converts a String representation of a JSON object into an ObjectNode
	 *
	 * @param body - String representation of a JSON object
	 * @return ObjectNode - String body converted into a node object
	 */
	public static ObjectNode createObjectNodeFromString(String body) {
		ObjectNode jsonBody = null;
		try {
			jsonBody = (ObjectNode) TestContainer.mapper.readTree(body);
		} catch (Exception e) {
			Log.logFatal(log, "Cannot convert String to ObjectNode. String may be malformed: " + body, e);
		}
		return jsonBody;
	}

	/**
	 * Converts an Object into a String representation of a JSON object
	 *
	 * @param body - JSON object
	 * @return String - String representation of a JSON object
	 */
	public static String createStringFromObject(Object body) {
		String jsonBody = null;
		try {
			jsonBody = TestContainer.mapper.writeValueAsString(body);
		} catch (Exception e) {
			Log.logFatal(log, "Cannot convert Object to String. Object may not represent a valid JSON: " + body.toString(), e);
		}
		return jsonBody;
	}
}
