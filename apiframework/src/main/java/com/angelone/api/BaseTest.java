package com.angelone.api;

import java.util.Objects;

import org.testng.SkipException;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.angelone.api.pojo.LTPPrice;
import com.angelone.api.pojo.UserDetails;
import com.angelone.testdataMapper.LTPPriceData;
import com.angelone.testdataMapper.UserTestData;

import io.restassured.response.Response;

public class BaseTest 
{
	protected ReqresApi setupApi = new ReqresApi();

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
	
	
}
