package com.angelone.tests.orderapi;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.testng.annotations.Test;

import com.angelone.api.BaseTestApi;
import com.angelone.api.pojo.GetOrdersDetailsResponsePOJO;
import com.angelone.api.pojo.OrdersDetailsData;

import io.restassured.response.Response;

class TokenGenTest extends BaseTestApi {
	


	@Test(enabled = false)
	void placeOrder() throws IOException {
		//String pLtp = getLTPPrice("10666");
		//System.out.println("LTP price =" + pLtp);
		//Response response = placeStockOrder("MARKET", pLtp, "DELIVERY", "10666","PNB-EQ", "NORMAL");
		Response response = placeStockOrder("BSE", "MARKET", "0.0","DELIVERY",
				"1", "0","500113", "SAIL", "BUY","0.0","NORMAL");
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
	
	@Test(enabled = true)
	void testNonTradedAccessToken() throws IOException {
		String userdetails = "9741636854:chouhan.upendra@gmail.com:nbktqbmmbqqkzdaq:U50049267:KD9Vh0";
		getNonTradingAccessToken(userdetails);
		Response marketBuiltupResponse = getMarketBuiltup("NEAR", "All", "Short Covering");
		String data = marketBuiltupResponse.jsonPath().getString("data");
		decodeJsonResponse(data);
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

}
