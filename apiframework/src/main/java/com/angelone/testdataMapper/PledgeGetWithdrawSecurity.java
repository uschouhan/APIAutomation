package com.angelone.testdataMapper;

import com.angelone.api.pojo.OptionsPOJO;
import com.angelone.api.pojo.PledgeGetUserSecurityPOJO;
import com.angelone.api.pojo.PledgeGetWithdrawSecurityPOJO;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;

import java.io.File;

public final class PledgeGetWithdrawSecurity {
	private PledgeGetWithdrawSecurity() {
	}

	@SneakyThrows
	public static PledgeGetWithdrawSecurityPOJO getWithdrawSecurityData() {
		return new ObjectMapper()
				.readValue(new File(System.getProperty("user.dir") + "/src/test/resources/requests/getWithdrawSecurity.json"),
						PledgeGetWithdrawSecurityPOJO.class);

	}

	
	@SneakyThrows
	public static PledgeGetWithdrawSecurityPOJO getWithdrawSecurityData(String party_code) {
		return new ObjectMapper()
				.readValue(new File(System.getProperty("user.dir") + "/src/test/resources/requests/getWithdrawSecurity.json"),
						PledgeGetWithdrawSecurityPOJO.class)
			.setParty_code(party_code);
			
	}
}
