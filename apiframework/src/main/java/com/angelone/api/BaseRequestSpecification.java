package com.angelone.api;

import com.angelone.config.factory.ApiConfigFactory;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

public final class BaseRequestSpecification {

    private BaseRequestSpecification() {
    }

    public static final String LOGIN_BASE_URL = ApiConfigFactory.getConfig().loginBaseUrl();
    public static final String BASE_URL = ApiConfigFactory.getConfig().apiBaseUrl();
    public static final String TRADE_BASE_URL = ApiConfigFactory.getConfig().tradeBaseUrl();
    public static final String DISCOVERY_BASE_URL = ApiConfigFactory.getConfig().discoveryBaseUrl();
    public static final String WATCHLIST_BASE_URL = ApiConfigFactory.getConfig().watchlistEndpoint();
    public static final String CHARTS_EQUITY_BASE_URL = ApiConfigFactory.getConfig().chartsEquityBaseURL();
    public static final String PORTFOLIO_BASE_URL = ApiConfigFactory.getConfig().getPortfolioBaseURL();
    public static final String OPTIONS_BASE_URL = ApiConfigFactory.getConfig().getOptionBaseURL();
    public static final String FUND_WITHDRAWAL_BASE_URL = ApiConfigFactory.getConfig().fundWithdrawalBaseUrl();
    public static final String MARGIN_AMOUNT_BASE_URL = ApiConfigFactory.getConfig().marginAmountBaseURL();
    public static final String PLEDGE_GETUSERSECURITY_BASE_URL = ApiConfigFactory.getConfig().getUserSecurityBaseURL();
    public static final String CHARTS_MCX_BASE_URL = ApiConfigFactory.getConfig().chartsMCXBaseURL();
    public static final String PLEDGE_GETWITHDRAWSECURITY_BASE_URL = ApiConfigFactory.getConfig().getWithdrawSecurityBaseURL();
    public static final String IPO_BASE_URL = ApiConfigFactory.getConfig().getIpoBaseURL();
    public static final String PORTFOLIO_ADVISORY_BASE_URL = ApiConfigFactory.getConfig().getPortfolioAdvisoryBaseURL();
    public static final String GETWITHDRAWSLIST_BASE_URL = ApiConfigFactory.getConfig().getWithdrawListBaseURL();
    public static final String GTT_BASE_URL = ApiConfigFactory.getConfig().getGttBaseURL();
    public static final String REPORT_EXCHANGE_BASE_URL = ApiConfigFactory.getConfig().reportexchangebaseurl();
    public static final String PLEDGE_GETTRANSACTION_BASE_URL = ApiConfigFactory.getConfig().getPledgeTransactionBaseURL();
    public static final String PG_TRANSACTION_BASE_URL = ApiConfigFactory.getConfig().getPGTransactionBaseURL();
    public static final String PG_BASE_URL = ApiConfigFactory.getConfig().getPGBaseURL();
    public static final String PG_ACTUATOR_BASEURL = ApiConfigFactory.getConfig().getPGActuatorBaseURL();
    private static final String STOCK_SIP_BASE_ENDPOINT = ApiConfigFactory.getConfig().stockSipBaseUrl();


    public static RequestSpecification getDefaultRequestSpecForLogin() {
        return RestAssured
                .given()
                .contentType(ContentType.JSON)
                .baseUri(LOGIN_BASE_URL);
    }

    public static RequestSpecification getDefaultRequestSpec() {
        return RestAssured
                .given()
                .contentType(ContentType.JSON)
                .baseUri(BASE_URL);
    }

    public static RequestSpecification getStockSIPBaseSpec() {
        return RestAssured
                .given()
                .contentType(ContentType.JSON)
                .baseUri(STOCK_SIP_BASE_ENDPOINT);
    }


    public static RequestSpecification getGttRequestSpec() {
        return RestAssured
                .given()
                .contentType(ContentType.JSON)
                .baseUri(GTT_BASE_URL);
    }

    public static RequestSpecification getIpoRequestSpec() {
        return RestAssured
                .given()
                .contentType(ContentType.JSON)
                .baseUri(IPO_BASE_URL);
    }

    public static RequestSpecification getPortfolioAdvisorySpec() {
        return RestAssured
                .given()
                .contentType(ContentType.JSON)
                .baseUri(PORTFOLIO_ADVISORY_BASE_URL);
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

    public static RequestSpecification getFundWithdrawalSpec() {
        return RestAssured
                .given()
                .contentType(ContentType.JSON)
                .baseUri(FUND_WITHDRAWAL_BASE_URL);
    }

    public static RequestSpecification getMarginAmountSpec() {
        return RestAssured
                .given()
                .contentType(ContentType.JSON)
                .baseUri(MARGIN_AMOUNT_BASE_URL);
    }

    public static RequestSpecification getUserSecuritySpec() {
        return RestAssured
                .given()
                .contentType(ContentType.JSON)
                .baseUri(PLEDGE_GETUSERSECURITY_BASE_URL);
    }

    public static RequestSpecification getChartsMCXSpec() {
        return RestAssured
                .given()
                .contentType(ContentType.JSON)
                .baseUri(CHARTS_MCX_BASE_URL);
    }

    public static RequestSpecification getWithdrawSecuritySpec() {
        return RestAssured
                .given()
                .contentType(ContentType.JSON)
                .baseUri(PLEDGE_GETWITHDRAWSECURITY_BASE_URL);
    }

    public static RequestSpecification getWithdrawList() {
        return RestAssured
                .given()
                .contentType(ContentType.JSON)
                .baseUri(FUND_WITHDRAWAL_BASE_URL);
    }

    public static RequestSpecification getTransactionMergedList() {
        return RestAssured
                .given()
                .contentType(ContentType.JSON)
                .baseUri(FUND_WITHDRAWAL_BASE_URL);
    }


    public static RequestSpecification getOrderCharge() {
        return RestAssured
                .given()
                .contentType(ContentType.JSON)
                .baseUri(TRADE_BASE_URL);
    }

    public static RequestSpecification getEquityTransaction() {
        return RestAssured
                .given()
                .contentType(ContentType.JSON)
                .baseUri(TRADE_BASE_URL);

    }


    public static RequestSpecification getExchange() {
        return RestAssured
                .given()
                .contentType(ContentType.JSON)
                .baseUri(REPORT_EXCHANGE_BASE_URL);
    }

    public static RequestSpecification getReortExchange() {
        return RestAssured
                .given()
                .contentType(ContentType.JSON)
                .baseUri(REPORT_EXCHANGE_BASE_URL);
    }

    public static RequestSpecification getPledgeTransaction() {
        return RestAssured
                .given()
                .contentType(ContentType.JSON)
                .baseUri(PLEDGE_GETTRANSACTION_BASE_URL);
    }

    public static RequestSpecification getPledgeStatus() {
        return RestAssured
                .given()
                .contentType(ContentType.JSON)
                .baseUri(PLEDGE_GETTRANSACTION_BASE_URL);
    }

    public static RequestSpecification getPGTransactionList() {
        return RestAssured
                .given()
                .contentType(ContentType.JSON)
                .baseUri(PG_TRANSACTION_BASE_URL);
    }

    public static RequestSpecification getPGTransactionBase() {
        return RestAssured
                .given()
                .contentType(ContentType.JSON)
                .baseUri(PG_BASE_URL);
    }

    public static RequestSpecification getPGActuator() {
        return RestAssured
                .given()
                .contentType(ContentType.JSON)
                .baseUri(PG_ACTUATOR_BASEURL);
    }
}
