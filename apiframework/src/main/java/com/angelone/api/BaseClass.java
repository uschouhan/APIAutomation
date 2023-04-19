package com.angelone.api;

import java.lang.reflect.Method;
import java.util.Properties;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

import com.angelone.api.pojo.ClientDetails;
import com.angelone.api.utility.Helper;
import com.angelone.config.factory.ApiConfigFactory;
import com.angelone.reports.ExtentReport;
import com.angelone.reports.TracingPrintStream;

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
		//baseAPI.getNonTradingAccessToken(userDetails);
		baseAPI.getNonTradingAccessTokenWithoutOtp(userDetails);
		//baseAPI.refreshToken(userDetails);
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
	
	static {
		System.setOut(new TracingPrintStream(System.out));
    	System.setErr(new TracingPrintStream(System.err));
	}
}
