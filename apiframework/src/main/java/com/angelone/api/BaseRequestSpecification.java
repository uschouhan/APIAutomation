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
  public static final String WATCHLIST_BASE_URL = ApiConfigFactory.getConfig().watchlistEndpoint();
  public static final String CHARTS_EQUITY_BASE_URL = ApiConfigFactory.getConfig().chartsEquityBaseURL();
  public static final String PORTFOLIO_BASE_URL = ApiConfigFactory.getConfig().getPortfolioBaseURL();
  public static final String OPTIONS_BASE_URL = ApiConfigFactory.getConfig().getOptionBaseURL();
  public static final String CHARTS_MCX_BASE_URL = ApiConfigFactory.getConfig().chartsMCXBaseURL();
  
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

  public static RequestSpecification getWatchlistSpec() {
	    return RestAssured
	      .given()
	      .contentType(ContentType.JSON)
	      .baseUri(WATCHLIST_BASE_URL);
	  }
  
  public static RequestSpecification getChartsEquitySpec() {
	    return RestAssured
	      .given()
	      .contentType(ContentType.JSON)
	      .baseUri(CHARTS_EQUITY_BASE_URL);
	  }

  public static RequestSpecification getPortfolioSpec() {
	    return RestAssured
	      .given()
	      .contentType(ContentType.JSON)
	      .baseUri(PORTFOLIO_BASE_URL);
	  }
  public static RequestSpecification getOptionsSpec() {
	    return RestAssured
	      .given()
	      .contentType(ContentType.JSON)
	      .baseUri(OPTIONS_BASE_URL);
	  }
  public static RequestSpecification getChartsMCXSpec() {
	    return RestAssured
	      .given()
	      .contentType(ContentType.JSON)
	      .baseUri(CHARTS_MCX_BASE_URL);
	  }
}
