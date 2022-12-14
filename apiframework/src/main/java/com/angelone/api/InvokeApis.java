package com.angelone.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.angelone.api.pojo.CancelOrderPOJO;
import com.angelone.api.pojo.LTPPricePOJO;
import com.angelone.api.pojo.PlaceOrderDetailsPOJO;
import com.angelone.api.pojo.UserDetailsPOJO;
import com.angelone.config.factory.ApiConfigFactory;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public final class InvokeApis {

	public String token;
	//public String ltpPrice; 
	private static final String USER_TOKEN_ENDPOINT = ApiConfigFactory.getConfig().tokenEndpoint();
	private static final String CREATE_ORDER_ENDPOINT = ApiConfigFactory.getConfig().orderEndpoint();
	private static final String LTP_PRICE_ENDPOINT = ApiConfigFactory.getConfig().ltpPriceEndpoint();
	private static final String CANCEL_ORDER_ENDPOINT = ApiConfigFactory.getConfig().cancelOrderEndpoint();

	/**
	 * Method for calling create user Token
	 * @param userDetails
	 * @return userDetals
	 */
	public Response getUserToken(UserDetailsPOJO userDetails) {
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
	private static Map<String,Object> getHeaders() {

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
	public  Response placeOrder(PlaceOrderDetailsPOJO placeOrderDetails) {
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
	private  Map<String,Object> getHeadersForOrder() {

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
	
	
	/**
	 * this Method is to get LTP price
	 * @param ltprice
	 * @return
	 */
	public Response getLTPPrice(LTPPricePOJO ltprice) {
		System.out.println(" ########## API Called : " + BaseRequestSpecification.BASE_URL + LTP_PRICE_ENDPOINT);
		System.out.println(ltprice);
		Response response = BaseRequestSpecification.getDefaultRequestSpec().contentType(ContentType.JSON)
				.headers(getHeaderForLtpPrice())
				.body(ltprice)
				.post(LTP_PRICE_ENDPOINT);
		System.out.println("########  Api Response ########");
		response.then().log().all(true);
		return response;
	}

	private Map<String, ?> getHeaderForLtpPrice() {
		// TODO Auto-generated method stub
		Map<String, Object> m = new HashMap<String, Object>();
		if(Objects.nonNull(token)&&token.length()>1)
		m.put("Authorization", "Bearer "+token);
		else
		{
		 new BaseTestApi().genUserToken();	
		}
		
		m.put("Content-Type", "application/json");
		return m;
	}
	
	public  Response cancelOrder(CancelOrderPOJO cancelOrderDetails) {
		System.out.println(" ########## API Called : " + BaseRequestSpecification.BASE_URL + CANCEL_ORDER_ENDPOINT);
		System.out.println(cancelOrderDetails);
		Response response = BaseRequestSpecification.getDefaultRequestSpec().contentType(ContentType.JSON)
				.headers(getHeadersForOrder())
				.body(cancelOrderDetails)
				.post(CANCEL_ORDER_ENDPOINT);
		System.out.println("########  Api Response ########");
		response.then().log().all(true);
		return response;
	}
	
}
