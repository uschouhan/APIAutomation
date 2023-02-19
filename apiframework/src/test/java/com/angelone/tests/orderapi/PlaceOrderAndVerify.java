package com.angelone.tests.orderapi;

import java.io.IOException;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.angelone.api.BaseTestApi;
import com.angelone.api.utility.Helper;

import io.restassured.response.Response;

class PlaceOrderAndVerify {
	BaseTestApi baseAPI;
	Helper helper = new Helper();
	private static final String SEC_KEY = "db3a62b2-45f6-4b6c-a74b-80ce27491bb7";

	@Parameters({ "UserCredentials" })
	@BeforeClass
	public void Setup(String userDetails) {
		baseAPI = new BaseTestApi();
		// Generate User Mpin Token
		baseAPI.generateUserToken(userDetails, SEC_KEY);
		// Generate NonTraded Access Token
		baseAPI.getNonTradingAccessToken(userDetails);
	}

	@Test(enabled = true)
	void placeBuyOrder() throws IOException {

		String pLtp = baseAPI.getLTPPrice("10666", "nse_cm");
		String ltpPrice = helper.BuyroundoffValueToCancelOrder(pLtp);
		System.out.println("Post rounding off LTP value = " + ltpPrice);
		// Place Market Orders
		Response response = baseAPI.placeStockOrder("LIMIT", ltpPrice, "DELIVERY", "10666", "PNB-EQ", "AMO");
		Assert.assertTrue(response.getStatusCode() == 200, "some error in placeOrder api ");
		Assert.assertEquals(response.jsonPath().getString("message"), "SUCCESS", "Error in PlaceOrderAPI ");
		String orderNum = response.jsonPath().getString("data.orderid");

		// Cancel Order
		Response cancelOrderResponse = baseAPI.cancelOrder(orderNum, "AMO");
		Assert.assertTrue(cancelOrderResponse.getStatusCode() == 200, "some error in placeOrder api ");

	}

	@Test(enabled = true)
	void placeBuyOrderCurrency() throws IOException {

		String pLtp = baseAPI.getLTPPrice("1151", "cde_fo");
		String ltpPrice = helper.buyValueCustomPriceForCurrency(pLtp);
		System.out.println("Post rounding off LTP value = " + ltpPrice);
		// Place Market Orders
		Response response = baseAPI.placeStockOrder("CDS", "LIMIT", ltpPrice, "DELIVERY", "1", "0", "1151",
				"USDINR23DECFUT", "BUY", "0.0", "AMO");
		Assert.assertTrue(response.getStatusCode() == 200, "some error in placeOrderCurrency api ");
		Assert.assertEquals(response.jsonPath().getString("message"), "SUCCESS", "Error in placeOrderCurrency api ");
		String orderNum = response.jsonPath().getString("data.orderid");

		// Cancel Order
		Response cancelOrderResponse = baseAPI.cancelOrder(orderNum, "AMO");
		Assert.assertTrue(cancelOrderResponse.getStatusCode() == 200, "some error in placeOrderCurrency api ");

	}

	@Test(enabled = true)
	void placeSellOrder() throws IOException {
		// Generate User Mpin Token

		String pLtp = baseAPI.getLTPPrice("5948", "nse_cm");
		String ltpPrice = helper.BuyroundoffValueToCancelOrder(pLtp);
		System.out.println("Post rounding off LTP value = " + ltpPrice);
		// Place Market Orders
		Response response = baseAPI.placeStockOrder("NSE", "LIMIT", ltpPrice, "INTRADAY", "1", "0", "5948",
				"SOUTHBANK-EQ", "SELL", "0.0", "AMO");
		Assert.assertTrue(response.getStatusCode() == 200, "some error in placeOrder api ");
		Assert.assertEquals(response.jsonPath().getString("message"), "SUCCESS", "Error in PlaceOrderAPI ");
		String orderNum = response.jsonPath().getString("data.orderid");

		// Cancel Order
		Response cancelOrderResponse = baseAPI.cancelOrder(orderNum, "AMO");
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
	public void testBSE_EquityCharts() throws Exception {

		Response bseChartsEquity = baseAPI.getBSEChartsEquity(1, "Req", "18.505200", "OHLCV", "I", "h", 1,
				"2023-02-01T11:00:00+05:30", "2023-02-02T11:00:00+05:30");
		Assert.assertTrue(bseChartsEquity.getStatusCode() == 200, "Invalid Response");

		Response nseChartsEquity = baseAPI.getNSEChartsEquity(1, "Req", "17.10604", "OHLCV", "I", "h", 1,
				"2023-02-01T11:00:00+05:30", "2023-02-02T11:00:00+05:30");
		Assert.assertTrue(nseChartsEquity.getStatusCode() == 200, "Invalid Response");

		Response nseChartsCurrency = baseAPI.getNSEChartsCurrency(1, "Req", "12.1292", "OHLCV", "I", "h", 1,
				"2023-02-01T11:00:00+05:30", "2023-02-02T11:00:00+05:30");
		Assert.assertTrue(nseChartsCurrency.getStatusCode() == 200, "Invalid Response");

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
