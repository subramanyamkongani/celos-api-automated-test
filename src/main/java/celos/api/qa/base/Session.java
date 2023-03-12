package celos.api.qa.base;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import celos.api.qa.common.utils.Log;
import celos.api.qa.common.utils.TestContainer;

/******************************************************************************
 * This Session object is used to hold values while test run Holds common data
 * for give "Session".
 * 
 * @author subramanyamkongani
 * @since Mar 12, 2023
 *******************************************************************************/

public class Session {

	private static Logger log = LogManager.getLogger(Session.class.getName());

	/** used as a session object to maintain values for related calls **/
	private Map<String, String> session = null;

	/** Constructor - initializes empty session map **/
	public Session() {
		session = new HashMap<String, String>();
	}

	/** Constructor - initializes single map of default values **/
	public Session(Map<String, String> defaultValues) {
		session = defaultValues;
	}

	/** Constructor - initializes list of maps of default values **/
	public Session(List<Map<String, String>> defaultValues) {
		this();
		addMultipleDataMaps(defaultValues);
	}

	/********************
	 * Accessor Methods *
	 ********************/

	/**
	 * Returns single value based on key passed in
	 *
	 * @param key - String value representing the key
	 * @return String - value of the key, null if key is not found
	 **/
	public String getData(String key) {
		return session.get(key);
	}

	/**
	 * Adds a collection of key/values to the session object
	 * 
	 * @param mapOfNewValues - map of new values to be added to session object
	 **/
	public void addData(Map<String, String> mapOfNewValues) {
		session.putAll(mapOfNewValues);
	}

	/**
	 * Adds a list of collections of key/values to the session object
	 * 
	 * @param listOfMapsOfNewValues - list of maps of new values to be added to
	 *                              session object
	 **/
	public void addMultipleDataMaps(List<Map<String, String>> listOfMapsOfNewValues) {
		for (Map<String, String> mapOfNewValues : listOfMapsOfNewValues) {
			session.putAll(mapOfNewValues);
		}
	}

	/**
	 * Returns the session object as-is
	 *
	 * @return Map<String, String> - current 'session' object
	 **/
	public Map<String, String> getSession() {
		return session;
	}

	/**
	 * Sets a field/value in the session object
	 * 
	 * @param field - name of value to be added to session object
	 * @param value - object of value to be added to session object
	 **/
	public void setData(String field, String value) {
		session.put(field, value);
	}

	/**
	 * Clears 'session' object, reseting it to an empty Map
	 **/
	public void clearSession() {
		session.clear();
	}

	/**
	 * Formats 'session' object as JSON object
	 *
	 * @return String - 'session' object formatted
	 **/
	public String toString() {
		String formattedSession = null;
		try {
			formattedSession = TestContainer.mapper.writeValueAsString(session);
		} catch (Exception e) {
			Log.logFatal(log, "Error occured when converting Session object from map to json");
		}
		return formattedSession;
	}
}
