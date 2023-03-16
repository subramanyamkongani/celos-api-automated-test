package com.celos.qa.api.endpPoints;

import java.util.Map;

import com.celos.qa.api.data.ConfigMapping;
import com.celos.qa.api.restClient.JsonRestClient;
import com.fasterxml.jackson.databind.JsonNode;

import jakarta.ws.rs.client.Invocation;
import jakarta.ws.rs.core.Response;

public class CommonBaseApi extends JsonRestClient {
	
	private String baseUrl = ConfigMapping.getConfigProperty(ConfigMapping.URL_API_WDPRO_STARWAVE);
	
	/**
	 * Execute a GET Request to /api/users and returns the response in Json format
	 */
	
	public final Map<String, JsonNode> requestUsersData(){
		StringBuilder resource = new StringBuilder();
		resource.append("/api/users");
		System.out.println(baseUrl+resource.toString());
		Invocation.Builder builder = buildRequest(baseUrl, resource.toString(), null);
		Response response = builder.get();
		return formatResponse(response);
	}

}
