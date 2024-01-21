package com.angelone.testdataMapper;

import com.angelone.api.pojo.CreatePledgePOJO;
import com.angelone.api.pojo.OptionsPOJO;
import com.angelone.api.pojo.PledgeGetUserSecurityPOJO;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;

import java.io.File;

public final class CreatePledgeData {
	private CreatePledgeData() {
	}

	@SneakyThrows
	public static CreatePledgePOJO createPledgeData() {
		return new ObjectMapper()
				.readValue(new File(System.getProperty("user.dir") + "/src/main/resources/requests/getUserSecurity.json"),
						CreatePledgePOJO.class);

	}

	
	@SneakyThrows
	public static CreatePledgePOJO createPledgeData(String party_code) {
		return new ObjectMapper()
				.readValue(new File(System.getProperty("user.dir") + "/src/main/resources/requests/getUserSecurity.json"),
						CreatePledgePOJO.class)
			.setParty_code(party_code);
			
	}
}
