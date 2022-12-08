package com.angelone.api;

import java.util.HashMap;
import java.util.Map;

import com.angelone.api.pojo.PlaceOrderDetails;
import com.angelone.api.pojo.UserDetails;
import com.angelone.config.factory.ApiConfigFactory;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public final class ReqresApi {

	public String token;
	private static final String USER_TOKEN_ENDPOINT = ApiConfigFactory.getConfig().tokenEndpoint();
	private static final String CREATE_ORDER_ENDPOINT = ApiConfigFactory.getConfig().orderEndpoint();

	/**
	 * Method for calling create user Token
	 * @param userDetails
	 * @return userDetals
	 */
	public Response getUserToken(UserDetails userDetails) {
		System.out.println(" ########## API Called : " + BaseRequestSpecification.BASE_URL + USER_TOKEN_ENDPOINT);
		System.out.println(userDetails);
		Response response = BaseRequestSpecification.getDefaultRequestSpec().contentType(ContentType.JSON)
				.headers(getHeaders())
				.body(userDetails)
				.post(USER_TOKEN_ENDPOINT);
		System.out.println("########  Api Response ########");
		response.then().log().all(true);
		return response;
	}

	/**
	 * Method to construct headers for userToken api
	 * @return headers as map
	 */
	private static Map getHeaders() {

		Map<String, Object> m = new HashMap<String, Object>();
		m.put("X-SourceID", "5");
		m.put("X-UserType", "1");
		m.put("X-ClientLocalIP", "172.29.24.126");
		m.put("X-ClientPublicIP", "172.29.24.126");
		m.put("X-MACAddress", "00:25:96:FF:FE:12:34:56");
		m.put("X-OperatingSystem", "Ubuntu");
		m.put("X-DeviceID", "1234");
		m.put("Content-Type", "application/json");
		return m;
	}
	
	/**
	 * Method for calling placeOrder api
	 * @param placeOrderDetails
	 * @return orderDetails
	 */
	public  Response placeOrder(PlaceOrderDetails placeOrderDetails) {
		System.out.println(" ########## API Called : " + BaseRequestSpecification.BASE_URL + CREATE_ORDER_ENDPOINT);
		System.out.println(placeOrderDetails);
		Response response = BaseRequestSpecification.getDefaultRequestSpec().contentType(ContentType.JSON)
				.headers(getHeadersForOrder())
				.body(placeOrderDetails)
				.post(CREATE_ORDER_ENDPOINT);
		System.out.println("########  Api Response ########");
		response.then().log().all(true);
		return response;
	}
	
	/**
	 * Method to construct headers for placeOrder api
	 * @return headers as map
	 */
	private  Map getHeadersForOrder() {

		Map<String, Object> m = new HashMap<String, Object>();
		m.put("X-SourceID", "aliqua ad");
		m.put("X-UserType", "aliqua ad");
		m.put("X-ClientLocalIP", "aliqua ad");
		m.put("X-ClientPublicIP", "aliqua ad");
		m.put("X-MACAddress", "aliqua ad");
		m.put("X-Request-Id", "aliqua ad");
		m.put("X-AppID", "aliqua ad");
		m.put("X-SystemInfo", "aliqua ad");
		m.put("X-Location", "aliqua ad");
		m.put("Content-Type", "application/json");
		m.put("Authorization", "Bearer "+token);
		return m;
	}
	
}
