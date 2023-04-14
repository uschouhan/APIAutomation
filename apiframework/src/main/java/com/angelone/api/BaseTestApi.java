package com.angelone.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import com.angelone.api.pojo.*;
import com.angelone.testdataMapper.*;
import lombok.SneakyThrows;
import org.testng.SkipException;

import com.angelone.api.utility.Helper;

import io.restassured.response.Response;

public class BaseTestApi {
	// protected static ThreadLocal <ReqresApi> ReqresApi = new
	// ThreadLocal<String>();
	private final InvokeApis setupApi = new InvokeApis();
	private final Helper helper = new Helper();

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
		return response;
	}

	public Response placeStockOrder(String exchange, String ordertype, String price, String producttype,
			String symboltoken, String tradingsymbol, String variety) {
		PlaceOrderDetailsPOJO orderData = PlaceOrderTestData.placeOrder(exchange, ordertype, price, producttype,
				symboltoken, tradingsymbol, variety);
		Response response = setupApi.placeOrder(orderData);
		return response;
	}

	public Response placeStockOrder(String exchange, String ordertype, String price, String producttype,
			String quantity, String stoploss, String symboltoken, String tradingsymbol, String transactiontype,
			String triggerprice, String variety) {
		PlaceOrderDetailsPOJO orderData = PlaceOrderTestData.placeOrder(exchange, ordertype, price, producttype,
				quantity, stoploss, symboltoken, tradingsymbol, transactiontype, triggerprice, variety);
		Response response = setupApi.placeOrder(orderData);
		return response;
	}

	public Response placeStockOrder(String basketID, String disclosedquantity,String duration,String exchange,String multiplier,
			String orderValidityDate,String ordertag,String ordertype,String precision,String price,String producttype,
			String quantity,String squareoff,String stoploss,String strategyCode,String symboltoken,String tickSize,String tradingsymbol,
			String trailTickYesNo,String trailingStopLoss,String transactiontype,String triggerprice,String variety)
	{
		PlaceOrderDetailsPOJO orderData = PlaceOrderTestData.placeOrder(basketID,disclosedquantity,duration,exchange,multiplier,orderValidityDate,ordertag,
				ordertype,precision,price,producttype,quantity,squareoff,stoploss,strategyCode,symboltoken,tickSize,tradingsymbol,trailTickYesNo,trailingStopLoss,transactiontype,triggerprice,variety);
		Response response = setupApi.placeOrder(orderData);
		return response;
	}
	
	
	public Response modifyStockOrder(String disclosedquantity,String duration,String exchange,String multiplier,
			String orderValidityDate,String orderId,String ordertype,String precision,String price,
			String quantity,String symboltoken,String triggerprice,String variety) {
		ModifyOrderPOJO orderData = ModifyOrderMapper.modifyOrder(disclosedquantity, duration, exchange, multiplier, orderValidityDate, orderId, ordertype, precision, price,
				 quantity, symboltoken, triggerprice, variety);
		Response response = setupApi.modifyOrderApi(orderData);
		return response;
	}
	
	public Response cancelOrder(String orderId, String variety) {
		CancelOrderPOJO orderData = CancelOrderData.cancelOrder(orderId, variety);
		Response response = setupApi.cancelOrder(orderData);
		return response;
	}

	public Response callSetWatchListApi(Integer version, String watchlistData) {
		SetWatchListPOJO watchListData = SetWatchListDataMapper.getWatchListData(version, watchlistData);
		Response response = setupApi.call_setWatchlistAPI(setupApi.nonTradingAccessTokenId,watchListData);
		return response;
	}

	public Response getBSEChartsEquity(int seqno, String action,String topic,String rtype,String period,
			String type,int duration,String from,String to) {
		ChartsAPIPOJO chartsData = ChartsTestData.getChartsData(seqno, action,topic,rtype,period,type,duration,from,to);
		Response response = setupApi.getBSEEquityCharts(chartsData);
		return response;
	}
	
	
	public Response getMCXCharts(int seqno, String action,String topic,String rtype,String period,
			String type,int duration,String from,String to) {
		ChartsAPIPOJO chartsData = ChartsTestData.getChartsData(seqno, action,topic,rtype,period,type,duration,from,to);
		Response response = setupApi.callMCXChartsApi(chartsData);
		return response;
	}
	
	public Response getNSEChartsEquity(int seqno, String action,String topic,String rtype,String period,
			String type,int duration,String from,String to) {
		ChartsAPIPOJO chartsData = ChartsTestData.getChartsData(seqno, action,topic,rtype,period,type,duration,from,to);
		Response response = setupApi.getNSEEquityCharts(chartsData);
		return response;
	}
	
	public Response getNSEChartsCurrency(int seqno, String action,String topic,String rtype,String period,
			String type,int duration,String from,String to) {
		ChartsAPIPOJO chartsData = ChartsTestData.getChartsData(seqno, action,topic,rtype,period,type,duration,from,to);
		Response response = setupApi.getNSECurrencyCharts(chartsData);
		return response;
	}
	
	public Response getNSEChartsFNO(int seqno, String action,String topic,String rtype,String period,
			String type,int duration,String from,String to) {
		ChartsAPIPOJO chartsData = ChartsTestData.getChartsData(seqno, action,topic,rtype,period,type,duration,from,to);
		Response response = setupApi.getNSE_FNO_Charts(chartsData);
		return response;
	}
	
	public Response callOptionsAPI(String stockxchangecode, String expirydate) {
		OptionsPOJO chartsData = OptionsDataMapper.getOptionsData(stockxchangecode,expirydate);
		Response response = setupApi.getOptions(chartsData);
		return response;
	}

	public Response getHolding() {
		//String nonTradedToken="eyJhbGciOiJIUzUxMiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE2NzgxODU5OTEsImlhdCI6MTY3ODA5NTAyNiwib21uZW1hbmFnZXJpZCI6Mywic291cmNlaWQiOiI1Iiwic3ViIjoiUzgzMzQwMiJ9.wmJl3P3LT9_XeqQEPgc_bAVSn2Uzz3P7W6R4zQ5BqWPaXFzMF60ruWfyC8o5Bjj4hFToXpMiuE6gIyYEJlfFkg";
		Response response = setupApi.getHolding(setupApi.nonTradingAccessTokenId);
		return response;
	}

	public Response getIpoDetails() {
		//String nonTradedToken="eyJhbGciOiJIUzUxMiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE2NzgxODU5OTEsImlhdCI6MTY3ODA5NTAyNiwib21uZW1hbmFnZXJpZCI6Mywic291cmNlaWQiOiI1Iiwic3ViIjoiUzgzMzQwMiJ9.wmJl3P3LT9_XeqQEPgc_bAVSn2Uzz3P7W6R4zQ5BqWPaXFzMF60ruWfyC8o5Bjj4hFToXpMiuE6gIyYEJlfFkg";
		Response response = setupApi.callIpoMasterApi(setupApi.nonTradingAccessTokenId);
		return response;
	}
	
	public void generateUserToken(String userCredentials, String secret) {
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
		if (response.statusCode() == 200 && Objects.nonNull(response))
			setupApi.token = response.jsonPath().getString("data.accesstoken");
		else
			throw new SkipException("Couldnt generate MPIN Access Token for User .Hence skipping tests");
		System.out.println("User Mpin Token = " + setupApi.token);
		return setupApi.token;
	}

	public Response getOrderBook() {
		return setupApi.getAllOrderDetails();
	}

	public Response getPositions() {
		return setupApi.getAllPostions();
	}

	public Response callPortfolioAdvioryApi() {
		return setupApi.callPortfolioAdvisoryApi(setupApi.nonTradingAccessTokenId);
	}
	
	public String genLoginToken(String mobileNum, String emailId, String password) {

		LoginOtpPOJO otpDetails = GetLoginOTP.getOtp(mobileNum);
		Response response = setupApi.getLoginToken(otpDetails);
		if (response.statusCode() == 200 && Objects.nonNull(response))
			setupApi.request_id = response.jsonPath().getString("data.request_id");
		else
			throw new SkipException("Couldnt generate Access Token for User .Hence skipping tests");
		System.out.println("User request id = " + setupApi.request_id);
		return setupApi.request_id;
	}

	public String verifyLoginToken(String requestId, String mobileNum, String otp, String clientId) {
		VerifyLoginOtpPOJO verifyOtp = VerifyLoginOtpMapper.verifyOtp(requestId, mobileNum, otp);
		Response response = setupApi.verifyLoginToken(verifyOtp);
		if (response.statusCode() == 200 && Objects.nonNull(response))
			setupApi.nonTradingAccessTokenId = response.jsonPath()
					.getString("data.PartyCodeDetails." + clientId + ".non_trading_access_token");
		else
			throw new SkipException("Couldnt generate Access Token for User .Hence skipping tests");
		System.out.println("Non Trading access token " + setupApi.nonTradingAccessTokenId);
		return setupApi.token;
	}

	public String getNonTradingAccessToken(String userDetails) {
		String[] creden = userDetails.split(":");
		String nonTradedToken = "";
		try {
			String mobileNum = creden[0];
			String emailId = creden[1];
			String password = creden[2];
			String clientId = creden[3];
			String requestId = genLoginToken(mobileNum, emailId, password);
			String otp = helper.getOTPmail(emailId, password);
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

	public Response getMarketBuiltup(String expiry, String sector, String viewName) {
		// setupApi.nonTradingAccessTokenId="eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyRGF0YSI6eyJjb3VudHJ5X2NvZGUiOiIiLCJtb2Jfbm8iOiIiLCJ1c2VyX2lkIjoiVTUwMDQ5MjY3Iiwic291cmNlIjoiU1BBUksiLCJhcHBfaWQiOiI1NjU2NyIsImNyZWF0ZWRfYXQiOiIyMDIyLTEyLTMwVDA4OjMwOjU2LjgzMzc1NjA5NFoiLCJkYXRhQ2VudGVyIjoiIn0sImlzcyI6ImFuZ2VsIiwiZXhwIjoxNjcyOTkzODU2LCJpYXQiOjE2NzIzODkwNTZ9.0nRzf39OqjmD9W3aHDqHQHiVhpgiKuxDrXbeBOUhcKw";
		Map<String, Object> params = new HashMap<>();
		params.put("Expiry", expiry);
		params.put("SectorName", sector);
		params.put("ViewName", viewName);
		Response marketResponse = setupApi.call_marketPlace_futureBuiltupHeatmap(setupApi.nonTradingAccessTokenId,
				params);
		return marketResponse;
	}

	public Response getSectorHeatMap() {
		// setupApi.nonTradingAccessTokenId="eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyRGF0YSI6eyJjb3VudHJ5X2NvZGUiOiIiLCJtb2Jfbm8iOiIiLCJ1c2VyX2lkIjoiVTUwMDQ5MjY3Iiwic291cmNlIjoiU1BBUksiLCJhcHBfaWQiOiI1NjU2NyIsImNyZWF0ZWRfYXQiOiIyMDIyLTEyLTMwVDA4OjMwOjU2LjgzMzc1NjA5NFoiLCJkYXRhQ2VudGVyIjoiIn0sImlzcyI6ImFuZ2VsIiwiZXhwIjoxNjcyOTkzODU2LCJpYXQiOjE2NzIzODkwNTZ9.0nRzf39OqjmD9W3aHDqHQHiVhpgiKuxDrXbeBOUhcKw";
		Response marketResponse = setupApi.callSectorHeatMapApi(setupApi.nonTradingAccessTokenId);
		return marketResponse;
	}


	public Response getMarketMoversByMost(String Category) {
		//setupApi.nonTradingAccessTokenId="eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyRGF0YSI6eyJjb3VudHJ5X2NvZGUiOiIiLCJtb2Jfbm8iOiIiLCJ1c2VyX2lkIjoiVTUwMDQ5MjY3Iiwic291cmNlIjoiU1BBUksiLCJhcHBfaWQiOiI1NjU2NyIsImNyZWF0ZWRfYXQiOiIyMDIzLTAyLTIwVDA1OjIwOjA0Ljc4NjMwMTIxNVoiLCJkYXRhQ2VudGVyIjoiIn0sImlzcyI6ImFuZ2VsIiwiZXhwIjoxNjc5NDYyNDA0LCJpYXQiOjE2NzY4NzA0MDR9.EmgVMP-gNe8mVTd8hj2pwQMhfZm6apv9ArBjfv6Zw5k";
		Map<String, Object> params = new HashMap<>();
		params.put("Category", Category);
		Response marketResponse = setupApi.call_MartketMoversByMost(setupApi.nonTradingAccessTokenId,
				params);
		return marketResponse;
	}

	public Response getFundamentalRatio(String ISIN) {
		//setupApi.nonTradingAccessTokenId="eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyRGF0YSI6eyJjb3VudHJ5X2NvZGUiOiIiLCJtb2Jfbm8iOiIiLCJ1c2VyX2lkIjoiVTUwMDQ5MjY3Iiwic291cmNlIjoiU1BBUksiLCJhcHBfaWQiOiI1NjU2NyIsImNyZWF0ZWRfYXQiOiIyMDIzLTAyLTIwVDA1OjIwOjA0Ljc4NjMwMTIxNVoiLCJkYXRhQ2VudGVyIjoiIn0sImlzcyI6ImFuZ2VsIiwiZXhwIjoxNjc5NDYyNDA0LCJpYXQiOjE2NzY4NzA0MDR9.EmgVMP-gNe8mVTd8hj2pwQMhfZm6apv9ArBjfv6Zw5k";
		Map<String, Object> params = new HashMap<>();
		params.put("ISIN", ISIN);
		Response FundamentalRatio = setupApi.call_FundamentalRatio(setupApi.nonTradingAccessTokenId,
				params);
		return FundamentalRatio;
	}


	public Response getTopGainerNLoser(String category) {
		//setupApi.nonTradingAccessTokenId="eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyRGF0YSI6eyJjb3VudHJ5X2NvZGUiOiIiLCJtb2Jfbm8iOiIiLCJ1c2VyX2lkIjoiVTUwMDQ5MjY3Iiwic291cmNlIjoiU1BBUksiLCJhcHBfaWQiOiI1NjU2NyIsImNyZWF0ZWRfYXQiOiIyMDIzLTAyLTIwVDA1OjIwOjA0Ljc4NjMwMTIxNVoiLCJkYXRhQ2VudGVyIjoiIn0sImlzcyI6ImFuZ2VsIiwiZXhwIjoxNjc5NDYyNDA0LCJpYXQiOjE2NzY4NzA0MDR9.EmgVMP-gNe8mVTd8hj2pwQMhfZm6apv9ArBjfv6Zw5k";
		Map<String, Object> params = new HashMap<>();
		params.put("category", category);
		Response FundamentalRatio = setupApi.call_TopGainerNLoser(setupApi.nonTradingAccessTokenId,
				params);
		return FundamentalRatio;
	}


	public Response getIntraTradeDetails(String OptionType,String Symbol,String PriceUnderlying) {
		//setupApi.nonTradingAccessTokenId="eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyRGF0YSI6eyJjb3VudHJ5X2NvZGUiOiIiLCJtb2Jfbm8iOiIiLCJ1c2VyX2lkIjoiVTUwMDQ5MjY3Iiwic291cmNlIjoiU1BBUksiLCJhcHBfaWQiOiI1NjU2NyIsImNyZWF0ZWRfYXQiOiIyMDIzLTAyLTIwVDA1OjIwOjA0Ljc4NjMwMTIxNVoiLCJkYXRhQ2VudGVyIjoiIn0sImlzcyI6ImFuZ2VsIiwiZXhwIjoxNjc5NDYyNDA0LCJpYXQiOjE2NzY4NzA0MDR9.EmgVMP-gNe8mVTd8hj2pwQMhfZm6apv9ArBjfv6Zw5k";
		Map<String, Object> params = new HashMap<>();
		params.put("OptionType", OptionType);
		params.put("Symbol", Symbol);
		params.put("PriceUnderlying", PriceUnderlying);
		Response marketResponse = setupApi.callInstaTradeDetailsApi(setupApi.nonTradingAccessTokenId,
				params);
		return marketResponse;
	}
	
	public Response callFundWithdrawalDataApi(String name,String value) {

		FundWithdrawalPOJO verifyOtp = FundWithdrawalDataMapper.getFundData(name,value );
		Response fundWithdrawalResponse = setupApi.getFundWithdrawalData(verifyOtp);
		return fundWithdrawalResponse;
	}
	
	public Response callFundRMSLimitApi(String exchange,String product,String segment) {

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
		//String nonTradedToken="eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyRGF0YSI6eyJjb3VudHJ5X2NvZGUiOiIiLCJtb2Jfbm8iOiIiLCJ1c2VyX2lkIjoiVTUwMDQ5MjY3Iiwic291cmNlIjoiU1BBUksiLCJhcHBfaWQiOiI1NjU2NyIsImNyZWF0ZWRfYXQiOiIyMDIzLTAyLTE5VDAyOjQzOjUwLjIwMDUwMzQ0OFoiLCJkYXRhQ2VudGVyIjoiIn0sImlzcyI6ImFuZ2VsIiwiZXhwIjoxNjc5MzY2NjMwLCJpYXQiOjE2NzY3NzQ2MzB9.t12sYizMDjgVHBSm9rrtmyQkemjnqSz1ds9CEG6Z50w";
		//setupApi.nonTradingAccessTokenId="eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyRGF0YSI6eyJjb3VudHJ5X2NvZGUiOiIiLCJtb2Jfbm8iOiI5NzQxNjM2ODU0IiwidXNlcl9pZCI6IlU1MDA0OTI2NyIsInNvdXJjZSI6IlNQQVJLIiwiYXBwX2lkIjoiNTY1NjciLCJjcmVhdGVkX2F0IjoiMjAyMy0wNC0xNFQwNjoxNzowNC44NTYyMDM0MjlaIiwiZGF0YUNlbnRlciI6IiJ9LCJpc3MiOiJhbmdlbCIsImV4cCI6MTY4NDA0NTAyNCwiaWF0IjoxNjgxNDUzMDI0fQ.dkfyErxSOAPTvXPUH88hciGpK2IywsZyfd0WFF9G2YI";
		return setupApi.call_getWatchlistAPI(setupApi.nonTradingAccessTokenId);
		
	}
	
	
	public String decodeJsonResponse(String data) {
		String decodedJson = helper.decodeData(data);
		return decodedJson;
	}

	public String encodeJsonData(String data) {
		String encodeData = helper.encodeData(data);
		return encodeData;
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
		PledgeGetWithdrawSecurityPOJO getWithdrawSecurity = PledgeGetWithdrawSecurity.getWithdrawSecurityData(party_code);
		Response response = setupApi.getWithdrawSecurity(getWithdrawSecurity);
		return response;
	}


	public Response getFutureMarketsIndicators(String ExpiryType, String EnumName) {
		Map<String, Object> params = new HashMap<>();
		params.put("ExpiryType", ExpiryType);
		params.put("EnumName", EnumName);
		Response marketResponse = setupApi.call_future_markets_indicators(setupApi.nonTradingAccessTokenId,
				params);
		return marketResponse;
	}
}
