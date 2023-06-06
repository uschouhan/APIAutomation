package com.angelone.testdataMapper;


import com.angelone.api.pojo.PledgeGetStatusPOJO;
import com.angelone.api.pojo.PledgeGetTransactionPOJO;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;

import java.io.File;

public final class PledgeGetStatus {
	private PledgeGetStatus() {
	}

	@SneakyThrows
	public static PledgeGetStatusPOJO getPledgeStatusData() {
		return new ObjectMapper()
				.readValue(new File(System.getProperty("user.dir") + "/src/test/resources/requests/getPledgeStatus.json"),
						PledgeGetStatusPOJO.class);

	}

	
	@SneakyThrows
	public static PledgeGetStatusPOJO getPledgeStatusData(String party_code) {
		return new ObjectMapper()
				.readValue(new File(System.getProperty("user.dir") + "/src/test/resources/requests/getPledgeStatus.json"),
						PledgeGetStatusPOJO.class)
			.setParty_code(party_code);
			//.setReq_id(req_id);
			
	}
}
