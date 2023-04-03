package com.angelone.testdataMapper;

import java.io.File;

import com.angelone.api.pojo.FundWithdrawalPOJO;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.SneakyThrows;

public final class FundWithdrawalDataMapper {
	private FundWithdrawalDataMapper() {
	}

	@SneakyThrows
	public static FundWithdrawalPOJO getFundData() {
		return new ObjectMapper()
				.readValue(new File(System.getProperty("user.dir") + "/src/test/resources/requests/fundWithdraw.json"),
						FundWithdrawalPOJO.class);

	}

	@SneakyThrows
	public static FundWithdrawalPOJO getFundData(String name, String value) {
		return new ObjectMapper()
				.readValue(new File(System.getProperty("user.dir") + "/src/test/resources/requests/fundWithdraw.json"),
						FundWithdrawalPOJO.class)
				.setName(name)
				.setValue(value);
	}

	
}
