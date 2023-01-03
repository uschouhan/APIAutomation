package com.angelone.testdataMapper;

import com.angelone.api.pojo.UserDataJWT_POJO;
import com.angelone.api.pojo.UserDetailsPOJO;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;

import java.io.File;

public final class UserDATAJWTMapper {
	private UserDATAJWTMapper() {
	}

	@SneakyThrows
	public static UserDataJWT_POJO getUserDetails() {
		return new ObjectMapper()
				.readValue(new File(System.getProperty("user.dir") + "/src/test/resources/requests/userDataJWT.json"),
						UserDataJWT_POJO.class);

	}

	@SneakyThrows
	public static UserDataJWT_POJO getUserDetails(String userId) {
		return new ObjectMapper()
				.readValue(new File(System.getProperty("user.dir") + "/src/test/resources/requests/userDataJWT.json"),
						UserDataJWT_POJO.class)
				.setUser_id(userId);
	}
	
	
}
