package com.angelone.api.pojo;

import lombok.Data;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Data
public class SetWatchListPOJO {

	private Integer version;
	private String watchlistData;

}