package com.angelone.tests.orderapi;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import javax.crypto.spec.SecretKeySpec;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
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
		//Generate User Mpin Token
		String userdetails = "9741636854:upendra101087@gmail.com:qeewrwwqzycawdcs:U50049267:2222";
		String secKey = "db3a62b2-45f6-4b6c-a74b-80ce27491bb7";
		generateUserToken(userdetails, secKey);

		//Place two AMO Orders 
		Response response1 = placeStockOrder("MARKET", "0.0", "DELIVERY", "10666", "PNB-EQ", "AMO");
		Assert.assertTrue(response1.getStatusCode()==200,"some error in placeOrder api ");
		/*
		 * Response response2 = placeStockOrder("NSE", "MARKET", "0.0", "INTRADAY",
		 * "1491", "IFCI-EQ", "AMO"); Assert.assertTrue(response2.getStatusCode()==
		 * 200,"some error in placeOrder api ");
		 * 
		 * //Call gerOrderBook api to collect all orders Response callOrdersApi =
		 * getOrderBook(); Assert.assertTrue(callOrdersApi.getStatusCode()==
		 * 200,"some error in callOrdersApi api "); GetOrdersDetailsResponsePOJO as =
		 * callOrdersApi.getBody().as(GetOrdersDetailsResponsePOJO.class);
		 * List<OrdersDetailsData> data = as.getData();
		 * 
		 * //Filter all Open orders and call cancelOrder Api to cancel
		 * List<OrdersDetailsData> datafiltered = data.stream() .filter(x ->
		 * !(x.getOrderstatus().contains("CANCELLED") ||
		 * x.getOrderstatus().contains("REJECTED"))) .collect(Collectors.toList());
		 * datafiltered.forEach(orderId -> { Response cancelOrderResponse =
		 * cancelOrder(orderId.getOrderid(), "AMO");
		 * Assert.assertTrue(cancelOrderResponse.getStatusCode()==
		 * 200,"some error in callOrdersApi api "); });
		 */
	}

	
	
	
	
	


	@Test(enabled = true)
	void testExitPositions() throws IOException {
		String userdetails = "9741636854:upendra101087@gmail.com:qeewrwwqzycawdcs:U50049267:2222";
		String secKey = "db3a62b2-45f6-4b6c-a74b-80ce27491bb7";
		generateUserToken(userdetails, secKey);
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
		});
	}

	@Test(enabled = false)
	void testNonTradedAccessToken() throws IOException {
		String userdetails = "9741636854:upendra101087@gmail.com:qeewrwwqzycawdcs:U50049267:2222";
		getNonTradingAccessToken(userdetails);
		Response marketBuiltupResponse = getMarketBuiltup("NEAR", "All", "Short Covering");
		String data = marketBuiltupResponse.jsonPath().getString("data");
		decodeJsonResponse(data);
	}

	@Test(enabled = false)
	public void testGetWatchlist() throws Exception {
		String userdetails = "9741636854:upendra101087@gmail.com:qeewrwwqzycawdcs:U50049267:2222";
		getNonTradingAccessToken(userdetails);
		Response watchlists = getWatchLists();
		Assert.assertEquals(watchlists.getStatusCode(),200,"Error in watchlists api ");
		Assert.assertEquals(watchlists.jsonPath().getString("status"),"success","Status doesnt match in watchlists api ");
		Assert.assertEquals(watchlists.jsonPath().getString("error_code"),"","error_code doesnt match in watchlists api ");
		Assert.assertTrue(!watchlists.jsonPath().getString("data").isEmpty(),"data is empty in watchlists api ");
		Assert.assertTrue(!watchlists.jsonPath().getString("data.watchlistData").isEmpty(),"watchlistData is empty in watchlists api ");
	}

	
	@Test(enabled = true)
	public void testBSE_EquityCharts() throws Exception {
		
		//Response bseChartsEquity = getBSEChartsEquity(1,"Req","18.505200","OHLCV","I","h",1,"2023-02-01T11:00:00+05:30","2023-02-02T11:00:00+05:30");
		//Assert.assertTrue(bseChartsEquity.getStatusCode()==200,"Invalid Response");
		
		//Response nseChartsEquity = getNSEChartsEquity(1,"Req","17.10604","OHLCV","I","h",1,"2023-02-01T11:00:00+05:30","2023-02-02T11:00:00+05:30");
		//Assert.assertTrue(nseChartsEquity.getStatusCode()==200,"Invalid Response");
		
		Response nseChartsCurrency = getNSEChartsCurrency(1,"Req","12.1292","OHLCV","I","h",1,"2023-02-01T11:00:00+05:30","2023-02-02T11:00:00+05:30");
		Assert.assertTrue(nseChartsCurrency.getStatusCode()==200,"Invalid Response");
		
	}
	
	@Test(enabled=true)
	public void testGetHolding() throws Exception {
		String userdetails = "9741636854:upendra101087@gmail.com:qeewrwwqzycawdcs:U50049267:2222";
		getNonTradingAccessToken(userdetails);
		Response holding = getHolding();
		Assert.assertTrue(holding.getStatusCode()==200,"Invalid Response for getHolding API");
	}
	
	
	@Test
	public void testGetPositions() throws Exception {
		
		String userdetails = "9741636854:upendra101087@gmail.com:qeewrwwqzycawdcs:U50049267:2222";
		String secKey = "db3a62b2-45f6-4b6c-a74b-80ce27491bb7";
		generateUserToken(userdetails, secKey);
		Response positions = getPositions();
		Assert.assertTrue(positions.getStatusCode()==200,"Invalid Response for getPostion API");
	}
	
	@Test(enabled = true)
	void testMpinAccessToken() throws IOException {
		String userdetails = "9741636854:upendra101087@gmail.com:qeewrwwqzycawdcs:U50049267:2222";
		// String userdetails =
		// "9742000367:sateeshbavana@gmail.com:jwppymyurxuttagh:S304062:2222";
		String secKey = "db3a62b2-45f6-4b6c-a74b-80ce27491bb7";

		generateUserToken(userdetails, secKey);
		//String pLtp = getLTPPrice("500113", "bse_cm");
		String pLtp = getLTPPrice("252905","mcx_fo");
		// String pLtp = getLTPPrice("8741","cde_fo");

		// String ltpPrice = BuyroundoffValueToCancelOrder(pLtp);
		// Response response = placeStockOrder("BSE", "LIMIT", ltpPrice,"DELIVERY",
		// "1", "0","500113", "SAIL", "BUY","0.0","AMO");
		// Response response = placeStockOrder("NSE", "LIMIT", pLtp,"DELIVERY",
		// "1", "0","1491", "IFCI-EQ", "BUY","0.0","AMO");
		 String ltoPrice = buyValueCustomPriceForCurrency(pLtp) ;
		Response response = placeStockOrder("MCX", "LIMIT", ltoPrice, "DELIVERY", "1", "0", "252905", "GOLDPETAL28APRFUT",
				"BUY", "0.0", "NORMAL");
		// String OrderId = response.jsonPath().getString("data.orderid");

	}
	
	@Test(enabled = true)
	void testPlaceCurrencyOrder() throws IOException {
		String userdetails = "9741636854:upendra101087@gmail.com:qeewrwwqzycawdcs:U50049267:2222";
		// String userdetails =
		// "9742000367:sateeshbavana@gmail.com:jwppymyurxuttagh:S304062:2222";
		String secKey = "db3a62b2-45f6-4b6c-a74b-80ce27491bb7";

		generateUserToken(userdetails, secKey);
		//String pLtp = getLTPPrice("500113", "bse_cm");
		String pLtp = getLTPPrice("1151","cde_fo");
		// String pLtp = getLTPPrice("8741","cde_fo");

		// String ltpPrice = BuyroundoffValueToCancelOrder(pLtp);
		// Response response = placeStockOrder("BSE", "LIMIT", ltpPrice,"DELIVERY",
		// "1", "0","500113", "SAIL", "BUY","0.0","AMO");
		// Response response = placeStockOrder("NSE", "LIMIT", pLtp,"DELIVERY",
		// "1", "0","1491", "IFCI-EQ", "BUY","0.0","AMO");
		 String ltoPrice = buyValueCustomPriceForCurrency(pLtp) ;
		Response response = placeStockOrder("CDS", "LIMIT", ltoPrice, "DELIVERY", "1", "0", "1151", "USDINR23DECFUT",
				"BUY", "0.0", "AMO");
		// String OrderId = response.jsonPath().getString("data.orderid");

	}
	
	
	
	@Test(enabled = false)
	void getOrders() throws IOException {

		Response callOrdersApi = getOrderBook();// OrderBookAPi
		GetOrdersDetailsResponsePOJO as = callOrdersApi.getBody().as(GetOrdersDetailsResponsePOJO.class);
		List<OrdersDetailsData> data = as.getData();
		List<OrdersDetailsData> datafiltered = data.stream()
				.filter(x -> !(x.getOrderstatus().contains("CANCELLED") || x.getOrderstatus().contains("REJECTED")))
				.collect(Collectors.toList());

		datafiltered.forEach(orderId -> {
			Response cancelOrderResponse = cancelOrder(orderId.getOrderid(), "AMO");
		});

		// Response response = placeStockOrder("BSE", "MARKET", "0.0","DELIVERY",
		// "1", "0","500113", "SAIL", "BUY","0.0","NORMAL");
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

	public String buyValueCustomPriceForCurrency(String ltp) {
		double lt = Double.parseDouble(ltp);
		double per = lt * 2 / 100;
		double buyPrice = (lt - per) * 10;
		double roundOff = Math.round(buyPrice);
		double FinalBuyPrice = roundOff / 10;
		return String.valueOf(FinalBuyPrice);
	}

	@Test
	public void checkPrecision() throws Exception {

		// String text = "824825000";
		String text = "549300";
		Double ltp = Double.valueOf(text);
		// ltp = ltp / 10000000;
		ltp = ltp / 100;
		// DecimalFormat df = new DecimalFormat("#.##");
		// df.format(ltp);
		String ltpPrice = String.valueOf(ltp);
		System.out.println(ltpPrice);

	}

	@Test(enabled = false)
	public void watchlist() throws Exception {

		System.out.println("Learning how to display watchlist in reportdashboard");

	}

	@Test(enabled = true)
	public void Orders() throws Exception {
		//System.out.println("Learning how to display orders in reportdashboard");
		//Assert.fail("OrderPage not displayed");
		BigDecimal totalChargeCal1 = new BigDecimal(868.50);
		BigDecimal totalChargeAmount1 = new BigDecimal(121.40);
		int longTotalChargeCal = totalChargeCal1.intValue();
		int longTotalChargeAmount = totalChargeAmount1.intValue();
		
		System.out.println(longTotalChargeCal + "\n" + longTotalChargeAmount);
	}

	@Test(enabled = false)
	public void genJTWToken() throws Exception {
		String secret = "db3a62b2-45f6-4b6c-a74b-80ce27491bb7";

		SecretKeySpec hmacKey = new SecretKeySpec(secret.getBytes("UTF-8"), SignatureAlgorithm.HS256.getJcaName());

		Instant now = Instant.now();
		long futureTime = now.plus(365l, ChronoUnit.DAYS).toEpochMilli();
		long pastTime = now.minus(1l, ChronoUnit.DAYS).toEpochMilli();
		System.out.println("exp time = " + futureTime);
		System.out.println("iat time = " + pastTime);
		UserDataJWT data = new UserDataJWT();
		Map<String, Object> userData = new HashMap<>();
		userData.put("userData", data);
		String jwtToken = Jwts.builder().addClaims(userData).claim("iss", "angel").claim("exp", futureTime)
				.claim("iat", pastTime).setSubject("JWT key").setId(UUID.randomUUID().toString())
				.setIssuedAt(Date.from(now)).setExpiration(Date.from(now.plus(2l, ChronoUnit.DAYS))).signWith(hmacKey)
				.compact();

		System.out.println("JWT token === > " + jwtToken);
	}

	@Test
	public void exception() throws Exception {

		// BooleanSupplier b = ()->true;
		// Exceptions.silence().getAsBoolean(b).orElseThrow(()->new
		// SkipException("checking test"));

		WebDriver driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.get("https://www.zickty.com/gziptotext/");

	}

}
