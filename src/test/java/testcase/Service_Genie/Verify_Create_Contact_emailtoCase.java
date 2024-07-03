package testcase.Service_Genie;

import common_methods.globalvariables;
import common_methods.token;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Verify_Create_Contact_emailtoCase extends token {
/*SF2 -567*/
    //For this testcase you have to turn on mail
    String GET_CASE_ID="?q=SELECT CaseNumber FROM Case WHERE Id=";
    @Test
    public void verify_Create_Contact_emailtoCase() throws InterruptedException {
        RequestSpecification httpRequest = RestAssured.given();
        httpRequest.header("Content-type", globalvariables.ContantType);
        httpRequest.header("Authorization", token_type + " " + data);
        Map<String, Object> email_to_case_requestParams = new HashMap<String, Object>();
        email_to_case_requestParams.put("Origin","Email");
        email_to_case_requestParams.put("Status","Open");
        email_to_case_requestParams.put("Type","Complaint");
        email_to_case_requestParams.put("Subject","Email To Case");
        email_to_case_requestParams.put("Description","Email To Case");
        email_to_case_requestParams.put("First_Name__c","sachin");
        email_to_case_requestParams.put("Last_Name__c","indradasa");
        email_to_case_requestParams.put("Enquiry_Region__c","eu");
        email_to_case_requestParams.put("CurrencyIsoCode", globalvariables.Currency);
        email_to_case_requestParams.put("RecordTypeId","0126F000001USwlQAG");
        email_to_case_requestParams.put("Case_Type__c","Complaint");
        email_to_case_requestParams.put("SuppliedEmail","sachin.indradasa@intrepaidtravel.com");

        Response affiliate_respons =httpRequest.body(email_to_case_requestParams).post(globalvariables.instance_url+"/services/data/v56.0/sobjects/Case");
        String inquiry_id =affiliate_respons.path("id");
        System.out.println(inquiry_id);

        //////////////////////get the machine date and time////////////////////////////////
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        ////////////////////send the email to ///////////////////////////////////
        RequestSpecification httpRequest_email = RestAssured.given();
        httpRequest_email.header("Content-type", globalvariables.ContantType);
        httpRequest_email.header("Authorization", token_type + " " + data);
        Map<String, Object> email_create_requestParams = new HashMap<String, Object>();
        email_create_requestParams.put("ToAddress","hello@intrepidtravel.com");
        email_create_requestParams.put("Subject",""+date);
        email_create_requestParams.put("TextBody","");
        email_create_requestParams.put("ParentId",inquiry_id);
        email_create_requestParams.put("Incoming","true");
        Response email_respons =httpRequest_email.body(email_create_requestParams).post(globalvariables.instance_url+"/services/data/v56.0/sobjects/EmailMessage");

        ////////get case id///////////////////////////////////////////////////////
        RequestSpecification get_case_id = RestAssured.given();
        get_case_id.header("Content-type", globalvariables.ContantType);
        get_case_id.header("Authorization", token_type + " " + data);

        Response case_id =get_case_id.get(globalvariables.instance_url+"/services/data/v56.0/query/"+GET_CASE_ID+"'"+inquiry_id+"'");
        String case_number =case_id.path("records[0].CaseNumber");
        //////////////////////Login to salesforce app/////////////////
        UAT_login();
        driver.navigate().to(globalvariables.cc_Lightning_Console);
        driver.navigate().to(globalvariables.cc_Lightning_Console_all_cases);
        Thread.sleep(6000);
        pageobject.common_objects.item_close_tab(driver).click();
        pageobject.common_objects.search(driver).click();
        pageobject.common_objects.search(driver).sendKeys(case_number);
        pageobject.common_objects.search(driver).sendKeys(Keys.ENTER);
        Thread.sleep(6000);
        pageobject.common_objects.select_recored(driver).click();
        Thread.sleep(6000);
        WebElement email_tab = pageobject.CCLightning_Console.case_object.email_tab(driver);
        Actions actions = new Actions(driver);
        actions.moveToElement(email_tab).click().build().perform();
        Thread.sleep(2000);
        /////////Email sending ////////////
        pageobject.CCLightning_Console.case_object.email_subject(driver).clear();
        pageobject.CCLightning_Console.case_object.email_subject(driver).sendKeys("Test Email");
        Thread.sleep(2000);
        WebElement El =driver.findElement(By.xpath(".//*[@class='standardField input']"));
        JavascriptExecutor de = (JavascriptExecutor) driver;
        de.executeScript("arguments[0].scrollIntoView();",El);
        Thread.sleep(2000);
        WebElement email_send = pageobject.CCLightning_Console.case_object.submit_button(driver);
        Actions actions_send = new Actions(driver);
        actions_send.moveToElement(email_send).click().build().perform();

        ////////////////////Verify the email successful message ////////////////////
        Thread.sleep(4000);
        String message = pageobject.CCLightning_Console.case_object.email_successful(driver).getText();
        Assert.assertEquals(message,"Email was sent.");
        driver.quit();

    }
}
