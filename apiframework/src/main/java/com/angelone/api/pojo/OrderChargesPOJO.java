package com.angelone.api.pojo;
import lombok.Data;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Data
public class OrderChargesPOJO {

    private String productType;
    private String transactionType;
    private Integer quantity;
    private Integer price;
    private String exchange;
    private String isin;
}
