package com.angelone.api;

import java.text.DecimalFormat;
import java.util.Objects;

import org.testng.SkipException;
import org.testng.annotations.BeforeTest;

import com.angelone.api.pojo.CancelOrderPOJO;
import com.angelone.api.pojo.LTPPricePOJO;
import com.angelone.api.pojo.PlaceOrderDetailsPOJO;
import com.angelone.api.pojo.UserDetailsPOJO;
import com.angelone.testdataMapper.CancelOrderData;
import com.angelone.testdataMapper.LTPPriceData;
import com.angelone.testdataMapper.PlaceOrderTestData;
import com.angelone.testdataMapper.UserTestData;

import io.restassured.response.Response;

public class BaseTestApi {
	// protected static ThreadLocal <ReqresApi> ReqresApi = new
	// ThreadLocal<String>();
	private final InvokeApis setupApi = new InvokeApis();

	@BeforeTest
	public void setup() throws Exception {
		genUserToken();
	}

	public String getLTPPrice(String scriptId) {
		String ltpPrice;
		LTPPricePOJO ltpprice = LTPPriceData.getLTPPrice(scriptId);
		Response response = setupApi.getLTPPrice(ltpprice);
		if (response.statusCode() == 200 && Objects.nonNull(response)) {
			ltpPrice = response.jsonPath().getString("data[0].tradePrice");
			Double ltp = Double.valueOf(ltpPrice);
			ltp = ltp / 100;
			DecimalFormat df = new DecimalFormat("#.##");
			df.format(ltp);
			ltpPrice = String.valueOf(ltp);
		} else
			throw new SkipException("Couldnt generate Access Token for User .Hence skipping tests");
		System.out.println("LTP price fetched from api === " + ltpPrice);
		return ltpPrice;
	}

	public String genUserToken() {
		UserDetailsPOJO userDetails = UserTestData.getUserDetails();
		Response response = setupApi.getUserToken(userDetails);
		if (response.statusCode() == 200 && Objects.nonNull(response))
			setupApi.token = response.jsonPath().getString("data.accesstoken");
		else
			throw new SkipException("Couldnt generate Access Token for User .Hence skipping tests");
		System.out.println("User Token = " + setupApi.token);
		return setupApi.token;
	}

	public String genUserToken(String userid, String pwd) {
		UserDetailsPOJO userDetails = UserTestData.getUserDetails(userid, pwd);
		Response response = setupApi.getUserToken(userDetails);
		if (response.statusCode() == 200 && Objects.nonNull(response))
			setupApi.token = response.jsonPath().getString("data.accesstoken");
		else
			throw new SkipException("Couldnt generate Access Token for User .Hence skipping tests");
		System.out.println("User Token = " + setupApi.token);
		return setupApi.token;
	}

	public Response placeStockOrder(String ordertype, String price, String producttype, String symboltoken,
			String variety) {
		PlaceOrderDetailsPOJO orderData = PlaceOrderTestData.placeOrder(ordertype, price, producttype, symboltoken,
				variety);
		Response response = setupApi.placeOrder(orderData);
		return response;
	}

	public Response placeStockOrder(String ordertype, String price, String producttype, String symboltoken,String tradingsymbol,
			String variety) {
		PlaceOrderDetailsPOJO orderData = PlaceOrderTestData.placeOrder(ordertype, price, producttype, symboltoken,tradingsymbol,
				variety);
		Response response = setupApi.placeOrder(orderData);
		return response;
	}
	
	public Response placeStockOrder(String exchange, String ordertype, String price, String producttype,
			String quantity, String stoploss, String symboltoken, String tradingsymbol, String transactiontype,
			String triggerprice, String variety) {
		PlaceOrderDetailsPOJO orderData = PlaceOrderTestData.placeOrder(exchange, ordertype, price, producttype, quantity,
				stoploss, symboltoken, tradingsymbol, transactiontype, triggerprice, variety);
		Response response = setupApi.placeOrder(orderData);
		return response;
	}
	
	public Response cancelOrder(String orderId, String variety) {
		CancelOrderPOJO orderData = CancelOrderData.cancelOrder(orderId,variety);
		Response response = setupApi.cancelOrder(orderData);
		return response;
	}
	
	public void generateUserToken(String userCredentials) {
		String[] creden = userCredentials.split(":");
		try {
			String userId= creden[3];
			String password = creden[4];
			genUserToken(userId, password);
		} 
		catch (ArrayIndexOutOfBoundsException e) {
			System.out.println("UserId and Password Missing in testng xml file .Please provide if willing to use api call");
		}
		catch (Exception e) {
			System.out.println("Issue while generating token.");
		}
	}
	
	public Response callOrdersApi() 
	{
		return setupApi.getAllOrderDetails();
	}
}
