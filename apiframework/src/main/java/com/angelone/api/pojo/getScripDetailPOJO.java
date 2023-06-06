package com.angelone.api.pojo;
import lombok.Data;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Data
public class getScripDetailPOJO {

    private String exchange;
    private String symbol;
    private String flag;
}
