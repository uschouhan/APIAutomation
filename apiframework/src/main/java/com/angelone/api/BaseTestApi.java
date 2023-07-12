package com.angelone.api;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;
import java.util.UUID;
import java.util.stream.Collectors;

import com.angelone.api.pojo.*;
import com.angelone.testdataMapper.*;

import lombok.NonNull;
import lombok.SneakyThrows;

import org.apache.commons.configuration.PropertiesConfiguration;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.SkipException;

import com.angelone.api.utility.Helper;

import io.restassured.response.Response;

public class BaseTestApi {
	// protected static ThreadLocal <ReqresApi> ReqresApi = new
	// ThreadLocal<String>();
	private final InvokeApis setupApi = new InvokeApis();
	private final Helper helper = new Helper();
	private String secretKey;
	private String userDetails;

	public String getSecretKey() {
		return secretKey;
	}

	public void setSecretKey(String secretKey) {
		this.secretKey = secretKey;
	}

	public String getUserDetails() {
		return userDetails;
	}

	public void setUserDetails(String userDetails) {
		this.userDetails = userDetails;
	}

	public String getLTPPrice(String scriptId) {
		String ltpPrice;
		LTPPricePOJO ltpprice = LTPPriceData.getLTPPrice(scriptId);
		Response response = setupApi.getLTPPrice(ltpprice);
		if (response.statusCode() == 200 && Objects.nonNull(response)) {
			ltpPrice = response.jsonPath().getString("data[0].tradePrice");
			Double ltp = Double.valueOf(ltpPrice);
			ltp = ltp / 100;
			// DecimalFormat df = new DecimalFormat("#.##");
			// df.format(ltp);
			ltpPrice = String.valueOf(ltp);
		} else
			throw new SkipException("Couldnt generate Access Token for User .Hence skipping tests");
		System.out.println("LTP price fetched from api === " + ltpPrice);
		return ltpPrice;
	}

	public String getLTPPrice(String scriptId, String segment) {
		String ltpPrice;
		List<String> symbolId = new ArrayList<>();
		symbolId.add(scriptId);
		LTPPricePOJO ltpprice = LTPPriceData.getLTPPrice(segment, symbolId);
		Response response = setupApi.getLTPPrice(ltpprice);
		if (response.statusCode() == 200 && Objects.nonNull(response)) {
			ltpPrice = response.jsonPath().getString("data[0].tradePrice");
			Double ltp = Double.valueOf(ltpPrice);
			if (segment.equalsIgnoreCase("cde_fo"))
				ltp = ltp / 10000000;
			else
				ltp = ltp / 100;
			ltpPrice = String.valueOf(ltp);
		} else
			throw new SkipException("Couldnt generate Access Token for User .Hence skipping tests");
		System.out.println("LTP price fetched from api === " + ltpPrice);
		return ltpPrice;
	}

	public String getLTPPriceGTT(String scriptId, String segment) {
		String ltpPrice;
		List<String> symbolId = new ArrayList<>();
		symbolId.add(scriptId);
		LTPPricePOJO ltpprice = LTPPriceData.getLTPPrice(segment, symbolId);
		Response response = setupApi.getLTPPrice(ltpprice);
		if (response.statusCode() == 200 && Objects.nonNull(response)) {
			ltpPrice = response.jsonPath().getString("data[0].tradePrice");
			// Double ltp = Double.valueOf(ltpPrice);
			// ltp = ltp / 100;
			// DecimalFormat df = new DecimalFormat("#.##");
			// df.format(ltp);
			// ltpPrice = String.valueOf(ltp);
		} else
			throw new SkipException("Couldnt generate Access Token for User .Hence skipping tests");
		System.out.println("LTP price fetched from api === " + ltpPrice);
		return ltpPrice;
	}

	/*
	 * public String genUserToken() { UserDetailsPOJO userDetails =
	 * UserTestData.getUserDetails(); Response response =
	 * setupApi.getUserToken(userDetails); if (response.statusCode() == 200 &&
	 * Objects.nonNull(response)) setupApi.token =
	 * response.jsonPath().getString("data.accesstoken"); else throw new
	 * SkipException("Couldnt generate Access Token for User .Hence skipping tests"
	 * ); System.out.println("User Token = " + setupApi.token); return
	 * setupApi.token; }
	 */
//  Below method was for login by Password which is deprecated 
//	public String genUserToken(String userid, String pwd) {
//		UserDetailsPOJO userDetails = UserTestData.getUserDetails(userid, pwd);
//		Response response = setupApi.getUserToken(userDetails);
//		if (response.statusCode() == 200 && Objects.nonNull(response))
//			setupApi.token = response.jsonPath().getString("data.accesstoken");
//		else
//			throw new SkipException("Couldnt generate Access Token for User .Hence skipping tests");
//		System.out.println("User Token = " + setupApi.token);
//		return setupApi.token;
//	}

	public Response placeStockOrder(String ordertype, String price, String producttype, String symboltoken,
			String tradingsymbol, String variety) {
		PlaceOrderDetailsPOJO orderData = PlaceOrderTestData.placeOrder(ordertype, price, producttype, symboltoken,
				tradingsymbol, variety);
		Response response = setupApi.placeOrder(orderData);
		response = getResponse(orderData, response);
		return response;
	}

	public Response placeGttOrder(String clientId, String disclosedqty, String exchange, String price,
			String producttype, String qty, String symboltoken, String timeperiod, String tradingsymbol,
			String transactiontype, String triggerprice, String strategyCode) {
		CreateGttOrderPOJO placeGTTOrderData = CreateGTTOrderMapper.placeGTTOrder(clientId, disclosedqty, exchange,
				price, producttype, qty, symboltoken, timeperiod, tradingsymbol, transactiontype, triggerprice,
				strategyCode);
		Response response = setupApi.placeGttOrder(placeGTTOrderData);
		response = getResponseForGTT(placeGTTOrderData, response);
		return response;
	}

	public Response modifyGttOrder(String exchange, String id, String price, String qty, String symboltoken,
			String triggerprice, String timeperiod, String disclosedqty, String strategyCode) {
		ModifyGttOrderPOJO modifyGttOrderData = ModifyGttOrderMapper.modifyGttOrderData(exchange, id, price, qty,
				symboltoken, triggerprice, timeperiod, disclosedqty, strategyCode);
		Response response = setupApi.modifyGttOrder(modifyGttOrderData);
		response = getResponseForGTTModify(modifyGttOrderData, response);
		return response;
	}

	public Response modifyGttOrder(String exchange, String id, String price, String qty, String symboltoken,
			String triggerprice) {
		ModifyGttOrderPOJO modifyGttOrderData = ModifyGttOrderMapper.modifyGttOrderData(exchange, id, price, qty,
				symboltoken, triggerprice);
		Response response = setupApi.modifyGttOrder(modifyGttOrderData);
		response = getResponseForGTTModify(modifyGttOrderData, response);
		return response;
	}

	public Response placeGttOrder(String clientId, String exchange, String price, String producttype, String qty,
			String symboltoken, String tradingsymbol, String triggerprice) {
		CreateGttOrderPOJO placeGTTOrderData = CreateGTTOrderMapper.placeGTTOrder(clientId, exchange, price,
				producttype, qty, symboltoken, tradingsymbol, triggerprice);
		Response response = setupApi.placeGttOrder(placeGTTOrderData);
		response = getResponseForGTT(placeGTTOrderData, response);
		return response;
	}

	@NonNull
	private Response getResponse(PlaceOrderDetailsPOJO orderData, Response response) {
		if (response.getStatusCode() == 403) {
			System.out.println("Generating new trade token since current token is expired ...");
			generateUserToken(getUserDetails(), getSecretKey());
			response = setupApi.placeOrder(orderData);
		}
		return response;
	}

	@NonNull
	private Response getResponseForGTT(CreateGttOrderPOJO orderData, Response response) {
		if (response.getStatusCode() == 403) {
			System.out.println("Generating new trade token since current token is expired ...");
			generateUserToken(getUserDetails(), getSecretKey());
			response = setupApi.placeGttOrder(orderData);
		}
		return response;
	}

	@NonNull
	private Response getResponseForGTTModify(ModifyGttOrderPOJO orderData, Response response) {
		if (response.getStatusCode() == 403) {
			System.out.println("Generating new trade token since current token is expired ...");
			generateUserToken(getUserDetails(), getSecretKey());
			response = setupApi.modifyGttOrder(orderData);
		}
		return response;
	}

	public Response placeStockOrder(String exchange, String ordertype, String price, String producttype,
			String symboltoken, String tradingsymbol, String variety) {
		PlaceOrderDetailsPOJO orderData = PlaceOrderTestData.placeOrder(exchange, ordertype, price, producttype,
				symboltoken, tradingsymbol, variety);
		Response response = setupApi.placeOrder(orderData);
		response = getResponse(orderData, response);
		return response;
	}

	public Response placeStockOrder(String exchange, String ordertype, String price, String producttype,
			String quantity, String stoploss, String symboltoken, String tradingsymbol, String transactiontype,
			String triggerprice, String variety) {
		PlaceOrderDetailsPOJO orderData = PlaceOrderTestData.placeOrder(exchange, ordertype, price, producttype,
				quantity, stoploss, symboltoken, tradingsymbol, transactiontype, triggerprice, variety);
		Response response = setupApi.placeOrder(orderData);
		response = getResponse(orderData, response);
		return response;
	}

	public Response placeStockOrder(String basketID, String disclosedquantity, String duration, String exchange,
			String multiplier, String orderValidityDate, String ordertag, String ordertype, String precision,
			String price, String producttype, String quantity, String squareoff, String stoploss, String strategyCode,
			String symboltoken, String tickSize, String tradingsymbol, String trailTickYesNo, String trailingStopLoss,
			String transactiontype, String triggerprice, String variety) {
		PlaceOrderDetailsPOJO orderData = PlaceOrderTestData.placeOrder(basketID, disclosedquantity, duration, exchange,
				multiplier, orderValidityDate, ordertag, ordertype, precision, price, producttype, quantity, squareoff,
				stoploss, strategyCode, symboltoken, tickSize, tradingsymbol, trailTickYesNo, trailingStopLoss,
				transactiontype, triggerprice, variety);
		Response response = setupApi.placeOrder(orderData);
		response = getResponse(orderData, response);
		return response;
	}

	public Response modifyStockOrder(String disclosedquantity, String duration, String exchange, String multiplier,
			String orderValidityDate, String orderId, String ordertype, String precision, String price, String quantity,
			String symboltoken, String triggerprice, String variety) {
		ModifyOrderPOJO orderData = ModifyOrderMapper.modifyOrder(disclosedquantity, duration, exchange, multiplier,
				orderValidityDate, orderId, ordertype, precision, price, quantity, symboltoken, triggerprice, variety);
		Response response = setupApi.modifyOrderApi(orderData);
		return response;
	}

	public Response cancelOrder(String orderId, String variety) {
		CancelOrderPOJO orderData = CancelOrderData.cancelOrder(orderId, variety);
		Response response = setupApi.cancelOrder(orderData);
		return response;
	}

	public Response getOrderStatus(String guiOrderId, String orderId) {
		OrderStatusPOJO orderStatusData = OrderStatusMapper.orderStatusData(guiOrderId, orderId);
		Response response = setupApi.getOrderStatus(orderStatusData);
		return response;
	}

	public Response getGttOrderStatus(String orderId) {
		GttOrderStatusPOJO gttOrderStatusPOJO = GttOrderStatusMapper.setOrderStatusData(orderId);
		Response response = setupApi.getGttOrderStatus(gttOrderStatusPOJO);
		return response;
	}

	public Response callGttCancelOrderApi(String orderId, String symboltoken, String exchange) {
		GttCancelOrderPOJO gttOrderStatusPOJO = GttCancelOrderMapper.setGttCancelOrderData(orderId, symboltoken,
				exchange);
		Response response = setupApi.gttCancelOrderApi(gttOrderStatusPOJO);
		return response;
	}

	public Response callGttOrderListApi() {
		GttOrderListPOJO gttOrderStatusPOJO = GttOrderListMapper.setGttOrderListData();
		Response response = setupApi.gttOrderListApi(gttOrderStatusPOJO);
		return response;
	}

	public Response callGttOrderListApi(String count, String page, List<String> status) {
		GttOrderListPOJO gttOrderStatusPOJO = GttOrderListMapper.setGttOrderListData();
		Response response = setupApi.gttOrderListApi(gttOrderStatusPOJO);
		return response;
	}

	public Response callSetWatchListApi(Integer version, String watchlistData) {
		SetWatchListPOJO watchListData = SetWatchListDataMapper.getWatchListData(version, watchlistData);
		Response response = setupApi.call_setWatchlistAPI(setupApi.getNonTradingAccessTokenId(), watchListData);
		return response;
	}

	public Response getBSEChartsEquity(int seqno, String action, String topic, String rtype, String period, String type,
			int duration, String from, String to) {
		ChartsAPIPOJO chartsData = ChartsTestData.getChartsData(seqno, action, topic, rtype, period, type, duration,
				from, to);
		Response response = setupApi.getBSEEquityCharts(chartsData);
		return response;
	}

	public Response getMCXCharts(int seqno, String action, String topic, String rtype, String period, String type,
			int duration, String from, String to) {
		ChartsAPIPOJO chartsData = ChartsTestData.getChartsData(seqno, action, topic, rtype, period, type, duration,
				from, to);
		Response response = setupApi.callMCXChartsApi(chartsData);
		return response;
	}

	public Response getNSEChartsEquity(int seqno, String action, String topic, String rtype, String period, String type,
			int duration, String from, String to) {
		ChartsAPIPOJO chartsData = ChartsTestData.getChartsData(seqno, action, topic, rtype, period, type, duration,
				from, to);
		Response response = setupApi.getNSEEquityCharts(chartsData);
		return response;
	}

	public Response getNSEChartsCurrency(int seqno, String action, String topic, String rtype, String period,
			String type, int duration, String from, String to) {
		ChartsAPIPOJO chartsData = ChartsTestData.getChartsData(seqno, action, topic, rtype, period, type, duration,
				from, to);
		Response response = setupApi.getNSECurrencyCharts(chartsData);
		return response;
	}

	public Response getNSEChartsFNO(int seqno, String action, String topic, String rtype, String period, String type,
			int duration, String from, String to) {
		ChartsAPIPOJO chartsData = ChartsTestData.getChartsData(seqno, action, topic, rtype, period, type, duration,
				from, to);
		Response response = setupApi.getNSE_FNO_Charts(chartsData);
		return response;
	}

	public Response callOptionsAPI(String stockxchangecode, String expirydate) {
		OptionsPOJO chartsData = OptionsDataMapper.getOptionsData(stockxchangecode, expirydate);
		Response response = setupApi.getOptions(chartsData);
		return response;
	}

	public Response getHolding() {
		// String
		// nonTradedToken="eyJhbGciOiJIUzUxMiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE2NzgxODU5OTEsImlhdCI6MTY3ODA5NTAyNiwib21uZW1hbmFnZXJpZCI6Mywic291cmNlaWQiOiI1Iiwic3ViIjoiUzgzMzQwMiJ9.wmJl3P3LT9_XeqQEPgc_bAVSn2Uzz3P7W6R4zQ5BqWPaXFzMF60ruWfyC8o5Bjj4hFToXpMiuE6gIyYEJlfFkg";
		Response response = setupApi.getHolding(setupApi.getNonTradingAccessTokenId());
		return response;
	}

	public Response getIpoDetails() {
		// String
		// nonTradedToken="eyJhbGciOiJIUzUxMiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE2NzgxODU5OTEsImlhdCI6MTY3ODA5NTAyNiwib21uZW1hbmFnZXJpZCI6Mywic291cmNlaWQiOiI1Iiwic3ViIjoiUzgzMzQwMiJ9.wmJl3P3LT9_XeqQEPgc_bAVSn2Uzz3P7W6R4zQ5BqWPaXFzMF60ruWfyC8o5Bjj4hFToXpMiuE6gIyYEJlfFkg";
		Response response = setupApi.callIpoMasterApi(setupApi.getNonTradingAccessTokenId());
		return response;
	}

	public void generateUserToken(String userCredentials, String secret) {
		userDetails = userCredentials;
		secretKey = secret;
		String[] creden = userCredentials.split(":");
		try {
			String userId = creden[3];
			String mpin = creden[4];
			if (secret == null || secret == "")
				secret = "db3a62b2-45f6-4b6c-a74b-80ce27491bb7";
			genUserToken(userId, mpin, secret);
		} catch (ArrayIndexOutOfBoundsException e) {
			System.out.println(
					"UserId/Password/Secretkey Missing in testng xml file .Please provide if willing to use api call");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Issue while generating token.");
		}
	}

	@SneakyThrows
	private String genUserToken(String userId, String mpin, String secret) {

		UserDataJWT_POJO userDetails = UserDATAJWTMapper.getUserDetails(userId);
		String jwtToken = helper.genJTWToken(userDetails, secret);
		Thread.sleep(5000);
		LoginMpinPOJO userMpin = LoginMpinMapper.getUserDetails(userId, mpin);
		Response response = setupApi.getUserTokenViaMPIN(userMpin, jwtToken);
		if (response.statusCode() == 200 && Objects.nonNull(response)) {
			String string = response.jsonPath().getString("data.accesstoken");
			setupApi.setToken(string);
		} else
			throw new SkipException("Couldnt generate MPIN Access Token for User .Hence skipping tests");
		System.out.println("User Mpin Token = " + setupApi.getToken());
		return setupApi.getToken();
	}

	public Response getOrderBook() {
		return setupApi.getAllOrderDetails();
	}

	public Response getPositions() {
		return setupApi.getAllPostions();
	}

	public Response callPortfolioAdvioryApi() {
		return setupApi.callPortfolioAdvisoryApi(setupApi.getNonTradingAccessTokenId());
	}

	// public Response callWithdrawListAPI(String party_code) {
	// GetWithdrawListPOJO getWithdrawList =
	// GetWithdrawListData.getWithdrawListData(party_code);
	// Response response = setupApi.getWithdrawList(getWithdrawList);
	// return response;
	// }

	public String genLoginToken(String mobileNum, String emailId, String password, String oldTradeToken) {

		LoginOtpPOJO otpDetails = GetLoginOTP.getOtp(mobileNum);
		Response response = setupApi.getLoginToken(otpDetails, oldTradeToken);
		if (response.statusCode() == 200 && Objects.nonNull(response)) {
			String reqId = response.jsonPath().getString("data.request_id");
			setupApi.setRequest_id(reqId);
		} else
			throw new SkipException("Couldnt generate Access Token for User .Hence skipping tests");
		System.out.println("User request id = " + setupApi.getRequest_id());
		return setupApi.getRequest_id();
	}

	public String verifyLoginToken(String requestId, String mobileNum, String otp, String clientId) {
		VerifyLoginOtpPOJO verifyOtp = VerifyLoginOtpMapper.verifyOtp(requestId, mobileNum, otp);
		Response response = setupApi.verifyLoginToken(verifyOtp);
		if (response.statusCode() == 200 && Objects.nonNull(response)) {
			String nonTradeToken = response.jsonPath()
					.getString("data.PartyCodeDetails." + clientId + ".non_trading_access_token");
			setupApi.setNonTradingAccessTokenId(nonTradeToken);

		} else
			throw new SkipException("Couldnt generate Access Token for User .Hence skipping tests");
		System.out.println("Non Trading access token " + setupApi.getNonTradingAccessTokenId());
		return setupApi.getNonTradingAccessTokenId();
	}

	public CreateBasketPOJO createBasketData(String token, String scripExchg, String exchgName,
			String scripIsin, String symbolName, String details, String expiryDate, String tradeSymbol,
			String producttype, String exchange, String ordertype, String price, Integer qty, String variety) {
		CreateBasketPOJO basketDataEq = null;
		switch (exchange) {
		case "NFO":
			basketDataEq = CreateBasketMapper.createBasketForFNO( token,  scripExchg,  exchgName,
					 scripIsin,  symbolName,  details,  expiryDate,  tradeSymbol,
					 producttype,  exchange,  ordertype,  price,  qty,  variety);
			break;
		case "BSE":
		case "NSE":
			basketDataEq = CreateBasketMapper.createBasketForEquity( token,  scripExchg,  exchgName,
					 scripIsin,  symbolName,  details,  expiryDate,  tradeSymbol,
					 producttype,  exchange,  ordertype,  price,  qty,  variety);
			break;
		case "MCX":
			basketDataEq = CreateBasketMapper.createBasketForCommodityMCX( token,  scripExchg,  exchgName,
					 scripIsin,  symbolName,  details,  expiryDate,  tradeSymbol,
					 producttype,  exchange,  ordertype,  price,  qty,  variety);
			break;
		case "NCDEX":
			basketDataEq = CreateBasketMapper.createBasketForCommodityNCDEX( token,  scripExchg,  exchgName,
					 scripIsin,  symbolName,  details,  expiryDate,  tradeSymbol,
					 producttype,  exchange,  ordertype,  price,  qty,  variety);
			break;
		case "CDS":
			basketDataEq = CreateBasketMapper.createBasketFoCurrency( token,  scripExchg,  exchgName,
					 scripIsin,  symbolName,  details,  expiryDate,  tradeSymbol,
					 producttype,  exchange,  ordertype,  price,  qty,  variety);
			break;
		default:
			System.out.println("Invalid Segment");
		}

		return basketDataEq;
	}

	public Response callCreateBasketApi(List<CreateBasketPOJO> basketData) {
		JSONObject obj = new JSONObject();
		obj.put("orders", basketData);
		obj.put("basketName", "BASKET-"+UUID.randomUUID().toString());
		System.out.println(obj.toString());
		Response response = setupApi.invokeCreateBasket(obj);
		return response;
	}

	@SneakyThrows
	public String refreshToken(String userDetails) {
		String[] creden = userDetails.split(":");
		String clientId = creden[3];
		Properties prop = Helper.readPropertiesFile("src/test/resources/api-data.properties");
		String oldTradeToken = prop.getProperty(clientId);
		RefreshTokenPOJO setRefreshTokenData = RefreshTokenMapper.setRefreshTokenData(oldTradeToken);
		Response response = setupApi.refreshToken(setRefreshTokenData);
		if (response.statusCode() == 200 && Objects.nonNull(response)) {
			String nonTradedToken = response.jsonPath().getString("data.access_token");
			setupApi.setNonTradingAccessTokenId(nonTradedToken);
		} else
			throw new SkipException(
					"Couldnt generate Access Token for User using RefreshToken api .Hence skipping tests");
		System.out.println("Non Trading access token " + setupApi.getNonTradingAccessTokenId());
		return setupApi.getNonTradingAccessTokenId();
	}

	@SneakyThrows
	public String refreshTokenOnly(String oldToken) {
		RefreshTokenPOJO setRefreshTokenData = RefreshTokenMapper.setRefreshTokenData(oldToken);
		Response response = setupApi.refreshToken(setRefreshTokenData);
		if (response.statusCode() == 200 && Objects.nonNull(response)) {
			String nonTradedToken = response.jsonPath().getString("data.access_token");
			setupApi.setNonTradingAccessTokenId(nonTradedToken);
		} else
			throw new SkipException(
					"Couldnt generate Access Token for User using RefreshToken api .Hence skipping tests");
		System.out.println("Non Trading access token " + setupApi.getNonTradingAccessTokenId());
		return setupApi.getNonTradingAccessTokenId();
	}

	public String getNonTradingAccessToken(String userDetails) {
		String[] creden = userDetails.split(":");
		String nonTradedToken = "";
		try {
			String mobileNum = creden[0];
			String emailId = creden[1];
			String password = creden[2];
			String clientId = creden[3];
			Properties prop = Helper.readPropertiesFile("src/test/resources/api-data.properties");
			String oldTradeToken = prop.getProperty(clientId);
			String requestId = genLoginToken(mobileNum, emailId, password, oldTradeToken);
			String otp = helper.getOtpFromMail(emailId, password);
			nonTradedToken = verifyLoginToken(requestId, mobileNum, otp, clientId);
		} catch (ArrayIndexOutOfBoundsException e) {
			System.out.println(
					"mobNumber or emailId or Password Missing in testng xml file .Please provide if willing to use api call");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Issue while generating LoginToken.");
		}
		return nonTradedToken;

	}

	public String getNonTradingAccessTokenWithoutOtp(String userDetails) {
		setUserDetails(userDetails);
		String[] creden = userDetails.split(":");
		String oldTradeToken = "";
		try {
			String clientId = creden[3];
			Properties prop = Helper.readPropertiesFile("src/test/resources/api-data.properties");
			oldTradeToken = prop.getProperty(clientId);
			setupApi.setNonTradingAccessTokenId(oldTradeToken);
		} catch (ArrayIndexOutOfBoundsException e) {
			System.out.println("Client Id missing in tentNG xml file");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Issue while generating nonTradeAccessToken.");
		}
		return oldTradeToken;
	}

	public String setTradeToken(String token) {
		setupApi.setToken(token);
		return setupApi.getToken();
	}

	public Response getMarketBuiltup(String expiry, String sector, String viewName) {
		// setupApi.getNonTradingAccessTokenId()="eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyRGF0YSI6eyJjb3VudHJ5X2NvZGUiOiIiLCJtb2Jfbm8iOiIiLCJ1c2VyX2lkIjoiVTUwMDQ5MjY3Iiwic291cmNlIjoiU1BBUksiLCJhcHBfaWQiOiI1NjU2NyIsImNyZWF0ZWRfYXQiOiIyMDIyLTEyLTMwVDA4OjMwOjU2LjgzMzc1NjA5NFoiLCJkYXRhQ2VudGVyIjoiIn0sImlzcyI6ImFuZ2VsIiwiZXhwIjoxNjcyOTkzODU2LCJpYXQiOjE2NzIzODkwNTZ9.0nRzf39OqjmD9W3aHDqHQHiVhpgiKuxDrXbeBOUhcKw";
		Map<String, Object> params = new HashMap<>();
		params.put("Expiry", expiry);
		params.put("SectorName", sector);
		params.put("ViewName", viewName);
		Response marketResponse = setupApi.call_marketPlace_futureBuiltupHeatmap(setupApi.getNonTradingAccessTokenId(),
				params);
		return marketResponse;
	}

	public Response getSectorHeatMap() {
		// setupApi.getNonTradingAccessTokenId()="eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyRGF0YSI6eyJjb3VudHJ5X2NvZGUiOiIiLCJtb2Jfbm8iOiIiLCJ1c2VyX2lkIjoiVTUwMDQ5MjY3Iiwic291cmNlIjoiU1BBUksiLCJhcHBfaWQiOiI1NjU2NyIsImNyZWF0ZWRfYXQiOiIyMDIyLTEyLTMwVDA4OjMwOjU2LjgzMzc1NjA5NFoiLCJkYXRhQ2VudGVyIjoiIn0sImlzcyI6ImFuZ2VsIiwiZXhwIjoxNjcyOTkzODU2LCJpYXQiOjE2NzIzODkwNTZ9.0nRzf39OqjmD9W3aHDqHQHiVhpgiKuxDrXbeBOUhcKw";
		Response marketResponse = setupApi.callSectorHeatMapApi(setupApi.getNonTradingAccessTokenId());
		return marketResponse;
	}

	public Response getMarketMoversByMost(String Category) {
		// setupApi.getNonTradingAccessTokenId()="eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyRGF0YSI6eyJjb3VudHJ5X2NvZGUiOiIiLCJtb2Jfbm8iOiIiLCJ1c2VyX2lkIjoiVTUwMDQ5MjY3Iiwic291cmNlIjoiU1BBUksiLCJhcHBfaWQiOiI1NjU2NyIsImNyZWF0ZWRfYXQiOiIyMDIzLTAyLTIwVDA1OjIwOjA0Ljc4NjMwMTIxNVoiLCJkYXRhQ2VudGVyIjoiIn0sImlzcyI6ImFuZ2VsIiwiZXhwIjoxNjc5NDYyNDA0LCJpYXQiOjE2NzY4NzA0MDR9.EmgVMP-gNe8mVTd8hj2pwQMhfZm6apv9ArBjfv6Zw5k";
		Map<String, Object> params = new HashMap<>();
		params.put("Category", Category);
		Response marketResponse = setupApi.call_MartketMoversByMost(setupApi.getNonTradingAccessTokenId(), params);
		return marketResponse;
	}

	public Response getFundamentalRatio(String ISIN) {
		// setupApi.getNonTradingAccessTokenId()="eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyRGF0YSI6eyJjb3VudHJ5X2NvZGUiOiIiLCJtb2Jfbm8iOiIiLCJ1c2VyX2lkIjoiVTUwMDQ5MjY3Iiwic291cmNlIjoiU1BBUksiLCJhcHBfaWQiOiI1NjU2NyIsImNyZWF0ZWRfYXQiOiIyMDIzLTAyLTIwVDA1OjIwOjA0Ljc4NjMwMTIxNVoiLCJkYXRhQ2VudGVyIjoiIn0sImlzcyI6ImFuZ2VsIiwiZXhwIjoxNjc5NDYyNDA0LCJpYXQiOjE2NzY4NzA0MDR9.EmgVMP-gNe8mVTd8hj2pwQMhfZm6apv9ArBjfv6Zw5k";
		Map<String, Object> params = new HashMap<>();
		params.put("ISIN", ISIN);
		Response FundamentalRatio = setupApi.call_FundamentalRatio(setupApi.getNonTradingAccessTokenId(), params);
		return FundamentalRatio;
	}

	public Response getStockShareHolder(String ISIN) {
		// setupApi.getNonTradingAccessTokenId()="eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyRGF0YSI6eyJjb3VudHJ5X2NvZGUiOiIiLCJtb2Jfbm8iOiIiLCJ1c2VyX2lkIjoiVTUwMDQ5MjY3Iiwic291cmNlIjoiU1BBUksiLCJhcHBfaWQiOiI1NjU2NyIsImNyZWF0ZWRfYXQiOiIyMDIzLTAyLTIwVDA1OjIwOjA0Ljc4NjMwMTIxNVoiLCJkYXRhQ2VudGVyIjoiIn0sImlzcyI6ImFuZ2VsIiwiZXhwIjoxNjc5NDYyNDA0LCJpYXQiOjE2NzY4NzA0MDR9.EmgVMP-gNe8mVTd8hj2pwQMhfZm6apv9ArBjfv6Zw5k";
		Map<String, Object> params = new HashMap<>();
		params.put("ISIN", ISIN);
		Response StockShareHolder = setupApi.call_stockShareHolderApi(setupApi.getNonTradingAccessTokenId(), params);
		return StockShareHolder;
	}

	public Response getTopGainerNLoser(String category) {
		// setupApi.getNonTradingAccessTokenId()="eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyRGF0YSI6eyJjb3VudHJ5X2NvZGUiOiIiLCJtb2Jfbm8iOiIiLCJ1c2VyX2lkIjoiVTUwMDQ5MjY3Iiwic291cmNlIjoiU1BBUksiLCJhcHBfaWQiOiI1NjU2NyIsImNyZWF0ZWRfYXQiOiIyMDIzLTAyLTIwVDA1OjIwOjA0Ljc4NjMwMTIxNVoiLCJkYXRhQ2VudGVyIjoiIn0sImlzcyI6ImFuZ2VsIiwiZXhwIjoxNjc5NDYyNDA0LCJpYXQiOjE2NzY4NzA0MDR9.EmgVMP-gNe8mVTd8hj2pwQMhfZm6apv9ArBjfv6Zw5k";
		Map<String, Object> params = new HashMap<>();
		params.put("category", category);
		Response FundamentalRatio = setupApi.call_TopGainerNLoser(setupApi.getNonTradingAccessTokenId(), params);
		return FundamentalRatio;
	}

	public Response getIntraTradeDetails(String OptionType, String Symbol, String PriceUnderlying) {
		// setupApi.getNonTradingAccessTokenId()="eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyRGF0YSI6eyJjb3VudHJ5X2NvZGUiOiIiLCJtb2Jfbm8iOiIiLCJ1c2VyX2lkIjoiVTUwMDQ5MjY3Iiwic291cmNlIjoiU1BBUksiLCJhcHBfaWQiOiI1NjU2NyIsImNyZWF0ZWRfYXQiOiIyMDIzLTAyLTIwVDA1OjIwOjA0Ljc4NjMwMTIxNVoiLCJkYXRhQ2VudGVyIjoiIn0sImlzcyI6ImFuZ2VsIiwiZXhwIjoxNjc5NDYyNDA0LCJpYXQiOjE2NzY4NzA0MDR9.EmgVMP-gNe8mVTd8hj2pwQMhfZm6apv9ArBjfv6Zw5k";
		Map<String, Object> params = new HashMap<>();
		params.put("OptionType", OptionType);
		params.put("Symbol", Symbol);
		params.put("PriceUnderlying", PriceUnderlying);
		Response marketResponse = setupApi.callInstaTradeDetailsApi(setupApi.getNonTradingAccessTokenId(), params);
		return marketResponse;
	}

	public Response callFundWithdrawalDataApi(String name, String value) {

		FundWithdrawalPOJO verifyOtp = FundWithdrawalDataMapper.getFundData(name, value);
		Response fundWithdrawalResponse = setupApi.getFundWithdrawalData(verifyOtp);
		return fundWithdrawalResponse;
	}

	public Response callWithdrawalBalanceApi() {

		Response WithdrawalBalanceResponse = setupApi.call_withdrawalBalanceAPi(setupApi.getNonTradingAccessTokenId());
		return WithdrawalBalanceResponse;
	}

	public Response callgetWithdrawListAPI() {

		Response WithdrawListResponse = setupApi.getWithdrawListAPI(setupApi.getNonTradingAccessTokenId());
		return WithdrawListResponse;
	}

	public Response callGetTransactionMergedListAPI() {

		Response transactionMergedListResponse = setupApi
				.getTransactionMergedListAPI(setupApi.getNonTradingAccessTokenId());
		return transactionMergedListResponse;
	}

	public Response callFundRMSLimitApi(String exchange, String product, String segment) {

		FundRmsLimitPOJO fundData = FundRmsLimitMapper.getFundData(exchange, product, segment);
		Response fundRMSResponse = setupApi.getFundRMSLimitData(fundData);
		return fundRMSResponse;
	}

	public Response callMarginAmountApi(String partyCode) {

		MarginAmountPOJO marginData = MarginAmountMapper.getMarginAmountData(partyCode);
		Response marginDataResponse = setupApi.getMarginAmount(marginData);
		return marginDataResponse;
	}

	public Response getWatchLists() {
		// String
		// nonTradedToken="eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyRGF0YSI6eyJjb3VudHJ5X2NvZGUiOiIiLCJtb2Jfbm8iOiIiLCJ1c2VyX2lkIjoiVTUwMDQ5MjY3Iiwic291cmNlIjoiU1BBUksiLCJhcHBfaWQiOiI1NjU2NyIsImNyZWF0ZWRfYXQiOiIyMDIzLTAyLTE5VDAyOjQzOjUwLjIwMDUwMzQ0OFoiLCJkYXRhQ2VudGVyIjoiIn0sImlzcyI6ImFuZ2VsIiwiZXhwIjoxNjc5MzY2NjMwLCJpYXQiOjE2NzY3NzQ2MzB9.t12sYizMDjgVHBSm9rrtmyQkemjnqSz1ds9CEG6Z50w";
		return setupApi.call_getWatchlistAPI(setupApi.getNonTradingAccessTokenId());

	}

	public Response getProfileData() {
		// String
		// nonTradedToken="eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyRGF0YSI6eyJjb3VudHJ5X2NvZGUiOiIiLCJtb2Jfbm8iOiIiLCJ1c2VyX2lkIjoiVTUwMDQ5MjY3Iiwic291cmNlIjoiU1BBUksiLCJhcHBfaWQiOiI1NjU2NyIsImNyZWF0ZWRfYXQiOiIyMDIzLTAyLTE5VDAyOjQzOjUwLjIwMDUwMzQ0OFoiLCJkYXRhQ2VudGVyIjoiIn0sImlzcyI6ImFuZ2VsIiwiZXhwIjoxNjc5MzY2NjMwLCJpYXQiOjE2NzY3NzQ2MzB9.t12sYizMDjgVHBSm9rrtmyQkemjnqSz1ds9CEG6Z50w";
		return setupApi.callGetProfileApi(setupApi.getNonTradingAccessTokenId());

	}

	public Response searchAndgetScripToken(String q, String c, String includeIndices) {
		Map<String, Object> params = new HashMap<>();
		params.put("q", q);
		params.put("c", c);
		params.put("includeIndices", includeIndices);

		return setupApi.callSearchApi(setupApi.getNonTradingAccessTokenId(), params);

	}

	public String decodeJsonResponse(String data) {
		String decodedJson = helper.decodeData(data);
		return decodedJson;
	}

	public String decodeData(String data) {
		String decodedJson = helper.decompressData(data);
		return decodedJson;
	}

	public String encodeJsonData(String data) {
		String encodeData = helper.encodeData(data);
		return encodeData;
	}

	/**
	 * 
	 * @param scriptName
	 * @param category   CURNCYSEG , COMDTYSEG , ALLFUTURES ,ALLOPTIONS
	 *                   ,DERIVATIVESSEG
	 * @return
	 * @throws IOException
	 * @throws InterruptedException
	 */

	public String getSciptTokenFromSearchApi(String scriptName, String category)
			throws IOException, InterruptedException {

		Response searchAndgetScripToken = searchAndgetScripToken(scriptName, category, "false");
		String body = searchAndgetScripToken.asString();
		String tokenValue = null;
		String decodedValue = decodeData(body);

		JSONArray jsonArray = new JSONArray(decodedValue);

		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject object = jsonArray.getJSONObject(i);
			String expiryDate = object.getString("expd");
			String scriptDesc = object.getString("symD");

			if (!expiryDate.isEmpty() && Helper.isExpiryGreaterThanCurrentDateByWeek(expiryDate)) {
				tokenValue = object.getString("token");
				System.out.println("Token id = " + tokenValue);
				System.out.println("Token Scrip Name = " + scriptDesc);
				break;
			}
		}

		return tokenValue;

	}

	public List<String> getSciptTokenAndExpiryFromSearchApi(String scriptName, String category)
			throws IOException, InterruptedException {
		List<String> data = new ArrayList<>();
		Response searchAndgetScripToken = searchAndgetScripToken(scriptName, category, "false");
		String body = searchAndgetScripToken.asString();
		String tokenValue = null;
		String decodedValue = decodeData(body);

		JSONArray jsonArray = new JSONArray(decodedValue);

		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject object = jsonArray.getJSONObject(i);
			String expiryDate = object.getString("expd");
			String scriptDesc = object.getString("symD");
			if (!expiryDate.isEmpty() && Helper.isExpiryGreaterThanCurrentDateByWeek(expiryDate)) {
				tokenValue = object.getString("token");
				data.add(tokenValue);
				data.add(expiryDate);
				System.out.println("Token id = " + tokenValue);
				System.out.println("Token Scrip Name = " + scriptDesc);
				break;
			}
		}

		return data;

	}

	public String getSciptIdforEquity(String scriptName, String exchange) throws IOException, InterruptedException {

		String updatedScripName = scriptName.toUpperCase() + "-" + exchange.toUpperCase();
		Response searchAndgetScripToken = searchAndgetScripToken(scriptName, "CASHSEG", "false");
		String body = searchAndgetScripToken.asString();
		String tokenValue = null;
		String decodedValue = decodeData(body);

		JSONArray jsonArray = new JSONArray(decodedValue);

		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject object = jsonArray.getJSONObject(i);
			String scrip = object.getString("symD");
			if (scrip.equalsIgnoreCase(updatedScripName)) {
				tokenValue = object.getString("token");
				System.out.println("Token id = " + tokenValue);
				System.out.println("Token Scrip Name = " + scrip);
				break;
			}
		}

		return tokenValue;

	}

	/*
	 *
	 * @param scriptName
	 * 
	 * @param segment CURNCYSEG , COMDTYSEG , ALLFUTURES ,ALLOPTIONS ,DERIVATIVESSEG
	 * 
	 * @param exchange FNO=nse_fo, Commodity=mcx_fo,Currency=cde_fo
	 * 
	 * @return
	 * 
	 * @throws IOException
	 * 
	 * @throws InterruptedException
	 */

	public String getLowerPriceScripId(String scriptName, String segment, String exchange, Double expectedAmt)
			throws IOException, InterruptedException {

		Response searchAndgetScripToken = searchAndgetScripToken(scriptName, segment, "false");
		if (searchAndgetScripToken.getStatusCode() == 440) {
			String nonTradingAccessToken = getNonTradingAccessToken(getUserDetails());
			String[] split = getUserDetails().split(":");
			updatePropertyValue("api-data.properties", split[3], nonTradingAccessToken);
			searchAndgetScripToken = searchAndgetScripToken(scriptName, segment, "false");
		}
		String body = searchAndgetScripToken.asString();
		String tokenValue = null;
		String decodedValue = decodeData(body);
		int matchingCount = 0;
		JSONArray jsonArray = new JSONArray(decodedValue);

		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject object = jsonArray.getJSONObject(i);
			String expiryDate = object.getString("expd");
			String scriptDesc = object.getString("symD");

			if (!expiryDate.isEmpty() && Helper.isExpiryGreaterThanCurrentDateByWeek(expiryDate)) {
				tokenValue = object.getString("token");
				String ltpPrice = getLTPPrice(tokenValue, exchange);
				System.out.println("LTP price at " + Helper.dateTime() + " = " + ltpPrice);
				double value = Double.parseDouble(ltpPrice);
				if (value > 0.0 && value < expectedAmt) {
					System.out.println("Token has less price");
					System.out.println("Token id = " + tokenValue);
					System.out.println("Token Scrip Name = " + scriptDesc);
					matchingCount++;
					break;
				}
			}
		}
		if (matchingCount == 0)
			System.out.println("No scrip found which has less price for " + scriptName);
		return tokenValue;

	}

	public String getLowerPriceScripIdWoCEPECheck(String scriptName, String segment, String exchange,
			Double expectedAmt) throws IOException, InterruptedException {

		Response searchAndgetScripToken = searchAndgetScripToken(scriptName, segment, "false");
		if (searchAndgetScripToken.getStatusCode() == 440) {
			String nonTradingAccessToken = getNonTradingAccessToken(getUserDetails());
			String[] split = getUserDetails().split(":");
			updatePropertyValue("api-data.properties", split[3], nonTradingAccessToken);
			searchAndgetScripToken = searchAndgetScripToken(scriptName, segment, "false");
		}
		String body = searchAndgetScripToken.asString();
		String tokenValue = null;
		String decodedValue = decodeData(body);
		int matchingCount = 0;
		JSONArray jsonArray = new JSONArray(decodedValue);

		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject object = jsonArray.getJSONObject(i);
			String expiryDate = object.getString("expd");
			String scriptDesc = object.getString("symD");

			if (!expiryDate.isEmpty() && Helper.isExpiryGreaterThanCurrentDateByWeek(expiryDate)) {
				tokenValue = object.getString("token");
				String ltpPrice = getLTPPrice(tokenValue, exchange);
				System.out.println("LTP price at " + Helper.dateTime() + " = " + ltpPrice);
				double value = Double.parseDouble(ltpPrice);
				if (value > 0.0 && value < expectedAmt) {
					System.out.println("Token has less price");
					System.out.println("Token id = " + tokenValue);
					System.out.println("Token Scrip Name = " + scriptDesc);
					matchingCount++;
					break;
				}
			}
		}
		if (matchingCount == 0)
			System.out.println("No scrip found which has less price for " + scriptName);
		return tokenValue;

	}

	public static void updatePropertyValue(String fileName, String key, String value) {
		String propertyFilePath = "src/test/resources/" + fileName;
		try {
			PropertiesConfiguration conf = new PropertiesConfiguration(propertyFilePath);
			conf.setProperty(key, value);
			conf.save();
			System.out.println("Property file updated successfully!");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void exitPositions() {
		Response callOrdersApi = getOrderBook();// OrderBookAPi
		GetOrdersDetailsResponsePOJO as = callOrdersApi.getBody().as(GetOrdersDetailsResponsePOJO.class);
		List<OrdersDetailsData> data = as.getData();
		List<OrdersDetailsData> datafiltered = data.stream()
				.filter(x -> (x.getOrderstatus().contains("complete") && x.getTransactiontype().equalsIgnoreCase("BUY")
						&& !x.getProducttype().equalsIgnoreCase("CARRYFORWARD")))
				.collect(Collectors.toList());

		System.out.println(" $$$$$$$$$$$ completed orders $$$$$$$$$$$ \n");
		datafiltered.forEach(orderId -> System.out.println(orderId.getOrderid()));

		datafiltered.forEach(orderId -> {
			Response response = placeStockOrder(orderId.getExchange(), orderId.getOrdertype(), orderId.getPrice(),
					orderId.getProducttype(), orderId.getQuantity(), orderId.getStoploss(), orderId.getSymboltoken(),
					orderId.getTradingsymbol(), "SELL", "0.0", "NORMAL");
			System.out.println("Successfully Placed SELL order for " + orderId.getOrderid());
		});
	}

	public Response callGetUserSecurityAPI(String party_code) {
		PledgeGetUserSecurityPOJO getUserSecurity = PledgeGetUserSecurity.getUserSecurityData(party_code);
		Response response = setupApi.getUserSecurity(getUserSecurity);
		return response;
	}

	public Response callGetWithdrawSecurityAPI(String party_code) {
		PledgeGetWithdrawSecurityPOJO getWithdrawSecurity = PledgeGetWithdrawSecurity
				.getWithdrawSecurityData(party_code);
		Response response = setupApi.getWithdrawSecurity(getWithdrawSecurity);
		return response;
	}

	public Response getFutureMarketsIndicators(String ExpiryType, String EnumName) {
		Map<String, Object> params = new HashMap<>();
		params.put("ExpiryType", ExpiryType);
		params.put("EnumName", EnumName);
		Response marketResponse = setupApi.call_future_markets_indicators(setupApi.getNonTradingAccessTokenId(),
				params);
		return marketResponse;
	}

	public Response getOrderCharge(String productType, String transactionType, String quantity, String price,
			String exchange, String isin) {
		String ltpPrice;
		List<OrderChargesPOJO> symbolId = new ArrayList<>();

		OrderChargesPOJO oderchargepojo = OrderChargesMapper.OrderCharges(productType, transactionType, quantity, price,
				exchange, isin);
		symbolId.add(oderchargepojo);
		Response response = setupApi.getOrderCharges(symbolId);

		return response;

	}

	public Response callSecurityInfoApi(String exchange, String symbol) {

		GetSecurityInfoPOJO securitydata = GetSecurityInfoMapper.getsecurityinfo(exchange, symbol);
		Response securityDataResponse = setupApi.getSecurity(securitydata);
		return securityDataResponse;
	}

	public Response callscriptdetailApi(String exchange, String symbol, String flag) {

		getScripDetailPOJO scriptdetaildata = getScripDetailMapper.getScripDetail(exchange, symbol, flag);
		Response securityDataResponse = setupApi.getScriptDetail(scriptdetaildata);
		return securityDataResponse;
	}

	public Response callPledgeGetTransactionAPI(String party_code) {
		PledgeGetTransactionPOJO getTransaction = PledgeGetTransaction.getPledgeTransactionData(party_code);
		Response response = setupApi.getPledgeTransactionAPI(setupApi.getNonTradingAccessTokenId(), getTransaction);
		return response;
	}

	public Response callPledgeStatusAPI(String party_code) {
		PledgeGetStatusPOJO getStatus = PledgeGetStatus.getPledgeStatusData(party_code);
		Response response = setupApi.getPledgeStatusAPI(setupApi.getNonTradingAccessTokenId(), getStatus);
		return response;
	}

	public Response callCreatePledgeAPI(String party_code) {
		CreatePledgePOJO createPledge = CreatePledgeData.createPledgeData(party_code);
		Response response = setupApi.createPledgeAPI(setupApi.getNonTradingAccessTokenId(), createPledge);
		return response;
	}

	public Response callgetPGTransactionList() {
		return setupApi.getPGTransactionListAPI(setupApi.getNonTradingAccessTokenId());

	}

	public Response callgetPGTransaction(String transactionid) {
		Map<String, Object> params = new HashMap<>();
		params.put("transaction_id", transactionid);
		return setupApi.getPGTransactionAPI(setupApi.getNonTradingAccessTokenId(), params);

	}

	public Response callgetPGMergedTransaction(String transactionid) {
		Map<String, Object> params = new HashMap<>();
		params.put("transaction_id", transactionid);
		return setupApi.getPGMergedTransactionAPI(setupApi.getNonTradingAccessTokenId(), params);

	}

	public Response callgetPGQuickAddFunds() {
		return setupApi.getPGTransactionQuickAddFundSugestionAPI(setupApi.getNonTradingAccessTokenId());

	}

	public Response callgetPGTransactionLimit(String products) {
		Map<String, Object> params = new HashMap<>();
		params.put("product", products);
		return setupApi.getPGTransactionLimitAPI(setupApi.getNonTradingAccessTokenId(), params);

	}

	public Response callgetPGTransactionHelpTopic(String pathParams) {

		return setupApi.getPGTransactionHelpTopicAPI(setupApi.getNonTradingAccessTokenId(), pathParams);

	}

	public Response callgetPGActuatorAny() {
		return setupApi.getPGActuatorAnyAPI(setupApi.getNonTradingAccessTokenId());

	}

	public Response callgetPGActuatorInfo() {
		return setupApi.getPGActuatorInfoAPI(setupApi.getNonTradingAccessTokenId());

	}

	public Response callcheckOppositePendingOrderAPI(String stockxchangecode, String symboltoken, String productType,
			String transactionType, String qty) {
		CheckOppositePendingOrderPOJO oppositePendingOrder = CheckOppositePendingOrder
				.getOppositePendingOrderData(stockxchangecode, symboltoken, productType, transactionType, qty);
		Response response = setupApi.checkOppositePendingOrder(oppositePendingOrder);
		return response;
	}

	public Response callgetAllSymbol(String exchange, String page, String limit) {
		Map<String, Object> params = new HashMap<>();
		params.put("exchange", exchange);
		params.put("page", page);
		params.put("limit", limit);
		return setupApi.getAllSymbol(setupApi.getToken(), params);

	}

	/**
	 * 
	 * @param exchange FNO=nse_fo, Commodity=mcx_fo,Currency=cde_fo
	 * @param symbol
	 * @return
	 */
	public Response callgetSecurityInfo(String exchange, String symbol) {
		GetSecurityInfoPOJO securityinfo = getSecurityInfo.getSecurityInfoData(exchange, symbol);
		Response response = setupApi.getSecurityInfo(securityinfo);
		return response;

	}

}
