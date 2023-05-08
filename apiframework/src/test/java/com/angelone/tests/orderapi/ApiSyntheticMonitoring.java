package com.angelone.tests.orderapi;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.SoftAssertions;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.angelone.api.BaseClass;
import com.angelone.api.pojo.GetOrdersDetailsResponsePOJO;
import com.angelone.api.pojo.OrdersDetailsData;
import com.angelone.api.utility.Helper;
import com.angelone.config.factory.ApiConfigFactory;
import com.angelone.reports.ExtentLogger;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

class ApiSyntheticMonitoring extends BaseClass {
	private static final List<String> BUILDUPS = new ArrayList<>(
			Arrays.asList("Long Built Up", "Short Built Up", "Short Covering", "Long Unwinding"));
	private static final List<String> DERIVATIVE_MARKET = new ArrayList<>(
			Arrays.asList("PercPriceGainers", "PercPriceLosers", "PercOIGainers", "PercOILosers"));
	private static final List<String> MARKET_MOVERS_DATA = new ArrayList<>(Arrays.asList("TOPGAINER", "TOPLOSER"));
	private static final List<String> MARKET_MOVERS_BY_MOST_DATA = new ArrayList<>(
			Arrays.asList("MOST_ACT_VOLUMN", "MOST_ACT_VALUE"));

	@Test(enabled = true)
	void placeOrder_Buy_DELIVERY_LimitOrder_Cash() throws IOException, InterruptedException {

		// get LTP price to manipulate Limit Price
		String pLtp = baseAPI.getLTPPrice("10666", "nse_cm");
		String ltpPrice = helper.BuyroundoffValueToCancelOrder(pLtp);
		System.out.println("Post rounding off LTP value = " + ltpPrice);
		ExtentLogger.info("Post rounding off LTP value = " + ltpPrice);
		String variety = helper.orderTypeCheckForEquity();
		// Place Limit Orders
		Response response = baseAPI.placeStockOrder("LIMIT", ltpPrice, "DELIVERY", "10666", "PNB-EQ", variety);
		validateAPIResponse(response);
		String orderNum = response.jsonPath().getString("data.orderid");

		Thread.sleep(1000);

		// Call getOrderBook
		Response callOrdersApi = baseAPI.getOrderBook();
		GetOrdersDetailsResponsePOJO as = callOrdersApi.getBody().as(GetOrdersDetailsResponsePOJO.class);
		List<OrdersDetailsData> data = as.getData();
		List<String> orderIds = data.stream()
				.filter(x -> !(x.getOrderstatus().contains("cancelled") || x.getOrderstatus().contains("rejected")))
				.map(x -> x.getOrderid()).collect(Collectors.toList());

		if (orderIds.contains(orderNum))
			ExtentLogger.info(orderNum + " Order Id found in open order");
		else {
			ExtentLogger.fail(orderNum + " Order Id not found in open order.Probably rejected.Please check manually");
			Assert.fail("Place Order isnt successful as order Id " + orderNum + " doesnt found in Open Order");
		}

		// Cancel Order
		Response cancelOrderResponse = baseAPI.cancelOrder(orderNum, variety);
		Assert.assertTrue(cancelOrderResponse.getStatusCode() == 200, "some error in placeOrder api ");

		Thread.sleep(1000);
		// Verify Order is cancelled
		verifyOrderIsCancelled(orderNum, variety);

	}
	
	@Test(enabled = false)
	void testOrderStatus() throws IOException, InterruptedException {
		
		Response orderStatus = baseAPI.getOrderStatus("230425000002164", "230425000002164");
	}
	
	
	@Test(enabled = true)
	void placeStopLossOrder_Buy_DELIVERY_LimitOrder_Cash() throws IOException, InterruptedException {

		String variety = helper.orderTypeCheckForEquity();
		// get LTP price to manipulate Limit Price
		String pLtp = baseAPI.getLTPPrice("12018", "nse_cm");
		String ltpPrice = helper.BuyroundoffValueToCancelOrder(pLtp);
		String triggerPrice = helper.getTriggerPriceValueForBuy(ltpPrice);
		// Market Order
		Response response = baseAPI.placeStockOrder("NSE", "STOPLOSS_LIMIT", ltpPrice, "DELIVERY", "1", "1", "12018",
				"SUZLON-EQ", "BUY", triggerPrice, variety);
		String orderNum = response.jsonPath().getString("data.orderid");

		Thread.sleep(1000);
		// Call getOrderBook
		Response callOrdersApi = baseAPI.getOrderBook();
		GetOrdersDetailsResponsePOJO as = callOrdersApi.getBody().as(GetOrdersDetailsResponsePOJO.class);
		List<OrdersDetailsData> data = as.getData();
		List<String> orderIds = data.stream()
				.filter(x -> !(x.getOrderstatus().contains("cancelled") || x.getOrderstatus().contains("rejected")))
				.map(x -> x.getOrderid()).collect(Collectors.toList());

		if (orderIds.contains(orderNum))
			ExtentLogger.info(orderNum + " Order Id found in open order");
		else {
			ExtentLogger.fail(orderNum + " Order Id not found in open order.Probably rejected.Please check manually");
			Assert.fail("Place Order isnt successful as order Id " + orderNum + " doesnt found in Open Order");
		}

		// Cancel Order
		Response cancelOrderResponse = baseAPI.cancelOrder(orderNum, variety);
		Assert.assertTrue(cancelOrderResponse.getStatusCode() == 200, "some error in placeOrder api ");

		Thread.sleep(1000);
		// Verify Order is cancelled
		verifyOrderIsCancelled(orderNum, variety);

	}

	@Test(enabled = true)
	void modifyEquityOrder() throws IOException, InterruptedException {

		// get LTP price to manipulate Limit Price
		String pLtp = baseAPI.getLTPPrice("10666", "nse_cm");
		String ltpPrice = helper.BuyroundoffValueToCancelOrder(pLtp);
		System.out.println("Post rounding off LTP value = " + ltpPrice);
		String variety = helper.orderTypeCheckForEquity();
		// Place Market Orders
		Response response = baseAPI.placeStockOrder("LIMIT", ltpPrice, "DELIVERY", "10666", "PNB-EQ", variety);
		String orderNum = response.jsonPath().getString("data.orderid");
		validateAPIResponse(response);
		Thread.sleep(1000);
		// Call getOrderBook
		Response callOrdersApi = baseAPI.getOrderBook();// OrderBookAPi
		GetOrdersDetailsResponsePOJO as = callOrdersApi.getBody().as(GetOrdersDetailsResponsePOJO.class);
		List<OrdersDetailsData> data = as.getData();

		OrdersDetailsData orderIds = data.stream().filter(x -> x.getOrderid().toString().equalsIgnoreCase(orderNum))
				.findFirst().get();

		Response modifyStockOrder = baseAPI.modifyStockOrder("2", orderIds.getDuration(), orderIds.getExchange(),
				orderIds.getMultiplier(), orderIds.getOrderValidityDate(), orderIds.getOrderid(),
				orderIds.getOrdertype(), orderIds.getPrecision(), orderIds.getPrice(), "2", orderIds.getSymboltoken(),
				orderIds.getTriggerprice(), variety);
		validateAPIResponse(modifyStockOrder);
		Thread.sleep(1000);
		// Call getOrderBook
		Response orderBook = baseAPI.getOrderBook();// OrderBookAPi
		GetOrdersDetailsResponsePOJO entity = orderBook.getBody().as(GetOrdersDetailsResponsePOJO.class);
		List<OrdersDetailsData> orderData = entity.getData();

		OrdersDetailsData modifiedOrder = orderData.stream()
				.filter(x -> x.getOrderid().toString().equalsIgnoreCase(orderNum)).findFirst().get();
		Assertions.assertThat(modifiedOrder.getQuantity()).as("Quantity isnt updated").isEqualTo("2");
		Assertions.assertThat(modifiedOrder.getDisclosedquantity()).as("Quantity isnt updated").isEqualTo("2");

		Thread.sleep(1000);
		// Cancel Order
		Response cancelOrderResponse = baseAPI.cancelOrder(orderNum, variety);
		Assert.assertTrue(cancelOrderResponse.getStatusCode() == 200, "some error in placeOrder api ");
		Thread.sleep(1000);
		// Verify Order is cancelled
		verifyOrderIsCancelled(orderNum, variety);

	}

	@Test(enabled = true)
	void placeOrder_Buy_INTRADAY_LimitOrder_Cash() throws IOException, InterruptedException {

		// get LTP price to manipulate Limit Price
		String pLtp = baseAPI.getLTPPrice("10666", "nse_cm");
		String ltpPrice = helper.BuyroundoffValueToCancelOrder(pLtp);
		System.out.println("Post rounding off LTP value = " + ltpPrice);
		ExtentLogger.info("Post rounding off LTP value = " + ltpPrice);
		String variety = helper.orderTypeCheckForEquity();
		// Place Market Orders
		Response response = baseAPI.placeStockOrder("LIMIT", ltpPrice, "INTRADAY", "10666", "PNB-EQ", variety);
		validateAPIResponse(response);
		String orderNum = response.jsonPath().getString("data.orderid");

		Thread.sleep(1000);

		// Call getOrderBook
		Response callOrdersApi = baseAPI.getOrderBook();
		GetOrdersDetailsResponsePOJO as = callOrdersApi.getBody().as(GetOrdersDetailsResponsePOJO.class);
		List<OrdersDetailsData> data = as.getData();
		List<String> orderIds = data.stream()
				.filter(x -> !(x.getOrderstatus().contains("cancelled") || x.getOrderstatus().contains("rejected")))
				.map(x -> x.getOrderid()).collect(Collectors.toList());

		if (orderIds.contains(orderNum))
			ExtentLogger.info(orderNum + " Order Id found in open order");
		else {
			ExtentLogger.fail(orderNum + " Order Id not found in open order.Probably rejected.Please check manually");
			Assert.fail("Place Order isnt successful as order Id " + orderNum + " doesnt found in Open Order");
		}

		// Cancel Order
		Response cancelOrderResponse = baseAPI.cancelOrder(orderNum, variety);
		Assert.assertTrue(cancelOrderResponse.getStatusCode() == 200, "some error in placeOrder api ");

		Thread.sleep(1000);
		// Verify Order is cancelled
		verifyOrderIsCancelled(orderNum, variety);

	}

	@Test(enabled = true)
	void placeOrder_Buy_CARRYFORWARD_LimitOrder_Currency() throws IOException, InterruptedException {
		String currencySymbol = ApiConfigFactory.getConfig().currencySymbol();
		String currencySymbolToken = ApiConfigFactory.getConfig().currencySymbolToken();
		String pLtp = baseAPI.getLTPPrice(currencySymbolToken, "cde_fo");
		String ltpPrice = helper.buyValueCustomPriceForCurrency(pLtp);
		System.out.println("Post rounding off LTP value = " + ltpPrice);
		ExtentLogger.info("Post rounding off LTP value = " + ltpPrice);
		String variety = helper.orderTypeCheckForCurrency();
		// Place Market Orders
		Response response = baseAPI.placeStockOrder("CDS", "LIMIT", ltpPrice, "CARRYFORWARD", "1", "0", "1151",
				currencySymbol, "BUY", "0.0", variety);
		ExtentLogger.info(response.asPrettyString());
		validateAPIResponse(response);
		String orderNum = response.jsonPath().getString("data.orderid");
		Thread.sleep(1000);
		// Call getOrderBook
		Response callOrdersApi = baseAPI.getOrderBook();// OrderBookAPi
		GetOrdersDetailsResponsePOJO as = callOrdersApi.getBody().as(GetOrdersDetailsResponsePOJO.class);
		List<OrdersDetailsData> data = as.getData();

		List<String> orderIds = data.stream().filter(x -> x.getOrderid().toString().equalsIgnoreCase(orderNum))
				.map(x -> x.getOrderid() + " - " + x.getOrderstatus() + " - " + x.getText())
				.collect(Collectors.toList());

		String orderDetails = orderIds.get(0);
		System.out.println("order status -> " + orderDetails);

		if (orderDetails.contains(orderNum) && !orderDetails.contains("rejected")
				&& !orderDetails.contains("Insufficient Funds"))
			ExtentLogger.info(orderNum + " Order Id found in open order");
		// System.out.println(" Order Id found in open order");
		else if (orderDetails.contains(orderNum) && orderDetails.contains("rejected")
				&& orderDetails.contains("Insufficient Funds")) {
			ExtentLogger.info(orderNum + " Order is rejected due to Insufficient Funds");
			// System.out.println(" Order is rejected due to Insufficient Funds");
		} else {
			ExtentLogger.fail(orderNum + " Order Id not found in open order.Probably rejected.Please check manually");
			Assert.fail("Place Order isnt successful as order Id " + orderNum + " doesnt found in Open Order");
		}

		// Cancel Order
		cancelAndVerify(variety, orderNum, orderDetails);

	}

	@Test(enabled = true)
	void placeOrder_Buy_INTRADAY_LimitOrder_FNO() throws IOException, InterruptedException {
		String fnoSymbol = ApiConfigFactory.getConfig().fnoSymbol();
		String fnoSymbolToken = ApiConfigFactory.getConfig().fnoSymbolToken();
		String pLtp = baseAPI.getLTPPrice(fnoSymbolToken, "nse_fo");
		String ltpPrice = helper.fnoBuyMarketPending(pLtp);
		System.out.println("Post rounding off LTP value = " + ltpPrice);
		String variety = helper.orderTypeCheckForEquity();
		// Place Market Orders
		Response response = baseAPI.placeStockOrder("NFO", "LIMIT", ltpPrice, "INTRADAY", "1", "0", fnoSymbolToken,
				fnoSymbol, "BUY", "0.0", variety);
		ExtentLogger.info(response.asPrettyString());
		validateAPIResponse(response);
		String orderNum = response.jsonPath().getString("data.orderid");

		Response callOrdersApi = baseAPI.getOrderBook();// OrderBookAPi
		GetOrdersDetailsResponsePOJO as = callOrdersApi.getBody().as(GetOrdersDetailsResponsePOJO.class);
		List<OrdersDetailsData> data = as.getData();

		List<String> orderIds = data.stream().filter(x -> x.getOrderid().toString().equalsIgnoreCase(orderNum))
				.map(x -> x.getOrderid() + " - " + x.getOrderstatus() + " - " + x.getText())
				.collect(Collectors.toList());

		String orderDetails = orderIds.get(0);
		System.out.println("order status -> " + orderDetails);

		if (orderDetails.contains(orderNum) && !orderDetails.contains("rejected")
				&& !orderDetails.contains("Insufficient Funds"))
			// ExtentLogger.info(orderNum + " Order Id found in open order");
			System.out.println(" Order Id found in open order");
		else if (orderDetails.contains(orderNum) && orderDetails.contains("rejected")
				&& orderDetails.contains("Insufficient Funds")) {
			// ExtentLogger.info(orderNum + " Order is rejected due to Insufficient Funds");
			System.out.println(" Order is rejected due to Insufficient Funds");
		} else {
			ExtentLogger.fail(orderNum + " Order Id not found in open order.Probably rejected.Please check manually");
			// rejected.Please check manually");
			Assert.fail("Place Order isnt successful as order Id " + orderNum + " doesnt found in Open Order");
		}

		cancelAndVerify(variety, orderNum, orderDetails);

	}

	@Test(enabled = true)
	void placeOrder_Buy_INTRADAY_LimitOrder_Commodity() throws IOException, InterruptedException {

		String comoditySymbol = ApiConfigFactory.getConfig().comoditySymbol();
		String comoditySymbolToken = ApiConfigFactory.getConfig().comoditySymbolToken();
		String pLtp = baseAPI.getLTPPrice(comoditySymbolToken, "mcx_fo");
		String ltpPrice = helper.buyValueTriggerPriceForCommodity(pLtp);
		System.out.println("Post rounding off LTP value = " + ltpPrice);
		String variety = helper.orderTypeCheckForComodity();
		// Place Market Orders
		Response response = baseAPI.placeStockOrder("0", "0", "DAY", "MCX", "1", "", "", "LIMIT", "2", ltpPrice,
				"INTRADAY", "1", "0", "0", "0", comoditySymbolToken, "100.0", comoditySymbol, "NO", "0", "BUY", "0.0",
				variety);
		ExtentLogger.info(response.asPrettyString());
		validateAPIResponse(response);
		String orderNum = response.jsonPath().getString("data.orderid");

		Response callOrdersApi = baseAPI.getOrderBook();// OrderBookAPi
		GetOrdersDetailsResponsePOJO as = callOrdersApi.getBody().as(GetOrdersDetailsResponsePOJO.class);
		List<OrdersDetailsData> data = as.getData();

		List<String> orderIds = data.stream().filter(x -> x.getOrderid().toString().equalsIgnoreCase(orderNum))
				.map(x -> x.getOrderid() + " - " + x.getOrderstatus() + " - " + x.getText())
				.collect(Collectors.toList());

		String orderDetails = orderIds.get(0);
		System.out.println("order status -> " + orderDetails);

		if (orderDetails.contains(orderNum) && !orderDetails.contains("rejected")
				&& !orderDetails.contains("Insufficient Funds"))
			// ExtentLogger.info(orderNum + " Order Id found in open order");
			System.out.println(" Order Id found in open order");
		else if (orderDetails.contains(orderNum) && orderDetails.contains("rejected")
				&& orderDetails.contains("Insufficient Funds")) {
			// ExtentLogger.info(orderNum + " Order is rejected due to Insufficient Funds");
			System.out.println(" Order is rejected due to Insufficient Funds");
		} else {
			ExtentLogger.fail(orderNum + " Order Id not found in open order.Probably rejected.Please check manually");
			Assert.fail("Place Order isnt successful as order Id " + orderNum + " doesnt found in Open Order");
		}

		// Cancel Order
		cancelAndVerify(variety, orderNum, orderDetails);

	}

	@Test(enabled = true)
	void placeOrder_Buy_INTRADAY_LimitOrder_Commodity_Cockudakl() throws IOException, InterruptedException {

		String comoditySymbol = ApiConfigFactory.getConfig().comodityNcdexSymbol();
		String comoditySymbolToken = ApiConfigFactory.getConfig().comodityNcdexSymbolToken();
		String pLtp = baseAPI.getLTPPrice(comoditySymbolToken, "ncx_fo");
		String ltpPrice = helper.buyValueTriggerPriceForCommodity(pLtp);
		System.out.println("Post rounding off LTP value = " + ltpPrice);
		String variety = helper.orderTypeCheckForComodity();
		// Place Market Orders
		Response response = baseAPI.placeStockOrder("0", "0", "DAY", "NCDEX", "1", "", "", "LIMIT", "2", ltpPrice,
				"CARRYFORWARD", "1", "0", "0", "0", comoditySymbolToken, "100.0", comoditySymbol, "NO", "0", "BUY",
				"0.0", variety);
		ExtentLogger.info(response.asPrettyString());
		validateAPIResponse(response);
		String orderNum = response.jsonPath().getString("data.orderid");

		Response callOrdersApi = baseAPI.getOrderBook();// OrderBookAPi
		GetOrdersDetailsResponsePOJO as = callOrdersApi.getBody().as(GetOrdersDetailsResponsePOJO.class);
		List<OrdersDetailsData> data = as.getData();

		List<String> orderIds = data.stream().filter(x -> x.getOrderid().toString().equalsIgnoreCase(orderNum))
				.map(x -> x.getOrderid() + " - " + x.getOrderstatus() + " - " + x.getText())
				.collect(Collectors.toList());

		String orderDetails = orderIds.get(0);
		System.out.println("order status -> " + orderDetails);

		if (orderDetails.contains(orderNum) && !orderDetails.contains("rejected")
				&& !orderDetails.contains("Insufficient Funds"))
			// ExtentLogger.info(orderNum + " Order Id found in open order");
			System.out.println(" Order Id found in open order");
		else if (orderDetails.contains(orderNum) && orderDetails.contains("rejected")
				&& orderDetails.contains("Insufficient Funds")) {
			// ExtentLogger.info(orderNum + " Order is rejected due to Insufficient Funds");
			System.out.println(" Order is rejected due to Insufficient Funds");
		} else {
			ExtentLogger.fail(orderNum + " Order Id not found in open order.Probably rejected.Please check manually");
			Assert.fail("Place Order isnt successful as order Id " + orderNum + " doesnt found in Open Order");
		}

		// Cancel Order
		cancelAndVerify(variety, orderNum, orderDetails);

	}

	@Test(enabled = true)
	void placeOrder_Sell_INTRADAY_LimitOrder_Cash() throws IOException, InterruptedException {
		// Generate User Mpin Token
		String variety = helper.orderTypeCheckForEquity();
		String pLtp = "";
		String ltpPrice = "";
		Response response = null;
		if (variety.equalsIgnoreCase("NORMAL")) {
			pLtp = baseAPI.getLTPPrice("5948", "nse_cm");
			ltpPrice = helper.SellTriggerPriceGreater(pLtp);
			System.out.println("Post rounding off LTP value = " + ltpPrice);
			// Place Market Orders
			response = baseAPI.placeStockOrder("NSE", "LIMIT", ltpPrice, "INTRADAY", "1", "0", "5948", "SOUTHBANK-EQ",
					"SELL", "0.0", variety);
		} else {
			pLtp = baseAPI.getLTPPrice("10666", "nse_cm");
			ltpPrice = helper.SellTriggerPriceGreater(pLtp);
			System.out.println("Post rounding off LTP value = " + ltpPrice);
			// Place Market Orders
			response = baseAPI.placeStockOrder("NSE", "LIMIT", ltpPrice, "DELIVERY", "1", "0", "10666", "PNB-EQ",
					"SELL", "0.0", variety);
		}
		ExtentLogger.info(response.asPrettyString());
		validateAPIResponse(response);
		String orderNum = response.jsonPath().getString("data.orderid");

		// Call getOrderBook
		Response callOrdersApi = baseAPI.getOrderBook();
		GetOrdersDetailsResponsePOJO as = callOrdersApi.getBody().as(GetOrdersDetailsResponsePOJO.class);
		List<OrdersDetailsData> data = as.getData();
		List<String> orderIds = data.stream()
				.filter(x -> !(x.getOrderstatus().contains("cancelled") || x.getOrderstatus().contains("rejected")))
				.map(x -> x.getOrderid()).collect(Collectors.toList());

		if (orderIds.contains(orderNum))
			ExtentLogger.info(orderNum + " Order Id found in open order");
		else {
			ExtentLogger.fail(orderNum + " Order Id not found in open order.Probably rejected.Please check manually");
			Assert.fail("Place Order isnt successful as order Id " + orderNum + " doesnt found in Open Order");
		}

		// Cancel Order
		Response cancelOrderResponse = baseAPI.cancelOrder(orderNum, variety);
		Assert.assertTrue(cancelOrderResponse.getStatusCode() == 200, "some error in placeOrder api ");

		Thread.sleep(1000);
		// Verify Order is cancelled
		verifyOrderIsCancelled(orderNum, variety);
	}

	@Test(enabled = true)
	void placeOrder_Sell_DELIVERY_LimitOrder_Cash_FromHoldings() throws IOException, InterruptedException {

		String variety = helper.orderTypeCheckForEquity();
		String pLtp = "";
		String ltpPrice = "";
		Response response = null;

		pLtp = baseAPI.getLTPPrice("5948", "nse_cm");
		ltpPrice = helper.SellTriggerPriceGreater(pLtp);
		System.out.println("Post rounding off LTP value = " + ltpPrice);
		// Place Market Orders
		response = baseAPI.placeStockOrder("NSE", "LIMIT", ltpPrice, "DELIVERY", "1", "0", "5948", "SOUTHBANK-EQ",
				"SELL", "0.0", variety);

		ExtentLogger.info(response.asPrettyString());
		validateAPIResponse(response);
		String orderNum = response.jsonPath().getString("data.orderid");

		// Call getOrderBook
		Response callOrdersApi = baseAPI.getOrderBook();
		GetOrdersDetailsResponsePOJO as = callOrdersApi.getBody().as(GetOrdersDetailsResponsePOJO.class);
		List<OrdersDetailsData> data = as.getData();
		List<String> orderIds = data.stream()
				.filter(x -> !(x.getOrderstatus().contains("cancelled") || x.getOrderstatus().contains("rejected")))
				.map(x -> x.getOrderid()).collect(Collectors.toList());

		if (orderIds.contains(orderNum))
			ExtentLogger.info(orderNum + " Order Id found in open order");
		else {
			ExtentLogger.fail(orderNum + " Order Id not found in open order.Probably rejected.Please check manually");
			Assert.fail("Place Order isnt successful as order Id " + orderNum + " doesnt found in Open Order");
		}

		// Cancel Order
		Response cancelOrderResponse = baseAPI.cancelOrder(orderNum, variety);
		Assert.assertTrue(cancelOrderResponse.getStatusCode() == 200, "some error in placeOrder api ");

		Thread.sleep(1000);
		// Verify Order is cancelled
		verifyOrderIsCancelled(orderNum, variety);
	}

	@Test(enabled = true)
	void placeOrder_Sell_DELIVERY_LimitOrder_Cash_T2TStock() throws IOException, InterruptedException {

		String variety = helper.orderTypeCheckForEquity();
		String pLtp = "";
		String ltpPrice = "";
		Response response = null;

		pLtp = baseAPI.getLTPPrice("248", "nse_cm");
		ltpPrice = helper.SellTriggerPriceGreaterT2T(pLtp);
		System.out.println("Post rounding off LTP value = " + ltpPrice);
		// Place Market Orders
		response = baseAPI.placeStockOrder("NSE", "LIMIT", ltpPrice, "DELIVERY", "1", "0", "248", "ADL-BE", "SELL",
				"0.0", variety);

		ExtentLogger.info(response.asPrettyString());
		validateAPIResponse(response);
		String orderNum = response.jsonPath().getString("data.orderid");

		// Call getOrderBook
		Response callOrdersApi = baseAPI.getOrderBook();// OrderBookAPi
		GetOrdersDetailsResponsePOJO as = callOrdersApi.getBody().as(GetOrdersDetailsResponsePOJO.class);
		List<OrdersDetailsData> data = as.getData();

		List<String> orderIds = data.stream().filter(x -> x.getOrderid().toString().equalsIgnoreCase(orderNum))
				.map(x -> x.getOrderid() + " - " + x.getOrderstatus() + " - " + x.getText())
				.collect(Collectors.toList());

		String orderDetails = orderIds.get(0);
		System.out.println("order status -> " + orderDetails);

		if (orderDetails.contains(orderNum) && !orderDetails.contains("rejected")
				&& !orderDetails.contains("outside circuit limits"))
			ExtentLogger.info(orderNum + " Order Id found in open order");
		// System.out.println(" Order Id found in open order");
		else if (orderDetails.contains(orderNum) && orderDetails.contains("rejected")
				&& orderDetails.contains("outside circuit limits")) {
			ExtentLogger.info(orderNum + " Order is rejected due to outside circuit limits limit price given");
			// System.out.println(" Order is rejected due to Insufficient Funds");
		} else {
			ExtentLogger.fail(orderNum + " Order Id not found in open order.Probably rejected.Please check manually");
			Assert.fail("Place Order isnt successful as order Id " + orderNum + " doesnt found in Open Order");
		}

		// Cancel Order
		cancelAndVerify(variety, orderNum, orderDetails);
	}

	@Test(enabled = true)
	public void testGetHolding() throws Exception {

		Response holding = baseAPI.getHolding();
		ExtentLogger.info(holding.asPrettyString());
		Assert.assertTrue(holding.getStatusCode() == 200, "Invalid Response for getHolding API");
		JsonPath jPath = new JsonPath(holding.asString());

		List<String> list = holding.jsonPath().getList("data.HoldingDetail");
		int size = list.size();
		System.out.println("Total Holdings = " + size);
		for (int i = 0; i < size; i++) {
			String symbol = jPath.getString("data.HoldingDetail[" + i + "].tradeSymbol");
			if (symbol.equalsIgnoreCase("IFCI-EQ")) {
				String quantity = jPath.getString("data.HoldingDetail[" + i + "].qty");
				System.out.println("Trading Symbol = " + symbol);
				String invValue = jPath.getString("data.HoldingDetail[" + i + "].invValue");
				System.out.println("invValue = " + invValue);
				String ltp = jPath.getString("data.HoldingDetail[" + i + "].ltp");
				System.out.println("ltp = " + ltp);
				String avgPrice = jPath.getString("data.HoldingDetail[" + i + "].avgPrice");
				System.out.println("avgPrice = " + avgPrice);
				Assertions.assertThat(quantity).as("Qty is null").isNotNull();
				Assertions.assertThat(Integer.valueOf(quantity)).as("Qty is nt greater than equal to 0")
						.isGreaterThanOrEqualTo(0);
				Assertions.assertThat(Double.valueOf(ltp)).as("ltp is nt greater than 0").isGreaterThan(0);
				Assertions.assertThat(Double.valueOf(avgPrice)).as("avgPrice is nt greater than 0").isGreaterThan(0);
				Assertions.assertThat(Double.valueOf(invValue)).as("invValue is nt greater than 0").isGreaterThan(0);
				break;
			}
		}

	}

	@Test(enabled = true)
	public void testWithdrawalBalanceApi() throws Exception {

		Response wBalance = baseAPI.callWithdrawalBalanceApi();
		ExtentLogger.info(wBalance.asPrettyString());
		Assert.assertTrue(wBalance.getStatusCode() == 200, "Invalid Response for getHolding API");
		JsonPath jPath = new JsonPath(wBalance.asString());
		String totalBalance = jPath.getString("data.totalBalance");
		String releasbleAmount = jPath.getString("data.releasbleAmount");
		String unsettledBalance = jPath.getString("data.unsettledBalance");
		
		Assertions.assertThat(totalBalance).as("totalBalance shouldn't be null").isNotBlank().isNotNull().isNotEmpty().isNotEqualTo("0");
		Assertions.assertThat(releasbleAmount).as("releasbleAmount shouldn't be null").isNotBlank().isNotNull().isNotEmpty().isNotEqualTo("0");
		Assertions.assertThat(unsettledBalance).as("unsettledBalance shouldn't be null").isNotBlank().isNotNull().isNotEmpty();
	}
	
	@Test(enabled = true)
	public void testWithdrawListApi() throws Exception {

		Response withdrawList = baseAPI.callgetWithdrawListAPI();
		ExtentLogger.info(withdrawList.asPrettyString());
		Assert.assertTrue(withdrawList.getStatusCode() == 200, "Invalid Response for getwithdraw list API");
		JsonPath jPath = new JsonPath(withdrawList.asString());
		
		String transactionAmount = jPath.getString("data.transactions.amount");
		String transactionDate = jPath.getString("data.transactions.transaction_date");
		String transactionID = jPath.getString("data.transactions.transaction_id");
		
		Assertions.assertThat(transactionAmount).as("transactionAmount shouldn't be null").isNotBlank().isNotNull().isNotEmpty().isNotEqualTo("0");
		Assertions.assertThat(transactionDate).as("transactionDate shouldn't be null").isNotBlank().isNotNull().isNotEmpty().isNotEqualTo("0");
		Assertions.assertThat(transactionID).as("trabnsactionID shouldn't be null").isNotBlank().isNotNull().isNotEmpty();
	}
	
	@Test(enabled = true)
	public void testTransactionMergedListApi() throws Exception {

		Response transactionMergedList = baseAPI.callGetTransactionMergedListAPI();
		ExtentLogger.info(transactionMergedList.asPrettyString());
		Assert.assertTrue(transactionMergedList.getStatusCode() == 200, "Invalid Response for gettransactionmerged list API");
		JsonPath jPath = new JsonPath(transactionMergedList.asString());
		
		String transactionAmount = jPath.getString("data.transactions.amount");
		String transactionDate = jPath.getString("data.transactions.transaction_date");
		String transactionID = jPath.getString("data.transactions.transaction_id");
		
		Assertions.assertThat(transactionAmount).as("transactionAmount shouldn't be null").isNotBlank().isNotNull().isNotEmpty().isNotEqualTo("0");
		Assertions.assertThat(transactionDate).as("transactionDate shouldn't be null").isNotBlank().isNotNull().isNotEmpty().isNotEqualTo("0");
		Assertions.assertThat(transactionID).as("trabnsactionID shouldn't be null").isNotBlank().isNotNull().isNotEmpty();
	}
	
	@Test(enabled = true)
	public void testGetPositions() throws Exception {

		Response positions = baseAPI.getPositions();
		ExtentLogger.info(positions.asPrettyString());
		Assert.assertTrue(positions.getStatusCode() == 200, "Invalid Response for getPostion API");
		Assertions.assertThat(positions.jsonPath().getString("message")).as("position status isnt showing success")
				.isEqualTo("SUCCESS");
		List<String> list = positions.jsonPath().getList("data");
		if (list.size() > 0) {
			String exchange = positions.jsonPath().getString("data[0].exchange");
			String segment = positions.jsonPath().getString("data[0].segment");
			String symboltoken = positions.jsonPath().getString("data[0].symboltoken");
			String tradingsymbol = positions.jsonPath().getString("data[0].tradingsymbol");
			String avgPrice = positions.jsonPath().getString("data[0].avgPrice");

			Assert.assertNotNull(exchange, "exchange is null");
			Assert.assertNotNull(segment, "segment is null");
			Assert.assertNotNull(symboltoken, "symboltoken is null");
			Assert.assertNotNull(tradingsymbol, "tradingsymbol is null");
			Assert.assertNotNull(avgPrice, "avgPrice is null");
		} else {
			System.out.println("Position is Empty . Please Place some order and get it executed");
		}
	}

	@Test(enabled = true)
	public void testPortfolioAdvisoryApi() throws Exception {

		Response portfolioAdvioryData = baseAPI.callPortfolioAdvioryApi();
		ExtentLogger.info(portfolioAdvioryData.asPrettyString());
		Assert.assertTrue(portfolioAdvioryData.getStatusCode() == 200, "Invalid Response for portfolioAdviory API");
		Assertions.assertThat(portfolioAdvioryData.jsonPath().getString("status"))
				.as("portfolioAdviory status isn't showing success").isEqualTo("success");
		List<String> list = portfolioAdvioryData.jsonPath().getList("data");
		if (list.size() > 0) {
			String portfolioName = portfolioAdvioryData.jsonPath().getString("data[0].portfolioName");
			String vendor = portfolioAdvioryData.jsonPath().getString("data[0].vendor");
			String short_description = portfolioAdvioryData.jsonPath().getString("data[0].short_description");
			String label = portfolioAdvioryData.jsonPath().getString("data[0].label");
			String minInvestment = portfolioAdvioryData.jsonPath().getString("data[0].minInvestment");
			String risk = portfolioAdvioryData.jsonPath().getString("data[0].risk");
			String cagrAmt = portfolioAdvioryData.jsonPath().getString("data[0].cagr.cagr");

			Assert.assertNotNull(portfolioName, "exchange is null");
			Assert.assertNotNull(vendor, "segment is null");
			Assert.assertNotNull(short_description, "symboltoken is null");
			Assert.assertNotNull(label, "tradingsymbol is null");
			Assert.assertNotNull(minInvestment, "minInvestment is null");
			Assert.assertNotNull(risk, "risk is null");
			Assert.assertNotNull(cagrAmt, "cagrAmt is null");
		} else {
			System.out.println("Position is Empty . Please Place some order and get it executed");
		}
	}

	@Test(enabled = true)
	public void testIpoDetails() throws Exception {
		Response ipoDetails = baseAPI.getIpoDetails();
		ExtentLogger.info(ipoDetails.asPrettyString());
		Assert.assertTrue(ipoDetails.getStatusCode() == 200, "Invalid Response for IPO Details API");
		Assertions.assertThat(ipoDetails.jsonPath().getString("message")).as("IPO Details status isnt showing success")
				.isEqualTo("SUCCESS");
		List<String> list = ipoDetails.jsonPath().getList("data");
		if (list.size() > 0) {
			String symbol = ipoDetails.jsonPath().getString("data[0].symbol");
			String name = ipoDetails.jsonPath().getString("data[0].name");
			String offerType = ipoDetails.jsonPath().getString("data[0].offerType");
			String startDate = ipoDetails.jsonPath().getString("data[0].startDate");
			String endDate = ipoDetails.jsonPath().getString("data[0].endDate");
			String companyDesription = ipoDetails.jsonPath().getString("data[0].companyDesription");
			String companyStrength = ipoDetails.jsonPath().getString("data[0].companyStrength");
			String companyRisks = ipoDetails.jsonPath().getString("data[0].companyRisks");
			String quantity = ipoDetails.jsonPath().getString("data[0].categories[0].quantity");
			String lowerValue = ipoDetails.jsonPath().getString("data[0].categories[0].lowerValue");
			String upperValue = ipoDetails.jsonPath().getString("data[0].categories[0].upperValue");
			String maxTotalAmt = ipoDetails.jsonPath().getString("data[0].categories[0].maxTotalAmt");
			String minimumBidLotQty = ipoDetails.jsonPath().getString("data[0].categories[0].minimumBidLotQty");
			String maximumBidLotQty = ipoDetails.jsonPath().getString("data[0].categories[0].maximumBidLotQty");

			// Assertions
			Assertions.assertThat(symbol).as("symbol shouldn't be null").isNotBlank().isNotNull().isNotEmpty();
			Assertions.assertThat(name).as("name shouldn't be null").isNotBlank().isNotNull().isNotEmpty();
			Assertions.assertThat(offerType).as("offerType shouldn't be null").isNotBlank().isNotNull().isNotEmpty();
			Assertions.assertThat(startDate).as("startDate shouldn't be null").isNotBlank().isNotNull().isNotEmpty();
			Assertions.assertThat(endDate).as("endDate shouldn't be null").isNotBlank().isNotNull().isNotEmpty();
			Assertions.assertThat(companyDesription).as("companyDesription shouldn't be null").isNotBlank().isNotNull()
					.isNotEmpty();
			Assertions.assertThat(companyStrength).as("companyStrength shouldn't be null").isNotBlank().isNotNull()
					.isNotEmpty();
			Assertions.assertThat(companyRisks).as("companyRisks shouldn't be null").isNotBlank().isNotNull()
					.isNotEmpty();
			Assertions.assertThat(quantity).as("quantity shouldn't be null").isNotBlank().isNotNull().isNotEmpty();
			Assertions.assertThat(lowerValue).as("lowerValue shouldn't be null").isNotBlank().isNotNull().isNotEmpty();
			Assertions.assertThat(upperValue).as("upperValue shouldn't be null").isNotBlank().isNotNull().isNotEmpty();
			Assertions.assertThat(maxTotalAmt).as("maxTotalAmt shouldn't be null").isNotBlank().isNotNull()
					.isNotEmpty();
			Assertions.assertThat(minimumBidLotQty).as("minimumBidLotQty shouldn't be null").isNotBlank().isNotNull()
					.isNotEmpty();
			Assertions.assertThat(maximumBidLotQty).as("maximumBidLotQty shouldn't be null").isNotBlank().isNotNull()
					.isNotEmpty();

		} else {
			System.out.println("IPO lists is Empty");
		}
	}

	@Test(enabled = true)
	void testMarketMoversByMost() throws IOException {
		MARKET_MOVERS_BY_MOST_DATA.forEach(queryParam -> {
			Response marketMovers = baseAPI.getMarketMoversByMost("MOST_ACT_VALUE");
			Assert.assertEquals(marketMovers.getStatusCode(), 200, "Error in marketMovers api ");
			Assert.assertEquals(marketMovers.jsonPath().getString("status"), "success",
					"Status doesnt match in marketMovers api ");
			Assert.assertTrue(!marketMovers.jsonPath().getString("data").isEmpty(),
					"data is empty in marketMovers api ");
			String data = marketMovers.jsonPath().getString("data");
			String decodeJsonResponse = baseAPI.decodeData(data);
			JsonPath jPath = new JsonPath(decodeJsonResponse);
			List<String> list = jPath.getList("Data");
			System.out.println("Data Size = " + list.size());
			int totalValue = list.size();
			Assertions.assertThat(totalValue).as("total market moves not greater than 0").isGreaterThan(0);
		});
	}

	@Test(enabled = true)
	void testStockShareHolder() throws IOException {
		Response marketMovers = baseAPI.getStockShareHolder("INE467B01029");
		Assert.assertEquals(marketMovers.getStatusCode(), 200, "Error in marketMovers api ");
		Assert.assertEquals(marketMovers.jsonPath().getString("status"), "success",
				"Status doesnt match in marketMovers api ");
		Assert.assertTrue(!marketMovers.jsonPath().getString("data").isEmpty(), "data is empty in marketMovers api ");
		String data = marketMovers.jsonPath().getString("data");
		String decodeJsonResponse = baseAPI.decodeData(data);
		JSONObject object = new JSONObject(decodeJsonResponse);
		JSONArray jsonArray3 = object.getJSONArray("MonthList");
		Assertions.assertThat(jsonArray3.length()).as("MonthList doesnt hv 2 records").isEqualTo(2);
		JSONArray jsonArray = new JSONArray(object.getJSONArray("ItemList"));
		for (int i = 0; i < jsonArray.length(); i++) {
			JSONArray jsonArray2 = jsonArray.getJSONObject(i).getJSONArray("MonthList");
			String ChangePercentage = jsonArray.getJSONObject(i).getString("ChangePercentage");
			String Name = jsonArray.getJSONObject(i).getString("Name");
			Assertions.assertThat(jsonArray2.length()).as("MonthList doesnt hv 2 records").isEqualTo(2);
			Assertions.assertThat(ChangePercentage).as("ChangePercentage market is null").isNotNull().isNotBlank()
					.isNotEmpty();
			Assertions.assertThat(Name).as("Name is null").isNotNull().isNotBlank().isNotEmpty();
		}

	}

	@Test(enabled = true)
	void testFundamentalRatioApi() throws IOException {
		Response FundamentalRatio = baseAPI.getFundamentalRatio("INE467B01029");
		Assert.assertEquals(FundamentalRatio.getStatusCode(), 200, "Error in FundamentalRatio api ");
		Assert.assertEquals(FundamentalRatio.jsonPath().getString("status"), "success",
				"Status doesnt match in FundamentalRatio api ");
		Assert.assertTrue(!FundamentalRatio.jsonPath().getString("data").isEmpty(),
				"data is empty in FundamentalRatio api ");
		String data = FundamentalRatio.jsonPath().getString("data");
		String decodeJsonResponse = baseAPI.decodeData(data);
		JSONArray jsonArray = new JSONArray(decodeJsonResponse);
		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject object = jsonArray.getJSONObject(i);
			String Name = object.getString("Name");
			String Value = object.getString("Value");

			Assertions.assertThat(Name).as("Name is null for FundamentalRatioApi").isNotEmpty().isNotNull()
					.isNotBlank();
			Assertions.assertThat(Value).as("Value is null for FundamentalRatioApi").isNotEmpty().isNotNull()
					.isNotBlank();
		}
	}

	@Test(enabled = true)
	void testFutureBuildUpHeatMap() throws IOException {
		BUILDUPS.forEach(buildup -> {
			Response marketBuildupResponse = baseAPI.getMarketBuiltup("NEAR", "All", buildup);
			Assert.assertEquals(marketBuildupResponse.getStatusCode(), 200, "Error in marketBuildupResponse api ");
			Assert.assertEquals(marketBuildupResponse.jsonPath().getString("status"), "success",
					"Status doesnt match in marketBuildup api " + buildup);
			Assert.assertTrue(!marketBuildupResponse.jsonPath().getString("data").isEmpty(),
					"data is empty in marketBuildupResponse api ");
			String data = marketBuildupResponse.jsonPath().getString("data");
			String decodeJsonResponse = baseAPI.decodeData(data);
			JSONArray jsonArray = new JSONArray(decodeJsonResponse);
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject object = jsonArray.getJSONObject(i);
				String Symbol = object.getString("Symbol");
				Double NetChg = object.getDouble("NetChg");
				Double Ltp = object.getDouble("Ltp");
				String SecurityDesc = object.getString("SecurityDesc");

				Assertions.assertThat(Symbol).as("Symbol is null for " + buildup).isNotEmpty().isNotNull().isNotBlank();
				Assertions.assertThat(String.valueOf(NetChg)).as("NetChg is null for " + buildup).isNotEmpty()
						.isNotNull().isNotBlank();
				Assertions.assertThat(String.valueOf(Ltp)).as("Ltp is null for " + buildup).isNotEmpty().isNotNull()
						.isNotBlank();
				Assertions.assertThat(SecurityDesc).as("SecurityDesc is null for " + buildup).isNotEmpty().isNotNull()
						.isNotBlank();
			}
		});
	}

	@Test(enabled = true)
	void testFutureMarketsIndicatorsDerivativeMarketApi() throws IOException {
		DERIVATIVE_MARKET.forEach(buildup -> {
			Response FutureMarketsIndicators = baseAPI.getFutureMarketsIndicators("NEAR", buildup);
			Assert.assertEquals(FutureMarketsIndicators.getStatusCode(), 200, "Error in FutureMarketsIndicators api ");
			Assert.assertEquals(FutureMarketsIndicators.jsonPath().getString("status"), "success",
					"Status doesnt match in marketBuildup api " + buildup);
			Assert.assertTrue(!FutureMarketsIndicators.jsonPath().getString("data").isEmpty(),
					"data is empty in FutureMarketsIndicators api ");
			String data = FutureMarketsIndicators.jsonPath().getString("data");
			String decodeJsonResponse = baseAPI.decodeData(data);
			JSONArray jsonArray = new JSONArray(decodeJsonResponse);
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject object = jsonArray.getJSONObject(i);
				String StockXChangeCode = object.getString("StockXChangeCode");
				String EnumName = object.getString("EnumName");
				Double Ltp = object.getDouble("Ltp");
				String ExpiryDate = object.getString("ExpiryDate");

				Assertions.assertThat(StockXChangeCode).as("StockXChangeCode is null for " + buildup).isNotEmpty()
						.isNotNull().isNotBlank();
				Assertions.assertThat(EnumName).as("EnumName is null for " + buildup).isNotEmpty().isNotNull()
						.isNotBlank();
				Assertions.assertThat(String.valueOf(Ltp)).as("Ltp is null for " + buildup).isNotEmpty().isNotNull()
						.isNotBlank();
				Assertions.assertThat(ExpiryDate).as("ExpiryDate is null for " + buildup).isNotEmpty().isNotNull()
						.isNotBlank();
			}
		});
	}

	@Test(enabled = true)
	void testDerivativeMarketSectorHeatMap() throws IOException {
		Response sectorHeatMap = baseAPI.getSectorHeatMap();
		Assert.assertEquals(sectorHeatMap.getStatusCode(), 200, "Error in sectorHeatMap api ");
		Assert.assertEquals(sectorHeatMap.jsonPath().getString("status"), "success",
				"Status doesnt match in sectorHeatMap api");
		Assert.assertTrue(!sectorHeatMap.jsonPath().getString("data").isEmpty(), "data is empty in sectorHeatMap api ");
		String data = sectorHeatMap.jsonPath().getString("data");
		String decodeJsonResponse = baseAPI.decodeData(data);
		JsonPath jPath = new JsonPath(decodeJsonResponse);
		String IndexName = jPath.getString("Data[0].IndexName");
		String SectorName = jPath.getString("Data[0].SectorName");
		String PercChg = jPath.getString("Data[0].PercChg");
		String PercChgOI = jPath.getString("Data[0].PercChgOI");
		String PercChgVolume = jPath.getString("Data[0].PercChgVolume");
		String PercRollover = jPath.getString("Data[0].PercRollover");

		Assertions.assertThat(IndexName).as("IndexName is null for sectorHeatMap").isNotEmpty().isNotNull()
				.isNotBlank();
		Assertions.assertThat(String.valueOf(SectorName)).as("NetChg is null for sectorHeatMap").isNotEmpty()
				.isNotNull().isNotBlank();
		Assertions.assertThat(String.valueOf(PercChg)).as("PercChg is null for sectorHeatMap").isNotEmpty().isNotNull()
				.isNotBlank();
		Assertions.assertThat(PercChgOI).as("PercChgOI is null for sectorHeatMap").isNotEmpty().isNotNull()
				.isNotBlank();
		Assertions.assertThat(PercChgVolume).as("PercChgVolume is null for sectorHeatMap").isNotEmpty().isNotNull()
				.isNotBlank();
		Assertions.assertThat(PercRollover).as("PercRollover is null for sectorHeatMap").isNotEmpty().isNotNull()
				.isNotBlank();

	}

	@Test(enabled = true)
	void testInstaTradeDetailsApi() throws IOException {
		Response IntraTradeResponse = baseAPI.getIntraTradeDetails("CE", "NIFTY", "18000");
		ExtentLogger.info(IntraTradeResponse.asPrettyString());
		Assert.assertEquals(IntraTradeResponse.getStatusCode(), 200, "Error in IntraTrade api ");
		Assert.assertEquals(IntraTradeResponse.jsonPath().getString("status"), "success",
				"Status doesnt match in IntraTrade api ");
		Assert.assertTrue(!IntraTradeResponse.jsonPath().getString("data").isEmpty(),
				"data is empty in IntraTrade api ");
		JsonPath jPath = new JsonPath(IntraTradeResponse.asString());
		List<String> list = jPath.getList("data.symbolList");
		System.out.println("Data Size = " + list.size());
		int totalValue = list.size();
		Assertions.assertThat(totalValue).as("total IntraTrade isnt greater than 0").isGreaterThan(0);
		List<String> cardValues = jPath.getList("data.symbolList[0].cardValues");
		Assertions.assertThat(cardValues.size()).as("CardValues doesnt have 2 records").isEqualTo(2);
		Assertions.assertThat(jPath.getString("data.symbolList[0].symbolDesc")).as("Symbol desc is blank/null")
				.isNotBlank().isNotEmpty().isNotNull();
		Assertions.assertThat(jPath.getString("data.symbolList[0].symbolDetails.symbolName"))
				.as("symbolName is blank/null").isNotBlank().isNotEmpty().isNotNull();
		Assertions.assertThat(jPath.getString("data.symbolList[0].symbolDetails.tradeSymbol"))
				.as("tradeSymbol is blank/null").isNotBlank().isNotEmpty().isNotNull();
		Assertions.assertThat(jPath.getString("data.symbolList[0].symbolDetails.symbolDesc"))
				.as("symbolDesc is blank/null").isNotBlank().isNotEmpty().isNotNull();
		Assertions.assertThat(jPath.getString("data.symbolList[0].symbolDetails.tokenID")).as("tokenID is blank/null")
				.isNotBlank().isNotEmpty().isNotNull();
		Assertions.assertThat(jPath.getString("data.symbolList[0].symbolDetails.expiry")).as("expiry is blank/null")
				.isNotBlank().isNotEmpty().isNotNull();
		Assertions.assertThat(jPath.getString("data.symbolList[0].symbolDetails.details")).as("details is blank/null")
				.isNotBlank().isNotEmpty().isNotNull();
		Assertions.assertThat(jPath.getString("data.symbolList[0].symbolDetails.astCls")).as("astCls is blank/null")
				.isNotBlank().isNotEmpty().isNotNull();
		Assertions.assertThat(jPath.getString("data.symbolList[0].symbolDetails.optType")).as("optType is blank/null")
				.isNotBlank().isNotEmpty().isNotNull();
		Assertions.assertThat(jPath.getString("data.symbolList[0].symbolDetails.minLot")).as("minLot is blank/null")
				.isNotBlank().isNotEmpty().isNotNull();
		Assertions.assertThat(jPath.getString("data.symbolList[0].symbolDetails.regLot")).as("regLot is blank/null")
				.isNotBlank().isNotEmpty().isNotNull();
	}

	@Test(enabled = true,groups = {"charts"})
	public void testMCXChartsApi() {
		String variety = helper.orderTypeCheckForComodity();
		String mcxTopic = ApiConfigFactory.getConfig().mcx_Topic_value();
		String durationType = variety.equalsIgnoreCase("AMO")?"W":"h";
		String startTime = helper.getCurrenctTimeMinus(durationType, 1);
		String endTime = helper.getCurrenctTime();

		Response mcxChartsApiRespoonse = baseAPI.getMCXCharts(1, "Req", mcxTopic, "OHLCV", "I", durationType, 1, startTime,
				endTime);
		Assert.assertTrue(mcxChartsApiRespoonse.getStatusCode() == 200, "Invalid Response for MCX charts api");
		ExtentLogger.pass("mcxChartsApi api test passed");
	}

	@Test(enabled = true,groups = {"charts"})
	public void testBSEChartsEquityApi() {
		String variety = helper.orderTypeCheckForEquity();
		String durationType = variety.equalsIgnoreCase("AMO")?"W":"h";
		String startTime = helper.getCurrenctTimeMinus(durationType, 1);
		String endTime = helper.getCurrenctTime();
		String bSE_Equity_Topic_value = ApiConfigFactory.getConfig().bSE_Equity_Topic_value();
		Response bseChartsEquity = baseAPI.getBSEChartsEquity(1, "Req", bSE_Equity_Topic_value, "OHLCV", "I",
				durationType, 1, startTime, endTime);
		ExtentLogger.info(bseChartsEquity.asPrettyString());
		Assert.assertTrue(bseChartsEquity.getStatusCode() == 200, "Invalid Response for bseChartsEquity");
		ExtentLogger.pass("bseChartsEquity api test passed");
	}

	@Test(enabled = true,groups = {"charts"})
	public void testNSEChartsEquityApi() {
		String variety = helper.orderTypeCheckForEquity();
		String durationType = variety.equalsIgnoreCase("AMO")?"W":"h";
		String nSE_Equity_Topic_value = ApiConfigFactory.getConfig().nSE_Equity_Topic_value();
		String startTime = helper.getCurrenctTimeMinus(durationType, 1);
		String endTime = helper.getCurrenctTime();
		Response nseChartsEquity = baseAPI.getNSEChartsEquity(1, "Req", nSE_Equity_Topic_value, "OHLCV", "I",
				durationType, 1, startTime, endTime);
		ExtentLogger.info(nseChartsEquity.asPrettyString());
		Assert.assertTrue(nseChartsEquity.getStatusCode() == 200, "Invalid Response nseChartsEquity");
		ExtentLogger.pass("nseChartsEquity api test passed");
	}

	@Test(enabled = true,groups = {"charts"})
	public void testNSEChartsCurrencyApi() {
		String variety = helper.orderTypeCheckForCurrency();
		String durationType = variety.equalsIgnoreCase("AMO")?"W":"h";
		String nSE_CURRENCY_Topic_value = ApiConfigFactory.getConfig().nSE_CURRENCY_Topic_value();
		String startTime = helper.getCurrenctTimeMinus(durationType, 1);
		String endTime = helper.getCurrenctTime();
		Response nseChartsCurrency = baseAPI.getNSEChartsCurrency(1, "Req", nSE_CURRENCY_Topic_value, "OHLCV", "I",
				durationType, 1, startTime, endTime);
		ExtentLogger.info(nseChartsCurrency.asPrettyString());
		Assert.assertTrue(nseChartsCurrency.getStatusCode() == 200, "Invalid Response nseChartsCurrency");
		ExtentLogger.pass("nseChartsCurrency api test passed");
	}

	@Test(enabled = true,groups = {"charts"})
	public void testNSEChartsFNOApi() {
		String variety = helper.orderTypeCheckForEquity();
		String durationType = variety.equalsIgnoreCase("AMO")?"W":"h";
		String nSE_FNO_Topic_value = ApiConfigFactory.getConfig().nSE_FNO_Topic_value();
		String startTime = helper.getCurrenctTimeMinus(durationType, 1);
		String endTime = helper.getCurrenctTime();
		Response nseChartsFNO = baseAPI.getNSEChartsFNO(17, "Req", nSE_FNO_Topic_value, "OHLCV", "I", durationType, 1,
				startTime, endTime);
		//ExtentLogger.info(nseChartsFNO.asPrettyString());
		Assert.assertTrue(nseChartsFNO.getStatusCode() == 200, "Invalid Response nseChartsFNO");
		//ExtentLogger.pass("nseChartsFNO api test passed");
		JSONArray jsonArrayResponse = new JSONArray(nseChartsFNO.asString());
		JSONObject object = jsonArrayResponse.getJSONObject(0);
		Double closePrice = object.getDouble("close");
		System.out.println("Close Price "+closePrice);
		
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
		String data = watchlists.jsonPath().getString("data.watchlistData");
		String watchlistdata = baseAPI.decodeData(data);
		JSONArray jsonArray = new JSONArray(watchlistdata);
		JSONObject object = jsonArray.getJSONObject(0);
		Long watchlistId = object.getLong("watchlistId");
		String watchlistName = object.getString("watchlistName");
		Assertions.assertThat(watchlistId).isNotNull();
		Assertions.assertThat(watchlistName).isNotNull();
	}
	
	
	@Test(enabled = true)
	public void testProfileData() throws Exception {

		Response profileDataResponse = baseAPI.getProfileData();
		Assert.assertEquals(profileDataResponse.getStatusCode(), 200, "Error in profileDataResponse api ");
		Assert.assertEquals(profileDataResponse.jsonPath().getString("status"), "success",
				"Status doesnt match in profileDataResponse api ");
		String clientId = profileDataResponse.jsonPath().getString("data.clientId");
		String dpNumber = profileDataResponse.jsonPath().getString("data.dpNumber");
		String mobileNum = profileDataResponse.jsonPath().getString("data.mobile");
		String fullName = profileDataResponse.jsonPath().getString("data.clientDetails.fullName");
		String email = profileDataResponse.jsonPath().getString("data.clientDetails.email");
		String birthdate = profileDataResponse.jsonPath().getString("data.clientDetails.birthdate");
		String gender = profileDataResponse.jsonPath().getString("data.clientDetails.gender");
		String address = profileDataResponse.jsonPath().getString("data.clientDetails.address");
		String bankName = profileDataResponse.jsonPath().getString("data.bankDetails[0].bankName");
		String branchName = profileDataResponse.jsonPath().getString("data.bankDetails[0].branchName");
		String accountType = profileDataResponse.jsonPath().getString("data.bankDetails[0].accountType");
		String ifscCode = profileDataResponse.jsonPath().getString("data.bankDetails[0].ifscCode");
		
		Assertions.assertThat(clientId).as("client is null").isNotBlank().isNotEmpty().isNotNull();
		Assertions.assertThat(dpNumber).as("dpNumber is null").isNotBlank().isNotEmpty().isNotNull();
		Assertions.assertThat(mobileNum).as("mobileNum is null").isNotBlank().isNotEmpty().isNotNull();
		Assertions.assertThat(fullName).as("fullName is null").isNotBlank().isNotEmpty().isNotNull();
		Assertions.assertThat(email).as("email is null").isNotBlank().isNotEmpty().isNotNull();
		Assertions.assertThat(birthdate).as("birthdate is null").isNotBlank().isNotEmpty().isNotNull();
		Assertions.assertThat(gender).as("gender is null").isNotBlank().isNotEmpty().isNotNull();
		Assertions.assertThat(address).as("address is null").isNotBlank().isNotEmpty().isNotNull();
		Assertions.assertThat(bankName).as("bankName is null").isNotBlank().isNotEmpty().isNotNull();
		Assertions.assertThat(branchName).as("branchName is null").isNotBlank().isNotEmpty().isNotNull();
		Assertions.assertThat(accountType).as("accountType is null").isNotBlank().isNotEmpty().isNotNull();
		Assertions.assertThat(ifscCode).as("ifscCode is null").isNotBlank().isNotEmpty().isNotNull();
	}
	

	@Test(enabled = false)
	public void testSetWatchlist() throws Exception {
		String jsonFilePath = "requests/setWatchlistData.json";
		String scriptId = "10666";
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
		String versionId = watchlists.jsonPath().getString("data.version");
		String setWatchListData = helper.modifyJsonData(jsonFilePath, scriptId);
		String encodedWatchListData = baseAPI.encodeJsonData(setWatchListData);
		Response callSetWatchListApi = baseAPI.callSetWatchListApi(Integer.valueOf(versionId), encodedWatchListData);
		Assert.assertEquals(callSetWatchListApi.getStatusCode(), 200, "Error in watchlists api ");
	}

	@Test(enabled = true)
	public void verifyHolding() throws Exception {
		String variety = helper.orderTypeCheckForEquity();
		// Place two Market Orders
		Response response1 = baseAPI.placeStockOrder("MARKET", "0.0", "DELIVERY", "14366", "IDEA-EQ", variety);
		validateAPIResponse(response1);

		String quantity = "";
		String invValue = "";
		Thread.sleep(5000);
		Response holding = baseAPI.getHolding();
		Assert.assertTrue(holding.getStatusCode() == 200, "Invalid Response for getHolding API");
		// String data = holding.jsonPath().getString("data.HoldingDetail");
		JsonPath jPath = new JsonPath(holding.asString());
		List<String> list = holding.jsonPath().getList("data.HoldingDetail");
		System.out.println("Total Holdings = " + list.size());
		int s = list.size();
		for (int i = 0; i < s; i++) {
			String symbol = jPath.getString("data.HoldingDetail[" + i + "].tradeSymbol");
			if (symbol.equalsIgnoreCase("IDEA-EQ")) {
				quantity = jPath.getString("data.HoldingDetail[" + i + "].qty");
				System.out.println("Quantity " + quantity);
				invValue = jPath.getString("data.HoldingDetail[" + i + "].invValue");
				System.out.println("invValue " + invValue);
				break;
			}
		}
		Assert.assertNotEquals(quantity, "", "Order didnt reach to holding");
		if (!quantity.equalsIgnoreCase(""))
			Assert.assertTrue(Integer.valueOf(quantity) >= 1, "Order Quantity Doesnt match");
		Assert.assertNotNull(invValue, "Invested Price is null");

		Response response = baseAPI.placeStockOrder("NSE", "MARKET", "0.0", "DELIVERY", "1", "0", "14366", "IDEA-EQ",
				"SELL", "0.0", variety);
		Assert.assertTrue(response.getStatusCode() == 200, "Invalid Response for getHolding API");
		Assert.assertEquals(response.jsonPath().getString("message"), "SUCCESS", "Error in PlaceOrderAPI ");
	}

	@Test(enabled = true)
	public void testPledgeMarginAmountApi() throws Exception {

		Response marginResponse = baseAPI.callMarginAmountApi(cDetails.getClientId());
		Assert.assertTrue(marginResponse.getStatusCode() == 200, "Invalid Response for getPostion API");
		String data = marginResponse.jsonPath().getString("data");
		String decryptedData = baseAPI.decodeData(data);
		JsonPath jPath = new JsonPath(decryptedData);
		String avaMargin = jPath.getString("eligibleMargin");
		String eligibleWithdraw = jPath.getString("eligibleWithdraw");
		System.out.println("avaMargin  = " + avaMargin + " eligibleWithdraw = " + eligibleWithdraw);
		Assert.assertNotNull(avaMargin, "avaMargin didnt found");
		Assert.assertNotNull(eligibleWithdraw, "eligibleWithdraw didnt found");
		Assert.assertTrue(Double.valueOf(avaMargin) > 0, "eligibleMargin is equal to or less than 0");

	}



	@Test(enabled = true)
	public void pledgeUserSecurityApi() throws Exception {

		Response userSecurity = baseAPI.callGetUserSecurityAPI(cDetails.getClientId());
		Assert.assertTrue(userSecurity.getStatusCode() == 200, "Invalid Response for getPostion API");

		String data = userSecurity.jsonPath().getString("data");
		String decodedValue = baseAPI.decodeData(data);
		JsonPath jPath = new JsonPath(decodedValue);
		String mobileNumber = jPath.getString("stock_list[0].Mobile_Num");
		String clientType = jPath.getString("stock_list[0].Client_Type");
		String clientName = jPath.getString("stock_list[0].Client_name");

		Assert.assertNotNull(mobileNumber, "mobile number didnt found");
		Assert.assertNotNull(clientType, "client type didnt found");
		Assert.assertNotNull(clientName, "client name didnt found");
	}

	@Test(enabled = true)
	public void pledgeGetWithdrawSecurityAPI() throws Exception {

		Response withdrawSecurity = baseAPI.callGetWithdrawSecurityAPI(cDetails.getClientId());
		Assert.assertTrue(withdrawSecurity.getStatusCode() == 200, "Invalid Response for getUserSecurity API");
		String data = withdrawSecurity.jsonPath().getString("data");
		String decodedValue = baseAPI.decodeData(data);
		JsonPath jPath = new JsonPath(decodedValue);
		String qty = jPath.getString("stock_list[0].qty");
		String netAmount = jPath.getString("stock_list[0].net_amt");
		Assert.assertNotNull(qty, "The value of quantity is not shown in response");
		Assert.assertNotNull(netAmount, "The value of Netamount is not shown in response");

	}

	@Test(enabled = true)
	public void testTopGainerNLoserApi() throws Exception {
		MARKET_MOVERS_DATA.forEach(queryParam -> {
			Response TopGainerNLoser = baseAPI.getTopGainerNLoser(queryParam);
			Assert.assertTrue(TopGainerNLoser.getStatusCode() == 200, "Invalid Response for getUserSecurity API");
			Assert.assertEquals(TopGainerNLoser.jsonPath().getString("status"), "success", "Error in PlaceOrderAPI ");

			String symbolName = TopGainerNLoser.jsonPath().getString("data.response.data.bse[0].symbolName");
			String details = TopGainerNLoser.jsonPath().getString("data.response.data.bse[0].details");
			String ltp = TopGainerNLoser.jsonPath().getString("data.response.data.bse[0].details");
			String change = TopGainerNLoser.jsonPath().getString("data.response.data.bse[0].change");
			String changePer = TopGainerNLoser.jsonPath().getString("data.response.data.bse[0].changePer");

			Assertions.assertThat(symbolName).as("symbolName is null for market movers " + queryParam).isNotEmpty()
					.isNotNull().isNotBlank();
			Assertions.assertThat(String.valueOf(details)).as("details is null for market movers " + queryParam)
					.isNotEmpty().isNotNull().isNotBlank();
			Assertions.assertThat(String.valueOf(ltp)).as("ltp is null for market movers " + queryParam).isNotEmpty()
					.isNotNull().isNotBlank();
			Assertions.assertThat(change).as("change is null for market movers " + queryParam).isNotEmpty().isNotNull()
					.isNotBlank();
			Assertions.assertThat(changePer).as("changePer is null for market movers " + queryParam).isNotEmpty()
					.isNotNull().isNotBlank();
		});
	}

	@Test(enabled = true)
	public void testFundRMSAPI() throws Exception {
		// Call FundRMSLimit API
		Response fundRmsLimitResponse = baseAPI.callFundRMSLimitApi("", "", "");
		Assert.assertTrue(fundRmsLimitResponse.getStatusCode() == 200, "Invalid Response for getPostion API");

		// Assert
		SoftAssertions softAssertions = new SoftAssertions();
		String deposit = fundRmsLimitResponse.jsonPath().getString("data.deposit");
		String cashDeposit = fundRmsLimitResponse.jsonPath().getString("data.cashDeposit");
		String netAvailableFunds = fundRmsLimitResponse.jsonPath().getString("data.netAvailableFunds");
		String fundsForTrading = fundRmsLimitResponse.jsonPath().getString("data.fundsForTrading");
		String fundsAvailable = fundRmsLimitResponse.jsonPath().getString("data.fundsAvailable");

		softAssertions.assertThat(deposit).as("Deport is null").isNotNull().as("Deport is zero").isNotEqualTo("0");

		softAssertions.assertThat(cashDeposit).as("cashDeposit is null").isNotNull().as("cashDeposit is zero")
				.isNotEqualTo("0");

		softAssertions.assertThat(netAvailableFunds).as("netAvailableFunds is null").isNotNull()
				.as("netAvailableFunds is zero").isNotEqualTo("0");

		softAssertions.assertThat(fundsForTrading).as("fundsForTrading is null").isNotNull()
				.as("fundsForTrading is zero").isNotEqualTo("0");

		softAssertions.assertThat(fundsAvailable).as("fundsAvailable is null").isNotNull().as("fundsAvailable is zero")
				.isNotEqualTo("0");

		softAssertions.assertAll();

	}

	private void verifyOrderIsCancelled(String orderNum, String variety) {
		// Call getOrderBook to verify order is cancelled
		List<String> cancelledorderIds = null;
		Response callOrdersApiForCancelOrder = baseAPI.getOrderBook();
		GetOrdersDetailsResponsePOJO map = callOrdersApiForCancelOrder.getBody().as(GetOrdersDetailsResponsePOJO.class);
		List<OrdersDetailsData> dataCancel = map.getData();
		if (variety.equalsIgnoreCase("NORMAL"))
			cancelledorderIds = dataCancel.stream().filter(x -> (x.getOrderstatus().contains("cancelled")))
					.map(x -> x.getOrderid()).collect(Collectors.toList());
		else
			cancelledorderIds = dataCancel.stream().filter(x -> (x.getOrderstatus().contains("AMO CANCELLED")))
					.map(x -> x.getOrderid()).collect(Collectors.toList());

		if (cancelledorderIds.contains(orderNum))
			ExtentLogger.info(orderNum + " Order Id found in cancelled order");
		else {
			ExtentLogger.fail(orderNum + " Order Id not cancelled");
			Assert.fail("Order Id not cancelled " + orderNum);
		}
	}

	private void cancelAndVerify(String variety, String orderNum, String orderDetails) throws InterruptedException {
		if (orderDetails.contains(orderNum) && !orderDetails.contains("rejected")) {
			Response cancelOrderResponse = baseAPI.cancelOrder(orderNum, variety);
			Assert.assertTrue(cancelOrderResponse.getStatusCode() == 200, "some error in placeBuyComodityOrder api ");
			// Verify Order is cancelled
			Thread.sleep(1000);
			verifyOrderIsCancelled(orderNum, variety);
		}
	}

	private void validateAPIResponse(Response response) {
		if (response.getStatusCode() != 200
				|| !(response.jsonPath().getString("message").equalsIgnoreCase("SUCCESS"))) {
			ExtentLogger.fail("some error in placeOrder api \n" + response.prettyPrint());
			Assert.fail("Place Order isnt successful");
		} else
			ExtentLogger.pass("Order Placement Successful \n" + response.prettyPrint());

	}
}
