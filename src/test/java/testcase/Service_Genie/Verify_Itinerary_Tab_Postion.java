package testcase.Service_Genie;

import common_methods.api_tokens;
import common_methods.globalvariables;
import common_methods.token;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Verify_Itinerary_Tab_Postion  extends token {

    @Test
    public void verify_itinerary_tab_postion() throws IOException, InterruptedException {

        api_tokens booking_details = new api_tokens();
        String booking_id=booking_details.starship_booking().get(0);
        UAT_login();
        driver.navigate().to(globalvariables.Service_Genie);
        driver.navigate().to(globalvariables.Opportunities);
        Thread.sleep(4000);
        pageobject.common_objects.item_close_tab(driver).click();

        pageobject.common_objects.search(driver).sendKeys(booking_id);
        pageobject.common_objects.search(driver).sendKeys(Keys.ENTER);
        Thread.sleep(4000);
        pageobject.common_objects.select_recored(driver).click();
        Thread.sleep(4000);
        String headers = driver.findElement(By.xpath("(.//*[@role='tablist'])[3]")).getText();
        String lines[] = headers.split("\\r?\\n");


       ///Assertion for get the correct position in itinerary ////////
        Assert.assertEquals(lines[0],"Itinerary");
        driver.quit();



    }
}
