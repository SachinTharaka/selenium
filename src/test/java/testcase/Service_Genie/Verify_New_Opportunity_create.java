package testcase.Service_Genie;

import common_methods.api_tokens;
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
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Verify_New_Opportunity_create extends token {
    /*SF2-458*/
    String Case_ID ="?q=SELECT CaseNumber FROM Case WHERE Id=";

    @Test
    public void Verify_New_Opportunity_create() throws IOException, InterruptedException {

        RequestSpecification httpRequest = RestAssured.given();
        httpRequest.header("Content-type", globalvariables.ContantType);
        httpRequest.header("Authorization", token_type + " " + data);
        byte[] inp = Files.readAllBytes(Paths.get(".\\src\\test\\java\\jsonfiles\\webinquiry.json"));
        String inputval = new String(inp);
        Response responsebooking =httpRequest.body(inputval).post(instance_url+"/services/data/v56.0/sobjects/Case");

        String inquiry_id =responsebooking.path("id");
        Response get_caseID =httpRequest.get(instance_url+"/services/data/v56.0/query/"+Case_ID+"'"+inquiry_id+"'");
        String body =get_caseID.getBody().prettyPrint();

        String case_id =get_caseID.path("records[0].CaseNumber");
        System.out.println(case_id);
        UAT_login();
        driver.navigate().to(globalvariables.Service_Genie);
        driver.navigate().to(globalvariables.all_opencases);
        Thread.sleep(4000);
        pageobject.common_objects.item_close_tab(driver).click();
        pageobject.common_objects.search(driver).sendKeys(case_id);
        pageobject.common_objects.search(driver).sendKeys(Keys.ENTER);
        Thread.sleep(4000);
        pageobject.common_objects.select_recored(driver).click();
        Thread.sleep(6000);
        //////////////create a opportunity//////////////////////////////
        WebElement Element = driver.findElement(By.xpath("(.//*[@class='test-id__field-label'])[9]"));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView();", Element);
        Thread.sleep(2000);

        pageobject.Service_Genie.opportunity_object.lead_source(driver).click();
        driver.findElement(By.xpath("(//span[@class='slds-truncate'][normalize-space()='Email'])[1]")).click();
        pageobject.Service_Genie.opportunity_object.sales_model(driver).click();
        driver.findElement(By.xpath("(//span[@class='slds-truncate'][normalize-space()='Direct'])[1]")).click();
        Thread.sleep(2000);
        pageobject.Service_Genie.opportunity_object.ccurrency(driver).click();
        driver.findElement(By.xpath("(//span[@class='slds-truncate'][normalize-space()='AUD - Australian Dollar'])[1]")).click();
        pageobject.Service_Genie.opportunity_object.salutation(driver).click();
        driver.findElement(By.xpath("(//span[@class='slds-truncate'][normalize-space()='Mr'])[1]")).click();
        pageobject.Service_Genie.opportunity_object.first_name(driver).click();
        pageobject.Service_Genie.opportunity_object.first_name(driver).sendKeys("sachin");
        ///////////////////////scroll the element ///////////////////////////////////////
        WebElement name = driver.findElement(By.xpath("(.//*[@class='slds-form-element__label slds-no-flex'])[2]"));
        JavascriptExecutor js_name = (JavascriptExecutor) driver;
        js_name.executeScript("arguments[0].scrollIntoView();", name);

        ///////////////////////////////////////////////////////////////////////////////////////////////////
        pageobject.Service_Genie.opportunity_object.last_name(driver).click();
        pageobject.Service_Genie.opportunity_object.last_name(driver).sendKeys("indradasa");
        pageobject.Service_Genie.opportunity_object.email(driver).click();
        pageobject.Service_Genie.opportunity_object.email(driver).sendKeys("sachin.indradasa@intrepidtravel.com");

        pageobject.Service_Genie.opportunity_object.country(driver).click();
        ////////////////////////////////////////////////////////////////
        Thread.sleep(2000);
        WebElement Elements = driver.findElement(By.xpath("(.//span[text()='Australia'])[1]"));
        JavascriptExecutor jss = (JavascriptExecutor) driver;
        jss.executeScript("arguments[0].scrollIntoView();", Elements);
        Thread.sleep(1000);
        ///////////////////////////////////////////////////////////

        driver.findElement(By.xpath("(.//span[text()='Australia'])[1]")).click();
        pageobject.Service_Genie.opportunity_object.submit_button(driver).click();
        Thread.sleep(50000);
        String Starship_booking_ID =driver.findElement(By.xpath(".//*[@class='slds-p-around_small slds-col']")).getText();
        String[] booking_number = Starship_booking_ID.split(" ");
        String id =booking_number[8];
        String final_id = "";
        final_id = id.substring(0, 7);
        System.out.println(final_id);
        //////Verify with starship side ////////////////////////////////
        RequestSpecification httpRequests = RestAssured.given();
        api_tokens opportunity_deatails = new api_tokens();
        opportunity_deatails.starship_directheader();
        httpRequests.headers(opportunity_deatails.starship_directheader());
        Response response =httpRequests.get(globalvariables.Starshiphost+"/bookings/"+final_id+"?webaccessemail=sachin.indradasa@intrepidtravel.com");
        int booking_id =response.path("id");
        String email_address =response.path("emailAddress");
        String lead_pax_name =response.path("leadPaxName");
        String starship_id=Integer.toString(booking_id);
        String salesforce_id=final_id;
        Assert.assertEquals(salesforce_id,starship_id);
        Assert.assertEquals(email_address,"sachin.indradasa@intrepidtravel.com");
        Assert.assertEquals(lead_pax_name,"sachin indradasa");
        driver.quit();

    }
}
