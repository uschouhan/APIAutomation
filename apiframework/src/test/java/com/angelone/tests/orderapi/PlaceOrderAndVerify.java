package com.angelone.tests.orderapi;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.function.BooleanSupplier;
import java.util.stream.Collectors;

import javax.crypto.spec.SecretKeySpec;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.angelone.api.BaseTestApi;
import com.angelone.api.pojo.GetOrdersDetailsResponsePOJO;
import com.angelone.api.pojo.OrdersDetailsData;
import com.machinezoo.noexception.Exceptions;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.restassured.response.Response;

class PlaceOrderAndVerify extends BaseTestApi {

	@Parameters({"UserCredentials"})
	@Test(enabled = true)
	void placeOrder(String userDetails) throws IOException {
		// Generate User Mpin Token
		String secKey = "db3a62b2-45f6-4b6c-a74b-80ce27491bb7";
		generateUserToken(userDetails, secKey);
		
		// Place  Market Orders
		Response response = placeStockOrder("MARKET", "0.0", "DELIVERY", "1491", "IRFC-EQ", "NORMAL");
		Assert.assertTrue(response.getStatusCode() == 200, "some error in placeOrder api ");
		String orderNum= response.jsonPath().getString("data.orderid");

		//Validate Order and Exit 
		Response callOrdersApi = getOrderBook();// OrderBookAPi
		GetOrdersDetailsResponsePOJO as = callOrdersApi.getBody().as(GetOrdersDetailsResponsePOJO.class);
		List<OrdersDetailsData> data = as.getData();
		
		for(int i=0;i<data.size();i++)
		{
			String orderId=data.get(i).getOrderid();
			if(orderId.equalsIgnoreCase(orderNum))
			{
				Assert.assertTrue(data.get(i).getExchange().equalsIgnoreCase("NSE"),"Exchange doenst match");
				System.out.println("###### Exchange is matching ######");
				Assert.assertTrue(data.get(i).getOrdertype().equalsIgnoreCase("MARKET"),"Exchange doenst match");
				System.out.println("###### OrderType is matching ######");
				Assert.assertTrue(data.get(i).getProducttype().equalsIgnoreCase("DELIVERY"),"Exchange doenst match");
				System.out.println("###### ProduceType is matching ######");
				Assert.assertTrue(data.get(i).getTradingsymbol().equalsIgnoreCase("IRFC-EQ"),"Exchange doenst match");
				System.out.println("###### Symbol is matching ######");
				
				//Exit Order
				placeStockOrder(data.get(i).getExchange(), data.get(i).getOrdertype(), data.get(i).getPrice(),
						data.get(i).getProducttype(), data.get(i).getQuantity(), data.get(i).getStoploss(), data.get(i).getSymboltoken(),
						data.get(i).getTradingsymbol(), "SELL", "0.0", "NORMAL");
				break;
			
			}	
		}
		
	}
}
