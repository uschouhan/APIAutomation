package com.angelone.testdataMapper;

import com.angelone.api.pojo.LoginMpinPOJO;
import com.angelone.api.pojo.UserDataJWT_POJO;
import com.angelone.api.pojo.UserDetailsPOJO;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;

import java.io.File;

public final class LoginMpinMapper {
	private LoginMpinMapper() {
	}

	@SneakyThrows
	public static LoginMpinPOJO getUserDetails() {
		return new ObjectMapper()
				.readValue(new File(System.getProperty("user.dir") + "/src/test/resources/requests/loginMpin.json"),
						LoginMpinPOJO.class);

	}

	@SneakyThrows
	public static LoginMpinPOJO getUserDetails(String requestId) {
		return new ObjectMapper()
				.readValue(new File(System.getProperty("user.dir") + "/src/test/resources/requests/loginMpin.json"),
						LoginMpinPOJO.class)
				.setRequestid(requestId);
	}
	
	
}
