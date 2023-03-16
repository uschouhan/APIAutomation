package com.angelone.tests.orderapi;

import java.io.IOException;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.angelone.api.BaseClass;
import com.angelone.api.utility.Helper;
import com.angelone.config.factory.ApiConfigFactory;
import com.angelone.reports.ExtentLogger;

import io.restassured.response.Response;

class ApiSyntheticMonitoring extends BaseClass{
	
	
	@Test(enabled = false)
	void placeBuyEquityOrder() throws IOException {

		String pLtp = baseAPI.getLTPPrice("10666", "nse_cm");
		String ltpPrice = helper.BuyroundoffValueToCancelOrder(pLtp);
		System.out.println("Post rounding off LTP value = " + ltpPrice);
		ExtentLogger.info("Post rounding off LTP value = " + ltpPrice);
		// Place Market Orders
		Response response = baseAPI.placeStockOrder("LIMIT", ltpPrice, "DELIVERY", "10666", "PNB-EQ", "NORMAL");
		if(response.getStatusCode() == 200)
		ExtentLogger.info(response.asPrettyString());
		else
		ExtentLogger.fail(response.asPrettyString());	
		Assert.assertTrue(response.getStatusCode() == 200, "some error in placeOrder api ");
		Assert.assertEquals(response.jsonPath().getString("message"), "SUCCESS", "Error in PlaceOrderAPI ");
		String orderNum = response.jsonPath().getString("data.orderid");

		// Cancel Order
		Response cancelOrderResponse = baseAPI.cancelOrder(orderNum, "NORMAL");
		Assert.assertTrue(cancelOrderResponse.getStatusCode() == 200, "some error in placeOrder api ");

	}

	@Test(enabled = false)
	void placeBuyCurrencyOrder() throws IOException {
		String currencySymbol = ApiConfigFactory.getConfig().currencySymbol();
		String currencySymbolToken = ApiConfigFactory.getConfig().currencySymbolToken();
		String pLtp = baseAPI.getLTPPrice(currencySymbolToken, "cde_fo");
		String ltpPrice = helper.buyValueCustomPriceForCurrency(pLtp);
		System.out.println("Post rounding off LTP value = " + ltpPrice);
		ExtentLogger.info("Post rounding off LTP value = " + ltpPrice);
		// Place Market Orders
		Response response = baseAPI.placeStockOrder("CDS", "LIMIT", ltpPrice, "DELIVERY", "1", "0", "1151",
				currencySymbol, "BUY", "0.0", "NORMAL");
		ExtentLogger.info(response.asPrettyString());
		Assert.assertTrue(response.getStatusCode() == 200, "some error in placeOrderCurrency api ");
		Assert.assertEquals(response.jsonPath().getString("message"), "SUCCESS", "Error in placeOrderCurrency api ");
		String orderNum = response.jsonPath().getString("data.orderid");

		// Cancel Order
		Response cancelOrderResponse = baseAPI.cancelOrder(orderNum, "NORMAL");
		Assert.assertTrue(cancelOrderResponse.getStatusCode() == 200, "some error in placeOrderCurrency api ");

	}
	
	@Test(enabled = false)
	void placeBuyFNOOrder() throws IOException {
		String fnoSymbol = ApiConfigFactory.getConfig().fnoSymbol();
		String fnoSymbolToken = ApiConfigFactory.getConfig().fnoSymbolToken();
		String pLtp = baseAPI.getLTPPrice(fnoSymbolToken, "nse_fo");
		String ltpPrice = helper.buyValueCustomPriceForCurrency(pLtp);
		System.out.println("Post rounding off LTP value = " + ltpPrice);
		ExtentLogger.info("Post rounding off LTP value = " + ltpPrice);
		// Place Market Orders
		Response response = baseAPI.placeStockOrder("NFO", "LIMIT", ltpPrice, "INTRADAY", "1", "0", fnoSymbolToken, fnoSymbol,
				"BUY", "0.0", "NORMAL");
		ExtentLogger.info(response.asPrettyString());
		Assert.assertTrue(response.getStatusCode() == 200, "some error in placeOrderFNO api ");
		Assert.assertEquals(response.jsonPath().getString("message"), "SUCCESS", "Error in placeOrderFNO api ");
		String orderNum = response.jsonPath().getString("data.orderid");

		// Cancel Order
		Response cancelOrderResponse = baseAPI.cancelOrder(orderNum, "NORMAL");
		Assert.assertTrue(cancelOrderResponse.getStatusCode() == 200, "some error in placeOrderFNO api ");

	}
	
	
	@Test(enabled = false)
	void placeBuyComodityOrder() throws IOException {
		
		String comoditySymbol = ApiConfigFactory.getConfig().comoditySymbol();
		String comoditySymbolToken = ApiConfigFactory.getConfig().comoditySymbolToken();
		String pLtp = baseAPI.getLTPPrice(comoditySymbolToken,"mcx_fo");
		String ltpPrice = helper.buyValueTriggerPriceForCommodity(pLtp);
		System.out.println("Post rounding off LTP value = " + ltpPrice);
		// Place Market Orders
		Response response = baseAPI.placeStockOrder("0","0","DAY","MCX","1","","",
				"LIMIT","2",ltpPrice,"INTRADAY","1","0","0","0",comoditySymbolToken,"100.0",comoditySymbol,"NO","0","BUY","0.0","NORMAL");
		ExtentLogger.info(response.asPrettyString());
		Assert.assertTrue(response.getStatusCode() == 200, "some error in placeBuyComodityOrder api ");
		Assert.assertEquals(response.jsonPath().getString("message"), "SUCCESS", "Error in placeBuyComodityOrder api ");
		String orderNum = response.jsonPath().getString("data.orderid");

		// Cancel Order
		Response cancelOrderResponse = baseAPI.cancelOrder(orderNum, "NORMAL");
		Assert.assertTrue(cancelOrderResponse.getStatusCode() == 200, "some error in placeBuyComodityOrder api ");

	}
	

	@Test(enabled = false)
	void placeSellOrder() throws IOException {
		// Generate User Mpin Token

		String pLtp = baseAPI.getLTPPrice("5948", "nse_cm");
		String ltpPrice = helper.SellTriggerPriceGreater(pLtp);
		System.out.println("Post rounding off LTP value = " + ltpPrice);
		// Place Market Orders
		Response response = baseAPI.placeStockOrder("NSE", "LIMIT", ltpPrice, "INTRADAY", "1", "0", "5948",
				"SOUTHBANK-EQ", "SELL", "0.0", "NORMAL");
		ExtentLogger.info(response.asPrettyString());
		Assert.assertTrue(response.getStatusCode() == 200, "some error in placeOrder api ");
		Assert.assertEquals(response.jsonPath().getString("message"), "SUCCESS", "Error in PlaceOrderAPI ");
		String orderNum = response.jsonPath().getString("data.orderid");

		// Cancel Order
		Response cancelOrderResponse = baseAPI.cancelOrder(orderNum, "NORMAL");
		Assert.assertTrue(cancelOrderResponse.getStatusCode() == 200, "some error in placeOrder api ");
	}

	@Test(enabled = false)
	public void testGetHolding() throws Exception {

		Response holding = baseAPI.getHolding();
		ExtentLogger.info(holding.asPrettyString());
		Assert.assertTrue(holding.getStatusCode() == 200, "Invalid Response for getHolding API");
	}

	@Test(enabled = false)
	public void testGetPositions() throws Exception {

		Response positions = baseAPI.getPositions();
		ExtentLogger.info(positions.asPrettyString());
		Assert.assertTrue(positions.getStatusCode() == 200, "Invalid Response for getPostion API");
	}

	@Test(enabled = false)
	void testMarketMoversByMost() throws IOException {
		Response marketMovers = baseAPI.getMarketMoversByMost("MOST_ACT_VALUE");
		ExtentLogger.info(marketMovers.asPrettyString());
		Assert.assertEquals(marketMovers.getStatusCode(),200,"Error in marketMovers api ");
		Assert.assertEquals(marketMovers.jsonPath().getString("status"),"success","Status doesnt match in marketMovers api ");
		Assert.assertTrue(!marketMovers.jsonPath().getString("data").isEmpty(),"data is empty in marketMovers api ");
		//String data = marketBuiltupResponse.jsonPath().getString("data");
		//decodeJsonResponse(data);
	}
	
	@Test(enabled = true)
	public void testChartsApi() throws Exception {
		
		String bSE_Equity_Topic_value = ApiConfigFactory.getConfig().bSE_Equity_Topic_value();
		String nSE_Equity_Topic_value = ApiConfigFactory.getConfig().nSE_Equity_Topic_value();
		String nSE_CURRENCY_Topic_value = ApiConfigFactory.getConfig().nSE_CURRENCY_Topic_value();
		String nSE_FNO_Topic_value = ApiConfigFactory.getConfig().nSE_FNO_Topic_value();
		String durationType = ApiConfigFactory.getConfig().durationType();
		String mcxTopic = ApiConfigFactory.getConfig().mcx_Topic_value();
		 
		String startTime = helper.getCurrenctTimeMinus(durationType, 1);
		String endTime= helper.getCurrenctTime();
	
		Response bseChartsEquity = baseAPI.getBSEChartsEquity(1, "Req", bSE_Equity_Topic_value, "OHLCV", "I", durationType, 1,
				startTime, endTime);
		ExtentLogger.info(bseChartsEquity.asPrettyString());
		Assert.assertTrue(bseChartsEquity.getStatusCode() == 200, "Invalid Response");
		ExtentLogger.pass("bseChartsEquity api test passed");
		Response nseChartsEquity = baseAPI.getNSEChartsEquity(1, "Req", nSE_Equity_Topic_value, "OHLCV", "I", durationType, 1,
				startTime, endTime);
		ExtentLogger.info(nseChartsEquity.asPrettyString());
		Assert.assertTrue(nseChartsEquity.getStatusCode() == 200, "Invalid Response");
		ExtentLogger.pass("nseChartsEquity api test passed");
		Response nseChartsCurrency = baseAPI.getNSEChartsCurrency(1, "Req", nSE_CURRENCY_Topic_value, "OHLCV", "I", durationType, 1,
				startTime, endTime);
		ExtentLogger.info(nseChartsCurrency.asPrettyString());
		Assert.assertTrue(nseChartsCurrency.getStatusCode() == 200, "Invalid Response");
		ExtentLogger.pass("nseChartsCurrency api test passed");
		Response nseChartsFNO = baseAPI.getNSEChartsFNO(17,"Req",nSE_FNO_Topic_value,"OHLCV","I",durationType,1,startTime,endTime);
		ExtentLogger.info(nseChartsFNO.asPrettyString());
		Assert.assertTrue(nseChartsFNO.getStatusCode()==200,"Invalid Response");
		ExtentLogger.pass("nseChartsFNO api test passed");
		
		Response mcxChartsApiRespoonse = baseAPI.getMCXCharts(1,"Req",mcxTopic,"OHLCV","I","h",1,startTime,endTime);
		Assert.assertTrue(mcxChartsApiRespoonse.getStatusCode()==200,"Invalid Response for MCX charts api");
		ExtentLogger.pass("mcxChartsApi api test passed");
	}

	@Test(enabled = false)
	public void testGetWatchlist() throws Exception {

		Response watchlists = baseAPI.getWatchLists();
		ExtentLogger.info(watchlists.asPrettyString());
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
