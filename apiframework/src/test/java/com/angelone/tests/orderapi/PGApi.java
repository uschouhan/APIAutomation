package com.angelone.tests.orderapi;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.angelone.api.BaseClass;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class PGApi extends BaseClass{
	
	private static final List<String> BUILDUPS = new ArrayList<>(
            Arrays.asList("Long Built Up", "Short Built Up", "Short Covering", "Long Unwinding"));
    private static final List<String> DERIVATIVE_MARKET = new ArrayList<>(
            Arrays.asList("PercPriceGainers", "PercPriceLosers", "PercOIGainers", "PercOILosers"));
    private static final List<String> MARKET_MOVERS_DATA = new ArrayList<>(Arrays.asList("TOPGAINER", "TOPLOSER"));
    private static final List<String> MARKET_MOVERS_BY_MOST_DATA = new ArrayList<>(
            Arrays.asList("MOST_ACT_VOLUMN", "MOST_ACT_VALUE"));
    
    
    
    @Test(enabled = true)
    public void getPGTransactionListAPI() throws Exception {

        Response pgTransactionList = baseAPI.callgetPGTransactionList();
        Assert.assertTrue(pgTransactionList.getStatusCode() == 200, "Invalid Response for pgTransactionList API");
        String data = pgTransactionList.jsonPath().getString("data");
       //String decodedValue = baseAPI.decodeData(data);
      // JsonPath jPath = new JsonPath(data);
   
        Assert.assertTrue( data.contains("bank_name"));
        Assert.assertTrue( data.contains("amount"));
    
    }  
    
    @Test(enabled = true)
    public void getPGTransactionAPI() throws Exception {

        Response pgTransaction = baseAPI.callgetPGTransaction("5F6254770007392");
        Assert.assertTrue(pgTransaction.getStatusCode() == 200, "Invalid Response for pgTransactionList API");
        String data = pgTransaction.jsonPath().getString("data");
    
        Assert.assertTrue( data.contains("bank_name"));
        Assert.assertTrue( data.contains("amount"));
    
    }  

    @Test(enabled = true)
    public void getPGMergedTransactionAPI() throws Exception {

        Response pgMergedTransaction = baseAPI.callgetPGMergedTransaction("5F6254770007392");
        Assert.assertTrue(pgMergedTransaction.getStatusCode() == 200, "Invalid Response for pgTransactionList API");
        String data = pgMergedTransaction.jsonPath().getString("data");
        Assertions.assertThat(data).isNotNull();
       Assert.assertTrue( data.contains("bank_name"));
       Assert.assertTrue( data.contains("amount"));
    
    }  
    
    @Test(enabled = true)
    public void getPGTransactionQuickAddFundsAPI() throws Exception {

        Response pgTransactionQuickAddFunds = baseAPI.callgetPGQuickAddFunds();
        Assert.assertTrue(pgTransactionQuickAddFunds.getStatusCode() == 200, "Invalid Response for pgTransactionList API");
        String data = pgTransactionQuickAddFunds.jsonPath().getString("data");
       
        Assertions.assertThat(data).isNotNull();
    
    }  
    
    @Test(enabled = true)
    public void getPGTransactionLimitAPI() throws Exception {

        Response pgTransactionLimit = baseAPI.callgetPGTransactionLimit("securities");
        Assert.assertTrue(pgTransactionLimit.getStatusCode() == 200, "Invalid Response for pgTransactionList API");
        String data = pgTransactionLimit.jsonPath().getString("data");
        Assertions.assertThat(data).isNotNull();
   
    
    
    }  
    
    @Test(enabled = true)
    public void getPGTransactionTopicAPI() throws Exception {

        Response pgTransactionHelpTopic = baseAPI.callgetPGTransactionHelpTopic("transaction failure");
        Assert.assertTrue(pgTransactionHelpTopic.getStatusCode() == 200, "Invalid Response for pgTransactionList API");
        String data = pgTransactionHelpTopic.jsonPath().getString("data");
       //String decodedValue = baseAPI.decodeData(data);
      // JsonPath jPath = new JsonPath(data);
   
       // Assert.assertTrue( data.contains("bank_name"));
        //Assert.assertTrue( data.contains("amount"));
    
    }  
    @Test(enabled = true)
    public void getPGAnyActuatorAPI() throws Exception {

        Response pgActuatorAny = baseAPI.callgetPGActuatorAny();
        Assert.assertTrue(pgActuatorAny.getStatusCode() == 200, "Invalid Response for pgTransactionList API");
        String data = pgActuatorAny.jsonPath().getString("data");
       //String decodedValue = baseAPI.decodeData(data);
      // JsonPath jPath = new JsonPath(data);
   
       // Assert.assertTrue( data.contains("bank_name"));
        //Assert.assertTrue( data.contains("amount"));
    
    }  
    
    @Test(enabled = true)
    public void getPGInfoActuatorAPI() throws Exception {

        Response pgActuatorInfo = baseAPI.callgetPGActuatorInfo();
        Assert.assertTrue(pgActuatorInfo.getStatusCode() == 200, "Invalid Response for pgTransactionList API");
        String data = pgActuatorInfo.jsonPath().getString("data");
       //String decodedValue = baseAPI.decodeData(data);
      // JsonPath jPath = new JsonPath(data);
   
       // Assert.assertTrue( data.contains("bank_name"));
        //Assert.assertTrue( data.contains("amount"));
    
    }  
}
