package com.angelone.tests.orderapi;

import com.angelone.api.BaseClass;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;



import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class PledgeApiTest extends BaseClass {
	
	
	@Test(enabled = true)
    public void pledgeGetTransactionAPI() throws Exception {

        Response pledgeTransaction = baseAPI.callPledgeGetTransactionAPI(cDetails.getClientId());
        Assert.assertTrue(pledgeTransaction.getStatusCode() == 200, "Invalid Response for pledge transactions API");
        String data = pledgeTransaction.jsonPath().getString("data");
        String decodedValue = baseAPI.decodeData(data);
        JsonPath jPath = new JsonPath(decodedValue);
        //System.out.println(jPath);
        String qty = jPath.getString("pledge_requests[2].qty");
        String netAmount = jPath.getString("pledge_requests[4].net_amt");
        Assert.assertNotNull(qty, "The value of quantity is not shown in response");
        Assert.assertNotNull(netAmount, "The value of Netamount is not shown in response");

    }
    @Test(enabled = true)
    public void getUnpledgeTransactionAPI() throws Exception {

        Response unpledgeTransaction = baseAPI.callPledgeGetTransactionAPI(cDetails.getClientId());
        Assert.assertTrue(unpledgeTransaction.getStatusCode() == 200, "Invalid Response for unledgeTrasaction API");
        String data = unpledgeTransaction.jsonPath().getString("data");
        String decodedValue = baseAPI.decodeData(data);
        JsonPath jPath = new JsonPath(decodedValue);
        //System.out.println(jPath);
        String qty = jPath.getString("pledge_requests[2].qty");
        String netAmount = jPath.getString("pledge_requests[4].net_amt");
        Assert.assertNotNull(qty, "The value of quantity is not shown in response");
        Assert.assertNotNull(netAmount, "The value of Netamount is not shown in response");

    }  

    @Test(enabled = true)
    public void getPledgeStatusAPI() throws Exception {

        Response pledgeStatus = baseAPI.callPledgeStatusAPI(cDetails.getClientId());
        Assert.assertTrue(pledgeStatus.getStatusCode() == 200, "Invalid Response for getPledgeStatus API");
        String data = pledgeStatus.jsonPath().getString("data");
        String decodedValue = baseAPI.decodeData(data);
        JsonPath jPath = new JsonPath(decodedValue);

        String reqid = jPath.getString("data[0].reqid");
       String resstatus = jPath.getString("resstatus[4].resstatus");
        Assert.assertNotNull(reqid, "The value of reqid is not shown in response");
       Assert.assertNotNull(resstatus, "The value of resstatus is not shown in response");

    }  

    @Test(enabled = true)
    public void createPledgeAPI() throws Exception {

        Response createPledge = baseAPI.callCreatePledgeAPI(cDetails.getClientId());
        Assert.assertTrue(createPledge.getStatusCode() == 200, "Invalid Response for createPledge API");
        String data = createPledge.jsonPath().getString("data");
        String decodedValue = baseAPI.decodeData(data);
        JsonPath jPath = new JsonPath(decodedValue);

        String dpid = jPath.getString("data[0].DPID");
        String boid = jPath.getString("data[2].Boid");
        Assert.assertNotNull(dpid, "The value of dpid is not shown in response");
       Assert.assertNotNull(boid, "The value of boid is not shown in response");

    }  


}
