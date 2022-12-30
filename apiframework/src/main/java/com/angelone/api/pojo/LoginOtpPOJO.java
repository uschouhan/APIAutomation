package com.angelone.api.pojo;

import lombok.Data;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Data
public class LoginOtpPOJO {

	private String country_code;
	private String mob_no;
	private Boolean is_otp_resend;
	private String user_id;
}