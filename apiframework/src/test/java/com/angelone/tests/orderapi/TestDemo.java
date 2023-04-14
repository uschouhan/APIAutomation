package com.angelone.tests.orderapi;

import java.io.InputStream;
import java.util.Calendar;
import java.util.TimeZone;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.testng.annotations.Test;

import com.angelone.api.utility.Helper;

public class TestDemo {

    @Test
    public void testName1() throws Exception {
        InputStream resourceAsStream = null;
        try {
            String dataFileName = "requests/setWatchlistData.json";
            resourceAsStream = getClass().getClassLoader().getResourceAsStream(dataFileName);
            JSONTokener tokener = new JSONTokener(resourceAsStream);
            JSONArray testData = new JSONArray(tokener);
            String text = String.format(testData.toString(), "14366");
            System.out.println(text);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        } finally {
            if (resourceAsStream != null) {
                resourceAsStream.close();
            }
        }
    }

}
