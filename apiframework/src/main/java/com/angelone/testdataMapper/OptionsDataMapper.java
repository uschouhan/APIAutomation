package com.angelone.testdataMapper;

import com.angelone.api.pojo.OptionsPOJO;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;

import java.io.File;

public final class OptionsDataMapper {
	private OptionsDataMapper() {
	}

	@SneakyThrows
	public static OptionsPOJO getOptionsData() {
		return new ObjectMapper()
				.readValue(new File(System.getProperty("user.dir") + "/src/test/resources/requests/OptionsData.json"),
						OptionsPOJO.class);

	}

	
	@SneakyThrows
	public static OptionsPOJO getOptionsData(String stockxchangecode,String expirydate) {
		return new ObjectMapper()
				.readValue(new File(System.getProperty("user.dir") + "/src/test/resources/requests/OptionsData.json"),
						OptionsPOJO.class)
				.setStockxchangecode(stockxchangecode)
				.setExpirydate(expirydate);

			
	}
}
