package com.angelone.testdataMapper;


import com.angelone.api.pojo.CheckOppositePendingOrderPOJO;
import com.angelone.api.pojo.GetSecurityInfoPOJO;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;

import java.io.File;

public final class getSecurityInfo {
	private getSecurityInfo() {
	}

	@SneakyThrows
	public static GetSecurityInfoPOJO getSecurityInfoData() {
		return new ObjectMapper()
				.readValue(new File(System.getProperty("user.dir") + "/src/test/resources/requests/getsecurityInfo.json"),
						GetSecurityInfoPOJO.class);

	}

	
	@SneakyThrows
	public static GetSecurityInfoPOJO getSecurityInfoData(String exchange,String symbol ) {
		return new ObjectMapper()
				.readValue(new File(System.getProperty("user.dir") + "/src/test/resources/requests/getsecurityInfo.json"),
						GetSecurityInfoPOJO.class)
				.setExchange(exchange)
		.setSymbol(symbol);
		

			
	}
}
