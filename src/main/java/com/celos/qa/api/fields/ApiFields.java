package com.celos.qa.api.fields;

/****************************************************
 * ENUM holds general string values for api calls
 * @author subramanyamkongani
 * @since Mar 12, 2023
 ****************************************************/

public enum ApiFields {
	
	/** API response field - status **/
    RESPONSE_FIELD_STATUS("status"),
    /** API response field - code **/
    RESPONSE_FIELD_CODE("code"),
    /** API response field - reason **/
    RESPONSE_FIELD_REASON("reason"),
    /** API response field - message **/
    RESPONSE_FIELD_MESSAGE("message"),
    /** API response field - responseBody **/
    RESPONSE_FIELD_RESPONSE_BODY("responseBody"),
    /** API response field - headers **/
    RESPONSE_FIELD_HEADERS("headers"),

    /** API response value - 'OK' **/
    RESPONSE_MESSAGE_OK("OK"),
    /** API response value - 'Unauthorized' **/
    RESPONSE_MESSAGE_UNAUTHORIZED("Unauthorized"),
    /** API response value - 'Bad Request' **/
    RESPONSE_MESSAGE_BADREQUEST("Bad Request"),
    /** API response value - 'Method Not Allowed' **/
    RESPONSE_MESSAGE_METHODNOTALLOWED("Method Not Allowed"),
    /** API response value - 'Not Found' **/
    RESPONSE_MESSAGE_NOTFOUND("Not Found"),
    /** API response value - 'No Content' **/
    RESPONSE_MESSAGE_NOCONTENT("No Content"),
    /** API response value - 'Created' **/
    RESPONSE_MESSAGE_CREATED("Created"),
    /** API response value - 'Forbidden' **/
    RESPONSE_MESSAGE_FORBIDDEN("Forbidden"),
    /** API response value - 'Internal Server Error' **/
    RESPONSE_MESSAGE_INTERNALSERVERERROR("Internal Server Error"),
	/** API response value - 'Accepted' **/
    RESPONSE_MESSAGE_ACCEPTED("Accepted"),
	/** API response value - 'Conflict' **/
    RESPONSE_MESSAGE_CONFLICT("Conflict"),
	/** API response value - 'Precondition Failed' **/
    RESPONSE_MESSAGE_PRECONDITIONFAILED("Precondition Failed");
	

	private String field;
	
	/**
	 * Initilization ApiFields Enum values
	 * 
	 * @author subramanyamkongani
	 * @parameters values to set
	 **/
	ApiFields(String field) {
		this.field = field;
	}
	
	/**
     * Retrieves ENUM value.
     *
     * @return String - value of calling ENUM field
     **/
	public String field() {
		return field;
	}
	

}
