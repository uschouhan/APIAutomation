package com.angelone.api;

import java.util.Objects;

import org.testng.SkipException;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.angelone.api.pojo.LTPPrice;
import com.angelone.api.pojo.PlaceOrderDetails;
import com.angelone.api.pojo.UserDetails;
import com.angelone.testdataMapper.LTPPriceData;
import com.angelone.testdataMapper.PlaceOrderTestData;
import com.angelone.testdataMapper.UserTestData;

import io.restassured.response.Response;

public class BaseTestApi 
{
	//protected static ThreadLocal <ReqresApi> ReqresApi = new ThreadLocal<String>();
	private final ReqresApi setupApi = new ReqresApi();

	@BeforeTest
	public void setup() throws Exception {
		genUserToken();
	}
	 
	public String getLTPPrice(String scriptId) {
		LTPPrice ltpprice = LTPPriceData.getLTPPrice(scriptId);
		Response response = setupApi.getLTPPrice(ltpprice);
		if(response.statusCode()==200 && Objects.nonNull(response))
			setupApi.ltpPrice=response.jsonPath().getString("data[0].tradePrice");
		else
			throw new SkipException("Couldnt generate Access Token for User .Hence skipping tests");
		System.out.println("LTP price fetched from api === "+setupApi.ltpPrice);
		return setupApi.ltpPrice;
	}

	public String genUserToken() {
		UserDetails userDetails = UserTestData.getUserDetails();
		Response response = setupApi.getUserToken(userDetails);
		if(response.statusCode()==200 && Objects.nonNull(response))
			setupApi.token = response.jsonPath().getString("data.accesstoken");
		else
			throw new SkipException("Couldnt generate Access Token for User .Hence skipping tests");
		System.out.println("User Token = "+setupApi.token);
		return setupApi.token;
	}
	
	public String genUserToken(String userid,String pwd) {
		UserDetails userDetails = UserTestData.getUserDetails(userid,pwd);
		Response response = setupApi.getUserToken(userDetails);
		if(response.statusCode()==200 && Objects.nonNull(response))
			setupApi.token = response.jsonPath().getString("data.accesstoken");
		else
			throw new SkipException("Couldnt generate Access Token for User .Hence skipping tests");
		System.out.println("User Token = "+setupApi.token);
		return setupApi.token;
	}
	
	public Response placeStockOrder(String ordertype,String price,String producttype,
			String symboltoken,String variety)
	{
	    PlaceOrderDetails orderData = PlaceOrderTestData.placeOrder(ordertype,price,producttype,symboltoken,variety);
	    Response response = setupApi.placeOrder(orderData);
	    return response;
	}
	
	public Response placeStockOrder(String exchange,String ordertype,String price,String producttype,
			String quantity,String stoploss,String symboltoken,String tradingsymbol,String transactiontype,String triggerprice,String variety)
	{
		PlaceOrderDetails orderData = PlaceOrderTestData.placeOrder(exchange, ordertype,price,producttype,
				 quantity, stoploss,symboltoken,tradingsymbol,transactiontype,triggerprice,variety);
	    Response response = setupApi.placeOrder(orderData);
	    return response;
	}
}
