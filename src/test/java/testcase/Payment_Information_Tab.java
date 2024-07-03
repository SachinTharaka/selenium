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
import pageobject.web_payment;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;


public class Payment_Information_Tab extends token {
    int bookingId,customerId;

    @Test
    public void payment_information_tab() throws IOException, InterruptedException {
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
        customers.put("href","/bookings/"+bookingId+"/customers/"+customerId);
        userDetails.put("departureCode",globalvariables.departureCode);
        userDetails.put("customers", Arrays.asList(customers, customers));
        Response addtrip =httpRequest.body(userDetails).post(globalvariables.Starshiphost+"/bookings/"+bookingId+"/trips");
        String tbody =addtrip.getBody().prettyPrint();
        String Tripbooking_Id=Integer.toString(bookingId);

        /////////////////////////////Search payment for booking in UAT web///////////////////////////////////
        driver.navigate().to("https://bookings.intrepidtravel.com.uat.internal/bookings/en_US/booking_engine/viewBooking/");
        //pageobject.chat_transcripts.advance_button(driver).click();
        //pageobject.chat_transcripts.Proceed_link(driver).click();
        web_payment.email(driver).sendKeys("sachin.indradasa@intrepidtravel.com");
        web_payment.booking_number(driver).sendKeys(Tripbooking_Id);
        web_payment.search_booking(driver).click();
        Thread.sleep(16000);

        //////////////Adding card details for Payment ////////////////////////////////////
        //driver.navigate().to("https://bookings.intrepidtravel.com.uat.internal/bookings/en_US/booking_engine/viewBooking/email/sachin.indradasa@intrepidtravel.com/txnId/"+bookingId+"#payment");
        web_payment.make_payment(driver).click();
        web_payment.card_holder(driver).sendKeys("Sachin");
        int countIframesInFrame1 =driver. findElements(By. tagName("iframe")). size();
        System.out.println("Number of Frames inside the Frame1:"+countIframesInFrame1);
        driver.switchTo().frame(0);
        web_payment.card_number(driver).sendKeys("370000000000002");
        Thread.sleep(4000);
        driver.switchTo().defaultContent();

       driver.switchTo().frame(driver.findElement(By.xpath("//*[@id='postbooking-payment']/div[3]/div[1]/span[1]/iframe")));
        pageobject.web_payment.expire_date(driver).sendKeys("03/30");
        driver.switchTo().defaultContent();
        driver.switchTo().frame(driver.findElement(By.xpath("//*[@id='postbooking-payment']/div[3]/div[2]/span[1]/iframe")));
       pageobject.web_payment.cvv(driver).sendKeys("7373");
        driver.switchTo().defaultContent();
        pageobject.web_payment.Continue_button(driver).click();
        Thread.sleep(6000);
        System.out.println("Web Payment Successfully Done");
        ////////////////////////////////open salesforce omni channel///////////////////////

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

        ((JavascriptExecutor)driver).executeScript("window.open()");
        ArrayList<String> tabs= new ArrayList<>(driver.getWindowHandles());
        driver.switchTo().window(tabs.get(1));
        driver.get("https://www.intrepidtravel.com.uat.internal/au/contact-us");
        driver.get("https://www.intrepidtravel.com.uat.internal/au/contact-us");
        pageobject.chat_transcripts.accept_all_cookies(driver).click();
        Thread.sleep(12000);

        //////////////////////////////enter to the chat bot ///////////////////////////////////////////////
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
        pageobject.chat_transcripts.chat_booking_referance(driver).sendKeys(customer_booking_id);
        pageobject.chat_transcripts.chat_start_chatting_button(driver).click();
        Thread.sleep(10000);
        System.out.println("Submit the booking details for online agent ");
        driver.switchTo().window(tabs.get(0));
        Thread.sleep(500);
        pageobject.chat_transcripts.accept_chat_message(driver).click();
        Thread.sleep(500);
        pageobject.chat_transcripts.chat_minimize(driver).click();
        Thread.sleep(6000);

        System.out.println("Before click opportunity tab ");
        pageobject.chat_transcripts.chat_Opportunity_tab(driver).click();
        System.out.println("After click opportunity tab ");
        Thread.sleep(4000);
        WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(30));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(".//*[@data-tab-name='customTab4']")));
        System.out.println("#################################");
        pageobject.chat_transcripts.chat_payment(driver).click();
        Thread.sleep(2000);
        pageobject.chat_transcripts.chat_invoice_action(driver).click();
        pageobject.chat_transcripts.chat_generate_invoice(driver).click();
        Thread.sleep(2000);
        pageobject.chat_transcripts.chat_ok_button(driver).click();
        //driver.navigate().refresh();
        //Thread.sleep(10000);
       WebDriverWait message = new WebDriverWait(driver,Duration.ofSeconds(30));
       message.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(".//*[@class='toastTitle slds-text-heading--small']")));
       String Success_message = pageobject.chat_transcripts.payment_generate_link_success_message(driver).getText();
       Assert.assertEquals(Success_message,"Success");

        ///////////////click the payment tab////////////////////////////////////////////
      /*  pageobject.chat_transcripts.chat_Opportunity_tab(driver).click();
        System.out.println("After click opportunity tab ");
        Thread.sleep(4000);
        WebDriverWait click_payment = new WebDriverWait(driver,Duration.ofSeconds(30));
        click_payment.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(".//*[@data-tab-name='customTab4']")));
        System.out.println("#################################");
        pageobject.chat_transcripts.chat_payment(driver).click();*/

        /////////////////////verify the documents//////////////////////////////////////////
        /*WebElement Element = driver.findElement(By.xpath(".//*[@title='Files for Parent Opportunity']"));
        JavascriptExecutor js = (JavascriptExecutor) driver;*/
        ////////This will scroll the page Horizontally till the element is found////////////////
       /* js.executeScript("arguments[0].scrollIntoView();", Element);
        Thread.sleep(10000);
        String owner = pageobject.chat_transcripts.chat_doc_owner(driver).getText();
        Assert.assertEquals("Sachin Indradasa",owner);*/
        driver.quit();

    }
}
