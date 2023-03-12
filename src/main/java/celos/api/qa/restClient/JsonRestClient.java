package celos.api.qa.restClient;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.glassfish.jersey.client.ClientConfig;

import com.celos.qa.api.commonUtils.Log;
import com.celos.qa.api.commonUtils.TestContainer;
import com.celos.qa.api.commonUtils.TestCoreUtils;
import com.celos.qa.api.fields.ApiFields;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Invocation;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

public class JsonRestClient {

	private static Logger log = LogManager.getLogger(JsonRestClient.class.getName());

	/** headers for a request **/
	private Map<String, String> headers = new HashMap<String, String>();

	/** Configuration state for the ClientBuilder **/
	private ClientConfig config = new ClientConfig();

	/** default request media type - application/json **/
	protected MediaType mediaType = MediaType.APPLICATION_JSON_TYPE;

	/** Allow any type of headers to be added to requests with no restrictions **/
	static {
		System.setProperty("sun.net.http.allowRestrictedHeaders", "true");
	}

	/**
	 * Builds a request object, based on the URL, query parameters and request
	 * headers. Headers need to be pre-set by calling setHeaders().
	 *
	 * @param domain      - host:port of request
	 * @param resource    - the entire URL, except for the host:port
	 * @param queryParams - a JsonNode containing all query parameters
	 * @return Invocation.Builder - the built request
	 **/
	public final Invocation.Builder buildRequest(String domain, String resource, JsonNode queryParams) {
		Log.logInfo(log, ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		Client client = ClientBuilder.newClient(config);
		// Set URL
		WebTarget target = client.target(domain).path(resource);
		// Add query parameters
		if (queryParams != null) {
			Iterator<Entry<String, JsonNode>> nodeIterator = queryParams.fields();
			while (nodeIterator.hasNext()) {
				Map.Entry<String, JsonNode> entry = (Map.Entry<String, JsonNode>) nodeIterator.next();
				target = target.queryParam(entry.getKey(), entry.getValue().asText());
			}
		}
		Invocation.Builder builder = target.request();
		// Add request headers
		for (Map.Entry<String, String> header : getHeaders().entrySet()) {
			builder.header(header.getKey(), header.getValue());
		}
		if (!headers.isEmpty())
			Log.logInfo(log, "Request Headers: " + headersToString());
		return builder;
	}

	/**
	 * Takes in a Response object retrieved from a request. The response body,
	 * status block & headers are extracted from the response object. The return
	 * value is constructed as a map modeling the following: { "responseBody" :
	 * {...}, "status" : {"code":404,"reason":"Not Found"}, "headers" : {...} }
	 *
	 * @param response - Response object retrieved from a request made
	 * @return Map<String, JsonNode> - map containing the response body, status &
	 *         headers
	 **/
	public final Map<String, JsonNode> formatResponse(Response response) {
		Map<String, JsonNode> formattedResponse = new HashMap<String, JsonNode>();
		try {
			// Print request
			String requestLog = TestCoreUtils.parseLine(response.toString(), ".*context=ClientResponse(.*)");
			Log.logInfo(log, "Request Executed: " + requestLog.substring(0, requestLog.length() - 1));
			// Add status block to map
			ObjectNode status = TestContainer.mapper.createObjectNode();
			status.put(ApiFields.RESPONSE_FIELD_CODE.field(), response.getStatusInfo().getStatusCode());
			status.put(ApiFields.RESPONSE_FIELD_REASON.field(), response.getStatusInfo().getReasonPhrase());
			formattedResponse.put(ApiFields.RESPONSE_FIELD_STATUS.field(), status);
			// Add headers to map
			formattedResponse.put(ApiFields.RESPONSE_FIELD_HEADERS.field(), convertResponseHeadersToJson(response));
			Log.logInfo(log, "Response Headers: " + response.getStringHeaders().toString());
			// Check response body
			if (response.hasEntity()) {
				try {
					// Read response body and store value as a JsonNode
					String entity = response.readEntity(String.class);
					if (entity.startsWith("[") || entity.startsWith("{")) {
						formattedResponse.put(ApiFields.RESPONSE_FIELD_RESPONSE_BODY.field(),
								TestContainer.mapper.readTree(entity));
					} else {
						ObjectNode temp = TestContainer.mapper.createObjectNode();
						temp.put("responseBodyNonJson", entity);
						formattedResponse.put(ApiFields.RESPONSE_FIELD_RESPONSE_BODY.field(), temp);
					}
					Log.logInfo(log, "Response Body: " + entity.toString());
				} catch (Exception e) {
					formattedResponse.put(ApiFields.RESPONSE_FIELD_RESPONSE_BODY.field(), null);
					Log.logInfo(log, "Entity could not map to a String, returning null");
				}
			}
		} catch (Exception e) {
			Log.logFatal(log, "Exception when attempting to read the response values", e);
		} finally {
			response.close();
		}
		Log.logInfo(log, "<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
		return formattedResponse;
	}

	/**
	 * Returns a String representation of the currently set headers
	 *
	 * @return String - all headers formatted in a single line
	 **/
	public final String headersToString() {
		StringBuilder headersAsString = new StringBuilder();
		headersAsString.append("{");
		for (Map.Entry<String, String> header : getHeaders().entrySet()) {
			headersAsString.append(header.getKey() + "=" + header.getValue() + ", ");
		}
		return headersAsString.substring(0, headersAsString.length() - 2) + "}";
	}

	/**
	 * Returns an ObjectNode representation of the response headers passed in
	 *
	 * @return ObjectNode - transforms response headers from a MultivaluedMap to a
	 *         JSON object
	 **/
	public final ObjectNode convertResponseHeadersToJson(Response response) {
		ObjectNode responseHeaders = TestContainer.mapper.createObjectNode();
		for (Map.Entry<String, List<Object>> header : response.getHeaders().entrySet()) {
			ArrayNode array = TestContainer.mapper.createArrayNode();
			for (Object entry : header.getValue()) {
				array.add(entry.toString());
			}
			responseHeaders.set(header.getKey(), array);
		}
		return responseHeaders;
	}

	/**
	 * Outputs a request body to a single log line
	 *
	 * @param body - JSON object
	 **/
	public final void logRequestBody(String body) {
		JsonBody.logJsonSingleLine(body, "Request Body: ");
	}

	/**
	 * Constructs a comma-separated String of entries from the passed in list
	 * 
	 * @param list - list of values to be concatenated
	 * @return String - all values in list concatenated into a String separated by
	 *         commas
	 **/
	public String buildCommaSeparatedString(List<String> list) {
		if (list == null || list.isEmpty())
			return "";
		StringBuilder constructedList = new StringBuilder();
		for (String entry : list) {
			constructedList.append(entry + ",");
		}
		return constructedList.substring(0, constructedList.length() - 1);
	}

	/**
	 * Builds a query parameter list separated by semi-colons
	 *
	 * @param JsonNode - JSON of key-value pairs representing each query parameter
	 * @return String - semi-colon separated query parameter list
	 **/
	public final String buildSemiColonSeparatedQueryParamList(JsonNode queryParams) {
		if (queryParams == null || queryParams.size() == 0)
			return "";
		StringBuilder semiColonList = new StringBuilder();
		Map<String, String> params = null;
		try {
			params = TestContainer.mapper.readerFor(new TypeReference<Map<String, String>>() {
			}).readValue(queryParams);
		} catch (Exception e) {
			Log.logFatal(log, "Failure when attempting to convert query parameter JsonNode to Map", e);
		}
		for (Map.Entry<String, String> entry : params.entrySet()) {
			semiColonList.append(";" + entry.getKey() + "=" + entry.getValue());
		}
		return semiColonList.toString();
	}

	/********************
	 * Accessor Methods *
	 ********************/

	/**
	 * Returns the 'headers' object as-is.
	 *
	 * @return Map<String, Object> - current 'headers' variable, or default values
	 *         if empty
	 **/
	public Map<String, String> getHeaders() {
		return headers;
	}

	/**
	 * Adds a single header to the 'headers' map. If this header already exists in
	 * the map, it will be overwritten.
	 * 
	 * @param String - header key
	 * @param String - header value
	 **/
	public void setHeader(String key, String value) {
		headers.put(key, value);
	}

	/**
	 * Clears 'headers' and assigns all key/value pairs from the incoming map to the
	 * 'headers' variable.
	 * 
	 * @param Map<String, Object> - request headers map
	 **/
	public void setHeaders(Map<String, String> map) {
		clearHeaders();
		for (Map.Entry<String, String> entry : map.entrySet()) {
			headers.put(entry.getKey(), entry.getValue());
		}
	}

	/**
	 * Clears 'headers' object, reseting it to an empty Map
	 **/
	public void clearHeaders() {
		headers.clear();
	}

	/**
	 * Setter: mediaType
	 *
	 * @param type - MediaType to set
	 **/
	public void setMediaType(MediaType type) {
		mediaType = type;
	}

	/**
	 * Set a configuration property for the ClientBuilder
	 *
	 * @param name  - name of property
	 * @param value - value of property
	 **/
	public void setConfigProperty(String name, Object value) {
		config.property(name, value);
	}

	/**
	 * Set a configuration for the ClientBuilder
	 *
	 * @param config - entire configuration object to replace existing
	 **/
	public void setConfig(ClientConfig configuration) {
		config = configuration;
	}

	/**
	 * Get the current configuration of the ClientBuilder
	 *
	 * @return ClientConfig - entire configuration object in its current state
	 **/
	public ClientConfig getConfig() {
		return config;
	}
}
