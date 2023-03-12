package com.celos.qa.api.restClient;

import java.net.HttpURLConnection;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.celos.qa.api.commonUtils.Assertion;
import com.celos.qa.api.commonUtils.Log;
import com.celos.qa.api.commonUtils.TestContainer;
import com.celos.qa.api.fields.ApiFields;
import com.fasterxml.jackson.databind.JsonNode;

public class ApiCommonStatus {

	private static Logger log = LogManager.getLogger(ApiCommonStatus.class.getName());

	/**
	 * Verifies status code and reason values from an HTTP response. This method
	 * expects statusBlock to be in the following format:
	 *
	 * {"status":{"code":404,"reason":"Not Found"}}
	 *
	 * @param statusBlock - JsonNode containing status information
	 * @param code        - expected status code
	 * @param reason      - expected reason message
	 **/
	public static void verifyHttpResponseStatus(final JsonNode statusBlock, final int code, final String reason) {
		if (statusBlock == null) {
			Log.logFatal(log, "Status block is null. Expected - Code = " + code + " / Message = " + reason);
		}
		String reasonFound = "";
		int codeFound = -1;
		try {
			codeFound = statusBlock.get(ApiFields.RESPONSE_FIELD_CODE.field()).asInt();
			reasonFound = statusBlock.get(ApiFields.RESPONSE_FIELD_REASON.field()).asText();
		} catch (Exception e) {
			Log.logFatal(log, "Status block is incorrectly formatted - " + statusBlock.toString(), e);
		}
		if (!reasonFound.equals(reason) || codeFound != code) {
			Log.logFatal(log, "Status code/reason do not match. Expected - " + code + " " + reason + " / Found - "
					+ codeFound + " " + reasonFound);
		}
	}

	/**
	 * Verifies 200 OK status
	 *
	 * @param response - status block passed in from a request
	 **/
	public static void validateStatusOk(JsonNode response) {
		verifyHttpResponseStatus(response, HttpURLConnection.HTTP_OK, ApiFields.RESPONSE_MESSAGE_OK.field());
	}

	/**
	 * Verifies 201 CREATED status
	 *
	 * @param response - status block passed in from a request
	 **/
	public static void validateStatusCreated(JsonNode response) {
		verifyHttpResponseStatus(response, HttpURLConnection.HTTP_CREATED, ApiFields.RESPONSE_MESSAGE_CREATED.field());
	}

	/**
	 * Verifies 202 ACCEPTED status
	 *
	 * @param response - status block passed in from a request
	 **/
	public static void validateStatusAccepted(JsonNode response) {
		verifyHttpResponseStatus(response, HttpURLConnection.HTTP_ACCEPTED,
				ApiFields.RESPONSE_MESSAGE_ACCEPTED.field());
	}

	/**
	 * Verifies 204 NO CONTENT status
	 *
	 * @param response - status block passed in from a request
	 **/
	public static void validateStatusNoContent(JsonNode response) {
		verifyHttpResponseStatus(response, HttpURLConnection.HTTP_NO_CONTENT,
				ApiFields.RESPONSE_MESSAGE_NOCONTENT.field());
	}

	/**
	 * Verifies 400 BAD REQUEST status
	 *
	 * @param response - status block passed in from a request
	 **/
	public static void validateStatusBadRequest(JsonNode response) {
		verifyHttpResponseStatus(response, HttpURLConnection.HTTP_BAD_REQUEST,
				ApiFields.RESPONSE_MESSAGE_BADREQUEST.field());
	}

	/**
	 * Verifies 401 UNATHORIZED status
	 *
	 * @param response - status block passed in from a request
	 **/
	public static void validateStatusUnathorized(JsonNode response) {
		verifyHttpResponseStatus(response, HttpURLConnection.HTTP_UNAUTHORIZED,
				ApiFields.RESPONSE_MESSAGE_UNAUTHORIZED.field());
	}

	/**
	 * Verifies 403 FORBIDDEN status
	 *
	 * @param response - status block passed in from a request
	 **/
	public static void validateStatusForbidden(JsonNode response) {
		verifyHttpResponseStatus(response, HttpURLConnection.HTTP_FORBIDDEN,
				ApiFields.RESPONSE_MESSAGE_FORBIDDEN.field());
	}

	/**
	 * Verifies 404 NOT FOUND status
	 *
	 * @param response - status block passed in from a request
	 **/
	public static void validateStatusNotFound(JsonNode response) {
		verifyHttpResponseStatus(response, HttpURLConnection.HTTP_NOT_FOUND,
				ApiFields.RESPONSE_MESSAGE_NOTFOUND.field());
	}

	/**
	 * Verifies 409 CONFLICT status
	 *
	 * @param response - status block passed in from a request
	 **/
	public static void validateStatusConflict(JsonNode response) {
		verifyHttpResponseStatus(response, HttpURLConnection.HTTP_CONFLICT,
				ApiFields.RESPONSE_MESSAGE_CONFLICT.field());
	}

	/**
	 * Verifies 412 PRECONDITION FAILED status
	 *
	 * @param response - status block passed in from a request
	 **/
	public static void validateStatusPreconditionFailed(JsonNode response) {
		verifyHttpResponseStatus(response, HttpURLConnection.HTTP_PRECON_FAILED,
				ApiFields.RESPONSE_MESSAGE_PRECONDITIONFAILED.field());
	}

	/**
	 * Verifies 500 INTERNAL SERVER ERROR status
	 *
	 * @param response - status block passed in from a request
	 **/
	public static void validateStatusInternalServerError(JsonNode response) {
		verifyHttpResponseStatus(response, HttpURLConnection.HTTP_INTERNAL_ERROR,
				ApiFields.RESPONSE_MESSAGE_INTERNALSERVERERROR.field());
	}

	/**
	 * This method verifies the expectedValue matches a value found within a JSON
	 * object. The path syntax follows the jsonPointer syntax from Jackson.
	 * 
	 * Ex: {"data":[ ["1", "2", "3"], ["4", "5", "6"], ["7","8", "9"] ] }
	 * 
	 * jsonPath = "/data/0/2" -> result would be "3"
	 * 
	 * @param responseAsString - JSON object represented as a String
	 * @param value            - value to compare against
	 * @param jsonPath         - path to value
	 **/
	public static void validateValueAsPerJsonPath(String responseString, String expectedValue, String jsonPath)
			throws Exception {
		JsonNode response = TestContainer.mapper.readTree(responseString);
		Assertion.assertTrue(expectedValue.equalsIgnoreCase(response.at(jsonPath).asText()),
				"Expected Value (" + expectedValue + ") != Actual Value (" + response.at(jsonPath).asText() + ")");
		log.info("Path - " + jsonPath + " - verified to match.");
	}

}
