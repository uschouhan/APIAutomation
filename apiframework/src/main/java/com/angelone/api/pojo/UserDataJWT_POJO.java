package com.angelone.api.pojo;

import lombok.Data;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Data
public class UserDataJWT_POJO {

	private String country_code;
	private String user_id;
	private String created_at;
	private String mob_no;
	private String source;
	private String app_id;

}
