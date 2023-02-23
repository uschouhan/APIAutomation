package com.angelone.tests.orderapi;

import java.io.IOException;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.angelone.api.BaseTestApi;
import com.angelone.api.utility.Helper;
import com.angelone.config.factory.ApiConfigFactory;

import io.restassured.response.Response;

class ApiSyntheticMonitoring {
	BaseTestApi baseAPI;
	Helper helper = new Helper();
	private static final String SECRET_KEY = ApiConfigFactory.getConfig().secretKey();

	@Parameters({ "UserCredentials" })
	@BeforeClass
	public void Setup(String userDetails) {
		baseAPI = new BaseTestApi();
		// Generate User Mpin Token
		baseAPI.generateUserToken(userDetails, SECRET_KEY);
		// Generate NonTraded Access Token
		baseAPI.getNonTradingAccessToken(userDetails);
	}

	@Test(enabled = true)
	void placeBuyEquityOrder() throws IOException {

		String pLtp = baseAPI.getLTPPrice("10666", "nse_cm");
		String ltpPrice = helper.BuyroundoffValueToCancelOrder(pLtp);
		System.out.println("Post rounding off LTP value = " + ltpPrice);
		// Place Market Orders
		Response response = baseAPI.placeStockOrder("LIMIT", ltpPrice, "DELIVERY", "10666", "PNB-EQ", "NORMAL");
		Assert.assertTrue(response.getStatusCode() == 200, "some error in placeOrder api ");
		Assert.assertEquals(response.jsonPath().getString("message"), "SUCCESS", "Error in PlaceOrderAPI ");
		String orderNum = response.jsonPath().getString("data.orderid");

		// Cancel Order
		Response cancelOrderResponse = baseAPI.cancelOrder(orderNum, "NORMAL");
		Assert.assertTrue(cancelOrderResponse.getStatusCode() == 200, "some error in placeOrder api ");

	}

	@Test(enabled = true)
	void placeBuyCurrencyOrder() throws IOException {
		String currencySymbol = ApiConfigFactory.getConfig().currencySymbol();
		String currencySymbolToken = ApiConfigFactory.getConfig().currencySymbolToken();
		String pLtp = baseAPI.getLTPPrice(currencySymbolToken, "cde_fo");
		String ltpPrice = helper.buyValueCustomPriceForCurrency(pLtp);
		System.out.println("Post rounding off LTP value = " + ltpPrice);
		// Place Market Orders
		Response response = baseAPI.placeStockOrder("CDS", "LIMIT", ltpPrice, "DELIVERY", "1", "0", "1151",
				currencySymbol, "BUY", "0.0", "NORMAL");
		Assert.assertTrue(response.getStatusCode() == 200, "some error in placeOrderCurrency api ");
		Assert.assertEquals(response.jsonPath().getString("message"), "SUCCESS", "Error in placeOrderCurrency api ");
		String orderNum = response.jsonPath().getString("data.orderid");

		// Cancel Order
		Response cancelOrderResponse = baseAPI.cancelOrder(orderNum, "NORMAL");
		Assert.assertTrue(cancelOrderResponse.getStatusCode() == 200, "some error in placeOrderCurrency api ");

	}
	
	@Test(enabled = true)
	void placeBuyFNOOrder() throws IOException {
		String fnoSymbol = ApiConfigFactory.getConfig().fnoSymbol();
		String fnoSymbolToken = ApiConfigFactory.getConfig().fnoSymbolToken();
		String pLtp = baseAPI.getLTPPrice(fnoSymbolToken, "nse_fo");
		String ltpPrice = helper.buyValueCustomPriceForCurrency(pLtp);
		System.out.println("Post rounding off LTP value = " + ltpPrice);
		// Place Market Orders
		Response response = baseAPI.placeStockOrder("NFO", "LIMIT", ltpPrice, "INTRADAY", "1", "0", fnoSymbolToken, fnoSymbol,
				"BUY", "0.0", "NORMAL");
		Assert.assertTrue(response.getStatusCode() == 200, "some error in placeOrderFNO api ");
		Assert.assertEquals(response.jsonPath().getString("message"), "SUCCESS", "Error in placeOrderFNO api ");
		String orderNum = response.jsonPath().getString("data.orderid");

		// Cancel Order
		Response cancelOrderResponse = baseAPI.cancelOrder(orderNum, "NORMAL");
		Assert.assertTrue(cancelOrderResponse.getStatusCode() == 200, "some error in placeOrderFNO api ");

	}
	
	
	@Test(enabled = true)
	void placeBuyComodityOrder() throws IOException {
		
		String comoditySymbol = ApiConfigFactory.getConfig().comoditySymbol();
		String comoditySymbolToken = ApiConfigFactory.getConfig().comoditySymbolToken();
		String pLtp = baseAPI.getLTPPrice(comoditySymbolToken,"mcx_fo");
		String ltpPrice = helper.buyValueTriggerPriceForCommodity(pLtp);
		System.out.println("Post rounding off LTP value = " + ltpPrice);
		// Place Market Orders
		Response response = baseAPI.placeStockOrder("0","0","DAY","MCX","1","","",
				"LIMIT","2",ltpPrice,"INTRADAY","1","0","0","0",comoditySymbolToken,"100.0",comoditySymbol,"NO","0","BUY","0.0","NORMAL");
		
		Assert.assertTrue(response.getStatusCode() == 200, "some error in placeBuyComodityOrder api ");
		Assert.assertEquals(response.jsonPath().getString("message"), "SUCCESS", "Error in placeBuyComodityOrder api ");
		String orderNum = response.jsonPath().getString("data.orderid");

		// Cancel Order
		Response cancelOrderResponse = baseAPI.cancelOrder(orderNum, "NORMAL");
		Assert.assertTrue(cancelOrderResponse.getStatusCode() == 200, "some error in placeBuyComodityOrder api ");

	}
	

	@Test(enabled = true)
	void placeSellOrder() throws IOException {
		// Generate User Mpin Token

		String pLtp = baseAPI.getLTPPrice("5948", "nse_cm");
		String ltpPrice = helper.SellTriggerPriceGreater(pLtp);
		System.out.println("Post rounding off LTP value = " + ltpPrice);
		// Place Market Orders
		Response response = baseAPI.placeStockOrder("NSE", "LIMIT", ltpPrice, "INTRADAY", "1", "0", "5948",
				"SOUTHBANK-EQ", "SELL", "0.0", "NORMAL");
		Assert.assertTrue(response.getStatusCode() == 200, "some error in placeOrder api ");
		Assert.assertEquals(response.jsonPath().getString("message"), "SUCCESS", "Error in PlaceOrderAPI ");
		String orderNum = response.jsonPath().getString("data.orderid");

		// Cancel Order
		Response cancelOrderResponse = baseAPI.cancelOrder(orderNum, "NORMAL");
		Assert.assertTrue(cancelOrderResponse.getStatusCode() == 200, "some error in placeOrder api ");
	}

	@Test(enabled = true)
	public void testGetHolding() throws Exception {

		Response holding = baseAPI.getHolding();
		Assert.assertTrue(holding.getStatusCode() == 200, "Invalid Response for getHolding API");
	}

	@Test(enabled = true)
	public void testGetPositions() throws Exception {

		Response positions = baseAPI.getPositions();
		Assert.assertTrue(positions.getStatusCode() == 200, "Invalid Response for getPostion API");
	}

	@Test(enabled = true)
	void testMarketMoversByMost() throws IOException {
		Response marketMovers = baseAPI.getMarketMoversByMost("MOST_ACT_VALUE");
		Assert.assertEquals(marketMovers.getStatusCode(),200,"Error in marketMovers api ");
		Assert.assertEquals(marketMovers.jsonPath().getString("status"),"success","Status doesnt match in marketMovers api ");
		Assert.assertTrue(!marketMovers.jsonPath().getString("data").isEmpty(),"data is empty in marketMovers api ");
		//String data = marketBuiltupResponse.jsonPath().getString("data");
		//decodeJsonResponse(data);
	}
	
	@Test(enabled = true)
	public void testBSE_EquityCharts() throws Exception {
		
		String bSE_Equity_Topic_value = ApiConfigFactory.getConfig().bSE_Equity_Topic_value();
		String nSE_Equity_Topic_value = ApiConfigFactory.getConfig().nSE_Equity_Topic_value();
		String nSE_CURRENCY_Topic_value = ApiConfigFactory.getConfig().nSE_CURRENCY_Topic_value();
		String nSE_FNO_Topic_value = ApiConfigFactory.getConfig().nSE_FNO_Topic_value();
		String durationType = ApiConfigFactory.getConfig().durationType();
		
		 
		String startTime = helper.getCurrenctTimeMinus(durationType, 1);
		String endTime= helper.getCurrenctTime();
	
		Response bseChartsEquity = baseAPI.getBSEChartsEquity(1, "Req", bSE_Equity_Topic_value, "OHLCV", "I", durationType, 1,
				startTime, endTime);
		Assert.assertTrue(bseChartsEquity.getStatusCode() == 200, "Invalid Response");

		Response nseChartsEquity = baseAPI.getNSEChartsEquity(1, "Req", nSE_Equity_Topic_value, "OHLCV", "I", durationType, 1,
				startTime, endTime);
		Assert.assertTrue(nseChartsEquity.getStatusCode() == 200, "Invalid Response");

		Response nseChartsCurrency = baseAPI.getNSEChartsCurrency(1, "Req", nSE_CURRENCY_Topic_value, "OHLCV", "I", durationType, 1,
				startTime, endTime);
		Assert.assertTrue(nseChartsCurrency.getStatusCode() == 200, "Invalid Response");
		
		Response nseChartsFNO = baseAPI.getNSEChartsFNO(17,"Req",nSE_FNO_Topic_value,"OHLCV","I",durationType,1,startTime,endTime);
		Assert.assertTrue(nseChartsFNO.getStatusCode()==200,"Invalid Response");
		
	}

	@Test(enabled = true)
	public void testGetWatchlist() throws Exception {

		Response watchlists = baseAPI.getWatchLists();
		Assert.assertEquals(watchlists.getStatusCode(), 200, "Error in watchlists api ");
		Assert.assertEquals(watchlists.jsonPath().getString("status"), "success",
				"Status doesnt match in watchlists api ");
		Assert.assertEquals(watchlists.jsonPath().getString("error_code"), "",
				"error_code doesnt match in watchlists api ");
		Assert.assertTrue(!watchlists.jsonPath().getString("data").isEmpty(), "data is empty in watchlists api ");
		Assert.assertTrue(!watchlists.jsonPath().getString("data.watchlistData").isEmpty(),
				"watchlistData is empty in watchlists api ");
	}

	
}
