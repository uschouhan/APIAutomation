package com.angelone.testdataMapper;

import java.io.File;
import java.util.List;

import com.angelone.api.pojo.GttOrderListPOJO;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.SneakyThrows;

public final class GttOrderListMapper {
	private GttOrderListMapper() {
	}

	@SneakyThrows
	public static GttOrderListPOJO setGttOrderListData() {
		return new ObjectMapper()
				.readValue(new File(System.getProperty("user.dir") + "/src/main/resources/requests/gttOrderList.json"),
						GttOrderListPOJO.class);

	}

	@SneakyThrows
	public static GttOrderListPOJO setGttOrderListData(Integer count,Integer page, List<String> status) {
		return new ObjectMapper()
				.readValue(new File(System.getProperty("user.dir") + "/src/main/resources/requests/gttOrderList.json"),
						GttOrderListPOJO.class)
				.setCount(count).setPage(page).setStatus(status);
	}

}
