package com.angelone.testdataMapper;

import java.io.File;


import com.angelone.api.pojo.getScripDetailPOJO;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.SneakyThrows;
public class getScripDetailMapper {

    private getScripDetailMapper() {
    }

    @SneakyThrows
    public static getScripDetailPOJO getScripDetail() {
        return new ObjectMapper()
                .readValue(new File(System.getProperty("user.dir") + "/src/test/resources/requests/getScripDetail.json"),
                        getScripDetailPOJO.class);
}
    @SneakyThrows
    public static getScripDetailPOJO getScripDetail(String exchange, String symbol,String flag) {
        return new ObjectMapper()
                .readValue(new File(System.getProperty("user.dir") + "/src/test/resources/requests/getScripDetail.json"),
                        getScripDetailPOJO.class)
                .setSymbol(symbol)
                .setExchange(exchange)
                .setFlag(flag);

    }

}