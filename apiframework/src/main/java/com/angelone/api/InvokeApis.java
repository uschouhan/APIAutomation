package com.angelone.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.angelone.api.pojo.CancelOrderPOJO;
import com.angelone.api.pojo.LTPPricePOJO;
import com.angelone.api.pojo.LoginOtpPOJO;
import com.angelone.api.pojo.PlaceOrderDetailsPOJO;
import com.angelone.api.pojo.UserDetailsPOJO;
import com.angelone.api.pojo.VerifyLoginOtpPOJO;
import com.angelone.config.factory.ApiConfigFactory;
import com.angelone.testdataMapper.GetLoginOTP;
import com.angelone.testdataMapper.VerifyLoginOtpMapper;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public final class InvokeApis {

	public String token;
	public String request_id;
	public String otp;
	public String nonTradingAccessTokenId;
	//public String ltpPrice; 
	private static final String USER_TOKEN_ENDPOINT = ApiConfigFactory.getConfig().tokenEndpoint();
	private static final String CREATE_ORDER_ENDPOINT = ApiConfigFactory.getConfig().orderEndpoint();
	private static final String LTP_PRICE_ENDPOINT = ApiConfigFactory.getConfig().ltpPriceEndpoint();
	private static final String CANCEL_ORDER_ENDPOINT = ApiConfigFactory.getConfig().cancelOrderEndpoint();
	private static final String GET_ORDER_BOOK_ENDPOINT = ApiConfigFactory.getConfig().getOrderBookEndpoint();
	private static final String GET_LOGIN_OTP_ENDPOINT = ApiConfigFactory.getConfig().getLoginOTPEndpoint();
	private static final String VERIFY_OTP_ENDPOINT = ApiConfigFactory.getConfig().verifyOTPEndpoint();
	private static final String FUTURE_BUILTUP_HEATMAP_ENDPOINT = ApiConfigFactory.getConfig().futureBuiltupHeatMapEndpoint();

	/**
	 * Method for calling create user Token
	 * @param userDetails
	 * @return userDetals
	 */
	public Response getUserToken(UserDetailsPOJO userDetails) {
		System.out.println(" ########## API Called : " + BaseRequestSpecification.BASE_URL + USER_TOKEN_ENDPOINT);
		Response response = BaseRequestSpecification.getDefaultRequestSpec().contentType(ContentType.JSON)
				.headers(getHeaders())
				.body(userDetails)
				.log()
				.all()
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
		Response response = BaseRequestSpecification.getDefaultRequestSpec().contentType(ContentType.JSON)
				.headers(getHeadersForOrder())
				.body(placeOrderDetails)
				.log()
				.all()
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
		Response response = BaseRequestSpecification.getDefaultRequestSpec().contentType(ContentType.JSON)
				.headers(getHeaderForLtpPrice())
				.body(ltprice)
				.log()
				.all()
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
		Response response = BaseRequestSpecification.getDefaultRequestSpec().contentType(ContentType.JSON)
				.headers(getHeadersForOrder())
				.body(cancelOrderDetails)
				.log()
				.all()
				.post(CANCEL_ORDER_ENDPOINT);
		System.out.println("########  Api Response ########");
		response.then().log().all(true);
		return response;
	}
	
	public Response getAllOrderDetails() {
		System.out.println(" ########## API Called : " + BaseRequestSpecification.BASE_URL + GET_ORDER_BOOK_ENDPOINT);
		Response response = BaseRequestSpecification.getDefaultRequestSpec().contentType(ContentType.JSON)
				.headers(getHeadersForOrder())
				.log()
				.all()
				.get(GET_ORDER_BOOK_ENDPOINT);
		System.out.println("########  Api Response ########");
		response.then().log().all(true);
		return response;
	}
	
	
	//##################### Trade related APIs    ######################
	
	public Response getLoginToken(LoginOtpPOJO otpRequestDetails) {
		System.out.println(" ########## API Called : " + BaseRequestSpecification.TRADE_BASE_URL + GET_LOGIN_OTP_ENDPOINT);
		Response response = BaseRequestSpecification.getTradeRequestSpec().contentType(ContentType.JSON)
				.headers(getOTPHeaders())
				.body(otpRequestDetails)
				.log()
				.all()
				.post(GET_LOGIN_OTP_ENDPOINT);
		System.out.println("########  Api Response ########");
		response.then().log().all(true);
		return response;
	}

	/**
	 * Method to construct headers for loginOtp api
	 * @return headers as map
	 */
	private static Map<String,Object> getOTPHeaders() {

		Map<String, Object> m = new HashMap<String, Object>();
		m.put("x-clientlocalip", "1.2.3.4");
		m.put("x-clientpublicip", "1.2.3.4");
		m.put("x-deviceid", "1234");
		m.put("x-macaddress", "00:25:96:FF:FE:12:34:56");
		m.put("X-operatingsystem", "Ubuntu");
		m.put("x-sourceid", "5");
		m.put("x-usertype", "1");
		m.put("x-source", "mutualfund");
		m.put("x-source-v2", "abma");
		return m;
	}
	
	public Response verifyLoginToken(VerifyLoginOtpPOJO verifyOtpDetails) {
		System.out.println(" ########## API Called : " + BaseRequestSpecification.TRADE_BASE_URL + VERIFY_OTP_ENDPOINT);
		Response response = BaseRequestSpecification.getTradeRequestSpec().contentType(ContentType.JSON)
				.headers(getVerifyOTPHeaders() )
				.body(verifyOtpDetails)
				.log()
				.all()
				.post(VERIFY_OTP_ENDPOINT);
		System.out.println("########  Api Response ########");
		response.then().log().all(true);
		return response;
	}
	
	/**
	 * Method to construct headers for VerifyloginOtp api
	 * @return headers as map
	 */
	private static Map<String,Object> getVerifyOTPHeaders() {

		Map<String, Object> m = getOTPHeaders();
		m.put("X-ProductVersion", "v4.0.1");
		m.put("X-Location", "India");
		return m;
	}

	
	// ###################### Market Place APIs #####################
	
	public Response call_marketPlace_futureBuiltupHeatmap(String nonTradeAccessToken,Map<String,Object> queryParams) {
		System.out.println(" ########## API Called : " + BaseRequestSpecification.DISCOVERY_BASE_URL + FUTURE_BUILTUP_HEATMAP_ENDPOINT);
		Response response = BaseRequestSpecification.getDiscoveryRequestSpec().contentType(ContentType.JSON)
				.headers("AccessToken",nonTradeAccessToken)
				.queryParams(queryParams)
				.log()
				.all()
				.get(FUTURE_BUILTUP_HEATMAP_ENDPOINT);
		System.out.println("########  Api Response ########");
		response.then().log().all(true);
		return response;
	}		
	
}
