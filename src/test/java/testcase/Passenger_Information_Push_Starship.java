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
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.Test;

import javax.swing.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

import static common_methods.token.dirrequestSpec;

public class Passenger_Information_Push_Starship extends token {
    int bookingId,customerId;
    String mobile_phone,Email;
    @Test
    public void passenger_information_push_starship() throws IOException, InterruptedException {


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


        /////////////edit from the salesforce side/////////////////////////////////////
        UAT_login();
        driver.navigate().to("https://intrepidgroup--uat.sandbox.lightning.force.com/lightning/app/06m6D000000U4j0QAC");
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
        Thread.sleep(2000);
        driver.get("https://www.intrepidtravel.com.uat.internal/au/contact-us");
        Thread.sleep(2000);
        pageobject.chat_transcripts.accept_all_cookies(driver).click();
        Thread.sleep(12000);

        pageobject.chat_transcripts.chat_button(driver).click();
        Thread.sleep(14000);
        System.out.println("Open the chat bot");
        pageobject.chat_transcripts.chat_fname(driver).sendKeys("sachin");
        pageobject.chat_transcripts.chat_Lname(driver).sendKeys("Indradasa");
        pageobject.chat_transcripts.chat_email(driver).sendKeys("sachin.indradasa@intrepidtravel.com");
        Thread.sleep(4000);
        pageobject.chat_transcripts.chat_enquiry_reason(driver).click();
        pageobject.chat_transcripts.chat_select_enquiry_reason(driver).click();
        String id=String.valueOf(bookingId);
        pageobject.chat_transcripts.chat_booking_referance(driver).sendKeys(id);
        pageobject.chat_transcripts.chat_start_chatting_button(driver).click();
        Thread.sleep(10000);
        System.out.println("Submit the booking details for online agent ");
        //////////move to the agent salesforce app and accept the live chat /////////////////////////////////////
        driver.switchTo().window(tabs.get(0));
        Thread.sleep(1000);
        pageobject.chat_transcripts.accept_chat_message(driver).click();
        Thread.sleep(500);
        pageobject.chat_transcripts.chat_minimize(driver).click();
        ////////////move to the passenger details tab ////////////////////////////
        /*UAT_login();
        driver.navigate().to("https://intrepidgroup--uat.sandbox.lightning.force.com/lightning/r/LiveChatTranscript/5706D000000E1LlQAK/view?channel=LIVE_AGENT");
        Thread.sleep(6000);*/
        System.out.println("Before click opportunity tab ");
        pageobject.chat_transcripts.cloase_tab(driver).click();
        Thread.sleep(8000);
        Thread.sleep(10000);
        pageobject.chat_transcripts.chat_Opportunity_tab(driver).click();
        Thread.sleep(6000);
        List<WebElement> elements = driver.findElements(By.xpath(".//*[@class='tabHeader']"));
        System.out.println("Number of elements:" +elements.get(1));
        Thread.sleep(6000);
        elements.get(6).getClass();
        System.out.println("location:" +elements.get(6).getClass());
        System.out.println("getAccessibleName:" +elements.get(6).getAccessibleName());

        Thread.sleep(6000);
        pageobject.chat_transcripts.chat_passenger_details_tab(driver).click();
        Thread.sleep(4000);
        pageobject.chat_transcripts.chat_passenger_details_edit_button(driver).click();
        Thread.sleep(6000);
        Thread.sleep(8000);
       /* JavascriptExecutor js = (JavascriptExecutor) driver;
        WebElement Element = driver.findElement(By.xpath("//*[@id=\"combobox-button-316\"]/span"));
        js.executeScript("arguments[0].scrollIntoView();", Element);*/
        System.out.println("Enter to edit email");
        Thread.sleep(6000);
        WebElement Element = driver.findElement(By.xpath("(//*[@class='slds-form-element slds-form-element_stacked'])[7]"));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView();", Element);
        System.out.println("scroll");
        Thread.sleep(8000);
        pageobject.chat_transcripts.chat_passenger_details_edit_email(driver).click();
        pageobject.chat_transcripts.chat_passenger_details_edit_email(driver).sendKeys(Keys.CONTROL,"a");
        pageobject.chat_transcripts.chat_passenger_details_edit_email(driver).sendKeys(Keys.BACK_SPACE);
        pageobject.chat_transcripts.chat_passenger_details_edit_email(driver).sendKeys("newsachin.indradasa@intrepidtravel.com");
        pageobject.chat_transcripts.chat_passenger_details_edit_mobile(driver).click();
        pageobject.chat_transcripts.chat_passenger_details_edit_mobile(driver).sendKeys("99");
        pageobject.chat_transcripts.chat_passenger_details_edit_save_button(driver).click();
        Thread.sleep(8000);
        pageobject.chat_transcripts.cloase_tab(driver).click();
        pageobject.chat_transcripts.cloase_tab_verify(driver).click();
        driver.quit();



        ////////////Verify from the starship side ////////////////////////////////////
        RequestSpecification httpRequests = RestAssured.given().spec(dirrequestSpec);
        Response viewcustomer = httpRequests.get(globalvariables.Starshiphost+"/customers/"+customerId);
        mobile_phone =viewcustomer.path("mobilePhone");
        Assert.assertEquals(mobile_phone,"+6143548554899");
        System.out.println("Mobile number was successfully Updated");
        Email =viewcustomer.path("emailAddress");
        Assert.assertEquals(Email,"newsachin.indradasa@intrepidtravel.com");
        System.out.println("Email was successfully Updated");
    }
}
