package com.angelone.tests.orderapi;

import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.TreeSet;

import com.angelone.api.utility.Helper;

import org.assertj.core.api.Assertions;
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

	ClientDetails cDetails ;

	@Parameters({ "UserCredentials" })
	@BeforeTest(enabled = false)
	public void Setup(String userDetails) {
		ExtentReport.initReports();
		//List<String> collect = Stream.of(userDetails.split(":")).map(String::trim).collect(Collectors.toList());
		cDetails= new ClientDetails(userDetails);

	}

	@BeforeMethod(enabled = false)
	public void beforeMethod(Method m) {
		ExtentReport.createTest(cDetails.getMobileNumber()+":"+m.getName());
		ExtentReport.assignAuthor(cDetails.getClientId());
	}

	@AfterMethod(enabled = false)
	public void cleanUp() {
		ExtentReport.flushReports();
	}

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
	public void testName1(Method m) throws Exception {
		LocalDateTime now = LocalDateTime.now();
		System.out.println("Before Formatting: " + now);
		DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
		ExtentLogger.info("testing extent logging in 2 ");
		String formatDateTime = now.format(format);
		System.out.println("After Formatting: " + formatDateTime);
		Assert.fail("checking");

	}

	@Test
	public void daysDiffrence() {
		String fromDateStr = "2023-05-10T19:02:30.473+05:30";
		String toDateStr = "2024-05-09T19:02:30.440+05:30";
		Helper.numOfDaysDiff(fromDateStr,toDateStr);
	}

	@Test
	public void setValues() {
		Helper.updatePropertyValue("api-data.properties","N213207","gm104");
	}
	

	@Test
	public void collectionTest() {
		ArrayList<Double> arrayList = new ArrayList<>();
	
		arrayList.add(-16.12);
		arrayList.add(-15.12);
		arrayList.add(12.12);
		arrayList.add(13.12);
		arrayList.add(14.12);
		
		TreeSet<Double> set = new TreeSet<>();
		set.add(12.12);
		set.add(13.12);
		set.add(14.12);
		set.add(-15.12);
		set.add(-16.12);
		
		ArrayList<Double> arrayList1 = new ArrayList<>(set);
	
		System.out.println(arrayList);
		System.out.println(arrayList1);
		
		Assertions.assertThat(arrayList).as("both list isnt equal").isEqualTo(arrayList1);
		
	}
	
}
