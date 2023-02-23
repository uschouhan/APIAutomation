package com.angelone.api;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import org.testng.SkipException;
import org.testng.annotations.BeforeTest;

import com.angelone.api.pojo.CancelOrderPOJO;
import com.angelone.api.pojo.ChartsAPIPOJO;
import com.angelone.api.pojo.GetOrdersDetailsResponsePOJO;
import com.angelone.api.pojo.LTPPricePOJO;
import com.angelone.api.pojo.LoginMpinPOJO;
import com.angelone.api.pojo.LoginOtpPOJO;
import com.angelone.api.pojo.OptionsPOJO;
import com.angelone.api.pojo.OrdersDetailsData;
import com.angelone.api.pojo.PlaceOrderDetailsPOJO;
import com.angelone.api.pojo.UserDataJWT_POJO;
import com.angelone.api.pojo.UserDetailsPOJO;
import com.angelone.api.pojo.VerifyLoginOtpPOJO;
import com.angelone.api.utility.Helper;
import com.angelone.testdataMapper.CancelOrderData;
import com.angelone.testdataMapper.ChartsTestData;
import com.angelone.testdataMapper.GetLoginOTP;
import com.angelone.testdataMapper.LTPPriceData;
import com.angelone.testdataMapper.LoginMpinMapper;
import com.angelone.testdataMapper.OptionsDataMapper;
import com.angelone.testdataMapper.PlaceOrderTestData;
import com.angelone.testdataMapper.UserDATAJWTMapper;
import com.angelone.testdataMapper.UserTestData;
import com.angelone.testdataMapper.VerifyLoginOtpMapper;

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
	
	public Response cancelOrder(String orderId, String variety) {
		CancelOrderPOJO orderData = CancelOrderData.cancelOrder(orderId, variety);
		Response response = setupApi.cancelOrder(orderData);
		return response;
	}
	
	public Response getBSEChartsEquity(int seqno, String action,String topic,String rtype,String period,
			String type,int duration,String from,String to) {
		ChartsAPIPOJO chartsData = ChartsTestData.getChartsData(seqno, action,topic,rtype,period,type,duration,from,to);
		Response response = setupApi.getBSEEquityCharts(chartsData);
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
		//String nonTradedToken="eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyRGF0YSI6eyJjb3VudHJ5X2NvZGUiOiIiLCJtb2Jfbm8iOiIiLCJ1c2VyX2lkIjoiVTUwMDQ5MjY3Iiwic291cmNlIjoiU1BBUksiLCJhcHBfaWQiOiI1NjU2NyIsImNyZWF0ZWRfYXQiOiIyMDIzLTAyLTE5VDAyOjQzOjUwLjIwMDUwMzQ0OFoiLCJkYXRhQ2VudGVyIjoiIn0sImlzcyI6ImFuZ2VsIiwiZXhwIjoxNjc5MzY2NjMwLCJpYXQiOjE2NzY3NzQ2MzB9.t12sYizMDjgVHBSm9rrtmyQkemjnqSz1ds9CEG6Z50w";
		Response response = setupApi.getHolding(setupApi.nonTradingAccessTokenId);
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

	private String genUserToken(String userId, String mpin, String secret) {

		UserDataJWT_POJO userDetails = UserDATAJWTMapper.getUserDetails(userId);
		String jwtToken = helper.genJTWToken(userDetails, secret);
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
	
	public Response getMarketMoversByMost(String Category) {
		//setupApi.nonTradingAccessTokenId="eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyRGF0YSI6eyJjb3VudHJ5X2NvZGUiOiIiLCJtb2Jfbm8iOiIiLCJ1c2VyX2lkIjoiVTUwMDQ5MjY3Iiwic291cmNlIjoiU1BBUksiLCJhcHBfaWQiOiI1NjU2NyIsImNyZWF0ZWRfYXQiOiIyMDIzLTAyLTIwVDA1OjIwOjA0Ljc4NjMwMTIxNVoiLCJkYXRhQ2VudGVyIjoiIn0sImlzcyI6ImFuZ2VsIiwiZXhwIjoxNjc5NDYyNDA0LCJpYXQiOjE2NzY4NzA0MDR9.EmgVMP-gNe8mVTd8hj2pwQMhfZm6apv9ArBjfv6Zw5k";
		Map<String, Object> params = new HashMap<>();
		params.put("Category", Category);
		Response marketResponse = setupApi.call_MartketMoversByMost(setupApi.nonTradingAccessTokenId,
				params);
		return marketResponse;
	}
	
	
	public Response getWatchLists() {
		//String nonTradedToken="eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyRGF0YSI6eyJjb3VudHJ5X2NvZGUiOiIiLCJtb2Jfbm8iOiIiLCJ1c2VyX2lkIjoiVTUwMDQ5MjY3Iiwic291cmNlIjoiU1BBUksiLCJhcHBfaWQiOiI1NjU2NyIsImNyZWF0ZWRfYXQiOiIyMDIzLTAyLTE5VDAyOjQzOjUwLjIwMDUwMzQ0OFoiLCJkYXRhQ2VudGVyIjoiIn0sImlzcyI6ImFuZ2VsIiwiZXhwIjoxNjc5MzY2NjMwLCJpYXQiOjE2NzY3NzQ2MzB9.t12sYizMDjgVHBSm9rrtmyQkemjnqSz1ds9CEG6Z50w";
		return setupApi.call_getWatchlistAPI(setupApi.nonTradingAccessTokenId);
		
	}
	
	
	public String decodeJsonResponse(String data) {
		String decodedJson = helper.decodeData(data);
		return decodedJson;
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

}
