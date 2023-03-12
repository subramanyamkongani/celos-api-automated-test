package com.celos.qa.api.lib;

import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.celos.qa.api.commonUtils.Log;
import com.celos.qa.api.endpPoints.CommonBaseApi;
import com.celos.qa.api.fields.ApiFields;
import com.celos.qa.api.restClient.ApiCommonStatus;
import com.fasterxml.jackson.databind.JsonNode;

public class UserLib {
	
	private static Logger log = LogManager.getLogger(UserLib.class.getName());
	
	public static Map<String, JsonNode> getUsersDetails(){
		Log.logInfo(log, "Get User Details");
		CommonBaseApi baseApi = new CommonBaseApi();
		Map<String, JsonNode> userDetailsResponse = baseApi.requestUsersData();
		ApiCommonStatus.validateStatusOk(userDetailsResponse.get(ApiFields.RESPONSE_FIELD_STATUS.field()));
		Log.logInfo(log, "Get Users Details is completed");
		return userDetailsResponse;
	}

}
