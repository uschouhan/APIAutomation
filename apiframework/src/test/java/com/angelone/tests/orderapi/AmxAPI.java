package com.angelone.tests.orderapi;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.angelone.api.BaseClass;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class AmxAPI extends BaseClass{
	
	
    
    
    @Test(enabled = true)
    public void checkOppositePendingOrderAPI() throws Exception {

        Response oppositePendingOrder = baseAPI.callcheckOppositePendingOrderAPI("BSE","1594","DELIVERY","BUY","1");
        Assert.assertTrue(oppositePendingOrder.getStatusCode() == 200, "Invalid Response for checkOppositePendingOrderAPI API");
        String data = oppositePendingOrder.jsonPath().getString("data");
       //String decodedValue = baseAPI.decodeData(data);
     
        Assert.assertTrue( data.contains("SUCCESS"));
        Assert.assertTrue( data.contains("true"));
    
    }  
    
    
    @Test(enabled = true)
    public void testGetAllSymbolAPI() throws Exception {

        Response getAllSymbol = baseAPI.callgetAllSymbol("bse_cm","1","10");
        Assert.assertTrue(getAllSymbol.getStatusCode() == 200, "Invalid Response for testGetAllSymbolAPI API");
        String data = getAllSymbol.jsonPath().getString("data");
       //String  = baseAPI.decodeData(data);
        String count = getAllSymbol.jsonPath().getString("data.count");
        String limit = getAllSymbol.jsonPath().getString("data.limit");
        Assert.assertNotNull(count, "The value of count is not shown in response");
        Assert.assertNotNull(limit, "The value of limit is not shown in response");
        
    
    }  
    
    @Test(enabled = true)
    public void testGetSecurityInfoAPI() throws Exception {

        Response getSecurityinfo = baseAPI.callgetSecurityInfo("bse_cm","590103");
        Assert.assertTrue(getSecurityinfo.getStatusCode() == 200, "Invalid Response for oppositePendingOrder API");
        String data = getSecurityinfo.jsonPath().getString("data");
       //String  = baseAPI.decodeData(data);
        String symbolName = getSecurityinfo.jsonPath().getString("data.symbolName");
        String symbol = getSecurityinfo.jsonPath().getString("data.symbol");
        Assert.assertNotNull(symbolName, "The value of symbolName is not shown in response");
        Assert.assertNotNull(symbol, "The value of symbol is not shown in response");
        
    
    }  

    
}
