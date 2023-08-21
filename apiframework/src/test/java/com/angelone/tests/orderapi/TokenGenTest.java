package com.angelone.tests.orderapi;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.zip.Deflater;
import java.util.zip.GZIPInputStream;

import javax.crypto.spec.SecretKeySpec;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.SoftAssertions;
import org.json.JSONArray;
import org.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.Assertion;
import org.testng.asserts.SoftAssert;

import com.angelone.api.BaseTestApi;
import com.angelone.api.pojo.GetOrdersDetailsResponsePOJO;
import com.angelone.api.pojo.OrdersDetailsData;
import com.angelone.api.utility.Helper;
import com.angelone.config.factory.ApiConfigFactory;
import com.angelone.reports.ExtentLogger;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonArrayFormatVisitor;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class TokenGenTest extends BaseTestApi {

    @Test(enabled = true)
    void placeOrder() throws IOException {
        //Generate User Mpin Token
        String userdetails = "9741636854:upendra101087@gmail.com:qeewrwwqzycawdcs:U50049267:2222";
        String secKey = "db3a62b2-45f6-4b6c-a74b-80ce27491bb7";
        generateUserToken(userdetails, secKey);

        //Place two AMO Orders
        Response response1 = placeStockOrder("MARKET", "0.0", "INTRADAY", "10666", "PNB-EQ", "NORMAL");
        Assert.assertTrue(response1.getStatusCode() == 200, "some error in placeOrder api ");
        /*
         * Response response2 = placeStockOrder("NSE", "MARKET", "0.0", "INTRADAY",
         * "1491", "IFCI-EQ", "AMO"); Assert.assertTrue(response2.getStatusCode()==
         * 200,"some error in placeOrder api ");
         *
         * //Call gerOrderBook api to collect all orders Response callOrdersApi =
         * getOrderBook(); Assert.assertTrue(callOrdersApi.getStatusCode()==
         * 200,"some error in callOrdersApi api "); GetOrdersDetailsResponsePOJO as =
         * callOrdersApi.getBody().as(GetOrdersDetailsResponsePOJO.class);
         * List<OrdersDetailsData> data = as.getData();
         *
         * //Filter all Open orders and call cancelOrder Api to cancel
         * List<OrdersDetailsData> datafiltered = data.stream() .filter(x ->
         * !(x.getOrderstatus().contains("CANCELLED") ||
         * x.getOrderstatus().contains("REJECTED"))) .collect(Collectors.toList());
         * datafiltered.forEach(orderId -> { Response cancelOrderResponse =
         * cancelOrder(orderId.getOrderid(), "AMO");
         * Assert.assertTrue(cancelOrderResponse.getStatusCode()==
         * 200,"some error in callOrdersApi api "); });
         */
    }

    @Test(enabled = true)
    void modifyEquityOrder() throws IOException {
        //Generate User Mpin Token
        String userdetails = "9741636854:upendra101087@gmail.com:qeewrwwqzycawdcs:U50049267:2222";
        String secKey = "aAJALJFLJLSLFJ@##@!12123a";
        generateUserToken(userdetails, secKey);
        Helper helper = new Helper();
        // get LTP price to manipulate Limit Price
        String pLtp = getLTPPrice("3045", "nse_cm");
        String ltpPrice = helper.BuyroundoffValueToCancelOrder(pLtp);
        System.out.println("Post rounding off LTP value = " + ltpPrice);
        String variety = helper.orderTypeCheckForEquity();
        // Place Market Orders
        Response response = placeStockOrder("LIMIT", ltpPrice, "DELIVERY", "3045", "SBIN-EQ", variety);
        String orderNum = response.jsonPath().getString("data.orderid");
        // Call getOrderBook
        Response callOrdersApi = getOrderBook();// OrderBookAPi
        GetOrdersDetailsResponsePOJO as = callOrdersApi.getBody().as(GetOrdersDetailsResponsePOJO.class);
        List<OrdersDetailsData> data = as.getData();

        OrdersDetailsData orderIds = data.stream().filter(x -> x.getOrderid().toString().equalsIgnoreCase(orderNum))
                .findFirst().get();

        Response modifyStockOrder = modifyStockOrder("2", orderIds.getDuration(), orderIds.getExchange(), orderIds.getMultiplier(), orderIds.getOrderValidityDate(), orderIds.getOrderid(), orderIds.getOrdertype(), orderIds.getPrecision(), orderIds.getPrice(), "2", orderIds.getSymboltoken(), orderIds.getTriggerprice(), variety);

        // Call getOrderBook
        Response orderBook = getOrderBook();// OrderBookAPi
        GetOrdersDetailsResponsePOJO entity = orderBook.getBody().as(GetOrdersDetailsResponsePOJO.class);
        List<OrdersDetailsData> orderData = entity.getData();

        OrdersDetailsData modifiedOrder = orderData.stream().filter(x -> x.getOrderid().toString().equalsIgnoreCase(orderNum))
                .findFirst().get();
        Assertions.assertThat(modifiedOrder.getQuantity()).as("Quantity isnt updated").isEqualTo("2");
        Assertions.assertThat(modifiedOrder.getDisclosedquantity()).as("Quantity isnt updated").isEqualTo("2");

        // Cancel Order
        Response cancelOrderResponse = cancelOrder(orderNum, variety);
        Assert.assertTrue(cancelOrderResponse.getStatusCode() == 200, "some error in placeOrder api ");

    }


    @Test(enabled = true)
    public void Orders_StopLoss_BuyOrder_From_Positions() throws Exception {

        String userdetails = "9741636854:upendra101087@gmail.com:qeewrwwqzycawdcs:U50049267:2222";
        String secKey = "db3a62b2-45f6-4b6c-a74b-80ce27491bb7";
        generateUserToken(userdetails, secKey);
        String pLtp = getLTPPrice("12018", "nse_cm");
        String ltpPrice = BuyroundoffValueToCancelOrder(pLtp);
        String triggerPrice = getTriggerPriceValueForBuy(ltpPrice);
        //Market Order
        Response response = placeStockOrder("NSE", "STOPLOSS_LIMIT", ltpPrice, "DELIVERY", "1", "1", "12018", "SUZLON-EQ", "BUY", triggerPrice, "AMO");
        String orderId = response.jsonPath().getString("data.orderid");

    }


    @Test(enabled = true)
    void testExitPositions() throws IOException {
        String userdetails = "9741636854:upendra101087@gmail.com:qeewrwwqzycawdcs:U50049267:2222";
        String secKey = "db3a62b2-45f6-4b6c-a74b-80ce27491bb7";
        generateUserToken(userdetails, secKey);
        Response callOrdersApi = getOrderBook();// OrderBookAPi
        GetOrdersDetailsResponsePOJO as = callOrdersApi.getBody().as(GetOrdersDetailsResponsePOJO.class);
        List<OrdersDetailsData> data = as.getData();
        List<OrdersDetailsData> datafiltered = data.stream()
                .filter(x -> (x.getOrderstatus().contains("complete") && x.getTransactiontype().equalsIgnoreCase("BUY")
                        && !x.getProducttype().equalsIgnoreCase("CARRYFORWARD")))
                .collect(Collectors.toList());

        System.out.println(" $$$$$$$$$$$ completed orders $$$$$$$$$$$ \n");
        datafiltered.forEach(orderId -> System.out.println(orderId.getOrderid()));

        datafiltered.forEach(orderId -> {
            Response response = placeStockOrder(orderId.getExchange(), orderId.getOrdertype(), orderId.getPrice(),
                    orderId.getProducttype(), orderId.getQuantity(), orderId.getStoploss(), orderId.getSymboltoken(),
                    orderId.getTradingsymbol(), "SELL", "0.0", "NORMAL");
        });
    }

    @Test(enabled = false)
    void testNonTradedAccessToken() throws IOException {
        String userdetails = "9741636854:upendra101087@gmail.com:qeewrwwqzycawdcs:U50049267:2222";
        getNonTradingAccessToken(userdetails);
        Response marketBuiltupResponse = getMarketBuiltup("NEAR", "All", "Short Covering");
        String data = marketBuiltupResponse.jsonPath().getString("data");
        //String scriptValue= decodeJsonResponse(data);

    }

    @Test(enabled = true)
    void testMarketMoversByMost() throws IOException {
        String userdetails = "9741636854:upendra101087@gmail.com:qeewrwwqzycawdcs:U50049267:2222";
        //getNonTradingAccessToken(userdetails);
        Map<String, Double> mapForSymbolnValue = new HashMap<String, Double>();
        Response marketMovers = getMarketMoversByMost("MOST_ACT_VALUE");
        //Assert.assertEquals(marketMovers.getStatusCode(),200,"Error in marketMovers api ");
        //Assert.assertEquals(marketMovers.jsonPath().getString("status"),"success","Status doesnt match in marketMovers api ");
        //Assert.assertTrue(!marketMovers.jsonPath().getString("data").isEmpty(),"data is empty in marketMovers api ");
        String data = marketMovers.jsonPath().getString("data");
        String scriptValue = decodeJsonResponse(data);
        JsonPath jPath = new JsonPath(scriptValue);

        /*
         * List<String> list = jPath.getList("Data");
         * System.out.println("Data Size = "+list.size()); int s = list.size(); for(int
         * i = 0; i < s; i++) { String symbol =
         * jPath.getString("Data["+i+"].SymbolName"); String ltp =
         * jPath.getString("Data["+i+"].Ltp"); mapForSymbolnValue.put(symbol,
         * Double.valueOf(ltp)); }
         *
         * Entry<String, Double> entry = mapForSymbolnValue .entrySet() .stream()
         * .sorted(Map.Entry.comparingByValue(Comparator.naturalOrder())) .findFirst()
         * .get(); String lowestPriceScript = entry.getKey();
         * System.out.println("lowest price script is "+lowestPriceScript);
         */

        List<String> list = jPath.getList("Data");
        System.out.println("Data Size = " + list.size());
        int totalValue = list.size();
        Assertions.assertThat(totalValue).as("total market moves not greater than 0").isGreaterThan(0);

    }


    @Test(enabled = true)
    public void testGetWatchlist() throws Exception {
        String userdetails = "9741636854:upendra101087@gmail.com:qeewrwwqzycawdcs:U50049267:2222";
        getNonTradingAccessToken(userdetails);
       // getNonTradingAccessTokenWithoutOtp(userdetails);
        Response watchlists = getWatchLists();
        if(watchlists.getStatusCode()==401)
        {
            String nonTradingAccessToken = getNonTradingAccessToken(userdetails);
            String[] split = userdetails.split(":");
            Helper.updatePropertyValue("api-data.properties",split[3],nonTradingAccessToken);
            watchlists = getWatchLists();
        }
            
        Assert.assertEquals(watchlists.getStatusCode(), 200, "Error in watchlists api ");
        Assert.assertEquals(watchlists.jsonPath().getString("status"), "success", "Status doesnt match in watchlists api ");
        Assert.assertEquals(watchlists.jsonPath().getString("error_code"), "", "error_code doesnt match in watchlists api ");
        Assert.assertTrue(!watchlists.jsonPath().getString("data").isEmpty(), "data is empty in watchlists api ");
        Assert.assertTrue(!watchlists.jsonPath().getString("data.watchlistData").isEmpty(), "watchlistData is empty in watchlists api ");
        String data = watchlists.jsonPath().getString("data.watchlistData");
        String watchlistdata = new Helper().decompressData(data);
        System.out.println("WatchList Data - " + watchlistdata);
        JSONArray jsonArray = new JSONArray(watchlistdata);
        JSONObject object = jsonArray.getJSONObject(0);
        Long watchlistId = object.getLong("watchlistId");
        String watchlistName = object.getString("watchlistName");
        Assertions.assertThat(watchlistId).isNotNull();
        Assertions.assertThat(watchlistName).isNotNull();
    }


    @Test(enabled = true)
    public void testBSE_EquityCharts() throws Exception {
        String bSE_Equity_Topic_value = ApiConfigFactory.getConfig().bSE_Equity_Topic_value();
        String nSE_Equity_Topic_value = ApiConfigFactory.getConfig().nSE_Equity_Topic_value();
        String nSE_CURRENCY_Topic_value = ApiConfigFactory.getConfig().nSE_CURRENCY_Topic_value();
        String nSE_FNO_Topic_value = ApiConfigFactory.getConfig().nSE_FNO_Topic_value();
        String durationType = ApiConfigFactory.getConfig().durationType();

        Helper helper = new Helper();
        String endTime = helper.getCurrenctTime();
        String startTime = helper.getCurrenctTimeMinus(durationType, 1);

        Response bseChartsEquity = getBSEChartsEquity(1, "Req", bSE_Equity_Topic_value, "OHLCV", "I", "h", 1,
                startTime, endTime);
        Assert.assertTrue(bseChartsEquity.getStatusCode() == 200, "Invalid Response");

        Response nseChartsEquity = getNSEChartsEquity(1, "Req", nSE_Equity_Topic_value, "OHLCV", "I", "h", 1,
                startTime, endTime);
        Assert.assertTrue(nseChartsEquity.getStatusCode() == 200, "Invalid Response");

        Response nseChartsCurrency = getNSEChartsCurrency(1, "Req", nSE_CURRENCY_Topic_value, "OHLCV", "I", "h", 1,
                startTime, endTime);
        Assert.assertTrue(nseChartsCurrency.getStatusCode() == 200, "Invalid Response");

        Response nseChartsFNO = getNSEChartsFNO(17, "Req", nSE_FNO_Topic_value, "OHLCV", "I", "h", 1, startTime, endTime);
        Assert.assertTrue(nseChartsFNO.getStatusCode() == 200, "Invalid Response");

    }

    @Test(enabled = true)
    public void testGetHolding() throws Exception {
        //check
        String userdetails = "9741636854:upendra101087@gmail.com:qeewrwwqzycawdcs:U50049267:2222";
        String quantity = "";
        String invValue = "";
        //getNonTradingAccessToken(userdetails);
        Response holding = getHolding();
        Assert.assertTrue(holding.getStatusCode() == 200, "Invalid Response for getHolding API");
        //String data = holding.jsonPath().getString("data.HoldingDetail");
        JsonPath jPath = new JsonPath(holding.asString());

        List<String> list = holding.jsonPath().getList("data.HoldingDetail");
        System.out.println("Data Size = " + list.size());
        int s = list.size();
        for (int i = 0; i < s; i++) {
            String symbol = jPath.getString("data.HoldingDetail[" + i + "].tradeSymbol");
            if (symbol.equalsIgnoreCase("YESBANK-EQ")) {
                quantity = jPath.getString("data.HoldingDetail[" + i + "].qty");
                System.out.println("Quantity " + quantity);
                invValue = jPath.getString("data.HoldingDetail[" + i + "].invValue");
                System.out.println("invValue " + invValue);
                break;
            }
        }
        Assert.assertTrue(Integer.valueOf(quantity) >= 1, "Order Quantity Doesnt match");
        Assert.assertNotNull(invValue, "Invested Price is null");
    }


    @Test(enabled = true)
    public void GetHolding() throws Exception {

        Response holding = getHolding();
        //ExtentLogger.info(holding.asPrettyString());
        Assert.assertTrue(holding.getStatusCode() == 200, "Invalid Response for getHolding API");
        JsonPath jPath = new JsonPath(holding.asString());

        List<String> list = holding.jsonPath().getList("data.HoldingDetail");
        int size = list.size();
        System.out.println("Total Holdings = " + size);
        if (size > 0) {
            String quantity = jPath.getString("data.HoldingDetail[0].qty");
            String invValue = jPath.getString("data.HoldingDetail[0].invValue");
            String ltp = jPath.getString("data.HoldingDetail[0].ltp");
            String avgPrice = jPath.getString("data.HoldingDetail[0].avgPrice");
            Assertions.assertThat(quantity).as("Qty is null").isNotNull();
            Assertions.assertThat(Integer.valueOf(quantity)).as("Qty is nt greater than equal to 0").isGreaterThanOrEqualTo(0);
            Assertions.assertThat(Double.valueOf(ltp)).as("ltp is nt greater than 0").isGreaterThan(0);
            Assertions.assertThat(Double.valueOf(avgPrice)).as("avgPrice is nt greater than 0").isGreaterThan(0);
            Assertions.assertThat(Double.valueOf(invValue)).as("invValue is nt greater than 0").isGreaterThan(0);

        }

    }

    @Test(enabled = true)
    public void testOptionsAPI() throws Exception {
        //String userdetails = "9741636854:upendra101087@gmail.com:qeewrwwqzycawdcs:U50049267:2222";
        //getNonTradingAccessToken(userdetails);
        Response optionResult = callOptionsAPI("TCS", "feb 23 2023");
        Assert.assertTrue(optionResult.getStatusCode() == 200, "Invalid Response for getHolding API");
        JsonPath j = new JsonPath(optionResult.asString());

        List<String> list = optionResult.jsonPath().getList("Result.Data");
        System.out.println("Data Size = " + list.size());
        int s = list.size();
        for (int i = 0; i < s; i++) {
            String StkPrice = j.getString("Result.Data[" + i + "].StkPrice");
            String Expiry = j.getString("Result.Data[" + i + "].Expiry");
            System.out.println(StkPrice);
            System.out.println(Expiry);
        }
    }

    @Test
    public void testGetPositions() throws Exception {

        String userdetails = "9741636854:upendra101087@gmail.com:qeewrwwqzycawdcs:U50049267:2222";
        String secKey = "db3a62b2-45f6-4b6c-a74b-80ce27491bb7";
        generateUserToken(userdetails, secKey);
        Response positions = getPositions();
        Assert.assertTrue(positions.getStatusCode() == 200, "Invalid Response for getPostion API");
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

    }

    @Test(enabled = true)
    void testMpinAccessToken() throws IOException {
        String userdetails = "9741636854:upendra101087@gmail.com:qeewrwwqzycawdcs:U50049267:2222";
        // String userdetails =
        // "9742000367:sateeshbavana@gmail.com:jwppymyurxuttagh:S304062:2222";
        String secKey = "db3a62b2-45f6-4b6c-a74b-80ce27491bb7";

        generateUserToken(userdetails, secKey);
        //String pLtp = getLTPPrice("500113", "bse_cm");
        String pLtp = getLTPPrice("252905", "mcx_fo");
        // String pLtp = getLTPPrice("8741","cde_fo");

        // String ltpPrice = BuyroundoffValueToCancelOrder(pLtp);
        // Response response = placeStockOrder("BSE", "LIMIT", ltpPrice,"DELIVERY",
        // "1", "0","500113", "SAIL", "BUY","0.0","AMO");
        // Response response = placeStockOrder("NSE", "LIMIT", pLtp,"DELIVERY",
        // "1", "0","1491", "IFCI-EQ", "BUY","0.0","AMO");
        String ltoPrice = buyValueCustomPriceForCurrency(pLtp);
        Response response = placeStockOrder("MCX", "LIMIT", ltoPrice, "DELIVERY", "1", "0", "252905", "GOLDPETAL28APRFUT",
                "BUY", "0.0", "NORMAL");
        // String OrderId = response.jsonPath().getString("data.orderid");

    }

    @Test(enabled = true)
    void testPlaceCurrencyOrder() throws IOException {
        String userdetails = "9741636854:upendra101087@gmail.com:qeewrwwqzycawdcs:U50049267:2222";
        // String userdetails =
        // "9742000367:sateeshbavana@gmail.com:jwppymyurxuttagh:S304062:2222";
        String secKey = "db3a62b2-45f6-4b6c-a74b-80ce27491bb7";

        generateUserToken(userdetails, secKey);
        //String pLtp = getLTPPrice("500113", "bse_cm");
        String pLtp = getLTPPrice("1151", "cde_fo");
        // String pLtp = getLTPPrice("8741","cde_fo");

        // String ltpPrice = BuyroundoffValueToCancelOrder(pLtp);
        // Response response = placeStockOrder("BSE", "LIMIT", ltpPrice,"DELIVERY",
        // "1", "0","500113", "SAIL", "BUY","0.0","AMO");
        // Response response = placeStockOrder("NSE", "LIMIT", pLtp,"DELIVERY",
        // "1", "0","1491", "IFCI-EQ", "BUY","0.0","AMO");
        String ltoPrice = buyValueCustomPriceForCurrency(pLtp);
        Response response = placeStockOrder("CDS", "LIMIT", ltoPrice, "DELIVERY", "1", "0", "1151", "USDINR23DECFUT",
                "BUY", "0.0", "AMO");
        // String OrderId = response.jsonPath().getString("data.orderid");

    }

    @Test(enabled = true)
    void testPlaceFNOOrder() throws IOException {
        String userdetails = "9741636854:upendra101087@gmail.com:qeewrwwqzycawdcs:U50049267:2222";
        // String userdetails =
        // "9742000367:sateeshbavana@gmail.com:jwppymyurxuttagh:S304062:2222";
        String secKey = "db3a62b2-45f6-4b6c-a74b-80ce27491bb7";

        generateUserToken(userdetails, secKey);
        //String pLtp = getLTPPrice("500113", "bse_cm");
        String pLtp = getLTPPrice("36187", "nse_fo");
        // String pLtp = getLTPPrice("8741","cde_fo");

        // String ltpPrice = BuyroundoffValueToCancelOrder(pLtp);
        // Response response = placeStockOrder("BSE", "LIMIT", ltpPrice,"DELIVERY",
        // "1", "0","500113", "SAIL", "BUY","0.0","AMO");
        // Response response = placeStockOrder("NSE", "LIMIT", pLtp,"DELIVERY",
        // "1", "0","1491", "IFCI-EQ", "BUY","0.0","AMO");
        String ltoPrice = fnoBuyMarketPending(pLtp);
        Response response = placeStockOrder("NFO", "LIMIT", ltoPrice, "INTRADAY", "1", "0", "36187", "NIFTY25MAY23FUT",
                "BUY", "0.0", "NORMAL");
        String orderNum = response.jsonPath().getString("data.orderid");
        // String OrderId = response.jsonPath().getString("data.orderid");
        Response callOrdersApi = getOrderBook();// OrderBookAPi
        GetOrdersDetailsResponsePOJO as = callOrdersApi.getBody().as(GetOrdersDetailsResponsePOJO.class);
        List<OrdersDetailsData> data = as.getData();

        List<String> orderIds = data.stream()
                .filter(x -> x.getOrderid().toString().equalsIgnoreCase(orderNum))
                .map(x -> x.getOrderid() + " - " + x.getOrderstatus() + " - " + x.getText()).collect(Collectors.toList());

        String orderDetails = orderIds.get(0);
        System.out.println(orderDetails);

        if (orderDetails.contains(orderNum) && !orderDetails.contains("rejected") && !orderDetails.contains("Insufficient Funds"))
            //ExtentLogger.info(orderNum + " Order Id found in open order");
            System.out.println(" Order Id found in open order");
        else if (orderDetails.contains(orderNum) && orderDetails.contains("rejected") && orderDetails.contains("Insufficient Funds")) {
            //ExtentLogger.info(orderNum + " Order is rejected due to Insufficient Funds");
            System.out.println(" Order is rejected due to Insufficient Funds");
        } else {
            //ExtentLogger.fail(orderNum + " Order Id not found in open order.Proably rejected.Please check manually");
            Assert.fail("Place Order isnt successful as order Id " + orderNum + " doesnt found in Open Order");
        }

        // Cancel Order
        if (orderIds.contains(orderNum) && !orderIds.contains("Insufficient Funds")) {
            Response cancelOrderResponse = cancelOrder(orderNum, "NORMAL");
            Assert.assertTrue(cancelOrderResponse.getStatusCode() == 200, "some error in placeBuyComodityOrder api ");
        }

    }

    @Test(enabled = true)
    void testPlaceComodityOrder() throws IOException {
        String userdetails = "9741636854:upendra101087@gmail.com:qeewrwwqzycawdcs:U50049267:2222";
        // String userdetails =
        // "9742000367:sateeshbavana@gmail.com:jwppymyurxuttagh:S304062:2222";
        String secKey = "db3a62b2-45f6-4b6c-a74b-80ce27491bb7";

        generateUserToken(userdetails, secKey);
        //String pLtp = getLTPPrice("500113", "bse_cm");
        String pLtp = getLTPPrice("252905", "mcx_fo");
        // String pLtp = getLTPPrice("8741","cde_fo");

        // String ltpPrice = BuyroundoffValueToCancelOrder(pLtp);
        // Response response = placeStockOrder("BSE", "LIMIT", ltpPrice,"DELIVERY",
        // "1", "0","500113", "SAIL", "BUY","0.0","AMO");
        // Response response = placeStockOrder("NSE", "LIMIT", pLtp,"DELIVERY",
        // "1", "0","1491", "IFCI-EQ", "BUY","0.0","AMO");
        String ltpPrice = buyValueTriggerPriceForCommodity(pLtp);
        Response response = placeStockOrder("0", "0", "DAY", "MCX", "1", "", "",
                "LIMIT", "2", ltpPrice, "INTRADAY", "1", "0", "0", "0", "252905", "100.0", "GOLDPETAL23MAYFUT", "NO", "0", "BUY", "0.0", "NORMAL");
        // String OrderId = response.jsonPath().getString("data.orderid");

    }


    @Test(enabled = true)
    void testPlaceComodityOrderCockudakl() throws IOException {
        String userdetails = "9741636854:upendra101087@gmail.com:qeewrwwqzycawdcs:U50049267:2222";
        // String userdetails =
        // "9742000367:sateeshbavana@gmail.com:jwppymyurxuttagh:S304062:2222";
        String secKey = "db3a62b2-45f6-4b6c-a74b-80ce27491bb7";

        generateUserToken(userdetails, secKey);
        //String pLtp = getLTPPrice("500113", "bse_cm");
        String pLtp = getLTPPrice("COCUDAKL19MAY2023", "ncx_fo");

        String ltpPrice = buyValueTriggerPriceForCommodity(pLtp);
        Response response = placeStockOrder("0", "0", "DAY", "NCDEX", "1", "", "",
                "LIMIT", "2", ltpPrice, "CARRYFORWARD", "1", "0", "0", "0", "COCUDAKL19MAY2023", "100.0", "COCUDAKL19MAY2023", "NO", "0", "BUY", "0.0", "NORMAL");
        String OrderId = response.jsonPath().getString("data.orderid");
        System.out.println("Cockudakl OrderID =" + OrderId);

    }


    @Test(enabled = true)
    void getOrders() throws IOException {
        String userdetails = "9741636854:upendra101087@gmail.com:qeewrwwqzycawdcs:U50049267:2222";
        String secKey = "db3a62b2-45f6-4b6c-a74b-80ce27491bb7";
        generateUserToken(userdetails, secKey);
        Response callOrdersApi = getOrderBook();// OrderBookAPi
        GetOrdersDetailsResponsePOJO as = callOrdersApi.getBody().as(GetOrdersDetailsResponsePOJO.class);
        List<OrdersDetailsData> data = as.getData();
        List<OrdersDetailsData> datafiltered = data.stream()
                .filter(x -> !(x.getStatus().contains("cancelled") || (x.getStatus().contains("rejected") && !x.getText().contains("Insufficient Funds"))))
                .collect(Collectors.toList());


        datafiltered.forEach(orderId -> {
            System.out.println("order id -" + orderId.getAmoOrderId() + "order status " + orderId.getOrderstatus() + "Order Text " + orderId.getText());
        });

        // Response response = placeStockOrder("BSE", "MARKET", "0.0","DELIVERY",
        // "1", "0","500113", "SAIL", "BUY","0.0","NORMAL");
        //String OrderId = response.jsonPath().getString("data.orderid");
    }

    @Test(enabled = true)
    public void testMarginAmountApi() throws Exception {

        Response marginResponse = callMarginAmountApi("S304062");
        Assert.assertTrue(marginResponse.getStatusCode() == 200, "Invalid Response for getPostion API");
        String data = marginResponse.jsonPath().getString("data");
        String decryptedData = decodeJsonResponse(data);
        JsonPath jPath = new JsonPath(decryptedData);
        String eligibleMargin = jPath.getString("eligibleMargin");
        String eligibleWithdraw = jPath.getString("eligibleWithdraw");
        System.out.println("eligibleMargin  = " + eligibleMargin + " eligibleWithdraw = " + eligibleWithdraw);
        Assert.assertNotNull(eligibleMargin, "avaMargin didnt found");
        Assert.assertNotNull(eligibleWithdraw, "eligibleWithdraw didnt found");
        Assert.assertTrue(Double.valueOf(eligibleMargin) > 0, "eligibleMargin is equal to or less than 0");
    }

    public String BuyroundoffValueToCancelOrder(String ltp1) {
        double lt = Double.parseDouble(ltp1);
        double per = lt * 2 / 100;
        double buyPrice = (lt - per) * 10;
        double roundOff = Math.round(buyPrice);
        double FinalBuyPrice = roundOff / 10;
        System.out.print(FinalBuyPrice);
        return String.valueOf(FinalBuyPrice);
    }

    public String getTriggerPriceValueForBuy(String ltp1) {
        double lt = Double.parseDouble(ltp1);
        double per = lt * 5 / 100;
        double buyPrice = (lt - per) * 10;
        double roundOff = Math.round(buyPrice);
        double FinalBuyPrice = roundOff / 10;
        System.out.print(FinalBuyPrice);
        return String.valueOf(FinalBuyPrice);
    }

    public String getTriggerPriceValueForSell(String ltp1) {
        double lt = Double.parseDouble(ltp1);
        double per = lt * 5 / 100;
        double buyPrice = (lt + per) * 10;
        double roundOff = Math.round(buyPrice);
        double FinalBuyPrice = roundOff / 10;
        System.out.print(FinalBuyPrice);
        return String.valueOf(FinalBuyPrice);
    }

    public String buyValueCustomPriceForCurrency(String ltp) {
        double lt = Double.parseDouble(ltp);
        double per = lt * 2 / 100;
        double buyPrice = (lt - per) * 10;
        double roundOff = Math.round(buyPrice);
        double FinalBuyPrice = roundOff / 10;
        return String.valueOf(FinalBuyPrice);
    }

    public String buyValueTriggerPriceForCommodity(String ltp) {
        double lt = Double.parseDouble(ltp);
        double per = lt * 2 / 100;
        double buyPrice = (lt - per) * 10;
        double roundOff = Math.round(buyPrice);
        double FinalBuyPrice = roundOff / 10;
        double roundOffFinal = Math.round(FinalBuyPrice);
        return String.valueOf(roundOffFinal);
    }

    public String fnoBuyMarketPending(String ltp) {
        double lt = Double.parseDouble(ltp);
        double per = lt * 5 / 100;
        double buyPrice = (lt - per) * 10;
        double roundOff = Math.round(buyPrice);
        double FinalBuyPrice = roundOff / 10;
        return String.valueOf(FinalBuyPrice);
    }

    @Test
    public void checkPrecision() throws Exception {

        // String text = "824825000";
        String text = "549300";
        Double ltp = Double.valueOf(text);
        // ltp = ltp / 10000000;
        ltp = ltp / 100;
        // DecimalFormat df = new DecimalFormat("#.##");
        // df.format(ltp);
        String ltpPrice = String.valueOf(ltp);
        System.out.println(ltpPrice);

    }

    @Test(enabled = false)
    public void watchlist() throws Exception {

        System.out.println("Learning how to display watchlist in reportdashboard");

    }

    @Test(enabled = true)
    public void Orders() throws Exception {
        //System.out.println("Learning how to display orders in reportdashboard");
        //Assert.fail("OrderPage not displayed");
        BigDecimal totalChargeCal1 = new BigDecimal(868.50);
        BigDecimal totalChargeAmount1 = new BigDecimal(121.40);
        int longTotalChargeCal = totalChargeCal1.intValue();
        int longTotalChargeAmount = totalChargeAmount1.intValue();

        System.out.println(longTotalChargeCal + "\n" + longTotalChargeAmount);
    }

//    @Test(enabled = false)
//    public void genJTWToken() throws Exception {
//        String secret = "db3a62b2-45f6-4b6c-a74b-80ce27491bb7";
//
//        SecretKeySpec hmacKey = new SecretKeySpec(secret.getBytes("UTF-8"), SignatureAlgorithm.HS256.getJcaName());
//
//        Instant now = Instant.now();
//        long futureTime = now.plus(365l, ChronoUnit.DAYS).toEpochMilli();
//        long pastTime = now.minus(1l, ChronoUnit.DAYS).toEpochMilli();
//        System.out.println("exp time = " + futureTime);
//        System.out.println("iat time = " + pastTime);
//        UserDataJWT data = new UserDataJWT();
//        Map<String, Object> userData = new HashMap<>();
//        userData.put("userData", data);
//        String jwtToken = Jwts.builder().addClaims(userData).claim("iss", "angel").claim("exp", futureTime)
//                .claim("iat", pastTime).setSubject("JWT key").setId(UUID.randomUUID().toString())
//                .setIssuedAt(Date.from(now)).setExpiration(Date.from(now.plus(2l, ChronoUnit.DAYS))).signWith(hmacKey)
//                .compact();
//
//        System.out.println("JWT token === > " + jwtToken);
//    }

    @Test(enabled = false)
    public void exception() throws Exception {

        // BooleanSupplier b = ()->true;
        // Exceptions.silence().getAsBoolean(b).orElseThrow(()->new
        // SkipException("checking test"));

        WebDriver driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.get("https://www.zickty.com/gziptotext/");

    }

    public String decompressdToString(Response response1) throws IOException {
        InputStream response = response1.asInputStream();
        String decodedString = gzipToString1(response);
        return decodedString;
    }

    public static String gzipToString1(InputStream gzip) throws IOException {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        try (InputStream fromIs = new GZIPInputStream(gzip)) {
            byte[] buffer = new byte[1024];
            int len;
            while ((len = fromIs.read(buffer)) > 0) {
                os.write(buffer, 0, len);
            }
        }
        return os.toString("UTF-8");
    }

    @Test(enabled = true)
    public void pledgeGetUserSecurityAPI() throws Exception {

        Response userSecurity = callGetUserSecurityAPI("S304062");
        Assert.assertTrue(userSecurity.getStatusCode() == 200, "Invalid Response for getUserSecurity API");
        String data = userSecurity.jsonPath().getString("data");
        InputStream is = new ByteArrayInputStream(data.getBytes(StandardCharsets.UTF_8));
        String decompressdToString = gzipToString1(is);

        System.out.println("Post decrupting ---> \n" + decompressdToString);

        //String decodedValue = decodeJsonResponse(data);
        //JsonPath jPath = new JsonPath(decodedValue);
        //String qty = jPath.getString("stock_list[0].qty");
        //String netAmount = jPath.getString("stock_list[0].net_amt");

        //Assert.assertNotNull(qty,"The value of quantity is not shown in response");
        //Assert.assertNotNull(netAmount,"The value of Netamount is not shown in response");

    }

    @Test(enabled = true)
    public void pledgeGetWithdrawSecurityAPI() throws Exception {

        Response withdrawSecurity = callGetWithdrawSecurityAPI("S304062");
        Assert.assertTrue(withdrawSecurity.getStatusCode() == 200, "Invalid Response for getUserSecurity API");

        String data = withdrawSecurity.jsonPath().getString("data");
        String decodedValue = decodeJsonResponse(data);
        JsonPath jPath = new JsonPath(decodedValue);
        String qty = jPath.getString("stock_list[0].qty");
        String netAmount = jPath.getString("stock_list[0].net_amt");

        Assert.assertNotNull(qty, "The value of quantity is not shown in response");
        Assert.assertNotNull(netAmount, "The value of Netamount is not shown in response");

    }

    @Test(enabled = true)
    public void getFundsWithdrawalData() throws Exception {

        String userdetails = "9741636854:upendra101087@gmail.com:qeewrwwqzycawdcs:U50049267:2222";
        String secKey = "db3a62b2-45f6-4b6c-a74b-80ce27491bb7";
        //	generateUserToken(userdetails, secKey);
        Response positions = callFundWithdrawalDataApi("PartyCode", "S304062");
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
    public void testFundRMSAPi() throws Exception {

        String userdetails = "9741636854:upendra101087@gmail.com:qeewrwwqzycawdcs:U50049267:2222";
        String secKey = "db3a62b2-45f6-4b6c-a74b-80ce27491bb7";
        generateUserToken(userdetails, secKey);
        Response fundRmsLimitResponse = callFundRMSLimitApi("", "", "");
        Assert.assertTrue(fundRmsLimitResponse.getStatusCode() == 200, "Invalid Response for getPostion API");
        SoftAssertions softAssertions = new SoftAssertions();
        String deposit = fundRmsLimitResponse.jsonPath().getString("data.deposit");
        String cashDeposit = fundRmsLimitResponse.jsonPath().getString("data.cashDeposit");
        String netAvailableFunds = fundRmsLimitResponse.jsonPath().getString("data.netAvailableFunds");
        String fundsForTrading = fundRmsLimitResponse.jsonPath().getString("data.fundsForTrading");
        String fundsAvailable = fundRmsLimitResponse.jsonPath().getString("data.fundsAvailable");
        softAssertions.assertThat(deposit)
                .as("Deport is null")
                .isNotNull()
                .as("Deport is zero")
                .isNotEqualTo("0");

        softAssertions.assertThat(cashDeposit)
                .as("cashDeposit is null")
                .isNotNull()
                .as("cashDeposit is zero")
                .isNotEqualTo("0");

        softAssertions.assertThat(netAvailableFunds)
                .as("netAvailableFunds is null")
                .isNotNull()
                .as("netAvailableFunds is zero")
                .isNotEqualTo("0");

        softAssertions.assertThat(fundsForTrading)
                .as("fundsForTrading is null")
                .isNotNull()
                .as("fundsForTrading is zero")
                .isNotEqualTo("0");

        softAssertions.assertThat(fundsAvailable)
                .as("fundsAvailable is null")
                .isNotNull()
                .as("fundsAvailable is zero")
                .isNotEqualTo("0");

        softAssertions.assertAll();

    }

    @Test(enabled = true)
    public void decryptData() throws Exception {
        String text = "eJzcmNtv2zYUxv8V4bxOJXjV7WmyLbdqUzmzFQzBMAhMzLnEdKulZDCK/u8DY3uJFBnLkMCzm7ccSORH8YfvO8ffoGmr2z+zXDctBL99g8/Vjc5VltwVEIDvcooxZo4LNkSF1DkE0MhWqebLjbyXpfx5ZarotirAhmWtlxAAoZgxijGlQrgCY7ChuV3rupSFggCS9HJsXaQTBDboRpcQQJxELmMRJpiYp/9YK5V9bTdmLYTNH9hQ52q52pfxP+WlbFWrC9W0sqghAIope4f5O8wsLAJBAuoh38ivbnK9kq2uzIbmzbtyu+Tu37qSRhzYUKo2q/Pl6sleYMOjHrDhNq8ala1la94mrkBkq6atWpl3S2WjMlnX6+pemY8zyu/U7Rddgw33cp0Vcv0gGu2OY/aWRWuW4Bhh76mcbX0nJ17ESZZuaiMg+nqn2w3YkIym42yUmwv9ZasdbBjnWpXt/tlZUapm0zzWd7ey2N6qNXq4VrDhs16Z8xnN19vjZ7kudEuMtqcFuttnrZa6ictGrc1HnshWpfph6adX4gaYBdhH1Pex7/2ERcDMu0mjmk1xU+U7PuC7fQwU4/nIipPpPFyk86txejWPrEmHSo+SGBNMaZdKdmJUsgEqqUBEdKB0BeJiiMn3VbXs8kgRdXo8Cg854jRxZK/DkXqIM9fjdBDHeD46Fo3lUsvSmkud/yU31lSXsuzQiAWbnqlHUgc5XYt8rPRoDO/VWq7UvxokJYj+wP7o+b5zAMjpsfxxkUbRhRVepR9m8zi9tmZTK+4QSQgPDZGkSyQ9MSLpAJEeRW4vtB2B+CtDm1HE6GlCSd8itIkvBqFchPHFsWxyOo6f94+Y+eGQN/JhFMkRUSQdc+QDLPrI66HIXERf7o4UYdEnETvI589JdJHD/m8SieijSP57YHPqucP+OB3Hx/LH2VX6wYqTSRwm1ihMPlkXaYdKx2OGSsrOL7EJR063f3ysvCyxCe8zSZDjn6Y5vslEQ30ybI4GE4PHkbC8vEo+hiMrCdN4loQXD2R2Y9vB4cBYcw6xzZ81kj47YJUvTm2XI+GcJpevDO3taOPy4Un7MjnWaDOJEiuJ0l9n808Lk90dGn3ufjTRLc5wyHb6ye3hAz3kS8cahyNGThPGN5mzfTY81kyi5EgwXkeLfVj3mkhBvfeYYCbOzxeJ2PeAexIZPvAT5AESXdYfsBnyfmBb5MwbJvE6WmzD+vfvfwcAAP//5auv4Q==";
        compressData(text);
    }

    @Test(enabled = true)
    public void encryptData() throws Exception {
        String text = "[{\"watchlistId\":16685830080,\"watchlistName\":\"broadcast\",\"watchlistOrder\":1,\"Isdefault\":true,\"scripDetails\":[{\"scripOrder\":1,\"marketCap\":\"0.00\",\"tradeSymbol\":\"GOLDPETAL23JULFUT\",\"isCashWithFno\":false,\"nseCashToken\":\"\",\"bseCashToken\":\"\",\"curFutToken\":\"\",\"astCls\":\"derivative\",\"details\":\"31 Jul 2023\",\"divider\":\"100\",\"exchName\":\"MCX Futures\",\"expirydate\":\"31 Jul 2023\",\"highPrice\":\"0\",\"instName\":\"FUTCOM\",\"isinCode\":\"\",\"lotMult\":\"1\",\"lotSize\":\"GRMS\",\"lowPrice\":\"0\",\"minLot\":\"1\",\"mktSegID\":\"5\",\"optType\":\"XX\",\"precsn\":\"2\",\"priceTck\":\"1.00\",\"regLot\":\"1\",\"secDesc\":\"GOLDPETAL\",\"series\":\"\",\"stockID\":\"\",\"strkPrice\":\"0\",\"symbolName\":\"GOLDPETAL\",\"token\":\"255321\",\"npriceNum\":\"1\",\"npriceDen\":\"1\",\"holdQty\":0,\"baseQty\":0,\"baseAvgPrice\":0,\"avgPrice\":0,\"dbq\":0,\"dsq\":0,\"dbap\":0,\"dsap\":0,\"dpholding\":0,\"invested\":0,\"isCommodityWithOption\":false,\"isIndices\":false,\"maxSingleTransactionQty\":\"10000\"}]},{\"watchlistId\":16685830081,\"watchlistName\":\"MyList\",\"watchlistOrder\":2,\"Isdefault\":false,\"scripDetails\":[]}]";
        compressData(text);
    }


    public String decodeData(String data) {

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        WebDriver driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.get("https://www.zickty.com/gziptotext/");
        driver.findElement(By.id("input")).sendKeys(data);
        driver.findElement(By.id("button1")).click();
        // String decodedJson = driver.findElement(By.id("output")).getText();
        // System.out.println(" ##### Decoded Text ### \n"+decodedJson);
        String decodedData = (String) ((JavascriptExecutor) driver).executeScript("return arguments[0].value;",
                driver.findElement(By.id("output")));
        System.out.println("Decoded data " + decodedData);
        driver.quit();
        return decodedData;
    }


    public static String compressData(String inputData) {
        byte[] inputBytes = inputData.getBytes();
        Deflater deflater = new Deflater();
        deflater.setInput(inputBytes);
        deflater.finish();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        // Compress the data
        byte[] buffer = new byte[40000];
        while (!deflater.finished()) {
            int compressedSize = deflater.deflate(buffer);
            outputStream.write(buffer, 0, compressedSize);
        }
        // Close the streams
        try {
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Get the compressed data as a byte array
        byte[] compressedBytes = outputStream.toByteArray();

        // Encode the compressed data using Base64
        String compressedData = Base64.getEncoder().encodeToString(compressedBytes);
        // Print the compressed data
        System.out.println(compressedData);
        return compressedData;
    }

}
