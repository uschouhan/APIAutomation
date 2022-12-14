package com.angelone.testdataMapper;

import com.angelone.api.pojo.UserDetailsPOJO;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;

import java.io.File;

public final class UserTestData {
	private UserTestData() {
	}

	@SneakyThrows
	public static UserDetailsPOJO getUserDetails() {
		return new ObjectMapper()
				.readValue(new File(System.getProperty("user.dir") + "/src/test/resources/requests/postUser.json"),
						UserDetailsPOJO.class);

	}

	@SneakyThrows
	public static UserDetailsPOJO getUserDetails(String userId, String password) {
		return new ObjectMapper()
				.readValue(new File(System.getProperty("user.dir") + "/src/test/resources/requests/postUser.json"),
						UserDetailsPOJO.class)
				.setUserid(userId)
				.setPassorpin(password);
	}
	
	
}
