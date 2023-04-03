package com.angelone.testdataMapper;

import java.io.File;

import com.angelone.api.pojo.MarginAmountPOJO;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.SneakyThrows;

public final class MarginAmountMapper {
	private MarginAmountMapper() {
	}

	@SneakyThrows
	public static MarginAmountPOJO getMarginAmountData() {
		return new ObjectMapper()
				.readValue(new File(System.getProperty("user.dir") + "/src/test/resources/requests/marginAmount.json"),
						MarginAmountPOJO.class);

	}

	@SneakyThrows
	public static MarginAmountPOJO getMarginAmountData(String partyCode) {
		return new ObjectMapper()
				.readValue(new File(System.getProperty("user.dir") + "/src/test/resources/requests/marginAmount.json"),
						MarginAmountPOJO.class)
				.setParty_code(partyCode);
			
	}

	
}
