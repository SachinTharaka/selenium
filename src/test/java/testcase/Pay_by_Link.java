package testcase;

import common_methods.globalvariables;
import common_methods.token;
import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Pay_by_Link extends token {
    int bookingId,customerId;
    @Test
    public void pay_by_link() throws IOException, InterruptedException {
        RequestSpecification httpRequest = RestAssured.given().spec(dirrequestSpec);
        JSONObject requestParams = new JSONObject();
        requestParams.put("currency", globalvariables.Currency);
        requestParams.put("sellingAgentId", globalvariables.SellingAgentId);
        requestParams.put("bookingSource", globalvariables.bookingSource);
        httpRequest.body(requestParams.toString());
        Response response = httpRequest.request(Method.POST, globalvariables.Starshiphost + "/bookings");
        bookingId = response.path("id");
        byte[] inp = Files.readAllBytes(Paths.get(".\\src\\test\\java\\jsonfiles\\common_customer.json"));
        String inputval = new String(inp);
        Response addcustomer = httpRequest.body(inputval).post(globalvariables.Starshiphost + "/bookings/" + bookingId + "/customers");
        customerId = addcustomer.path("id");
        System.out.println(bookingId);

        ///////////////Add Tripdetails////////////////////////////////////////////
        Map<String, Object> userDetails = new HashMap<>();
        Map<String, Object> customers = new HashMap<>();
        customers.put("href", "/bookings/" + bookingId + "/customers/" + customerId);
        userDetails.put("departureCode", globalvariables.departureCode);
        userDetails.put("customers", Arrays.asList(customers, customers));
        Response addtrip = httpRequest.body(userDetails).post(globalvariables.Starshiphost + "/bookings/" + bookingId + "/trips");
        String tbody = addtrip.getBody().prettyPrint();

        ///////// Redirect to the chat page//////////////////////////////////////
        UAT_login();
        driver.navigate().to("https://intrepidgroup--uat.sandbox.lightning.force.com/lightning/app/06m6D000000U4j0QAC");
        Thread.sleep(6000);
        pageobject.chat_transcripts.cloase_tab(driver).click();
        Thread.sleep(6000);
        pageobject.chat_transcripts.omnichannel(driver).click();
        Thread.sleep(4000);
        pageobject.chat_transcripts.omnichannel_online(driver).click();
        Thread.sleep(4000);
        pageobject.chat_transcripts.select_avalable(driver).click();
        Thread.sleep(4000);
        System.out.println("Open new tab for customer chat");

        /////////////Open the new tab for submit the booking details///////////////////////////

        ((JavascriptExecutor)driver).executeScript("window.open()");
        ArrayList<String> tabs= new ArrayList<>(driver.getWindowHandles());
        driver.switchTo().window(tabs.get(1));
        driver.get("https://www.intrepidtravel.com.uat.internal/au/contact-us");
        pageobject.chat_transcripts.accept_all_cookies(driver).click();
        Thread.sleep(12000);

        ///////////////////////Open the chat bot for /////////////////////////
        pageobject.chat_transcripts.chat_button(driver).click();
        Thread.sleep(14000);
        System.out.println("Open the chat bot");
        pageobject.chat_transcripts.chat_fname(driver).sendKeys("sachin");
        pageobject.chat_transcripts.chat_Lname(driver).sendKeys("Indradasa");
        pageobject.chat_transcripts.chat_email(driver).sendKeys("sachin.indradasa@intrepidtravel.com");
        Thread.sleep(4000);
        pageobject.chat_transcripts.chat_enquiry_reason(driver).click();
        pageobject.chat_transcripts.chat_select_enquiry_reason(driver).click();
        String customer_booking_id=Integer.toString(bookingId);
        pageobject.chat_transcripts.chat_booking_referance(driver).sendKeys("7316811");
        pageobject.chat_transcripts.chat_start_chatting_button(driver).click();
        Thread.sleep(10000);
        /////////////////////////Move to gent window tab //////////////////////////////////////
        driver.switchTo().window(tabs.get(0));
        Thread.sleep(500);
        pageobject.chat_transcripts.accept_chat_message(driver).click();
        Thread.sleep(500);
        pageobject.chat_transcripts.chat_minimize(driver).click();
        Thread.sleep(6000);
/*
         UAT_login();
        driver.navigate().to("https://intrepidgroup--uat.sandbox.lightning.force.com/lightning/r/LiveChatTranscript/5706D000000E1LlQAK/view?channel=LIVE_AGENT");
        Thread.sleep(6000);*/
        Thread.sleep(6000);
       Thread.sleep(6000);

        System.out.println("Before click opportunity tab ");
        pageobject.chat_transcripts.chat_Opportunity_tab(driver).click();
        System.out.println("After click opportunity tab ");
        Thread.sleep(4000);
    ////////////////Click the Payment Tab//////////////////////////
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(".//*[@data-tab-name='customTab4']")));
        pageobject.chat_transcripts.chat_payment(driver).click();
        Thread.sleep(2000);
        WebElement Element = driver.findElement(By.xpath(".//*[@class='slds-card__header-title']"));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView();", Element);
        Thread.sleep(2000);
        pageobject.chat_transcripts.payment_generate_link_amount(driver).click();
        pageobject.chat_transcripts.payment_generate_link_amount(driver).sendKeys("10");
        pageobject.chat_transcripts.payment_generate_link_button(driver).click();



        ////Assert verification//////////////////////////////

        Thread.sleep(6000);
        String Success_message = pageobject.chat_transcripts.payment_generate_link_success_message(driver).getText();
        Assert.assertEquals(Success_message,"Success");
        driver.findElement(By.xpath(".//*[@class='slds-icon-utility-close slds-icon_container']")).click();

       /* Thread.sleep(8000);
        pageobject.chat_transcripts.cloase_tab(driver).click();
        pageobject.chat_transcripts.cloase_tab_verify(driver).click();*/

        driver.quit();



    }
}
