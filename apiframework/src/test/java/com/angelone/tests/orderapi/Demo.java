package com.angelone.tests.orderapi;

import java.io.InputStream;
import java.lang.reflect.Method;
import java.sql.SQLOutput;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import com.angelone.api.BaseTestApi;
import com.angelone.api.utility.Helper;
import io.restassured.response.Response;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.angelone.api.BaseClass;
import com.angelone.api.pojo.ClientDetails;
import com.angelone.reports.ExtentLogger;
import com.angelone.reports.ExtentReport;
import com.angelone.reports.TracingPrintStream;


public class Demo {

    ClientDetails cDetails;

    @Test
    public void testName(Method m) throws Exception {

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
        Date date = new Date();
        System.out.println("Checking Custom Print in Extent Report");
        System.out.println(dateFormat.format(date));
        ExtentLogger.info("testing extent logging in 1 ");
        Date dateNew = new Date(System.currentTimeMillis() - 3600 * 1000);
        System.out.println(dateFormat.format(dateNew));
    }

    @Test
    public void testName3(Method m) throws Exception {

        ExtentLogger.info("testing extent logging in 3 ");


    }

    @Test(enabled = true)
    public void testSetWatchlist() throws Exception {
        BaseTestApi baseApi = new BaseTestApi();
        String jsonFilePath = "requests/setWatchlistData.json";
        String scriptId = "10666";
        Response watchlists = baseApi.getWatchLists();
        Helper helper = new Helper();
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
        String encodedWatchListData = baseApi.encodeJsonData(setWatchListData);
        Response response = baseApi.callSetWatchListApi(Integer.valueOf(versionId), encodedWatchListData);
        Assert.assertEquals(response.getStatusCode(), 200, "Error in watchlists api ");
    }


    @Test
    public void testNam4(Method m) throws Exception {

        ExtentLogger.info("testing extent logging in 4 ");


    }

}
