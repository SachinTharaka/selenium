package testcase.Intrepid_Service;

import common_methods.api_tokens;
import common_methods.globalvariables;
import common_methods.token;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.Test;
import java.util.HashMap;
import java.util.Map;

public class Verify_Affiliate_Create extends token {
    String sql="?q=SELECT CaseNumber FROM Case WHERE Id=";
    String Asset_quiry ="?q=SELECT Id FROM Asset WHERE Case__c=";
    String Assert_name="?q=SELECT Name FROM Asset WHERE Id=";
@Test
    public void verify_affiliate_create() throws InterruptedException {
        RequestSpecification httpRequest = RestAssured.given();
        httpRequest.header("Content-type", globalvariables.ContantType);
        httpRequest.header("Authorization", token_type + " " + data);
        Map<String, Object> affiliate_requestParams = new HashMap<String, Object>();
        affiliate_requestParams.put("Origin","Email");
        affiliate_requestParams.put("Status","Open");
        affiliate_requestParams.put("Type","affiliate");
        affiliate_requestParams.put("Subject","affiliate_api-");
        affiliate_requestParams.put("Description","affiliate_api");
        affiliate_requestParams.put("First_Name__c","sachin");
        affiliate_requestParams.put("Last_Name__c","indradasa");
        affiliate_requestParams.put("Enquiry_Region__c","eu");
        affiliate_requestParams.put("CurrencyIsoCode", globalvariables.Currency);
        affiliate_requestParams.put("RecordTypeId","0126D000002vELEQA2");
        affiliate_requestParams.put("Case_Type__c","Complaint");
        affiliate_requestParams.put("SuppliedEmail","sachin.indradasa@intrepaidtravel.com");

         Response affiliate_respons =httpRequest.body(affiliate_requestParams).post(globalvariables.instance_url+"/services/data/v56.0/sobjects/Case");
         String id =affiliate_respons.path("id");
         Response get_affiliate_casenumber =httpRequest.get(globalvariables.instance_url+"/services/data/v56.0/query/"+sql+"'"+id+"'");
         String Case_number =get_affiliate_casenumber.path("records[0].CaseNumber");

         //////////////////////Login to salesforce app/////////////////
         UAT_login();
         driver.navigate().to(globalvariables.Intrepid_Service);
         driver.navigate().to(globalvariables.all_opencases);
         Thread.sleep(6000);
         pageobject.common_objects.item_close_tab(driver).click();
         pageobject.common_objects.search(driver).click();
         pageobject.common_objects.search(driver).sendKeys(Case_number);
         pageobject.common_objects.search(driver).sendKeys(Keys.ENTER);
         Thread.sleep(4000);
         pageobject.common_objects.select_recored(driver).click();
         Thread.sleep(10000);
         WebElement element = driver.findElement(By.xpath("(.//span[@class='title'])[4]"));
         Actions actions = new Actions(driver);
         actions.moveToElement(element).click().build().perform();
         Thread.sleep(8000);

         WebElement feed = driver.findElement(By.xpath("(.//*[@class='select'])[2]"));
         Actions feedactions = new Actions(driver);
         feedactions.moveToElement(feed).click().build().perform();
         pageobject.case_object.feed_closeEnquiry_Follow_up_method(driver).click();

         WebElement enquiry_Type = driver.findElement(By.xpath("(.//*[@class='select'])[3]"));
         Actions enquiry_actions = new Actions(driver);
         enquiry_actions.moveToElement(enquiry_Type).click().build().perform();
         pageobject.case_object.feed_closeEnquiry_enquiry_type(driver).click();

         Thread.sleep(8000);
         WebElement save_button = driver.findElement(By.xpath(".//*[@class='bottomBarRight slds-col--bump-left']"));
         Actions saveactions = new Actions(driver);
         saveactions.moveToElement(save_button).click().build().perform();
         driver.navigate().to(globalvariables.assets);
         Thread.sleep(6000);
         ////////////////////get the assert id/////////////////////////////////////////////////////////////////////
        RequestSpecification httpRequest_assert = RestAssured.given();
        httpRequest_assert.header("Content-type", globalvariables.ContantType);
        httpRequest_assert.header("Authorization", token_type + " " + data);
        Response get_assert_id = httpRequest_assert.get(globalvariables.instance_url+"/services/data/v56.0/query/"+Asset_quiry+"'"+id+"'");
        String asset_id =get_assert_id.path("records[0].Id");

       ////////////////////get the assert name/////////////////////////////////////////////////////////////////////
        RequestSpecification httpRequest_assert_name = RestAssured.given();
        httpRequest_assert_name.header("Content-type", globalvariables.ContantType);
        httpRequest_assert_name.header("Authorization", token_type + " " + data);
        Response get_assert_name =httpRequest_assert_name.get(globalvariables.instance_url+"/services/data/v56.0/query/"+Assert_name+"'"+asset_id+"'");
        String assert_name_id =get_assert_name.path("records[0].Name");

       ////////////////////Verify the close assert/////////////////////////////////////////////////////////////////////
        System.out.println("Assert Name "+assert_name_id);
        pageobject.common_objects.search(driver).sendKeys(assert_name_id);
        Thread.sleep(2000);
        pageobject.common_objects.search(driver).sendKeys(Keys.ENTER);
        Thread.sleep(6000);
        pageobject.common_objects.select_recored(driver).click();
        Thread.sleep(6000);
        driver.quit();

}
}
