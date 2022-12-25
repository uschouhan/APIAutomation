package com.angelone.tests.orderapi;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.angelone.api.BaseTestApi;
import com.angelone.api.pojo.GetOrdersDetailsResponsePOJO;
import com.angelone.api.pojo.OrdersDetailsData;
import com.angelone.api.pojo.PlaceOrderDetailsPOJO;
import com.angelone.testdataMapper.PlaceOrderTestData;

import io.restassured.response.Response;

class TokenGenTest extends BaseTestApi {

	@Test(enabled = true)
	void placeOrder() throws IOException {
		String pLtp = getLTPPrice("1491");
		System.out.println("LTP price =" + pLtp);
		Response response = placeStockOrder("MARKET", pLtp, "DELIVERY", "1491", "AMO");
		String OrderId = response.jsonPath().getString("data.orderid");

		// String OrderId="221214000901644";
		Response cancelOrderResponse = cancelOrder(OrderId, "AMO");
		Assert.assertTrue(cancelOrderResponse.getStatusCode() == 200, "Place order not successful");

	}

	@Test(enabled = false)
	void getOrders() throws IOException {
		Response callOrdersApi = callOrdersApi();
		GetOrdersDetailsResponsePOJO as = callOrdersApi.getBody().as(GetOrdersDetailsResponsePOJO.class);
		List<OrdersDetailsData> data = as.getData();
		List<OrdersDetailsData> datafiltered = data.stream()
				.filter(x -> !(x.getOrderstatus().contains("CANCELLED") || x.getOrderstatus().contains("REJECTED")))
				.collect(Collectors.toList());

		datafiltered.forEach(orderId -> {
			Response cancelOrderResponse = cancelOrder(orderId.getOrderid(), "AMO");
		});
	}

}
