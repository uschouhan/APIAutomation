package com.angelone.testdataMapper;

import java.io.File;

import com.angelone.api.pojo.ChartsAPIPOJO;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.SneakyThrows;

public final class ChartsTestData {
	private ChartsTestData() {
	}

	@SneakyThrows
	public static ChartsAPIPOJO getChartsData() {
		return new ObjectMapper()
				.readValue(new File(System.getProperty("user.dir") + "/src/test/resources/requests/Charts.json"),
						ChartsAPIPOJO.class);

	}

	@SneakyThrows
	public static ChartsAPIPOJO getChartsData(int seqno, String action,String topic,String rtype,String period,
			String type,int duration,String from,String to) {
		return new ObjectMapper()
				.readValue(new File(System.getProperty("user.dir") + "/src/test/resources/requests/Charts.json"),
						ChartsAPIPOJO.class)
				.setSeqno(seqno)
				.setAction(action)
				.setTopic(topic)
				.setRtype(rtype)
				.setPeriod(period)
				.setType(type)
				.setDuration(duration)
				.setFrom(from)
				.setTo(to);
				
	}
	
}
