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
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import utils.ExcelDataProvider;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


public class starship_directbooking extends token {
    String customer_href;
    String dQuery="SELECT Id,DM_ID__c,BDM_Sales_Model_Name__c FROM Opportunity WHERE DM_ID__c =";
    int bookingId,customerId;
  @DataProvider(name="Dcustomer")
  public Object[][] getData(){
    String excelPath =".//src//test//java//excel//Customer.xlsx";
    Object data[][]= ExcelDataProvider.testData(excelPath,"Sheet1");
    return data;
  }

    @Test(dataProvider = "Dcustomer")
    @Severity(SeverityLevel.NORMAL)
      public void Starship_Directbooking(String Fname,String Lname,String Email) throws IOException {
          driver.quit();
          RequestSpecification httpRequest = RestAssured.given().spec(dirrequestSpec);
          JSONObject requestParams = new JSONObject();
          requestParams.put("currency", globalvariables.Currency);
          requestParams.put("sellingAgentId", globalvariables.SellingAgentId);
          requestParams.put("bookingSource", globalvariables.bookingSource);
          httpRequest.body(requestParams.toString());
          Response response=httpRequest.request(Method.POST,globalvariables.Starshiphost+"/bookings");
          String body =response.getBody().prettyPrint();
          bookingId =response.path("id");

          /////////////////////////Add customer///////////////////////////////
        HashMap<String,Object> results = new ObjectMapper().readValue(new File(".//src//test//java//jsonfiles//customer.json"),HashMap.class);
        Map<String, Object> customerData = new HashMap<String, Object>();
        customerData.put("firstName",Fname);
        customerData.put("surname",Lname);
        customerData.put("emailAddress",Email);
        Map<String, Object> combineRequest= new HashMap<String, Object>();
        combineRequest.putAll(customerData);
        combineRequest.putAll(results);
        Response addcustomer =httpRequest.body(combineRequest).post(globalvariables.Starshiphost+"/bookings/"+bookingId+"/customers");

        customer_href =addcustomer.path("href");
        customerId =addcustomer.path("id");

        String cbody =addcustomer.getBody().prettyPrint();

        ///////////////Add Tripdetails////////////////////////////////////////////
        Map<String, Object> userDetails = new HashMap<>();
        Map<String, Object> customers = new HashMap<>();
        customers.put("href","/bookings/"+bookingId+"/customers/"+customerId);
        userDetails.put("departureCode",globalvariables.departureCode);
        userDetails.put("customers", Arrays.asList(customers, customers));
        Response addtrip =httpRequest.body(userDetails).post(globalvariables.Starshiphost+"/bookings/"+bookingId+"/trips");
        String tbody =addtrip.getBody().prettyPrint();

        /////////////////Assertion//////////////////////////////////
        String bookingidpath = addtrip.path("href");
        String first_word = bookingidpath.split("/")[2];
        int i=Integer.parseInt(first_word);
        Assert.assertEquals(i,bookingId);
      //////////////////Assertion from sales force side//////////
      RequestSpecification httpRequest1 = RestAssured.given();
      httpRequest1.header("Content-type", globalvariables.ContantType);
      httpRequest1.header("Authorization", token_type + " " + data);

      Response dsalesModel = httpRequest1.get(instance_url+"/services/data/v56.0/query/?q="+dQuery+"'"+bookingId+"'");
      JsonPath insertDmId =dsalesModel.jsonPath();
      String salesmodel =insertDmId.get("records[0].BDM_Sales_Model_Name__c");

      Assert.assertEquals(salesmodel,"Direct");
   }

}
