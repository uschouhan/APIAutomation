package com.angelone.testdataMapper;

import com.angelone.api.pojo.OptionsPOJO;
import com.angelone.api.pojo.PledgeGetUserSecurityPOJO;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;

import java.io.File;

public final class PledgeGetUserSecurity {
	private PledgeGetUserSecurity() {
	}

	@SneakyThrows
	public static PledgeGetUserSecurityPOJO getUserSecurityData() {
		return new ObjectMapper()
				.readValue(new File(System.getProperty("user.dir") + "/src/main/resources/requests/getUserSecurity.json"),
						PledgeGetUserSecurityPOJO.class);

	}

	
	@SneakyThrows
	public static PledgeGetUserSecurityPOJO getUserSecurityData(String party_code) {
		return new ObjectMapper()
				.readValue(new File(System.getProperty("user.dir") + "/src/main/resources/requests/getUserSecurity.json"),
						PledgeGetUserSecurityPOJO.class)
			.setParty_code(party_code);
			
	}
}
