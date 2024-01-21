package com.angelone.tests.orderapi;

import java.util.Calendar;
import java.util.TimeZone;

import com.angelone.api.BaseTestApi;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import com.angelone.api.utility.Helper;

public class TestDemo {

	@Test
	public void testDemo() throws Exception {
		System.out.println("Demo  Test");
		String orderTypeCheckForComodity = orderTypeCheckForComodity();
		System.out.println(" Order Type = "+orderTypeCheckForComodity);
	}

	@Test
	public void testInvalidToken() {
		String user = "9741636854:upendra101087@gmail.com:lfixpzyvcbpoixrm:U50049267:2222:552724";
		String secret="db3a62b2-45f6-4b6c-a74b-80ce27491bb7";
		BaseTestApi baseTest = new BaseTestApi();
		baseTest.generateUserToken(user,secret);
		//baseTest.setTradeToken("dsdsjdsdhksdksdksdhksdssdsdsds");
		//Response response = baseTest.placeStockOrder("MARKET", "0.0", "DELIVERY", "10666", "PNB-EQ", "AMO");
	}

	@Test
	public void genNonTradeToken() {
		String user = "9702610281:nikhil.kolwalkar@angelbroking.com:lgacdmzwhstmcbnl:M51705285:0000";
		String secret="db3a62b2-45f6-4b6c-a74b-80ce27491bb7";
		BaseTestApi baseTest = new BaseTestApi();
		String ntt = Helper.generateNonTradeToken("9702610281","M51705285","db3a62b2-45f6-4b6c-a74b-80ce27491bb7");
		//System.out.println("NonTradeToken "+ ntt);
		//baseTest.getNonTradingAccessToken(user);
	}

	public String orderTypeCheckForComodity() {
		Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("Asia/Kolkata"));
		//int hour = calendar.get(Calendar.HOUR_OF_DAY);
		//int minute = calendar.get(Calendar.MINUTE);
		int hour = 0;
		int minute = 55;
		if (hour >= 9 && hour <= 22) {
			if (hour == 9 && minute < 1)
				return "AMO";
			else
				return "NORMAL";
		} else if (hour == 23) {
			if (minute < 59)
				return "NORMAL";
			else
				return "AMO";

		} else
			return "AMO";
	}
}
