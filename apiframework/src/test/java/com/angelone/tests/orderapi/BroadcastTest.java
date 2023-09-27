package com.angelone.tests.orderapi;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

import lombok.SneakyThrows;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.SoftAssertions;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.angelone.api.BaseClass;
import com.angelone.api.pojo.CreateBasketPOJO;
import com.angelone.api.utility.Helper;

import io.restassured.response.Response;

public class BroadcastTest extends BaseClass {
	//Define DataProviders for broadcast test
	@DataProvider(name = "equity")
	public Object[][] getScripForEquity() {
		return new Object[][] { { "11536" }, { "1330" }, { "10604" } };
	}

	@DataProvider(name = "comodity")
	public Object[][] getScripForComodity() {
		return new Object[][] { { "Copper" }, { "Goldpetal" }, { "Gold" } ,{"Crudeoil"}};
	}

	@DataProvider(name = "fno")
	public Object[][] getScripForFNo() {
		return new Object[][] { { "NIFTY" }, { "BANKNIFTY" } };
	}

	
	@Test(enabled = true, dataProvider = "equity")
	public void broadcastTestForEquity(String scrip) throws IOException, InterruptedException {
		SoftAssertions softly = new SoftAssertions();
		System.out.println("Token id and Scrip Name ");
		System.out.println("11536  = TCS-NSE");
		System.out.println("1330  = HDFC-NSE");
		System.out.println("10604  = BHARTIARTL-NSE");
		String ltpPrice = null;
		HashSet<String> set = new HashSet<String>();
		for (int j = 0; j < 5; j++) {
			// get LTP price
			ltpPrice = baseAPI.getLTPPrice(scrip, "nse_cm");
			System.out.println("LTP price at " + Helper.dateTime() + " = " + ltpPrice);
			set.add(ltpPrice);
			Thread.sleep(1000);
		}
		System.out.println("Ltp price for " + scrip + " for last 5 sec === " + set);
		softly.assertThat(set).as("LTP value didnt change for 5 sec").doesNotContainNull().hasSizeGreaterThan(1);

		softly.assertAll();
	}

	@Test(enabled = true)
	public void broadcastTestForCurrency() throws IOException, InterruptedException {
		String currencySymbolToken = baseAPI.getSciptTokenFromSearchApi("USDINR", "CURNCYSEG");
		String ltpPrice = null;
		HashSet<String> set = new HashSet<String>();

		for (int i = 0; i < 5; i++) {
			// get LTP price
			ltpPrice = baseAPI.getLTPPrice(currencySymbolToken, "cde_fo");
			System.out.println("LTP price at " + Helper.dateTime() + " = " + ltpPrice);
			set.add(ltpPrice);
			Thread.sleep(1000);
		}
		System.out.println("Ltp price for last 5 sec === " + set);
		Assertions.assertThat(set).as("LTP value didnt change for 5 sec").doesNotContainNull().hasSizeGreaterThan(1);
	}

	@Test(enabled = true, dataProvider = "comodity")
	public void broadcastTestForComodity(String scrip) throws IOException, InterruptedException {
		SoftAssertions softly = new SoftAssertions();
		String comoditySymbolToken = baseAPI.getSciptTokenFromSearchApi(scrip, "COMDTYSEG");
		String ltpPrice = null;
		HashSet<String> set = new HashSet<String>();
		for (int j = 0; j < 10; j++) {
			// get LTP price
			ltpPrice = baseAPI.getLTPPrice(comoditySymbolToken, "mcx_fo");
			System.out.println("LTP price at " + Helper.dateTime() + " = " + ltpPrice + " for script " + scrip);
			set.add(ltpPrice);
			Thread.sleep(1000);
		}
		System.out.println("Ltp price for Symbol " + scrip);
		System.out.println("Ltp price for last 10 sec === " + set);
		softly.assertThat(set).as("LTP value didnt change for 5 sec").doesNotContainNull().hasSizeGreaterThan(1);
		softly.assertAll();
	}

	@Test(enabled = true,dataProvider = "fno")
	public void broadcastTestForFNO(String scrip) throws IOException, InterruptedException {

		String fnoSymbolToken = baseAPI.getSciptTokenFromSearchApi(scrip, "ALLFUTURES");
		String ltpPrice = null;
		HashSet<String> set = new HashSet<String>();
		for (int j = 0; j < 5; j++) {
			// get LTP price
			ltpPrice = baseAPI.getLTPPrice(fnoSymbolToken, "nse_fo");
			System.out.println("LTP price at " + Helper.dateTime() + " = " + ltpPrice);
			set.add(ltpPrice);
			Thread.sleep(1000);
		}
		System.out.println("Ltp price for Symbol " + scrip);
		System.out.println("Ltp price for last 5 sec === " + set);
		Assertions.assertThat(set).as("LTP value didnt change for 5 sec").doesNotContainNull().hasSizeGreaterThan(1);

	}

	@Test(enabled = true)
	public void broadcastTestForFNOOptions() throws IOException, InterruptedException {
		//String fnoSymbolToken = baseAPI.getSciptIdforEquity("PNB","BSE");
		String fnoSymbolToken = baseAPI.getLowerPriceScripId("SENSEX CE", "ALLOPTIONS","bse_fo",20.0);
		//String token = baseAPI.getSciptTokenFromSearchApi("USDINR", "CURNCYSEG");
		//Response callgetSecurityInfo = baseAPI.callgetSecurityInfo("mcx_fo", token);
		//String tradingSymbol = callgetSecurityInfo.jsonPath().getString("data.trdSymbol");
		//System.out.println("Trading symbol "+ tradingSymbol);
	}

	@SneakyThrows
	@Test
	public void lowPrice() {
		String currencySymbolToken = baseAPI.getLowerPriceScripId("USDINR 88.75 CE", "CURNCYSEG","cde_fo",5.00);
		Response callgetSecurityInfo = baseAPI.callgetSecurityInfo("cde_fo", currencySymbolToken);
		String tradingSymbol = callgetSecurityInfo.jsonPath().getString("data.trdSymbol");
		System.out.println("Trading symbol "+ tradingSymbol);
	}

	@Test(enabled = true)
	public void basketOrder() throws IOException, InterruptedException {
		//String fnoSymbolToken = baseAPI.getSciptIdforEquity("PNB","BSE");
		//String fnoSymbolToken = baseAPI.getLowerPriceScripId("RELIANCE CE", "DERIVATIVESSEG","nse_fo",2.4);
		//String token = baseAPI.getSciptTokenFromSearchApi("USDINR", "CURNCYSEG");
		//Response callgetSecurityInfo = baseAPI.callgetSecurityInfo("mcx_fo", token);
		//String tradingSymbol = callgetSecurityInfo.jsonPath().getString("data.trdSymbol");
		//System.out.println("Trading symbol "+ tradingSymbol);
		Helper helper = new Helper();
		List<CreateBasketPOJO> objdata = new ArrayList<>();
		 String dataFileName = "data/basketOrderDataMCX.json";
		 InputStream datais = getClass().getClassLoader().getResourceAsStream(dataFileName);
		JSONTokener tokener = new JSONTokener(datais);
		JSONObject object = new JSONObject(tokener);
		JSONArray basketData = object.getJSONArray("orders");
		//basketData.forEach(data->System.out.println(data.toString()));
		for (int i = 0; i < basketData.length(); i++) {
            String symbolName = basketData.getJSONObject(i).getString("symbolName");
            String scripExchg = basketData.getJSONObject(i).getString("scripExchg");
            String exchange = basketData.getJSONObject(i).getString("exchange");
            String exchgId = basketData.getJSONObject(i).getString("exchgId");
            String transType = basketData.getJSONObject(i).getString("transactiontype");
            String producttype = basketData.getJSONObject(i).getString("producttype");
            String ordertype = basketData.getJSONObject(i).getString("ordertype");
            String segment = basketData.getJSONObject(i).getString("segment");
            Integer qty = basketData.getJSONObject(i).getInt("qty");
            if(segment.equalsIgnoreCase("CASHSEG"))
            {
                String token = baseAPI.getSciptIdforEquity(symbolName,exchange);
                Response callgetSecurityInfo = baseAPI.callgetSecurityInfo(scripExchg, token);
                String tradingSymbol = callgetSecurityInfo.jsonPath().getString("data.trdSymbol");
                String scripIsin = callgetSecurityInfo.jsonPath().getString("data.isin");
                String details = callgetSecurityInfo.jsonPath().getString("data.desc");
                String tradeSymbol = callgetSecurityInfo.jsonPath().getString("data.trdSymbol");
                String ltpPrice = baseAPI.getLTPPrice(token, scripExchg);
                
               if(transType.equals("B")&&(ordertype.equalsIgnoreCase("LIMIT"))){
            	  
            	   CreateBasketPOJO createBasketData = baseAPI.createBasketData(token, scripExchg, exchange, exchgId,scripIsin, symbolName, details,
  						 "01 Jan 1980", tradeSymbol,transType, producttype, exchange, ordertype, ltpPrice, qty);
                   objdata.add(createBasketData);  
      
            }
               else if(transType.equals("S")&&(ordertype.equalsIgnoreCase("LIMIT"))){
            	
             	 CreateBasketPOJO createBasketData = baseAPI.createBasketData(token, scripExchg, exchange, exchgId,scripIsin, symbolName, details,
						 "01 Jan 1980", tradeSymbol,transType, producttype, exchange, ordertype, ltpPrice, qty);
                 objdata.add(createBasketData);  
            	   
               }
               
               else 
               {
            	   CreateBasketPOJO createBasketData = baseAPI.createBasketData(token, scripExchg, exchange, exchgId,scripIsin, symbolName, details,
  						 "01 Jan 1980", tradeSymbol,transType, producttype, exchange, ordertype, ltpPrice, qty);
                   objdata.add(createBasketData);  
               }
            }
            else
            {
                List<String> sciptTokenAndExpiryFromSearchApi = baseAPI.getSciptTokenAndExpiryFromSearchApi(symbolName, segment);
                String token = sciptTokenAndExpiryFromSearchApi.get(0);
                String expiryDate = sciptTokenAndExpiryFromSearchApi.get(1);
                Response callgetSecurityInfo = baseAPI.callgetSecurityInfo(scripExchg, token);
                String tradingSymbol = callgetSecurityInfo.jsonPath().getString("data.trdSymbol");
                String scripIsin = callgetSecurityInfo.jsonPath().getString("data.isin");
                String details = callgetSecurityInfo.jsonPath().getString("data.desc");
                String tradeSymbol = callgetSecurityInfo.jsonPath().getString("data.trdSymbol");
                String ltpPrice = baseAPI.getLTPPrice(token, scripExchg);
                //String variety = helper.orderTypeCheckForEquity();
                CreateBasketPOJO createBasketData = baseAPI.createBasketData(token, scripExchg, exchange, exchgId,scripIsin, symbolName, details,
                		expiryDate, tradeSymbol,transType, producttype, exchange, ordertype, ltpPrice, qty);
                  objdata.add(createBasketData);  
               
            }
        }
        Response callCreateBasketApi = baseAPI.callCreateBasketApi("TestBasket",objdata);
        String basketId=callCreateBasketApi.jsonPath().getString("data.basketId");
        //return basketId;
		//Response callDeleteBasketApi = baseAPI.callDeleteBasketApi(basketId);
	}
	
	@Test
	public void deleteAllBaskets() throws Exception {
        Response callgetBasketListApi = baseAPI.callgetBasketListApi();
        JSONObject object = new JSONObject(callgetBasketListApi.asString());
        Object listBasket = object.get("data");
        if (Objects.nonNull(listBasket) && listBasket instanceof org.json.JSONArray) {
            JSONArray jsonArray = object.getJSONArray("data");
            List<String> basketIds = new ArrayList<>();
            for (int i = 0; i < jsonArray.length(); i++) {
                String basketId = jsonArray.getJSONObject(i).getString("basketId");
                basketIds.add(basketId);
            }
            basketIds.forEach(bId -> baseAPI.callDeleteBasketApi(bId));
        }
        else
            System.out.println("No exising Basket Found ...");
    }

	@Test
	public void createStockSIPTest() {
		String sipStartDate = Helper.getNextMonthDateForStockSIP();
		Response createRes = baseAPI.callCreateStockSipApi("EQUITY","11915","YES BANK LIMITED",
				"YESBANK-EQ","YESBANK","1","MONTHLY","QTY","NSE",sipStartDate);
		String sipId = createRes.jsonPath().getString("data.sip_id");
		//String sipId="3c0a480a-4721-11ee-b112-0a58a9feac02";
		//Response response = baseAPI.callgetStockSipListApi();
		//baseAPI.callDeleteStockSipApi(sipId);
	}
	@Test
	public void deleteAllSipOrder() {
		baseAPI.cancelAllStockSip();
	}

	public void readTestData() throws Exception {
        InputStream datais = null;
        JSONObject testData;
        try {
            String dataFileName = "data/IOS_40_3_Release.json";
            datais = getClass().getClassLoader().getResourceAsStream(dataFileName);
            JSONTokener tokener = new JSONTokener(datais);
            testData = new JSONObject(tokener);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (datais != null) {
                datais.close();
            }
        }
    }
}
