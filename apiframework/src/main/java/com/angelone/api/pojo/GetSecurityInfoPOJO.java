package com.angelone.api.pojo;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;


import lombok.Data;


@Accessors(chain = true)
@Data
public class GetSecurityInfoPOJO {

    private String exchange;
    private String symbol;

}

