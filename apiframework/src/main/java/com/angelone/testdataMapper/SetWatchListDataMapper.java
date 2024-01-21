package com.angelone.testdataMapper;

import com.angelone.api.pojo.SetWatchListPOJO;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;

import java.io.File;

public final class SetWatchListDataMapper {
	private SetWatchListDataMapper() {
	}

	@SneakyThrows
	public static SetWatchListPOJO getWatchListData() {
		return new ObjectMapper()
				.readValue(new File(System.getProperty("user.dir") + "/src/main/resources/requests/setWatchlist.json"),
						SetWatchListPOJO.class);

	}

	@SneakyThrows
	public static SetWatchListPOJO getWatchListData(Integer version,String watchlistData) {
		return new ObjectMapper()
				.readValue(new File(System.getProperty("user.dir") + "/src/main/resources/requests/setWatchlist.json"),
						SetWatchListPOJO.class)
				.setVersion(version)
				.setWatchlistData(watchlistData);
	}

	
}
