package com.angelone.testdataMapper;

import java.io.File;

import com.angelone.api.pojo.FundRmsLimitPOJO;
import com.angelone.api.pojo.FundWithdrawalPOJO;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.SneakyThrows;

public final class FundRmsLimitMapper {
	private FundRmsLimitMapper() {
	}

	@SneakyThrows
	public static FundRmsLimitPOJO getFundData() {
		return new ObjectMapper()
				.readValue(new File(System.getProperty("user.dir") + "/src/main/resources/requests/fundRmsLimit.json"),
						FundRmsLimitPOJO.class);

	}

	@SneakyThrows
	public static FundRmsLimitPOJO getFundData(String exchange,String product ,String segment) {
		return new ObjectMapper()
				.readValue(new File(System.getProperty("user.dir") + "/src/main/resources/requests/fundRmsLimit.json"),
						FundRmsLimitPOJO.class)
				.setExchange(exchange)
				.setProduct(product)
				.setSegment(segment);
	}

	
}
