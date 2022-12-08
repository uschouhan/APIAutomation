package com.angelone.api;

import com.angelone.config.factory.ApiConfigFactory;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

public final class BaseRequestSpecification {

  private BaseRequestSpecification() {
  }

  public static final String BASE_URL = ApiConfigFactory.getConfig().apiBaseUrl();

  public static RequestSpecification getDefaultRequestSpec() {
    return RestAssured
      .given()
      .contentType(ContentType.JSON)
      .baseUri(BASE_URL);
  }
  
  
}
