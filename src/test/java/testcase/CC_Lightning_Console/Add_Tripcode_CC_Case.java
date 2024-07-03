package testcase.CC_Lightning_Console;

import common_methods.api_tokens;
import common_methods.globalvariables;
import common_methods.token;
import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.List;

public class Add_Tripcode_CC_Case extends token {
    /*SF2-512*/
    @Test
    public void add_tripcode_CC_case() throws InterruptedException, IOException {

        UAT_login();
        driver.navigate().to(globalvariables.cc_Lightning_Console);
        driver.navigate().to(globalvariables.cc_Lightning_Console_all_cases);
        Thread.sleep(6000);
        pageobject.common_objects.item_close_tab(driver).click();
        pageobject.case_object.new_button(driver).click();
        Thread.sleep(5000);
        pageobject.case_object.next_button(driver).click();
        Thread.sleep(2000);

        ////Create a new CC case ///////////////////////////////////////////////////
        WebElement Elements =pageobject.case_object.departure_code(driver);
        JavascriptExecutor departure_code = (JavascriptExecutor) driver;
        departure_code.executeScript("arguments[0].scrollIntoView();", Elements);
        pageobject.case_object.departure_code(driver).click();
        pageobject.case_object.departure_code(driver).sendKeys("TTZT181221");
        Thread.sleep(5000);
        pageobject.case_object.date(driver).sendKeys("14/12/2023");
        Thread.sleep(5000);
        pageobject.case_object.operator(driver).click();
        pageobject.case_object.operator(driver).sendKeys("Intrepid DMC Thail");
        Thread.sleep(4000);
        pageobject.case_object.operator(driver).sendKeys(Keys.ARROW_DOWN);
        pageobject.case_object.operator(driver).sendKeys(Keys.ARROW_DOWN);
        pageobject.case_object.operator(driver).sendKeys(Keys.ENTER);
        Thread.sleep(2000);
        pageobject.case_object.operator(driver).click();
        pageobject.case_object.product_manager(driver).click();
        pageobject.case_object.operator_name(driver).click();
        pageobject.case_object.save_button(driver).click();
        Thread.sleep(6000);

        ///Copy the newly created case number ///////////////////////////////
        String case_number = pageobject.case_object.case_number(driver).getText();
        System.out.println("###################################"+case_number);
        driver.navigate().to(globalvariables.cc_Lightning_Console_all_cases);
        Thread.sleep(2000);
        pageobject.common_objects.search(driver).sendKeys("01186900");
        pageobject.common_objects.search(driver).sendKeys(Keys.ENTER);
        Thread.sleep(4000);
        pageobject.common_objects.select_recored(driver).click();
        Thread.sleep(2000);


        //////////////Assert the case id with Cases by Trip table ////////////////////////////////////
        WebElement El =driver.findElement(By.xpath("((//table)[3]//tbody//tr//th)[1]"));
        JavascriptExecutor de = (JavascriptExecutor) driver;
        de.executeScript("arguments[0].scrollIntoView();",El);
        Thread.sleep(2000);
        driver.navigate().refresh();
        Thread.sleep(4000);
        String case_by_departure_case_number = pageobject.case_object.case_by_departure_case_number(driver).getText();
        Assert.assertEquals(case_by_departure_case_number,"01186899");
        driver.quit();

    }
}
