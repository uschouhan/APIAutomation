package com.angelone.api;

import java.lang.reflect.Method;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

import com.angelone.api.pojo.ClientDetails;
import com.angelone.api.utility.Helper;
import com.angelone.config.factory.ApiConfigFactory;
import com.angelone.reports.ExtentReport;

public class BaseClass {
	
	protected BaseTestApi baseAPI;
	protected ClientDetails cDetails ;
	protected Helper helper = new Helper();
	private static final String SECRET_KEY = ApiConfigFactory.getConfig().secretKey();

	@Parameters({ "UserCredentials" })
	@BeforeTest
	public void Setup(String userDetails) {
		baseAPI = new BaseTestApi();
		// Generate User Mpin Token
		baseAPI.generateUserToken(userDetails, SECRET_KEY);
		// Generate NonTraded Access Token
		baseAPI.getNonTradingAccessToken(userDetails);
		//ExtentReport.initReports();
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
		//ExtentReport.flushReports();
	}
		
}
