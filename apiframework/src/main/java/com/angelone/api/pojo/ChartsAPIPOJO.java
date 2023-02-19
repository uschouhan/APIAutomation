package com.angelone.api.pojo;

import lombok.Data;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Data
public class ChartsAPIPOJO {

	 private int seqno;
	 private String action;
	 private String topic;
	 private String rtype;
	 private String period;
	 private String type;
	 private int duration;
	 private String from;
	 private String to;
}
