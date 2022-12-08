package com.angelone.api.pojo;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserResponseDetails {

    public boolean status;
    public String message;
    public String errorcode;
    public Data data;
}
