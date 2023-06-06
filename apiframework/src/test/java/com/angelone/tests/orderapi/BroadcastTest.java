package com.angelone.tests.orderapi;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.SoftAssertions;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.angelone.api.BaseClass;
import com.angelone.api.utility.Helper;

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
		String fnoSymbolToken = baseAPI.getSciptIdforEquity("PNB","BSE");
	}

}
