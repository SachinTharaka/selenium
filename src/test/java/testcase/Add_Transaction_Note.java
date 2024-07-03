package testcase;


import common_methods.globalvariables;
import common_methods.token;
import io.restassured.RestAssured;
import io.restassured.http.Method;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONObject;
import org.openqa.selenium.Keys;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;


@Test
public class Add_Transaction_Note extends token {
    int bookingId,customerId;
    String tQuery="SELECT Id,CreatedDate,Note_Type_ID__c,Note__c FROM SS_Note__c WHERE Opportunity__r.DM_ID__c =";
    public void add_transaction_note() throws IOException, InterruptedException {
        RequestSpecification httpRequest = RestAssured.given().spec(dirrequestSpec);
        JSONObject requestParams = new JSONObject();
        requestParams.put("currency", globalvariables.Currency);
        requestParams.put("sellingAgentId", globalvariables.SellingAgentId);
        requestParams.put("bookingSource", globalvariables.bookingSource);
        httpRequest.body(requestParams.toString());
        Response response=httpRequest.request(Method.POST,globalvariables.Starshiphost+"/bookings");
        bookingId =response.path("id");
        byte[] inp = Files.readAllBytes(Paths.get(".\\src\\test\\java\\jsonfiles\\common_customer.json"));
        String inputval = new String(inp);
        Response addcustomer =httpRequest.body(inputval).post(globalvariables.Starshiphost+"/bookings/"+bookingId+"/customers");
        customerId =addcustomer.path("id");
        /////////////////refresh the sales force page//////////////////////////////////
        UAT_login();
        driver.navigate().to("https://intrepidgroup--uat.sandbox.lightning.force.com/lightning/o/Opportunity/list?filterName=00B6D000005vJ2KUAU");
        Thread.sleep(4000);
        pageobject.opportunity.search(driver).click();
        //driver.manage().timeouts().implicitlyWait(1000, TimeUnit.SECONDS);
        Thread.sleep(5000);
        String bookingnumber=Integer.toString(bookingId);
        pageobject.opportunity.search(driver).sendKeys(bookingnumber);
        pageobject.opportunity.search(driver).sendKeys(Keys.ENTER);
        Thread.sleep(4000);
        pageobject.opportunity.title(driver).click();
        Thread.sleep(8000);
        pageobject.opportunity.select_recored(driver).click();
        Thread.sleep(12000);
        pageobject.opportunity.title(driver).click();
        Thread.sleep(6000);
        pageobject.opportunity.select_note(driver).click();
        Thread.sleep(4000);

        ////////////////////////verify the transaction_note from salesforce side/////////////////////////////////////////

        String expected =pageobject.opportunity.note_text(driver).getText();
        Assert.assertEquals(expected,"Allergic_to_seafood");
        driver.quit();
 }
}
