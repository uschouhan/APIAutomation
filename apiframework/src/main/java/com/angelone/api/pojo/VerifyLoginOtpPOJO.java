package com.angelone.api.pojo;

import lombok.Data;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Data
public class VerifyLoginOtpPOJO {


	private String request_id;

	private String country_code;

	private String mob_no;

	private String otp;

	private String source;

	private String app_id;

	private String require_token_flag;
   
}