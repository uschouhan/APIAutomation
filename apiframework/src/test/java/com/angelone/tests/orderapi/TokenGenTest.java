package com.angelone.tests.orderapi;

import java.io.IOException;
import java.text.DecimalFormat;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.crypto.spec.SecretKeySpec;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.angelone.api.BaseTestApi;
import com.angelone.api.pojo.GetOrdersDetailsResponsePOJO;
import com.angelone.api.pojo.OrdersDetailsData;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.restassured.response.Response;

class TokenGenTest extends BaseTestApi {
	

	@Test(enabled = true)
	void placeOrder() throws IOException {
		String userdetails = "9741636854:chouhan.upendra@gmail.com:nbktqbmmbqqkzdaq:U50049267:2222";
		String secKey = "db3a62b2-45f6-4b6c-a74b-80ce27491bb7";
		
		generateUserToken(userdetails,secKey);
		String pLtp = getLTPPrice("10666");
		//System.out.println("LTP price =" + pLtp);
		Response response = placeStockOrder("MARKET", pLtp, "DELIVERY", "10666","PNB-EQ", "NORMAL");
		//Response response = placeStockOrder("BSE", "MARKET", "0.0","DELIVERY",
				//"1", "0","500113", "SAIL", "BUY","0.0","NORMAL");
		String OrderId = response.jsonPath().getString("data.orderid");

		// String OrderId="221214000901644";
		//Response cancelOrderResponse = cancelOrder(OrderId, "AMO");
		//Assert.assertTrue(cancelOrderResponse.getStatusCode() == 200, "Place order not successful");
	}

	@Test(enabled = false)
	void getOrders() throws IOException {
		Response callOrdersApi = getOrderBook();//OrderBookAPi 
		GetOrdersDetailsResponsePOJO as = callOrdersApi.getBody().as(GetOrdersDetailsResponsePOJO.class);
		List<OrdersDetailsData> data = as.getData();
		List<OrdersDetailsData> datafiltered = data.stream()
				.filter(x -> !(x.getOrderstatus().contains("CANCELLED") || x.getOrderstatus().contains("REJECTED")))
				.collect(Collectors.toList());

		datafiltered.forEach(orderId -> {
			Response cancelOrderResponse = cancelOrder(orderId.getOrderid(), "AMO");
		});
	}
	
	@Test(enabled = false)
	void testNonTradedAccessToken() throws IOException {
		String userdetails = "9741636854:chouhan.upendra@gmail.com:nbktqbmmbqqkzdaq:U50049267:KD9Vh0";
		getNonTradingAccessToken(userdetails);
		Response marketBuiltupResponse = getMarketBuiltup("NEAR", "All", "Short Covering");
		String data = marketBuiltupResponse.jsonPath().getString("data");
		decodeJsonResponse(data);
	}
	
	@Test(enabled = false)
	void testMpinAccessToken() throws IOException {
		String userdetails = "9741636854:chouhan.upendra@gmail.com:nbktqbmmbqqkzdaq:U50049267:2222";
		String secKey = "db3a62b2-45f6-4b6c-a74b-80ce27491bb7";
		
		generateUserToken(userdetails,secKey);
		String pLtp = getLTPPrice("500113","bse_cm");
		String ltpPrice =   BuyroundoffValueToCancelOrder(pLtp); 
		//Response response = placeStockOrder("BSE", "LIMIT", ltpPrice,"DELIVERY",
				//"1", "0","500113", "SAIL", "BUY","0.0","AMO");
		//Response response = placeStockOrder("NSE", "LIMIT", pLtp,"DELIVERY",
				//"1", "0","1491", "IFCI-EQ", "BUY","0.0","AMO");
		//String ltoPrice = buyValueCustomPriceForCurrency(pLtp) ;
		//Response response = placeStockOrder("CDS", "LIMIT", ltoPrice,"CARRYFORWARD",
				//"1", "0","8741", "USDINR23106FUT", "BUY","0.0","AMO");
		//String OrderId = response.jsonPath().getString("data.orderid");
		
	}
	
	public String BuyroundoffValueToCancelOrder(String ltp1) {
		double lt = Double.parseDouble(ltp1);
		double per = lt * 2 / 100;
		double buyPrice = (lt - per) * 10;
		double roundOff = Math.round(buyPrice);
		double FinalBuyPrice = roundOff / 10;
		System.out.print(FinalBuyPrice);
		return String.valueOf(FinalBuyPrice);
	}
	 
	public  String buyValueCustomPriceForCurrency(String ltp) 
	{
		double lt = Double.parseDouble(ltp);
		double per = lt * 2/ 100;
		double buyPrice = (lt - per) * 10;
		double roundOff = Math.round(buyPrice);
		double FinalBuyPrice = roundOff / 10;
		return String.valueOf(FinalBuyPrice);
	}
	
	
	@Test
	public void checkPrecision() throws Exception {
		
		//String text = "824825000";
		String text = "549300";
		Double ltp = Double.valueOf(text);
		//ltp = ltp / 10000000;
		ltp = ltp / 100;
		//DecimalFormat df = new DecimalFormat("#.##");
		//df.format(ltp);
		String ltpPrice = String.valueOf(ltp);
		System.out.println(ltpPrice);
		
	}

	
	@Test(enabled=false)
	public void watchlist() throws Exception {
		
		System.out.println("Learning how to display watchlist in reportdashboard");
		
	}
	
	@Test(enabled=false)
	public void Orders() throws Exception {
		System.out.println("Learning how to display orders in reportdashboard");
		Assert.fail("OrderPage not displayed");
	}

	@Test(enabled=false)
	public void genJTWToken() throws Exception {
		String secret = "db3a62b2-45f6-4b6c-a74b-80ce27491bb7";

		SecretKeySpec hmacKey = new SecretKeySpec(secret.getBytes("UTF-8"), 
		                            SignatureAlgorithm.HS256.getJcaName());

		Instant now = Instant.now();
		long futureTime = now.plus(365l, ChronoUnit.DAYS).toEpochMilli();
		long pastTime = now.minus(1l, ChronoUnit.DAYS).toEpochMilli();
		System.out.println("exp time = "+futureTime);
		System.out.println("iat time = "+pastTime);
		UserDataJWT data = new UserDataJWT();
		Map<String,Object> userData = new HashMap<>();
		userData.put("userData", data);
		String jwtToken = Jwts.builder()
				.addClaims(userData)
		        .claim("iss", "angel")
		        .claim("exp", futureTime)
		        .claim("iat", pastTime)
		        .setSubject("JWT key")
		        .setId(UUID.randomUUID().toString())
		        .setIssuedAt(Date.from(now))
		        .setExpiration(Date.from(now.plus(2l, ChronoUnit.DAYS)))
		        .signWith(hmacKey)
		        .compact();
		
		System.out.println("JWT token === > "+jwtToken);
	}
	
}
