package com.angelone.api.pojo;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import lombok.Data;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Data
public class ClientDetails {

	private String mobileNumber;
	private String emailId;
	private String emailPwd;
	private String clientId;
	private String mPin;
	
	public ClientDetails(String details)
	{
		List<String> collect = Stream.of(details.split(":")).map(String::trim).collect(Collectors.toList());
		this.mobileNumber=collect.get(0);
		this.emailId=collect.get(1);
		this.emailPwd=collect.get(2);
		this.clientId=collect.get(3);
		this.mPin=collect.get(4);
		
	}

}
