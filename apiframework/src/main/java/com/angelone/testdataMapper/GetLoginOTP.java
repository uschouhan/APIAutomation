package com.angelone.testdataMapper;

import com.angelone.api.pojo.LoginOtpPOJO;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;

import java.io.File;

public final class GetLoginOTP {
	private GetLoginOTP() {
	}

	@SneakyThrows
	public static LoginOtpPOJO getOtp(String mobNumber) {
		return new ObjectMapper()
				.readValue(new File(System.getProperty("user.dir") + "/src/main/resources/requests/getLoginOtp.json"),
						LoginOtpPOJO.class)
				.setMob_no(mobNumber);

	}

	@SneakyThrows
	public static LoginOtpPOJO getUserDetails(String countryCode, String mobNo,String isOtpResend,String userId) {
		return new ObjectMapper()
				.readValue(new File(System.getProperty("user.dir") + "/src/main/resources/requests/getLoginOtp.json"),
						LoginOtpPOJO.class)
				.setCountry_code(countryCode)
				.setMob_no(mobNo)
				.setIs_otp_resend(false)
				.setUser_id(userId);
			
	}
	
}
