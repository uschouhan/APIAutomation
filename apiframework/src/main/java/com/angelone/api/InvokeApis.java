package com.angelone.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.angelone.api.pojo.CancelOrderPOJO;
import com.angelone.api.pojo.ChartsAPIPOJO;
import com.angelone.api.pojo.LTPPricePOJO;
import com.angelone.api.pojo.LoginMpinPOJO;
import com.angelone.api.pojo.LoginOtpPOJO;
import com.angelone.api.pojo.OptionsPOJO;
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
	private static final String LOGIN_MPIN_ENDPOINT = ApiConfigFactory.getConfig().loginMpinEndpoint();
	private static final String CREATE_ORDER_ENDPOINT = ApiConfigFactory.getConfig().orderEndpoint();
	private static final String LTP_PRICE_ENDPOINT = ApiConfigFactory.getConfig().ltpPriceEndpoint();
	private static final String CANCEL_ORDER_ENDPOINT = ApiConfigFactory.getConfig().cancelOrderEndpoint();
	private static final String GET_ORDER_BOOK_ENDPOINT = ApiConfigFactory.getConfig().getOrderBookEndpoint();
	private static final String GET_POSITION_ENDPOINT = ApiConfigFactory.getConfig().getPositionEndpoint();
	private static final String GET_LOGIN_OTP_ENDPOINT = ApiConfigFactory.getConfig().getLoginOTPEndpoint();
	private static final String VERIFY_OTP_ENDPOINT = ApiConfigFactory.getConfig().verifyOTPEndpoint();
	private static final String FUTURE_BUILTUP_HEATMAP_ENDPOINT = ApiConfigFactory.getConfig().futureBuiltupHeatMapEndpoint();
	private static final String MARKET_MOVERS_BY_MOST_ENDPOINT = ApiConfigFactory.getConfig().marketMoversByMost();
	private static final String GET_WATCHLIST_ENDPOINT = ApiConfigFactory.getConfig().getWatchlistEndpoint();
	private static final String GET_BSE_EQUITY_CHARTS_ENDPOINT = ApiConfigFactory.getConfig().getBSEequityEndpoint();
	private static final String GET_NSE_EQUITY_CHARTS_ENDPOINT = ApiConfigFactory.getConfig().getNSEequityEndpoint();
	private static final String GET_NSE_CURRENCY_CHARTS_ENDPOINT = ApiConfigFactory.getConfig().getNSECurrencyEndpoint();
	private static final String GET_NSE_FNO_CHARTS_ENDPOINT = ApiConfigFactory.getConfig().getNseFnoEndpoint();
	private static final String GET_HOLDING_ENDPOINT = ApiConfigFactory.getConfig().getHoldingEndpoint();
	private static final String GET_OPTIONS_ENDPOINT = ApiConfigFactory.getConfig().getOptionEndpoint();
	/**
	 * Method for calling create user Token via MPIN
	 * @param userDetails
	 * @return userDetals
	 */
	public Response getUserTokenViaMPIN(LoginMpinPOJO userDetails,String jwtToken) {
		System.out.println(" ########## API Called : " + BaseRequestSpecification.BASE_URL + LOGIN_MPIN_ENDPOINT);
		Response response = BaseRequestSpecification.getDefaultRequestSpec().contentType(ContentType.JSON)
				.headers(getHeadersMpin(jwtToken))
				.body(userDetails)
				.log()
				.all()
				.post(LOGIN_MPIN_ENDPOINT);
		System.out.println("########  Api Response ########");
		response.then().log().all(true);
		return response;
	}
	/**
	 * Method to construct headers for userToken api via MPIN
	 * @return headers as map
	 */
	private Map<String,Object> getHeadersMpin(String jwtToken) {

		Map<String, Object> m = getHeaders();
		m.put("Accept", "application/json");
		m.put("Accept-Language", "en-GB,en-US;q=0.9,en;q=0.8");
		m.put("ApplicationName", "Spark-Web");
		m.put("Connection", "keep-alive");
		m.put("Origin", "http://uattrade.angelbroking.com");
		m.put("Referer", "http://uattrade.angelbroking.com/");
		m.put("Content-Type", "application/json");
		m.put("X-AppID", "");
		m.put("X-ClientLocalIP", "172.29.24.126");
		m.put("X-ClientPublicIP", "172.29.24.126");
		m.put("X-DeviceID", "ad8d9516-5244-497d-9c3b-f0d0e6d2c17c");
		m.put("X-GM-ID", "undefined");
		m.put("X-SystemInfo", "aliqua ad");
		m.put("X-Location", "aliqua ad");
		m.put("X-SourceID", "3");
		m.put("X-UserType", "1");
		m.put("X-MACAddress", "00:25:96:FF:FE:12:34:56");
		m.put("X-OperatingSystem", "Ubuntu");
		m.put("X-ProductVersion", "");
		m.put("X-Request-Id", "");
		m.put("Authorization", "Bearer "+jwtToken);
		return m;
	}
	
	
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
		 //new BaseTestApi().genUserToken();	
		 System.out.println("$$$$$$$$$$$ Access Token is null. Please check $$$$$$$");
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
	
	public Response getAllPostions() {
		System.out.println(" ########## API Called : " + BaseRequestSpecification.BASE_URL + GET_POSITION_ENDPOINT);
		Response response = BaseRequestSpecification.getDefaultRequestSpec().contentType(ContentType.JSON)
				.headers(getHeadersForOrder())
				.log()
				.all()
				.get(GET_POSITION_ENDPOINT);
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
	
	public Response call_MartketMoversByMost(String nonTradeAccessToken,Map<String,Object> queryParams) {
		System.out.println(" ########## API Called : " + BaseRequestSpecification.DISCOVERY_BASE_URL + MARKET_MOVERS_BY_MOST_ENDPOINT);
		Response response = BaseRequestSpecification.getDiscoveryRequestSpec().contentType(ContentType.JSON)
				.headers("AccessToken",nonTradeAccessToken)
				.queryParams(queryParams)
				.log()
				.all()
				.get(MARKET_MOVERS_BY_MOST_ENDPOINT);
		System.out.println("########  Api Response ########");
		response.then().log().all(true);
		return response;
	}	
	
	// #################### Watchlist API ############################
	public Response call_getWatchlistAPI(String nonTradeAccessToken) {
		System.out.println(" ########## API Called : " + BaseRequestSpecification.WATCHLIST_BASE_URL + GET_WATCHLIST_ENDPOINT);
		Response response = BaseRequestSpecification.getWatchlistSpec().contentType(ContentType.JSON)
				.headers("AccessToken",nonTradeAccessToken)
				.log()
				.all()
				.get(GET_WATCHLIST_ENDPOINT);
		System.out.println("########  Api Response ########");
		response.then().log().all(true);
		return response;
	}	
	
	// #################### CHARTS API ############################
	
	/**
	 * Method to construct headers for Charts api
	 * @return headers as map
	 */
	private static Map<String,Object> getHeadersForCharts() {

		Map<String, Object> m = new HashMap<String, Object>();
		m.put("Accept", "application/json");
		m.put("X-consumer", "postman");
		m.put("X-correlation-id", "uuid4");
		m.put("X-access-token", "abcd");
		return m;
	}
	
	public  Response getBSEEquityCharts(ChartsAPIPOJO chartspojo) {
		System.out.println(" ########## API Called : " + BaseRequestSpecification.CHARTS_EQUITY_BASE_URL + GET_BSE_EQUITY_CHARTS_ENDPOINT);
		Response response = BaseRequestSpecification.getChartsEquitySpec().contentType(ContentType.JSON)
				.headers( getHeadersForCharts())
				.body(chartspojo)
				.log()
				.all()
				.post(GET_BSE_EQUITY_CHARTS_ENDPOINT);
		System.out.println("########  Api Response ########");
		response.then().log().all(true);
		return response;
	}
	
	public  Response getNSEEquityCharts(ChartsAPIPOJO chartspojo) {
		System.out.println(" ########## API Called : " + BaseRequestSpecification.CHARTS_EQUITY_BASE_URL + GET_NSE_EQUITY_CHARTS_ENDPOINT);
		Response response = BaseRequestSpecification.getChartsEquitySpec().contentType(ContentType.JSON)
				.headers( getHeadersForCharts())
				.body(chartspojo)
				.log()
				.all()
				.post(GET_NSE_EQUITY_CHARTS_ENDPOINT);
		System.out.println("########  Api Response ########");
		response.then().log().all(true);
		return response;
	}
	
	public  Response getNSECurrencyCharts(ChartsAPIPOJO chartspojo) {
		System.out.println(" ########## API Called : " + BaseRequestSpecification.CHARTS_EQUITY_BASE_URL + GET_NSE_CURRENCY_CHARTS_ENDPOINT);
		Response response = BaseRequestSpecification.getChartsEquitySpec().contentType(ContentType.JSON)
				.headers( getHeadersForCharts())
				.body(chartspojo)
				.log()
				.all()
				.post(GET_NSE_CURRENCY_CHARTS_ENDPOINT);
		System.out.println("########  Api Response ########");
		response.then().log().all(true);
		return response;
	}
	
	public  Response getNSE_FNO_Charts(ChartsAPIPOJO chartspojo) {
		System.out.println(" ########## API Called : " + BaseRequestSpecification.CHARTS_EQUITY_BASE_URL + GET_NSE_FNO_CHARTS_ENDPOINT);
		Response response = BaseRequestSpecification.getChartsEquitySpec().contentType(ContentType.JSON)
				.headers( getHeadersForCharts())
				.body(chartspojo)
				.log()
				.all()
				.post(GET_NSE_FNO_CHARTS_ENDPOINT);
		System.out.println("########  Api Response ########");
		response.then().log().all(true);
		return response;
	}
	
	// #################### Portfolio API ############################
	
	public  Response getHolding(String nonTradeAccessToken) {
		System.out.println(" ########## API Called : " + BaseRequestSpecification.PORTFOLIO_BASE_URL + GET_HOLDING_ENDPOINT);
		Response response = BaseRequestSpecification.getPortfolioSpec().contentType(ContentType.JSON)
				.headers("token",nonTradeAccessToken)
				.log()
				.all()
				.post(GET_HOLDING_ENDPOINT);
		System.out.println("########  Api Response ########");
		response.then().log().all(true);
		return response;
	}
	
	//################### Options API ##########################
	
	public  Response getOptions(OptionsPOJO chartspojo) {
		System.out.println(" ########## API Called : " + BaseRequestSpecification.OPTIONS_BASE_URL + GET_OPTIONS_ENDPOINT);
		Response response = BaseRequestSpecification.getOptionsSpec().contentType(ContentType.JSON)
				.body(chartspojo)
				.log()
				.all()
				.post(GET_OPTIONS_ENDPOINT);
		System.out.println("########  Api Response ########");
		response.then().log().all(true);
		return response;
	}
	
}
