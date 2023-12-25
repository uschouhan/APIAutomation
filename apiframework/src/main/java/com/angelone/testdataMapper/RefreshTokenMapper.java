package com.angelone.testdataMapper;

import com.angelone.api.pojo.RefreshTokenPOJO;
import com.angelone.api.pojo.VerifyLoginOtpPOJO;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;

import java.io.File;

public final class RefreshTokenMapper {
	private RefreshTokenMapper() {
	}

	
	@SneakyThrows
	public static RefreshTokenPOJO setRefreshTokenData(String refresh_token) {
		return new ObjectMapper()
				.readValue(new File(System.getProperty("user.dir") + "/src/main/resources/requests/refreshToken.json"),
						RefreshTokenPOJO.class)
				.setRefresh_token(refresh_token);
						
	}
}
