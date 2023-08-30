package com.angelone.api.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.Getter;
import lombok.experimental.Accessors;

import java.util.List;

@Accessors(chain = true)
@Data
public class CreateStockSIPPOJO {
	@Getter
	@JsonIgnore
	private List<String> tags;
	private String source;
	private String instrument_type;
	private String strategy_code;
	private String instrument_id;
	private String instrument_name;
	private String instrument_trade_symbol;
	private String instrument_symbol;
	private Integer order_value;
	private String frequency;
	private String order_type;
	private String exchange;
	private String start_date;

	public void setTags(List<String> tags) {
		this.tags = tags;
	}

	public List<String> getTags() {
		return tags;
	}
}
