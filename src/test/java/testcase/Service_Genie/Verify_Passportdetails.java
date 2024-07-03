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
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.List;

public class Verify_Passportdetails extends token {

    @Test
    public void passport_details() throws IOException,InterruptedException {


        api_tokens booking_details = new api_tokens();
        List<String> booking_info =booking_details.starship_booking();

        String booking_id=booking_info.get(0);
        String customer_id=booking_info.get(1);

        UAT_login();
        driver.navigate().to(globalvariables.Service_Genie);
        driver.navigate().to(globalvariables.Opportunities);
        Thread.sleep(6000);
        pageobject.common_objects.item_close_tab(driver).click();

        Thread.sleep(6000);
        pageobject.common_objects.search(driver).sendKeys(booking_id);
        Thread.sleep(6000);
        pageobject.common_objects.search(driver).sendKeys(Keys.ENTER);
        Thread.sleep(6000);
        pageobject.common_objects.select_recored(driver).click();
        Thread.sleep(5000);
        pageobject.Service_Genie.opportunity_object.passenger_details_tab(driver).click();
        Thread.sleep(1000);
        pageobject.Service_Genie.opportunity_object.passenger_details_edit_button(driver).click();
        Thread.sleep(4000);
        WebElement Element = driver.findElement(By.xpath("(.//*[@class='slds-form-element__label slds-no-flex'])[12]"));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView();", Element);
        Thread.sleep(2000);
        pageobject.Service_Genie.opportunity_object.passenger_details_place_issue(driver).clear();
        pageobject.Service_Genie.opportunity_object.passenger_details_place_issue(driver).sendKeys("Srilanka");

        pageobject.Service_Genie.opportunity_object.passenger_details_passport_number(driver).clear();
        pageobject.Service_Genie.opportunity_object.passenger_details_passport_number(driver).sendKeys("N89171999");

        pageobject.Service_Genie.opportunity_object.passenger_details_passport_issuedate(driver).clear();
        pageobject.Service_Genie.opportunity_object.passenger_details_passport_issuedate(driver).sendKeys("1/1/2023");

        //pageobject.Service_Genie.opportunity_object.passenger_details_passport_middlename(driver).clear();
        pageobject.Service_Genie.opportunity_object.passenger_details_save_button(driver).click();
        driver.quit();


        //////////////////verify the edit details in starship side///////////////////////////////////////////

        RequestSpecification httpRequest_starship = RestAssured.given();
        api_tokens passport_details = new api_tokens();
        httpRequest_starship.headers(passport_details.starship_directheader());
        Response response =httpRequest_starship.get(globalvariables.Starshiphost+"/customers/"+customer_id);
        String body =response.getBody().prettyPrint();
        String passport_number =response.path("passport.number");
        Assert.assertEquals("N89171999",passport_number);
        String place_of_issue =response.path("passport.placeOfIssue");
        Assert.assertEquals("Srilanka",place_of_issue);
        String issue_date =response.path("passport.dateOfIssue");
        Assert.assertEquals("2023-01-01",issue_date);
        String middle_name =response.path("middleName");
        Assert.assertEquals("Tharaka",middle_name);


    }
}
