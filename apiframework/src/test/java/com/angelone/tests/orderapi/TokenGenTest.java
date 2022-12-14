package com.angelone.tests.orderapi;

import java.io.IOException;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.angelone.api.BaseTestApi;
import com.angelone.api.pojo.PlaceOrderDetailsPOJO;
import com.angelone.testdataMapper.PlaceOrderTestData;

import io.restassured.response.Response;

class TokenGenTest extends BaseTestApi{

  @Test(enabled=false)
  void placeOrder() throws IOException {
	String pLtp= getLTPPrice("1491");
	System.out.println("LTP price ="+ pLtp);
    Response response =   placeStockOrder("MARKET",pLtp,"DELIVERY","1491","AMO");
    String OrderId= response.jsonPath().getString("data.orderid");
    
	//String OrderId="221214000901644";
    Response cancelOrderResponse = cancelOrder(OrderId,"AMO");
    Assert.assertTrue(cancelOrderResponse.getStatusCode()==200, "Place order not successful");
    
  }
}
