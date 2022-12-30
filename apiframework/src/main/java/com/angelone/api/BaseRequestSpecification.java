package com.angelone.api;

import com.angelone.config.factory.ApiConfigFactory;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

public final class BaseRequestSpecification {

  private BaseRequestSpecification() {
  }

  public static final String BASE_URL = ApiConfigFactory.getConfig().apiBaseUrl();
  public static final String TRADE_BASE_URL = ApiConfigFactory.getConfig().tradeBaseUrl();
  public static final String DISCOVERY_BASE_URL = ApiConfigFactory.getConfig().discoveryBaseUrl();
  
  public static RequestSpecification getDefaultRequestSpec() {
    return RestAssured
      .given()
      .contentType(ContentType.JSON)
      .baseUri(BASE_URL);
  }
  
  public static RequestSpecification getTradeRequestSpec() {
	    return RestAssured
	      .given()
	      .contentType(ContentType.JSON)
	      .baseUri(TRADE_BASE_URL);
	  }
  
  public static RequestSpecification getDiscoveryRequestSpec() {
	    return RestAssured
	      .given()
	      .contentType(ContentType.JSON)
	      .baseUri(DISCOVERY_BASE_URL);
	  }

 
  
}
