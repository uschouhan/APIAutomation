package com.angelone.tests.orderapi;

import com.angelone.api.BaseClass;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.assertj.core.api.Assertions;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class B2C_API_TEST extends BaseClass {


    private static final List<String> EQUITY_TRANSACTIONS_DATA = new ArrayList<>(
            Arrays.asList("ISIN"));


    @Test(enabled = true)
    public void testOrderCharges() throws Exception {

        Response positions = baseAPI.getOrderCharge("MARGIN", "B","1", "695","NSE","INE742F01042");
        Assert.assertTrue(positions.getStatusCode() == 200, "Invalid Response for getPostion API");

        Assert.assertEquals(positions.jsonPath().getString("status"), "success",
                "Status doesnt match in profileDataResponse api ");
        String transactionCharges = positions.jsonPath().getString("data[0].transactionCharges");
        String transactionMessage = positions.jsonPath().getString("data[0].transactionMessage");
        String stampDuty = positions.jsonPath().getString("data[0].stampDuty");
        String stampDutyMessage = positions.jsonPath().getString("data[0].stampDutyMessage");
        String dpCharges = positions.jsonPath().getString("data[0].dpCharges");
        String dpMessage = positions.jsonPath().getString("data[0].dpMessage");
        String stt = positions.jsonPath().getString("data[0].stt");
        String sttMessage = positions.jsonPath().getString("data[0].sttMessage");
        String gst = positions.jsonPath().getString("data[0].gst");
        String gstMessage = positions.jsonPath().getString("data[0].gstMessage");
        String brokerage = positions.jsonPath().getString("data[0].brokerage");
        String brokerageMessage = positions.jsonPath().getString("data[0].brokerageMessage");
        String sebi = positions.jsonPath().getString("data[0].sebi");
        String sebiMessage = positions.jsonPath().getString("data[0].sebiMessage");



        Assertions.assertThat(transactionCharges).as("Tnansaction Charges is null for sectorHeatMap").isNotEmpty().isNotNull()
                .isNotBlank();

        Assertions.assertThat(transactionMessage).as("Tnansaction Messsage is null for sectorHeatMap").isNotEmpty().isNotNull()
                .isNotBlank().contains("% of the trade value");

        Assertions.assertThat(stampDuty).as("stampDuty is null for sectorHeatMap").isNotEmpty().isNotNull()
                .isNotBlank();
        Assertions.assertThat(stampDutyMessage).as("stampDutyMessage is null for sectorHeatMap").isNotEmpty().isNotNull()
                .isNotBlank().contains("% of the trade value");
        Assertions.assertThat(dpCharges).as("dpCharges is null for sectorHeatMap").isNotEmpty().isNotNull()
                .isNotBlank();
        Assertions.assertThat(dpMessage).as("dpMessage is null for sectorHeatMap").isNotEmpty().isNotNull()
                .isNotBlank();
        Assertions.assertThat(stt).as("stt is null for sectorHeatMap").isNotEmpty().isNotNull()
                .isNotBlank();
        Assertions.assertThat(sttMessage).as("sttMessage is null for sectorHeatMap").isNotEmpty().isNotNull()
                .isNotBlank().contains("% of the trade value");
        Assertions.assertThat(gst).as("gst is null for sectorHeatMap").isNotEmpty().isNotNull()
                .isNotBlank();
        Assertions.assertThat(gstMessage).as("gstMessage is null for sectorHeatMap").isNotEmpty().isNotNull()
                .isNotBlank().contains("% of the SEBI Tax");
        Assertions.assertThat(brokerage).as("brokerage is null for sectorHeatMap").isNotEmpty().isNotNull()
                .isNotBlank();
        Assertions.assertThat(brokerageMessage).as("brokerageMessage is null for sectorHeatMap").isNotEmpty().isNotNull()
                .isNotBlank();
        Assertions.assertThat(sebi).as("sebi is null for sectorHeatMap").isNotEmpty().isNotNull()
                .isNotBlank();
        Assertions.assertThat(sebiMessage).as("sebiMessage is null for sectorHeatMap").isNotEmpty().isNotNull()
                .isNotBlank().contains("% of the trade value");
    }


}
