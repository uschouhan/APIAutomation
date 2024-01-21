package com.angelone.testdataMapper;

import com.angelone.api.pojo.VerifyLoginOtpPOJO;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;

import java.io.File;

public final class VerifyLoginOtpMapper {
	private VerifyLoginOtpMapper() {
	}

	@SneakyThrows
	public static VerifyLoginOtpPOJO verifyOtp(String mobNum) {
		return new ObjectMapper()
				.readValue(new File(System.getProperty("user.dir") + "/src/main/resources/requests/verifyLoginOtp.json"),
						VerifyLoginOtpPOJO.class);

	}

	@SneakyThrows
	public static VerifyLoginOtpPOJO verifyOtp(String requestId,String countryCode, String mobNo,String otp,String source,String appId,String reqTokenFlag) {
		return new ObjectMapper()
				.readValue(new File(System.getProperty("user.dir") + "/src/main/resources/requests/verifyLoginOtp.json"),
						VerifyLoginOtpPOJO.class)
				.setRequest_id(requestId)
				.setCountry_code(countryCode)
				.setMob_no(mobNo)
				.setOtp(otp)
				.setSource(source)
				.setApp_id(appId)
				.setRequire_token_flag(reqTokenFlag);
			
	}
	
	@SneakyThrows
	public static VerifyLoginOtpPOJO verifyOtp(String requestId,String mobNo,String otp) {
		return new ObjectMapper()
				.readValue(new File(System.getProperty("user.dir") + "/src/main/resources/requests/verifyLoginOtp.json"),
						VerifyLoginOtpPOJO.class)
				.setRequest_id(requestId)
				.setMob_no(mobNo)
				.setOtp(otp);

			
	}
}
