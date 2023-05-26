package com.angelone.api;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.angelone.api.pojo.*;
import com.angelone.api.utility.Helper;
import com.angelone.config.factory.ApiConfigFactory;
import com.angelone.testdataMapper.GetLoginOTP;
import com.angelone.testdataMapper.VerifyLoginOtpMapper;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public final class InvokeApis {
	
	private String token;
	private String request_id;
	private String otp;
	private String nonTradingAccessTokenId;
    
    //public String ltpPrice;
    private static final String USER_TOKEN_ENDPOINT = ApiConfigFactory.getConfig().tokenEndpoint();
    private static final String LOGIN_MPIN_ENDPOINT = ApiConfigFactory.getConfig().loginMpinEndpoint();
    private static final String CREATE_ORDER_ENDPOINT = ApiConfigFactory.getConfig().orderEndpoint();
    private static final String LTP_PRICE_ENDPOINT = ApiConfigFactory.getConfig().ltpPriceEndpoint();
    private static final String CANCEL_ORDER_ENDPOINT = ApiConfigFactory.getConfig().cancelOrderEndpoint();
    private static final String GET_ORDER_BOOK_ENDPOINT = ApiConfigFactory.getConfig().getOrderBookEndpoint();
    private static final String GET_ORDER_STATUS_ENDPOINT = ApiConfigFactory.getConfig().getOrderStatus();
    private static final String GET_POSITION_ENDPOINT = ApiConfigFactory.getConfig().getPositionEndpoint();
    private static final String GET_LOGIN_OTP_ENDPOINT = ApiConfigFactory.getConfig().getLoginOTPEndpoint();
    private static final String VERIFY_OTP_ENDPOINT = ApiConfigFactory.getConfig().verifyOTPEndpoint();
    private static final String REFRESH_TOKEN_ENDPOINT = ApiConfigFactory.getConfig().refreshTokenEndpoint();
    private static final String FUTURE_BUILTUP_HEATMAP_ENDPOINT = ApiConfigFactory.getConfig().futureBuiltupHeatMapEndpoint();
    private static final String MARKET_MOVERS_BY_MOST_ENDPOINT = ApiConfigFactory.getConfig().marketMoversByMost();
    private static final String SECTOR_HEATMAP_ENDPOINT = ApiConfigFactory.getConfig().sectorHeatmapEndpoint();
    private static final String GET_WATCHLIST_ENDPOINT = ApiConfigFactory.getConfig().getWatchlistEndpoint();
    private static final String GET_BSE_EQUITY_CHARTS_ENDPOINT = ApiConfigFactory.getConfig().getBSEequityEndpoint();
    private static final String GET_NSE_EQUITY_CHARTS_ENDPOINT = ApiConfigFactory.getConfig().getNSEequityEndpoint();
    private static final String GET_NSE_CURRENCY_CHARTS_ENDPOINT = ApiConfigFactory.getConfig().getNSECurrencyEndpoint();
    private static final String GET_NSE_FNO_CHARTS_ENDPOINT = ApiConfigFactory.getConfig().getNseFnoEndpoint();
    private static final String GET_HOLDING_ENDPOINT = ApiConfigFactory.getConfig().getHoldingEndpoint();
    private static final String GET_OPTIONS_ENDPOINT = ApiConfigFactory.getConfig().getOptionEndpoint();
    private static final String GET_FUND_WITHDRAWAL_ENDPOINT = ApiConfigFactory.getConfig().fundWithdrawalEndpoint();
    private static final String GET_FUND_WITHDRAWAL_BALANCE_ENDPOINT = ApiConfigFactory.getConfig().fundWithdrawalBalanceEndpoint();
    private static final String GET_MARGIN_AMOUNT_ENDPOINT = ApiConfigFactory.getConfig().marginAmountEndpoint();
    private static final String PLEDGE_GETUSERSECURITY_ENDPOINT = ApiConfigFactory.getConfig().getUserSecurityEndpoint();
    private static final String GET_MCX_CHARTS_ENDPOINT = ApiConfigFactory.getConfig().getChartsMCXEndpoint();
    private static final String PLEDGE_GETWITHDRAWSECURITY_ENDPOINT = ApiConfigFactory.getConfig().getWithdrawSecurityEndpoint();
    private static final String FUND_RMS_LIMIT_ENDPOINT = ApiConfigFactory.getConfig().getFundRMSLimitEndpoint();
    private static final String MODIFY_ORDER_ENDPOINT = ApiConfigFactory.getConfig().modifyOrderEndpoint();
    private static final String GET_INSTA_TRADEDETAILS_ENDPOINT = ApiConfigFactory.getConfig().getInstaTradeDetailsEndpoint();
    private static final String GET_IPO_MASTER_ENDPOINT = ApiConfigFactory.getConfig().getIpoMasterEndpoint();
    private static final String GET_PORTFOLIO_LIST_ENDPOINT = ApiConfigFactory.getConfig().getPortfolioListEndpoint();
    private static final String FUTURE_MARKET_IND_ENDPOINT = ApiConfigFactory.getConfig().futureMarketIndEndpoint();
    private static final String FUNDAMENTAL_RATIO_ENDPOINT = ApiConfigFactory.getConfig().fundamentalRatioEndpoint();
    private static final String TOPGAINER_AND_LOSERS_ENDPOINT = ApiConfigFactory.getConfig().topgaineAndLosers();
    private static final String SET_WATCHLIST_ENDPOINT = ApiConfigFactory.getConfig().setWatchlist();
    private static final String STOCK_SHARE_HOLDER_ENDPOINT = ApiConfigFactory.getConfig().stockShareHolder();
    private static final String GETWITHDRAWLIST_ENDPOINT = ApiConfigFactory.getConfig().getWithdrawListEndpoint();
    private static final String GETTRANSACTIONMERGEDLIST_ENDPOINT = ApiConfigFactory.getConfig().getTransactionMergedListEndpoint();
    private static final String PROFILE_ENDPOINT = ApiConfigFactory.getConfig().getProfileEndpoint();
    private static final String GTT_CREATERULE_ENDPOINT = ApiConfigFactory.getConfig().getCreateRuleEndpoint();
    private static final String GTT_ORDERSTATUS_ENDPOINT = ApiConfigFactory.getConfig().getGttOrderStatusEndpoint();
    private static final String GTT_CANCELORDER_ENDPOINT = ApiConfigFactory.getConfig().getGttCancelOrderEndpoint();
	private static final String GTT_ORDERLIST_ENDPOINT = ApiConfigFactory.getConfig().gttOrderlistEndpoint();
	private static final String GTT_MODIFYORDER_ENDPOINT = ApiConfigFactory.getConfig().gttModifyOrderEndpoint();
	
	
    public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getNonTradingAccessTokenId() {
		return nonTradingAccessTokenId;
	}

	public void setNonTradingAccessTokenId(String nonTradingAccessTokenId) {
		this.nonTradingAccessTokenId = nonTradingAccessTokenId;
	}
	
    public String getRequest_id() {
		return request_id;
	}

	public void setRequest_id(String request_id) {
		this.request_id = request_id;
	}

	public String getOtp() {
		return otp;
	}

	public void setOtp(String otp) {
		this.otp = otp;
	}

	/**
     * Method for calling create user Token via MPIN
     *
     * @param userDetails
     * @return userDetals
     */
    public Response getUserTokenViaMPIN(LoginMpinPOJO userDetails, String jwtToken) {
        System.out.println(" ########## API Called : " + BaseRequestSpecification.BASE_URL + LOGIN_MPIN_ENDPOINT);
        Response response = BaseRequestSpecification.getDefaultRequestSpec().contentType(ContentType.JSON)
                .headers(getHeadersMpin(jwtToken))
                .body(userDetails)
                .log()
                .all()
                .post(LOGIN_MPIN_ENDPOINT);
        System.out.println("########  Api Response ########");
        response.then().log().all(true);
        return response;
    }

    /**
     * Method to construct headers for userToken api via MPIN
     *
     * @return headers as map
     */
    private Map<String, Object> getHeadersMpin(String jwtToken) {

        Map<String, Object> m = getHeaders();
        m.put("Accept", "application/json");
        m.put("Accept-Language", "en-GB,en-US;q=0.9,en;q=0.8");
        m.put("ApplicationName", "SparkAutomation");
        m.put("Connection", "keep-alive");
        m.put("Origin", "http://uattrade.angelbroking.com");
        m.put("Referer", "http://uattrade.angelbroking.com/");
        m.put("Content-Type", "application/json");
        m.put("X-AppID", "");
        m.put("X-ClientLocalIP", "172.29.24.126");
        m.put("X-ClientPublicIP", "172.29.24.126");
        m.put("X-DeviceID", Helper.generateDeviceId());
        m.put("X-GM-ID", "undefined");
        m.put("X-SystemInfo", "aliqua ad");
        m.put("X-Location", "aliqua ad");
        m.put("X-SourceID", "3");
        m.put("X-UserType", "1");
        m.put("X-MACAddress", "00:25:96:FF:FE:12:34:56");
        m.put("X-OperatingSystem", "Ubuntu");
        m.put("X-ProductVersion", "");
        m.put("X-Request-Id", "");
        m.put("Authorization", "Bearer " + jwtToken);
        return m;
    }


    /**
     * Method for calling create user Token
     *
     * @param userDetails
     * @return userDetals
     */
    public Response getUserToken(UserDetailsPOJO userDetails) {
        System.out.println(" ########## API Called : " + BaseRequestSpecification.BASE_URL + USER_TOKEN_ENDPOINT);
        Response response = BaseRequestSpecification.getDefaultRequestSpec().contentType(ContentType.JSON)
                .headers(getHeaders())
                .body(userDetails)
                .log()
                .all()
                .post(USER_TOKEN_ENDPOINT);
        System.out.println("########  Api Response ########");
        response.then().log().all(true);
        return response;
    }

    /**
     * Method to construct headers for userToken api
     *
     * @return headers as map
     */
    private static Map<String, Object> getHeaders() {

        Map<String, Object> m = new HashMap<String, Object>();
        m.put("X-SourceID", "5");
        m.put("X-UserType", "1");
        m.put("X-ClientLocalIP", "172.29.24.126");
        m.put("X-ClientPublicIP", "172.29.24.126");
        m.put("X-MACAddress", "00:25:96:FF:FE:12:34:56");
        m.put("X-OperatingSystem", "Ubuntu");
        m.put("X-DeviceID", Helper.generateDeviceId());
        m.put("Content-Type", "application/json");
        return m;
    }


    /**
     * Method to construct headers for userToken api
     *
     * @return headers as map
     */
    private static Map<String, Object> getHeadersForInstaTrade() {

        Map<String, Object> m = new HashMap<String, Object>();
        m.put("ClientIP", "172.29.24.126");
        m.put("ApplicationName", "SparkAutomation");
        m.put("X-DeviceID", Helper.generateDeviceId());
        m.put("DeviceType", "Android");
        m.put("AppVersion", "aayT0001");
        m.put("X-OperatingSystem", "Ubuntu");
        return m;
    }

    /**
     * Method for calling placeOrder api
     *
     * @param placeOrderDetails
     * @return orderDetails
     */
    public Response placeOrder(PlaceOrderDetailsPOJO placeOrderDetails) {
        System.out.println(" ########## API Called : " + BaseRequestSpecification.BASE_URL + CREATE_ORDER_ENDPOINT);
        Response response = BaseRequestSpecification.getDefaultRequestSpec().contentType(ContentType.JSON)
                .headers(getHeadersForOrder())
                .body(placeOrderDetails)
                .log()
                .all()
                .post(CREATE_ORDER_ENDPOINT);
        System.out.println("########  Api Response ########");
        response.then().log().all(true);
        return response;
    }

    /**
     * Method for calling placeGTTOrder api
     *
     * @param placeGTTOrderDetails
     * @return orderDetails
     */
    public Response placeGttOrder(CreateGttOrderPOJO placeOrderDetails) {
 
    	Response response = BaseRequestSpecification.getGttRequestSpec().contentType(ContentType.JSON)
                .headers(getHeadersForOrder())
                .body(placeOrderDetails)
                .log()
                .all()
                .post(GTT_CREATERULE_ENDPOINT);
        System.out.println("########  Api Response ########");
        response.then().log().all(true);
        return response;
    }

    
    /**
     * Method for calling modifyGTTOrder api
     *
     * @param placeGTTOrderDetails
     * @return orderDetails
     */
    public Response modifyGttOrder(ModifyGttOrderPOJO modifyGttOrderData) {
 
    	Response response = BaseRequestSpecification.getGttRequestSpec().contentType(ContentType.JSON)
                .headers(getHeadersForOrder())
                .body(modifyGttOrderData)
                .log()
                .all()
                .post(GTT_MODIFYORDER_ENDPOINT);
        System.out.println("########  Api Response ########");
        response.then().log().all(true);
        return response;
    }

    
    
    

    /**
     * Method for calling placeOrder api
     *
     * @param placeOrderDetails
     * @return orderDetails
     */
    public Response modifyOrderApi(ModifyOrderPOJO modifyOrderDetails) {
        System.out.println(" ########## API Called : " + BaseRequestSpecification.BASE_URL + MODIFY_ORDER_ENDPOINT);
        Response response = BaseRequestSpecification.getDefaultRequestSpec().contentType(ContentType.JSON)
                .headers(getHeadersForOrder())
                .body(modifyOrderDetails)
                .log()
                .all()
                .post(MODIFY_ORDER_ENDPOINT);
        System.out.println("########  Api Response ########");
        response.then().log().all(true);
        return response;
    }

    /**
     * Method to construct headers for placeOrder api
     *
     * @return headers as map
     */
    private Map<String, Object> getHeadersForOrder() {

        Map<String, Object> m = new HashMap<String, Object>();
        m.put("ApplicationName", "SparkAutomation");
        m.put("X-SourceID", "aliqua ad");
        m.put("X-UserType", "aliqua ad");
        m.put("X-ClientLocalIP", "aliqua ad");
        m.put("X-ClientPublicIP", "aliqua ad");
        m.put("X-MACAddress", "aliqua ad");
        m.put("X-Request-Id", "aliqua ad");
        m.put("X-AppID", "aliqua ad");
        m.put("X-SystemInfo", "aliqua ad");
        m.put("X-Location", "aliqua ad");
        m.put("Content-Type", "application/json");
        m.put("Authorization", "Bearer " + token);
        return m;
    }


    /**
     * this Method is to get LTP price
     *
     * @param ltprice
     * @return
     */
    public Response getLTPPrice(LTPPricePOJO ltprice) {
        System.out.println(" ########## API Called : " + BaseRequestSpecification.BASE_URL + LTP_PRICE_ENDPOINT);
        Response response = BaseRequestSpecification.getDefaultRequestSpec().contentType(ContentType.JSON)
                .headers(getHeaderForLtpPrice())
                .body(ltprice)
                .log()
                .all()
                .post(LTP_PRICE_ENDPOINT);
        System.out.println("########  Api Response ########");
        response.then().log().all(true);
        return response;
    }

    private Map<String, ?> getHeaderForLtpPrice() {
        // TODO Auto-generated method stub
        Map<String, Object> m = new HashMap<String, Object>();
        if (Objects.nonNull(token) && token.length() > 1)
            m.put("Authorization", "Bearer " + token);
        else {
            //new BaseTestApi().genUserToken();
            System.out.println("$$$$$$$$$$$ Access Token is null. Please check $$$$$$$");
        }

        m.put("Content-Type", "application/json");
        return m;
    }
    

    /**
     * this Method is to get OrderStatus
     *
     * @param OrderStatusPOJO
     * @return
     */
    public Response getOrderStatus(OrderStatusPOJO orderStatusData) {
        System.out.println(" ########## API Called : " + BaseRequestSpecification.BASE_URL + GET_ORDER_STATUS_ENDPOINT);
        Response response = BaseRequestSpecification.getDefaultRequestSpec().contentType(ContentType.JSON)
                .headers(getHeadersForOrder())
                .body(orderStatusData)
                .log()
                .all()
                .post(GET_ORDER_STATUS_ENDPOINT);
        System.out.println("########  Api Response ########");
        response.then().log().all(true);
        return response;
    }

    public Response getGttOrderStatus(GttOrderStatusPOJO orderStatusData) {
        Response response = BaseRequestSpecification.getGttRequestSpec().contentType(ContentType.JSON)
                .headers(getHeadersForOrder())
                .body(orderStatusData)
                .log()
                .all()
                .post(GTT_ORDERSTATUS_ENDPOINT);
        System.out.println("########  Api Response ########");
        response.then().log().all(true);
        return response;
    }
    
    public Response gttCancelOrderApi(GttCancelOrderPOJO orderStatusData) {
        Response response = BaseRequestSpecification.getGttRequestSpec().contentType(ContentType.JSON)
                .headers(getHeadersForOrder())
                .body(orderStatusData)
                .log()
                .all()
                .post(GTT_CANCELORDER_ENDPOINT);
        System.out.println("########  Api Response ########");
        response.then().log().all(true);
        return response;
    }

    
    public Response gttOrderListApi(GttOrderListPOJO orderStatusData) {
        Response response = BaseRequestSpecification.getGttRequestSpec().contentType(ContentType.JSON)
                .headers(getHeadersForOrder())
                .body(orderStatusData)
                .log()
                .all()
                .post(GTT_ORDERLIST_ENDPOINT);
        System.out.println("########  Api Response ########");
        response.then().log().all(true);
        return response;
    }
    
    public Response cancelOrder(CancelOrderPOJO cancelOrderDetails) {
        System.out.println(" ########## API Called : " + BaseRequestSpecification.BASE_URL + CANCEL_ORDER_ENDPOINT);
        Response response = BaseRequestSpecification.getDefaultRequestSpec().contentType(ContentType.JSON)
                .headers(getHeadersForOrder())
                .body(cancelOrderDetails)
                .log()
                .all()
                .post(CANCEL_ORDER_ENDPOINT);
        System.out.println("########  Api Response ########");
        response.then().log().all(true);
        return response;
    }

    public Response getAllOrderDetails() {
        System.out.println(" ########## API Called : " + BaseRequestSpecification.BASE_URL + GET_ORDER_BOOK_ENDPOINT);
        Response response = BaseRequestSpecification.getDefaultRequestSpec().contentType(ContentType.JSON)
                .headers(getHeadersForOrder())
                .log()
                .all()
                .get(GET_ORDER_BOOK_ENDPOINT);
        System.out.println("########  Api Response ########");
        response.then().log().all(true);
        return response;
    }

    public Response getAllPostions() {
        System.out.println(" ########## API Called : " + BaseRequestSpecification.BASE_URL + GET_POSITION_ENDPOINT);
        Response response = BaseRequestSpecification.getDefaultRequestSpec().contentType(ContentType.JSON)
                .headers(getHeadersForOrder())
                .log()
                .all()
                .get(GET_POSITION_ENDPOINT);
        System.out.println("########  Api Response ########");
        response.then().log().all(true);
        return response;
    }

    //##################### Trade related APIs    ######################

    public Response getLoginToken(LoginOtpPOJO otpRequestDetails , String oldTradeToken) {
        System.out.println(" ########## API Called : " + BaseRequestSpecification.TRADE_BASE_URL + GET_LOGIN_OTP_ENDPOINT);
        Response response = BaseRequestSpecification.getTradeRequestSpec().contentType(ContentType.JSON)
                .headers(getOTPHeadersForCaptchaApi(oldTradeToken))
                .body(otpRequestDetails)
                .log()
                .all()
                .post(GET_LOGIN_OTP_ENDPOINT);
        System.out.println("########  Api Response ########");
        response.then().log().all(true);
        return response;
    }
    
    

    /**
     * Method to construct headers for loginOtp api
     *
     * @return headers as map
     */
    private static Map<String, Object> getOTPHeaders() {

        Map<String, Object> m = new HashMap<String, Object>();
        m.put("x-clientlocalip", "1.2.3.4");
        m.put("x-clientpublicip", "1.2.3.4");
        m.put("x-deviceid", Helper.generateDeviceId());
        m.put("x-macaddress", "00:25:96:FF:FE:12:34:56");
        m.put("X-operatingsystem", "Ubuntu");
        m.put("x-sourceid", "5");
        m.put("x-usertype", "1");
        m.put("x-source", "mutualfund");
        m.put("x-source-v2", "abma");
        return m;
    }
    
    /**
     * Method to construct headers for loginOtp api
     *
     * @return headers as map
     */
    private static Map<String, Object> getOTPHeadersForCaptchaApi(String oldNonTradeToken) {

        Map<String, Object> m = new HashMap<String, Object>();
        m.put("x-clientlocalip", "1.2.3.4");
        m.put("x-clientpublicip", "1.2.3.4");
        m.put("x-deviceid", Helper.generateDeviceId());
        m.put("x-macaddress", "00:25:96:FF:FE:12:34:56");
        m.put("X-operatingsystem", "Ubuntu");
        m.put("x-sourceid", "5");
        m.put("x-usertype", "1");
        m.put("x-source", "mutualfund");
        m.put("x-source-v2", "abma");
        m.put("Authorization", "Bearer "+ oldNonTradeToken);
        return m;
    }
    
    

    public Response verifyLoginToken(VerifyLoginOtpPOJO verifyOtpDetails) {
        System.out.println(" ########## API Called : " + BaseRequestSpecification.TRADE_BASE_URL + VERIFY_OTP_ENDPOINT);
        Response response = BaseRequestSpecification.getTradeRequestSpec().contentType(ContentType.JSON)
                .headers(getVerifyOTPHeaders())
                .body(verifyOtpDetails)
                .log()
                .all()
                .post(VERIFY_OTP_ENDPOINT);
        System.out.println("########  Api Response ########");
        response.then().log().all(true);
        return response;
    }
    
    public Response refreshToken(RefreshTokenPOJO refreshPojoData) {
        System.out.println(" ########## API Called : " + BaseRequestSpecification.TRADE_BASE_URL + REFRESH_TOKEN_ENDPOINT);
        Response response = BaseRequestSpecification.getTradeRequestSpec().contentType(ContentType.JSON)
                .headers(getVerifyOTPHeaders())
                .body(refreshPojoData)
                .log()
                .all()
                .post(REFRESH_TOKEN_ENDPOINT);
        System.out.println("########  Api Response ########");
        response.then().log().all(true);
        return response;
    }


    /**
     * Method to construct headers for VerifyloginOtp api
     *
     * @return headers as map
     */
    private static Map<String, Object> getVerifyOTPHeaders() {

        Map<String, Object> m = getOTPHeaders();
        m.put("X-ProductVersion", "v4.0.1");
        m.put("X-Location", "India");
        return m;
    }


    public Response callInstaTradeDetailsApi(String nonTradeAccessToken, Map<String, Object> queryParams) {
        System.out.println(" ########## API Called : " + BaseRequestSpecification.TRADE_BASE_URL + GET_INSTA_TRADEDETAILS_ENDPOINT);
        Response response = BaseRequestSpecification.getTradeRequestSpec().contentType(ContentType.JSON)
                .headers("AccessToken", nonTradeAccessToken)
                .headers(getHeadersForInstaTrade())
                .queryParams(queryParams)
                .log()
                .all()
                .get(GET_INSTA_TRADEDETAILS_ENDPOINT);
        System.out.println("########  Api Response ########");
        response.then().log().all(true);
        return response;
    }
    
    public Response callGetProfileApi(String nonTradeAccessToken) {
        System.out.println(" ########## API Called : " + BaseRequestSpecification.getPortfolioAdvisorySpec() + PROFILE_ENDPOINT);
        Response response = BaseRequestSpecification.getPortfolioAdvisorySpec().contentType(ContentType.JSON)
                .headers("AccessToken", nonTradeAccessToken)
                .headers("ApplicationName","SPARK_Automation")
                .log()
                .all()
                .get(PROFILE_ENDPOINT);
        System.out.println("########  Api Response ########");
        response.then().log().all(true);
        return response;
    }


    public Response callIpoMasterApi(String nonTradeAccessToken) {
        System.out.println(" ########## API Called : " + BaseRequestSpecification.IPO_BASE_URL + GET_IPO_MASTER_ENDPOINT);
        Response response = BaseRequestSpecification.getIpoRequestSpec().contentType(ContentType.JSON)
                .headers("Authorization", "Bearer " + nonTradeAccessToken)
                .headers("X-requestId", "sparkipo")
                .log()
                .all()
                .get(GET_IPO_MASTER_ENDPOINT);
        System.out.println("########  Api Response ########");
        response.then().log().all(true);
        return response;
    }


    // ###################### Market Place APIs #####################

    public Response call_marketPlace_futureBuiltupHeatmap(String nonTradeAccessToken, Map<String, Object> queryParams) {
        System.out.println(" ########## API Called : " + BaseRequestSpecification.DISCOVERY_BASE_URL + FUTURE_BUILTUP_HEATMAP_ENDPOINT);
        Response response = BaseRequestSpecification.getDiscoveryRequestSpec().contentType(ContentType.JSON)
                .headers("AccessToken", nonTradeAccessToken)
                .queryParams(queryParams)
                .log()
                .all()
                .get(FUTURE_BUILTUP_HEATMAP_ENDPOINT);
        System.out.println("########  Api Response ########");
        response.then().log().all(true);
        return response;
    }

    public Response call_future_markets_indicators(String nonTradeAccessToken, Map<String, Object> queryParams) {
        System.out.println(" ########## API Called : " + BaseRequestSpecification.DISCOVERY_BASE_URL + FUTURE_MARKET_IND_ENDPOINT);
        Response response = BaseRequestSpecification.getDiscoveryRequestSpec().contentType(ContentType.JSON)
                .headers("AccessToken", nonTradeAccessToken)
                .queryParams(queryParams)
                .log()
                .all()
                .get(FUTURE_MARKET_IND_ENDPOINT);
        System.out.println("########  Api Response ########");
        response.then().log().all(true);
        return response;
    }

    public Response callSectorHeatMapApi(String nonTradeAccessToken) {
        System.out.println(" ########## API Called : " + BaseRequestSpecification.DISCOVERY_BASE_URL + SECTOR_HEATMAP_ENDPOINT);
        Response response = BaseRequestSpecification.getDiscoveryRequestSpec().contentType(ContentType.JSON)
                .headers("AccessToken", nonTradeAccessToken)
                .log()
                .all()
                .get(SECTOR_HEATMAP_ENDPOINT);
        System.out.println("########  Api Response ########");
        response.then().log().all(true);
        return response;
    }

    public Response call_MartketMoversByMost(String nonTradeAccessToken, Map<String, Object> queryParams) {
        System.out.println(" ########## API Called : " + BaseRequestSpecification.DISCOVERY_BASE_URL + MARKET_MOVERS_BY_MOST_ENDPOINT);
        Response response = BaseRequestSpecification.getDiscoveryRequestSpec().contentType(ContentType.JSON)
                .headers("AccessToken", nonTradeAccessToken)
                .queryParams(queryParams)
                .log()
                .all()
                .get(MARKET_MOVERS_BY_MOST_ENDPOINT);
        System.out.println("########  Api Response ########");
        response.then().log().all(true);
        return response;
    }

    public Response call_FundamentalRatio(String nonTradeAccessToken, Map<String, Object> queryParams) {
        System.out.println(" ########## API Called : " + BaseRequestSpecification.DISCOVERY_BASE_URL + FUNDAMENTAL_RATIO_ENDPOINT);
        Response response = BaseRequestSpecification.getDiscoveryRequestSpec().contentType(ContentType.JSON)
                .headers("AccessToken", nonTradeAccessToken)
                .queryParams(queryParams)
                .log()
                .all()
                .get(FUNDAMENTAL_RATIO_ENDPOINT);
        System.out.println("########  Api Response ########");
        response.then().log().all(true);
        return response;
    }


    public Response call_stockShareHolderApi(String nonTradeAccessToken, Map<String, Object> queryParams) {
        System.out.println(" ########## API Called : " + BaseRequestSpecification.DISCOVERY_BASE_URL + STOCK_SHARE_HOLDER_ENDPOINT);
        Response response = BaseRequestSpecification.getDiscoveryRequestSpec().contentType(ContentType.JSON)
                .headers("AccessToken", nonTradeAccessToken)
                .queryParams(queryParams)
                .log()
                .all()
                .get(STOCK_SHARE_HOLDER_ENDPOINT);
        System.out.println("########  Api Response ########");
        response.then().log().all(true);
        return response;
    }
    
    public Response call_TopGainerNLoser(String nonTradeAccessToken, Map<String, Object> queryParams) {
        System.out.println(" ########## API Called : " + BaseRequestSpecification.TRADE_BASE_URL + TOPGAINER_AND_LOSERS_ENDPOINT);
        Response response = BaseRequestSpecification.getTradeRequestSpec().contentType(ContentType.JSON)
                .headers("AccessToken", nonTradeAccessToken)
                .queryParams(queryParams)
                .log()
                .all()
                .get(TOPGAINER_AND_LOSERS_ENDPOINT);
        System.out.println("########  Api Response ########");
        response.then().log().all(true);
        return response;
    }


    // #################### Watchlist API ###########################

    private static Map<String, Object> setWatchlistHeader() {
        Map<String, Object> m = new HashMap<String, Object>();
        m.put("ClientIP", "127.0.0.1");
        m.put("DeviceID", Helper.generateDeviceId());
        m.put("AppVersion", "40.0.1");
        m.put("Content-Type", "application/json");
        m.put("ApplicationName", "SparkAutomation");
        return m;
    }

    public Response call_getWatchlistAPI(String nonTradeAccessToken) {
        System.out.println(" ########## API Called : " + BaseRequestSpecification.WATCHLIST_BASE_URL + GET_WATCHLIST_ENDPOINT);
        Response response = BaseRequestSpecification.getWatchlistSpec().contentType(ContentType.JSON)
                .headers("AccessToken", nonTradeAccessToken)
                .log()
                .all()
                .get(GET_WATCHLIST_ENDPOINT);
        System.out.println("########  Api Response ########");
        response.then().log().all(true);
        return response;
    }

    public Response call_setWatchlistAPI(String nonTradeAccessToken, SetWatchListPOJO body) {
        Response response = BaseRequestSpecification.getWatchlistSpec().contentType(ContentType.JSON)
                .headers(setWatchlistHeader())
                .headers("AccessToken",nonTradeAccessToken)
                .body(body)
                .log()
                .all()
                .post(SET_WATCHLIST_ENDPOINT);
        System.out.println("########  Api Response ########");
        response.then().log().all(true);
        return response;
    }


    // #################### CHARTS API ############################

    /**
     * Method to construct headers for Charts api
     *
     * @return headers as map
     */
    private static Map<String, Object> getHeadersForCharts() {

        Map<String, Object> m = new HashMap<String, Object>();
        m.put("Accept", "application/json");
        m.put("X-consumer", "postman");
        m.put("X-correlation-id", "uuid4");
        m.put("X-access-token", "abcd");
        return m;
    }

    public Response getBSEEquityCharts(ChartsAPIPOJO chartspojo) {
        System.out.println(" ########## API Called : " + BaseRequestSpecification.CHARTS_EQUITY_BASE_URL + GET_BSE_EQUITY_CHARTS_ENDPOINT);
        Response response = BaseRequestSpecification.getChartsEquitySpec().contentType(ContentType.JSON)
                .headers(getHeadersForCharts())
                .body(chartspojo)
                .log()
                .all()
                .post(GET_BSE_EQUITY_CHARTS_ENDPOINT);
        System.out.println("########  Api Response ########");
        response.then().log().all(true);
        return response;
    }

    public Response callMCXChartsApi(ChartsAPIPOJO chartspojo) {
        System.out.println(" ########## API Called : " + BaseRequestSpecification.CHARTS_MCX_BASE_URL + GET_MCX_CHARTS_ENDPOINT);
        Response response = BaseRequestSpecification.getChartsMCXSpec().contentType(ContentType.JSON)
                .headers(getHeadersForCharts())
                .body(chartspojo)
                .log()
                .all()
                .post(GET_MCX_CHARTS_ENDPOINT);
        System.out.println("########  Api Response ########");
        response.then().log().all(true);
        return response;
    }

    public Response getNSEEquityCharts(ChartsAPIPOJO chartspojo) {
        System.out.println(" ########## API Called : " + BaseRequestSpecification.CHARTS_EQUITY_BASE_URL + GET_NSE_EQUITY_CHARTS_ENDPOINT);
        Response response = BaseRequestSpecification.getChartsEquitySpec().contentType(ContentType.JSON)
                .headers(getHeadersForCharts())
                .body(chartspojo)
                .log()
                .all()
                .post(GET_NSE_EQUITY_CHARTS_ENDPOINT);
        System.out.println("########  Api Response ########");
        response.then().log().all(true);
        return response;
    }

    public Response getNSECurrencyCharts(ChartsAPIPOJO chartspojo) {
        System.out.println(" ########## API Called : " + BaseRequestSpecification.CHARTS_EQUITY_BASE_URL + GET_NSE_CURRENCY_CHARTS_ENDPOINT);
        Response response = BaseRequestSpecification.getChartsEquitySpec().contentType(ContentType.JSON)
                .headers(getHeadersForCharts())
                .body(chartspojo)
                .log()
                .all()
                .post(GET_NSE_CURRENCY_CHARTS_ENDPOINT);
        System.out.println("########  Api Response ########");
        response.then().log().all(true);
        return response;
    }

    public Response getNSE_FNO_Charts(ChartsAPIPOJO chartspojo) {
        System.out.println(" ########## API Called : " + BaseRequestSpecification.CHARTS_EQUITY_BASE_URL + GET_NSE_FNO_CHARTS_ENDPOINT);
        Response response = BaseRequestSpecification.getChartsEquitySpec().contentType(ContentType.JSON)
                .headers(getHeadersForCharts())
                .body(chartspojo)
                .log()
                .all()
                .post(GET_NSE_FNO_CHARTS_ENDPOINT);
        System.out.println("########  Api Response ########");
        response.then().log().all(true);
        return response;
    }

    // #################### Portfolio API ############################

    public Response getHolding(String nonTradeAccessToken) {
        System.out.println(" ########## API Called : " + BaseRequestSpecification.PORTFOLIO_BASE_URL + GET_HOLDING_ENDPOINT);
        Response response = BaseRequestSpecification.getPortfolioSpec().contentType(ContentType.JSON)
                .headers("token", nonTradeAccessToken)
                .log()
                .all()
                .post(GET_HOLDING_ENDPOINT);
        System.out.println("########  Api Response ########");
        response.then().log().all(true);
        return response;
    }

    //################### Options API ##########################

    public Response getOptions(OptionsPOJO chartspojo) {
        System.out.println(" ########## API Called : " + BaseRequestSpecification.OPTIONS_BASE_URL + GET_OPTIONS_ENDPOINT);
        Response response = BaseRequestSpecification.getOptionsSpec().contentType(ContentType.JSON)
                .body(chartspojo)
                .log()
                .all()
                .post(GET_OPTIONS_ENDPOINT);
        System.out.println("########  Api Response ########");
        response.then().log().all(true);
        return response;
    }

    //################### FundWithdrawal API ##########################

    public Response getFundWithdrawalData(FundWithdrawalPOJO chartspojo) {
        List<FundWithdrawalPOJO> requestData = new ArrayList<FundWithdrawalPOJO>();
        requestData.add(chartspojo);
        System.out.println(" ########## API Called : " + BaseRequestSpecification.FUND_WITHDRAWAL_BASE_URL + GET_FUND_WITHDRAWAL_ENDPOINT);
        Response response = BaseRequestSpecification.getFundWithdrawalSpec().contentType(ContentType.JSON)
                .relaxedHTTPSValidation()
                .body(requestData)
                .log()
                .all()
                .post(GET_FUND_WITHDRAWAL_ENDPOINT);
        System.out.println("########  Api Response ########");
        response.then().log().all(true);
        return response;
    }


    public Response call_withdrawalBalanceAPi(String nonTradeAccessToken) {
        System.out.println(" ########## API Called : " + BaseRequestSpecification.FUND_WITHDRAWAL_BASE_URL + GET_FUND_WITHDRAWAL_BALANCE_ENDPOINT);
        Response response = BaseRequestSpecification.getFundWithdrawalSpec().contentType(ContentType.JSON)
                .headers("Authorization", "Bearer "+nonTradeAccessToken)
                .log()
                .all()
                .get(GET_FUND_WITHDRAWAL_BALANCE_ENDPOINT);
        System.out.println("########  Api Response ########");
        response.then().log().all(true);
        return response;
    }
    
    //################### MarginAmount API ##########################

    public Response getMarginAmount(MarginAmountPOJO marginPojo) {

        System.out.println(" ########## API Called : " + BaseRequestSpecification.MARGIN_AMOUNT_BASE_URL + GET_MARGIN_AMOUNT_ENDPOINT);
        Response response = BaseRequestSpecification.getMarginAmountSpec().contentType(ContentType.JSON)
                .headers("authorization", "Bearer "+getToken())
                .relaxedHTTPSValidation()
                .body(marginPojo)
                .log()
                .all()
                .post(GET_MARGIN_AMOUNT_ENDPOINT);
        System.out.println("########  Api Response ########");
        response.then().log().all(true);
        return response;
    }


    //################### Pledge API ##########################

    public Response getUserSecurity(PledgeGetUserSecurityPOJO usersecuritypojo) {
        System.out.println(" ########## API Called : " + BaseRequestSpecification.PLEDGE_GETUSERSECURITY_BASE_URL + PLEDGE_GETUSERSECURITY_ENDPOINT);
        Response response = BaseRequestSpecification.getUserSecuritySpec().contentType(ContentType.JSON)
                .headers("authorization", "Bearer "+getToken())
                .body(usersecuritypojo)
                .log()
                .all()
                .post(PLEDGE_GETUSERSECURITY_ENDPOINT);
        System.out.println("########  Api Response ########");
        response.then().log().all(true);
        return response;
    }

    public Response getWithdrawSecurity(PledgeGetWithdrawSecurityPOJO withdrawsecuritypojo) {
        System.out.println(" ########## API Called : " + BaseRequestSpecification.PLEDGE_GETWITHDRAWSECURITY_BASE_URL + PLEDGE_GETWITHDRAWSECURITY_ENDPOINT);
        Response response = BaseRequestSpecification.getWithdrawSecuritySpec().contentType(ContentType.JSON)
                .headers("authorization", "Bearer "+getToken())
                .body(withdrawsecuritypojo)
                .log()
                .all()
                .post(PLEDGE_GETWITHDRAWSECURITY_ENDPOINT);
        System.out.println("########  Api Response ########");
        response.then().log().all(true);
        return response;
    }


    public Response getFundRMSLimitData(FundRmsLimitPOJO requestData) {

        System.out.println(" ########## API Called : " + BaseRequestSpecification.BASE_URL + FUND_RMS_LIMIT_ENDPOINT);
        Response response = BaseRequestSpecification.getDefaultRequestSpec().contentType(ContentType.JSON)
                .headers(getHeadersForOrder())
                .relaxedHTTPSValidation()
                .body(requestData)
                .log()
                .all()
                .post(FUND_RMS_LIMIT_ENDPOINT);
        System.out.println("########  Api Response ########");
        response.then().log().all(true);
        return response;
    }

    //################### Portfolio Advisory ###################
    public Response callPortfolioAdvisoryApi(String nonTradeAccessToken) {
        System.out.println(" ########## API Called : " + BaseRequestSpecification.getPortfolioAdvisorySpec() + GET_PORTFOLIO_LIST_ENDPOINT);
        Response response = BaseRequestSpecification.getPortfolioAdvisorySpec().contentType(ContentType.JSON)
                .headers("AccessToken", nonTradeAccessToken)
                .log()
                .all()
                .get(GET_PORTFOLIO_LIST_ENDPOINT);
        System.out.println("########  Api Response ########");
        response.then().log().all(true);
        return response;
    }
    
    public Response getWithdrawListAPI(String nonTradeAccessToken) {
        System.out.println(" ########## API Called : " + BaseRequestSpecification.FUND_WITHDRAWAL_BASE_URL + GETWITHDRAWLIST_ENDPOINT);
        Response response = BaseRequestSpecification.getWithdrawList().contentType(ContentType.JSON) 
        		.headers("Authorization", "Bearer "+nonTradeAccessToken)
                .log()
                .all()
                .get(GETWITHDRAWLIST_ENDPOINT);
        System.out.println("########  Api Response ########");
        response.then().log().all(true);
        return response;
    }

    public Response getTransactionMergedListAPI(String nonTradeAccessToken) {
        System.out.println(" ########## API Called : " + BaseRequestSpecification.FUND_WITHDRAWAL_BASE_URL + GETTRANSACTIONMERGEDLIST_ENDPOINT);
        Response response = BaseRequestSpecification.getTransactionMergedList().contentType(ContentType.JSON) 
        		.headers("Authorization", "Bearer "+nonTradeAccessToken)
                .log()
                .all()
                .get(GETTRANSACTIONMERGEDLIST_ENDPOINT);
        System.out.println("########  Api Response ########");
        response.then().log().all(true);
        return response;
    }
   
    
    
    
}
