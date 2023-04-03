package com.angelone.tests.orderapi;

import java.io.IOException;
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

	@Test(enabled = true)
	void placeOrder_Buy_DELIVERY_LimitOrder_Cash() throws IOException, InterruptedException {

		// get LTP price to manipulate Limit Price
		String pLtp = baseAPI.getLTPPrice("10666", "nse_cm");
		String ltpPrice = helper.BuyroundoffValueToCancelOrder(pLtp);
		System.out.println("Post rounding off LTP value = " + ltpPrice);
		ExtentLogger.info("Post rounding off LTP value = " + ltpPrice);
		String variety = helper.orderTypeCheckForEquity();
		// Place Market Orders
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
			ExtentLogger.fail(orderNum + " Order Id not found in open order.Proably rejected.Please check manually");
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
			ExtentLogger.fail(orderNum + " Order Id not found in open order.Proably rejected.Please check manually");
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
			ExtentLogger.fail(orderNum + " Order Id not found in open order.Proably rejected.Please check manually");
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
			ExtentLogger.fail(orderNum + " Order Id not found in open order.Proably rejected.Please check manually");
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
			ExtentLogger.fail(orderNum + " Order Id not found in open order.Proably rejected.Please check manually");
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
			ExtentLogger.fail(orderNum + " Order Id not found in open order.Proably rejected.Please check manually");
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
			ExtentLogger.fail(orderNum + " Order Id not found in open order.Proably rejected.Please check manually");
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
			ExtentLogger.fail(orderNum + " Order Id not found in open order.Proably rejected.Please check manually");
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
		ltpPrice = helper.SellTriggerPriceGreater(pLtp);
		System.out.println("Post rounding off LTP value = " + ltpPrice);
		// Place Market Orders
		response = baseAPI.placeStockOrder("NSE", "LIMIT", ltpPrice, "DELIVERY", "1", "0", "248", "ADL-BE",
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
			ExtentLogger.fail(orderNum + " Order Id not found in open order.Proably rejected.Please check manually");
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
	void testMarketMoversByMost() throws IOException {
		Response marketMovers = baseAPI.getMarketMoversByMost("MOST_ACT_VALUE");
		ExtentLogger.info(marketMovers.asPrettyString());
		Assert.assertEquals(marketMovers.getStatusCode(), 200, "Error in marketMovers api ");
		Assert.assertEquals(marketMovers.jsonPath().getString("status"), "success",
				"Status doesnt match in marketMovers api ");
		Assert.assertTrue(!marketMovers.jsonPath().getString("data").isEmpty(), "data is empty in marketMovers api ");
		String data = marketMovers.jsonPath().getString("data");
		String decodeJsonResponse = baseAPI.decodeJsonResponse(data);
		System.out.println("Market Data - " + decodeJsonResponse);
		JsonPath jPath = new JsonPath(decodeJsonResponse);

		List<String> list = jPath.getList("Data");
		System.out.println("Data Size = " + list.size());
		int totalValue = list.size();
		Assertions.assertThat(totalValue).as("total market moves not greater than 0").isGreaterThan(0);
	}

	@Test(enabled = true)
	public void testChartsApi() throws Exception {

		String bSE_Equity_Topic_value = ApiConfigFactory.getConfig().bSE_Equity_Topic_value();
		String nSE_Equity_Topic_value = ApiConfigFactory.getConfig().nSE_Equity_Topic_value();
		String nSE_CURRENCY_Topic_value = ApiConfigFactory.getConfig().nSE_CURRENCY_Topic_value();
		String nSE_FNO_Topic_value = ApiConfigFactory.getConfig().nSE_FNO_Topic_value();
		String mcxTopic = ApiConfigFactory.getConfig().mcx_Topic_value();
		String durationType = ApiConfigFactory.getConfig().durationType();

		String startTime = helper.getCurrenctTimeMinus(durationType, 1);
		String endTime = helper.getCurrenctTime();

		Response bseChartsEquity = baseAPI.getBSEChartsEquity(1, "Req", bSE_Equity_Topic_value, "OHLCV", "I",
				durationType, 1, startTime, endTime);
		ExtentLogger.info(bseChartsEquity.asPrettyString());
		Assert.assertTrue(bseChartsEquity.getStatusCode() == 200, "Invalid Response for bseChartsEquity");
		ExtentLogger.pass("bseChartsEquity api test passed");
		Response nseChartsEquity = baseAPI.getNSEChartsEquity(1, "Req", nSE_Equity_Topic_value, "OHLCV", "I",
				durationType, 1, startTime, endTime);
		ExtentLogger.info(nseChartsEquity.asPrettyString());
		Assert.assertTrue(nseChartsEquity.getStatusCode() == 200, "Invalid Response nseChartsEquity");
		ExtentLogger.pass("nseChartsEquity api test passed");
		Response nseChartsCurrency = baseAPI.getNSEChartsCurrency(1, "Req", nSE_CURRENCY_Topic_value, "OHLCV", "I",
				durationType, 1, startTime, endTime);
		ExtentLogger.info(nseChartsCurrency.asPrettyString());
		Assert.assertTrue(nseChartsCurrency.getStatusCode() == 200, "Invalid Response nseChartsCurrency");
		ExtentLogger.pass("nseChartsCurrency api test passed");
		Response nseChartsFNO = baseAPI.getNSEChartsFNO(17, "Req", nSE_FNO_Topic_value, "OHLCV", "I", durationType, 1,
				startTime, endTime);
		ExtentLogger.info(nseChartsFNO.asPrettyString());
		Assert.assertTrue(nseChartsFNO.getStatusCode() == 200, "Invalid Response nseChartsFNO");
		ExtentLogger.pass("nseChartsFNO api test passed");

		Response mcxChartsApiRespoonse = baseAPI.getMCXCharts(1, "Req", mcxTopic, "OHLCV", "I", "h", 1, startTime,
				endTime);
		Assert.assertTrue(mcxChartsApiRespoonse.getStatusCode() == 200, "Invalid Response for MCX charts api");
		ExtentLogger.pass("mcxChartsApi api test passed");
	}

	@Test(enabled = true)
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
		String data = watchlists.jsonPath().getString("data.watchlistData");
		String watchlistdata = baseAPI.decodeJsonResponse(data);
		System.out.println("WatchList Data - " + watchlistdata);
		JSONArray jsonArray = new JSONArray(watchlistdata);
		JSONObject object = jsonArray.getJSONObject(0);
		Long watchlistId = object.getLong("watchlistId");
		String watchlistName = object.getString("watchlistName");
		Assertions.assertThat(watchlistId).isNotNull();
		Assertions.assertThat(watchlistName).isNotNull();
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
	public void testMarginAmountApi() throws Exception {

		Response marginResponse = baseAPI.callMarginAmountApi(cDetails.getClientId());
		Assert.assertTrue(marginResponse.getStatusCode() == 200, "Invalid Response for getPostion API");
		String data = marginResponse.jsonPath().getString("data");
		String decryptedData = baseAPI.decodeJsonResponse(data);
		JsonPath jPath = new JsonPath(decryptedData);
		String avaMargin = jPath.getString("eligibleMargin");
		String eligibleWithdraw = jPath.getString("eligibleWithdraw");
		System.out.println("avaMargin  = " + avaMargin + " eligibleWithdraw = " + eligibleWithdraw);
		Assert.assertNotNull(avaMargin, "avaMargin didnt found");
		Assert.assertNotNull(eligibleWithdraw, "eligibleWithdraw didnt found");
		Assert.assertTrue(Double.valueOf(avaMargin) > 0, "eligibleMargin is equal to or less than 0");

	}

	@Test(enabled = true)
	public void getFundsWithdrawalData() throws Exception {

		Response positions = baseAPI.callFundWithdrawalDataApi("PartyCode", cDetails.getClientId());
		Assert.assertTrue(positions.getStatusCode() == 200, "Invalid Response for getPostion API");
		JSONArray jsonArray = new JSONArray(positions.asString());
		JSONObject object = jsonArray.getJSONObject(0);
		String holderName = object.getString("long_name");
		System.out.println("User name - " + holderName);
		String txnType = object.getString("Drcr");
		System.out.println("Debit/Credit - " + txnType);
		String accountNum = object.getString("ACNUM");
		System.out.println("ActNum - " + accountNum);
		String bankName = object.getString("BANKNAME");
		System.out.println("BANKNAME - " + bankName);
		String partyCode = object.getString("party_code");
		System.out.println("party_code - " + partyCode);
		Assert.assertNotNull(holderName);
		Assert.assertNotNull(txnType);
		Assert.assertNotNull(accountNum);
		Assert.assertNotNull(bankName);
		Assert.assertNotNull(partyCode);
		for (int i = 0; i < jsonArray.length(); i++) {
			if (jsonArray.getJSONObject(i).getString("Entity").equalsIgnoreCase("AllSegment")) {

				String netPayable = jsonArray.getJSONObject(i).getString("NetPayable");
				Assert.assertNotEquals(netPayable, "0.0000", "AllSegment has 0 netPayable amt");
				break;
			}
		}
	}

	@Test(enabled = true)
	public void pledgeUserSecurityApi() throws Exception {

		Response userSecurity = baseAPI.callGetUserSecurityAPI(cDetails.getClientId());
		Assert.assertTrue(userSecurity.getStatusCode() == 200, "Invalid Response for getPostion API");

		String data = userSecurity.jsonPath().getString("data");
		String decodedValue = baseAPI.decodeJsonResponse(data);
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
		String decodedValue = baseAPI.decodeJsonResponse(data);
		JsonPath jPath = new JsonPath(decodedValue);
		String qty = jPath.getString("stock_list[0].qty");
		String netAmount = jPath.getString("stock_list[0].net_amt");
		Assert.assertNotNull(qty, "The value of quantity is not shown in response");
		Assert.assertNotNull(netAmount, "The value of Netamount is not shown in response");

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
