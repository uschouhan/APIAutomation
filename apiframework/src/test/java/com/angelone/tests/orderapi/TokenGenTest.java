package com.angelone.tests.orderapi;

import java.io.IOException;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.angelone.api.BaseTest;
import com.angelone.api.pojo.PlaceOrderDetails;
import com.angelone.testdataMapper.PlaceOrderTestData;

import io.restassured.response.Response;

class TokenGenTest extends BaseTest{

  @Test
  void placeOrder() throws IOException {
    PlaceOrderDetails orderData = PlaceOrderTestData.placeOrder();
    Response response = setupApi.placeOrder(orderData);
    Assert.assertTrue(response.getStatusCode()==200, "Place order not successful");
  }
}
