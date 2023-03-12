package celos.api.qa.restClient;


/********************************************************************************
 * This is a Container class for all request headers. Pass a method in this class
 * an instance of JsonRestClient and the header will be added to the headers map
 * of that object.
 * 
 * @author subramanyamkongani
 * @since Mar 12, 2023
 ********************************************************************************/
public class RequestHeaders {
	
	/**
     * Adds key/value pair entry to client.headers:
     *  Content-Type : application/json
     */
    public static void addHeaderContentTypeApplicationJson(JsonRestClient client) {
    	client.getHeaders().put("Content-Type", client.mediaType.toString());
    }

    /**
     * Adds key/value pair entry to client.headers:
     *  Authorization : BEARER {token}
     */
    public static void addHeaderAuthorizationBearer(JsonRestClient client, String token) {
    	client.getHeaders().put("Authorization", "BEARER " + token);
    }

    /**
     * Adds key/value pair entry to client.headers:
     *  Authorization : APIKEY {token}
     */
    public static void addHeaderAuthorizationApiKey(JsonRestClient client, String token) {
    	client.getHeaders().put("Authorization", "APIKEY " + token);
    }

    /**
     * Adds key/value pair entry to client.headers:
     *  x-authorization-gc : APIKEY {token}
     */
    public static void addHeaderXAuthorizationGcApiKey(JsonRestClient client, String token) {
    	client.getHeaders().put("x-authorization-gc", "APIKEY " + token);
    }

    /**
     * Adds key/value pair entry to client.headers:
     *  Accept-Language : {lang}
     */
    public static void addHeaderAcceptLanguage(JsonRestClient client, String lang) {
    	client.getHeaders().put("Accept-Language", lang);
    }

    /**
     * Adds key/value pair entry to client.headers:
     *  Connection : keep-alive
     */
    public static void addHeaderConnectionKeepAlive(JsonRestClient client) {
    	client.getHeaders().put("Connection", "keep-alive");
    }

    /**
     * Adds key/value pair entry to client.headers:
     *  Content-Type : application/x-www-form-urlencoded
     */
    public static void addHeaderContentTypeApplicationXwwwFormUrlEncoded(JsonRestClient client) {
    	client.getHeaders().put("Content-Type", "application/x-www-form-urlencoded");
    }

    /**
     * Adds key/value pair entry to client.headers:
     *  X-Conversation-Id : {conversation-Id}
     */
    public static void addHeaderXConversationId(JsonRestClient client, String conversationId) {
    	client.getHeaders().put("X-Conversation-Id", conversationId);
    }

    /**
     * Adds key/value pair entry to client.headers:
     *  Accept : application/json
     */
    public static void addHeaderAcceptApplicationJson(JsonRestClient client) {
    	client.getHeaders().put("Accept", "application/json");
    }
    
    /**
     * Adds key/value pair entry to client.headers:
     *  Accept : X-Entitlements
     */
    public static void addHeaderXEntitlementsTrue(JsonRestClient client) {
    	client.getHeaders().put("X-Entitlements" , "true");
    }
    
    /**
     * Adds key/value pair entry to client.headers:
     *  Accept : source-appl-id
     */
    public static void addHeaderSourceApplId(JsonRestClient client, String sourceApplId) {
    	client.getHeaders().put("source-appl-id" , sourceApplId);
    }
    
    /**
     * Adds key/value pair entry to client.headers:
     *  X-Disney-Internal-Site : site - i.e. wdw/dlr
     */
    public static void addHeaderXDisneyInternalSite(JsonRestClient client, String site) {
    	client.getHeaders().put("X-Disney-Internal-Site", site);
    }
    
    /**
     * Adds key/value pair entry to client.headers:
     *  X-Guest-ID : {guestId}
     */
    public static void addHeaderXGuestId(JsonRestClient client, String guestId) {
    	client.getHeaders().put("X-Guest-ID", guestId);
    }
    
    /**
     * Adds key/value pair entry to client.headers:
     *  messageId : {messageId}
     */
    public static void addHeadermessageId(JsonRestClient client, String messageId) {
    	client.getHeaders().put("messageId" , messageId);
    }
    
    /**
     * Adds key/value pair entry to client.headers:
     *  Cache-Control : {cacheControl}
     */
    public static void addHeaderCacheControl(JsonRestClient client, String cacheControl) {
    	client.getHeaders().put("Cache-Control", cacheControl);
    }
    
    /**
     * Adds key/value pair entry to client.headers:
     *  Content-Length : {contentLength}
     */
    public static void addHeaderContentLength(JsonRestClient client, String contentLength) {
    	client.getHeaders().put("Content-Length", contentLength);
    }

}
