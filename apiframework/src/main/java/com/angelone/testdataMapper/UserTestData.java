package com.angelone.testdataMapper;

import com.angelone.api.pojo.UserDetails;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;

import java.io.File;

public final class UserTestData {
	private UserTestData() {
	}

	@SneakyThrows
	public static UserDetails getUserDetails() {
		return new ObjectMapper()
				.readValue(new File(System.getProperty("user.dir") + "/src/test/resources/requests/post-user.json"),
						UserDetails.class);

	}

	@SneakyThrows
	public static UserDetails getUserDetails(String userId, String password) {
		return new ObjectMapper()
				.readValue(new File(System.getProperty("user.dir") + "/src/test/resources/requests/post-user.json"),
						UserDetails.class)
				.setUserid(userId)
				.setPassorpin(password);
	}
	
	
}
