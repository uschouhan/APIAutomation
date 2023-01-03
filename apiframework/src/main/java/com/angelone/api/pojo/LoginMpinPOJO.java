package com.angelone.api.pojo;

import lombok.Data;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Data
public class LoginMpinPOJO {

	private String requestid;
	private String passorpin;
	private String requestidtype;

}
