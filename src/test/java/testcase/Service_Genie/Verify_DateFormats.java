package testcase.Service_Genie;

import common_methods.api_tokens;
import common_methods.globalvariables;
import common_methods.token;
import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
import org.openqa.selenium.Keys;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class Verify_DateFormats extends token {
    /*SF2-447*/
    Date date = null;
    boolean checkformat;

    @Test
    public void date_formats() throws IOException, InterruptedException {

        RequestSpecification httpRequest = RestAssured.given();
        api_tokens header_details = new api_tokens();
        httpRequest.headers(header_details.alldata());
        String booking_id=header_details.starship_booking().get(0);
        UAT_login();
        driver.navigate().to(globalvariables.Service_Genie);
        driver.navigate().to(globalvariables.Opportunities);
        Thread.sleep(4000);
        pageobject.common_objects.item_close_tab(driver).click();

        pageobject.common_objects.search(driver).sendKeys(booking_id);
        Thread.sleep(4000);
        pageobject.common_objects.search(driver).sendKeys(Keys.ENTER);
        Thread.sleep(4000);
        pageobject.common_objects.select_recored(driver).click();
        Thread.sleep(4000);
        pageobject.Service_Genie.opportunity_object.itinerary_tab(driver).click();
        Thread.sleep(2000);
        ////////////////checking Itinerary tab date format/////////////////////////////////////////////////////
        String sdate = pageobject.Service_Genie.opportunity_object.itinerary_startdate(driver).getText();
        System.out.println(sdate);
        String edate = pageobject.Service_Genie.opportunity_object.itinerary_enddate(driver).getText();
        System.out.println(edate);

        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            dateFormat.setLenient(false);
            date = dateFormat.parse(sdate);

            checkformat = true;

        } catch (ParseException ex) {
            ex.printStackTrace();
            checkformat = false;
        }

        System.out.println(checkformat);
        Assert.assertEquals(checkformat,true);
        ////////////////////////////Passenger Details date format////////////////////////////////////
        Thread.sleep(1000);
        pageobject.Service_Genie.opportunity_object.passenger_details_tab(driver).click();
        Thread.sleep(1000);
        String passenger_details_DOB =pageobject.Service_Genie.opportunity_object.passenger_details_DOB(driver).getText();


        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            dateFormat.setLenient(false);
            date = dateFormat.parse(passenger_details_DOB);
            checkformat = true;
        } catch (ParseException ex) {

            ex.printStackTrace();
            checkformat = false;
        }

        System.out.println(checkformat);
        Assert.assertEquals(checkformat,true);
        driver.quit();



    }
}


