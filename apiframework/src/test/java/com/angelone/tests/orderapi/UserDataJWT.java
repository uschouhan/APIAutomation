package com.angelone.tests.orderapi;

import lombok.Data;

@Data
public class UserDataJWT {

	private String country_code;
	private String user_id;
	private String created_at;
	private String mob_no;
	private String source;
	private String app_id;
	
	public UserDataJWT()
	{
		this.country_code="";
		this.user_id="U50049267";
		this.created_at="2022-02-24T19:12:24.087297091+05:30";
		this.mob_no="495358049";
		this.source="SPARK";
		this.app_id="56567";
	}
}
