package com.angelone.tests.orderapi;

import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.angelone.api.pojo.ClientDetails;
import com.angelone.reports.ExtentLogger;
import com.angelone.reports.ExtentReport;


public class Demo {

	ClientDetails cDetails ;
	
	@Parameters({ "UserCredentials" })
	@BeforeTest
	public void Setup(String userDetails) {
		ExtentReport.initReports();
		//List<String> collect = Stream.of(userDetails.split(":")).map(String::trim).collect(Collectors.toList());
		cDetails= new ClientDetails(userDetails);
		
	}
	
	@BeforeMethod
	public void beforeMethod(Method m) {
		ExtentReport.createTest(cDetails.getMobileNumber()+":"+m.getName());
		ExtentReport.assignAuthor(cDetails.getClientId());
	}
	
	@AfterMethod
	public void cleanUp() {
		ExtentReport.flushReports();
	}
	
	@Test
	public void testName(Method m) throws Exception {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
		Date date = new Date();
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
	public void testName3(Method m) throws Exception {
		
	        ExtentLogger.info("testing extent logging in 3 ");
	 
		
	}
	

	@Test
	public void testNam4(Method m) throws Exception {
		
	        ExtentLogger.info("testing extent logging in 4 ");
	 
		
	}
	
}
