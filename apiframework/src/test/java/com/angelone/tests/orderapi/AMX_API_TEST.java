package com.angelone.tests.orderapi;

import com.angelone.api.BaseClass;
import io.restassured.response.Response;
import org.assertj.core.api.Assertions;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AMX_API_TEST extends BaseClass {

    @Test(enabled = true)
    public void testAMXgetSecurityData() throws Exception {

        // String userdetails = "9741636854:upendra101087@gmail.com:qeewrwwqzycawdcs:U50049267:2222";
        //  String secKey = "db3a62b2-45f6-4b6c-a74b-80ce27491bb7";
        //  generateUserToken(userdetails, secKey);
        // getNonTradingAccessTokenWithoutOtp(userdetails);
        Response positions = baseAPI.callSecurityInfoApi("bse_cm", "590103");
        Assert.assertTrue(positions.getStatusCode() == 200, "Invalid Response for getPostion API");

        Assert.assertEquals(positions.jsonPath().getString("status"), "true",
                "Status doesnt match in profileDataResponse api ");
        String Exchange = positions.jsonPath().getString("data.exchange");
        String Symbol = positions.jsonPath().getString("data.symbol");
        String Group = positions.jsonPath().getString("data.group");

        Assertions.assertThat(Exchange).as("Exchange is null").isNotBlank().isNotEmpty().isNotNull();
        Assertions.assertThat(Symbol).as("Symbol is null").isNotBlank().isNotEmpty().isNotNull();
        Assertions.assertThat(Group).as("Group is null").isNotBlank().isNotEmpty().isNotNull();

    }

    @Test(enabled = true)
    public void testAMXScriptDetailData() throws Exception {

        //  String userdetails = "9741636854:upendra101087@gmail.com:qeewrwwqzycawdcs:U50049267:2222";
        // String secKey = "db3a62b2-45f6-4b6c-a74b-80ce27491bb7";
        //  generateUserToken(userdetails, secKey);
        // getNonTradingAccessTokenWithoutOtp(userdetails);
        //   Response positions = baseAPI.callSecurityInfoApi("bse_cm", "590103");
        Response positions = baseAPI.callscriptdetailApi("nse_cm","2885","0");
        Assert.assertTrue(positions.getStatusCode() == 200, "Invalid Response for getPostion API");

        Assert.assertEquals(positions.jsonPath().getString("status"), "true",
                "Status doesnt match in profileDataResponse api ");
        String Exchange = positions.jsonPath().getString("data.exchange");
        String Symbol = positions.jsonPath().getString("data.symbol");
        String TradePrice = positions.jsonPath().getString("data.tradePrice");
        String OpenPrice = positions.jsonPath().getString("data.AMXOHLC.openPrice");
        String HighPrice = positions.jsonPath().getString("data.AMXOHLC.highPrice");
        String LowPrice = positions.jsonPath().getString("data.AMXOHLC.lowPrice");
        String ClosingPrice = positions.jsonPath().getString("data.AMXOHLC.closingPrice");


        Assertions.assertThat(Exchange).as("Exchange is null").isNotBlank().isNotEmpty().isNotNull();
        Assertions.assertThat(Symbol).as("Symbol is null").isNotBlank().isNotEmpty().isNotNull();
        Assertions.assertThat(TradePrice).as("Tradeprice is null").isNotBlank().isNotEmpty().isNotNull();
        Assertions.assertThat(OpenPrice).as("OpenPrice is null").isNotBlank().isNotEmpty().isNotNull();
        Assertions.assertThat(HighPrice).as("HighPrice is null").isNotBlank().isNotEmpty().isNotNull();
        Assertions.assertThat(LowPrice).as("LowPrice is null").isNotBlank().isNotEmpty().isNotNull();
        Assertions.assertThat(ClosingPrice).as("ClosingPrice is null").isNotBlank().isNotEmpty().isNotNull();



    }

}






