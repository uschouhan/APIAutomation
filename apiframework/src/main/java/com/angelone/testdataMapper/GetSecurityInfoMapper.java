package com.angelone.testdataMapper;

import java.io.File;

import com.angelone.api.pojo.GetSecurityInfoPOJO;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.SneakyThrows;

public final class GetSecurityInfoMapper {
    private GetSecurityInfoMapper() {
    }

    @SneakyThrows
    public static GetSecurityInfoPOJO getsecurityinfo() {
        return new ObjectMapper()
                .readValue(new File(System.getProperty("user.dir") + "/src/main/resources/requests/getSecurityInfo.json"),
                        GetSecurityInfoPOJO.class);

    }

    @SneakyThrows
    public static GetSecurityInfoPOJO getsecurityinfo(String exchange, String symbol) {
        return new ObjectMapper()
                .readValue(new File(System.getProperty("user.dir") + "/src/main/resources/requests/getSecurityInfo.json"),
                        GetSecurityInfoPOJO.class)
                .setSymbol(symbol)
                .setExchange(exchange);


    }

}