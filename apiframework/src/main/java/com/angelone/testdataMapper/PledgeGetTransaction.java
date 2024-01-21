package com.angelone.testdataMapper;


import com.angelone.api.pojo.PledgeGetTransactionPOJO;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;

import java.io.File;

public final class PledgeGetTransaction {
	private PledgeGetTransaction() {
	}

	@SneakyThrows
	public static PledgeGetTransactionPOJO getPledgeTransactionData() {
		return new ObjectMapper()
				.readValue(new File(System.getProperty("user.dir") + "/src/main/resources/requests/getPledgeTransaction.json"),
						PledgeGetTransactionPOJO.class);

	}

	
	@SneakyThrows
	public static PledgeGetTransactionPOJO getPledgeTransactionData(String party_code) {
		return new ObjectMapper()
				.readValue(new File(System.getProperty("user.dir") + "/src/main/resources/requests/getPledgeTransaction.json"),
						PledgeGetTransactionPOJO.class)
			.setParty_code(party_code);
			
	}
}
