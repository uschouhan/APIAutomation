package com.angelone.testdataMapper;
import java.io.File;


import com.angelone.api.pojo.OrderChargesPOJO;
import com.angelone.api.pojo.getScripDetailPOJO;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.SneakyThrows;
public class OrderChargesMapper {

    private OrderChargesMapper() {
    }

    @SneakyThrows
    public static OrderChargesPOJO OrderCharges() {
        return new ObjectMapper()
                .readValue(new File(System.getProperty("user.dir") + "/src/test/resources/requests/OrderCharges.json"),
                        OrderChargesPOJO.class);

    }
    @SneakyThrows
    public static OrderChargesPOJO OrderCharges(String productType, String transactionType, String quantity, String price,String exchange,String isin) {
        return new ObjectMapper()
                .readValue(new File(System.getProperty("user.dir") + "/src/test/resources/requests/OrderCharges.json"),
                        OrderChargesPOJO.class)

                .setProductType(productType)
                .setTransactionType(transactionType)
                .setQuantity(Integer.valueOf(quantity))
                .setPrice(Integer.valueOf(price))
                .setExchange(exchange)
                .setIsin(isin);


    }
}