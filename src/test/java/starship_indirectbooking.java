import com.fasterxml.jackson.databind.ObjectMapper;
import common_methods.globalvariables;
import common_methods.token;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.*;
import utils.ExcelDataProvider;


import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class starship_indirectbooking extends token {
    int indirect_bookingId,indirect_customerId;
    String query="SELECT Id,DM_ID__c,BDM_Sales_Model_Name__c FROM Opportunity WHERE DM_ID__c =";

    @DataProvider(name="customer")
    public Object[][] getData(){
        String excelPath =".//src//test//java//excel//Customer.xlsx";
        Object data[][]= ExcelDataProvider.testData(excelPath,"Sheet1");
        return data;
    }

    @Test(dataProvider = "customer")
    @Severity(SeverityLevel.CRITICAL)

    public void Starship_Indirectbooking(String Fname,String Lname,String Email) throws IOException {
        driver.quit();
        RequestSpecification httpRequest = RestAssured.given().spec(indirrequestSpec);
        JSONObject requestParams = new JSONObject();
        requestParams.put("currency", globalvariables.Currency);
        requestParams.put("agentReferenceNumber",globalvariables.AgentReferenceNumber);
        requestParams.put("bookingNote", "This is a booking note");
        requestParams.put("storeAgentCode",globalvariables.XApiAgentId);
        requestParams.put("bookingMethod","Email");
        requestParams.put("bookingSource",globalvariables.bookingSource);
        httpRequest.body(requestParams.toString());
        Response response=httpRequest.request(Method.POST,globalvariables.Starshiphost+"/bookings");
        String body =response.getBody().prettyPrint();
        indirect_bookingId =response.path("id");

        //////////////////Add customer//////////////////////////////////////////////////////
        HashMap<String,Object> results = new ObjectMapper().readValue(new File(".//src//test//java//jsonfiles//customer.json"),HashMap.class);
        Map<String, Object> customerData = new HashMap<String, Object>();
        customerData.put("firstName",Fname);
        customerData.put("surname",Lname);
        customerData.put("emailAddress",Email);
        Map<String, Object> combineRequest= new HashMap<String, Object>();
        combineRequest.putAll(customerData);
        combineRequest.putAll(results);
        Response addcustomer =httpRequest.body(combineRequest).post(globalvariables.Starshiphost+"/bookings/"+indirect_bookingId+"/customers");
        String cbody =addcustomer.getBody().prettyPrint();
        indirect_customerId=addcustomer.path("id");

        /////////////////Add trpdetails//////////////////////////////////////////////////////
        Map<String, Object> userDetails = new HashMap<>();
        Map<String, Object> customers = new HashMap<>();
        customers.put("href","/bookings/"+indirect_bookingId+"/customers/"+indirect_customerId);
        userDetails.put("departureCode",globalvariables.departureCode);
        userDetails.put("customers", Arrays.asList(customers, customers));
        Response addtrip =httpRequest.body(userDetails).post(globalvariables.Starshiphost+"/bookings/"+indirect_bookingId+"/trips");
        String tbody =addtrip.getBody().prettyPrint();

        /////////////////Assertion//////////////////////////////////
        String bookingidpath = addtrip.path("href");
        String CompletebookingID = bookingidpath.split("/")[2];
        int b=Integer.parseInt(CompletebookingID);
        Assert.assertEquals(b,indirect_bookingId);

        //////////////////Assertion from sales force side//////////
         /*RequestSpecification httpRequest1 = RestAssured.given();
        httpRequest1.header("Content-type", globalvariables.ContantType);
        httpRequest1.header("Authorization", token_type + " " + data);

       Response salesModel = httpRequest1.get(instance_url+"/services/data/v56.0/query/?q="+query+"'"+indirect_bookingId+"'");

        JsonPath insertDmId =salesModel.jsonPath();
        String salesmodel =insertDmId.get("records[0].BDM_Sales_Model_Name__c");
        Assert.assertEquals(salesmodel,"Indirect");*/

    }
}
