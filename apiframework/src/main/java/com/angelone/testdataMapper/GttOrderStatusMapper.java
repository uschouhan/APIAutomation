package com.angelone.testdataMapper;

import com.angelone.api.pojo.GttOrderStatusPOJO;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;

import java.io.File;

public final class GttOrderStatusMapper {
	private GttOrderStatusMapper() {
	}

	@SneakyThrows
	public static GttOrderStatusPOJO setOrderStatusData(String id) {
		return new ObjectMapper()
				.readValue(new File(System.getProperty("user.dir") + "/src/main/resources/requests/getGttOrderStatus.json"),
						GttOrderStatusPOJO.class)
				.setId(id);

	}
	
}
