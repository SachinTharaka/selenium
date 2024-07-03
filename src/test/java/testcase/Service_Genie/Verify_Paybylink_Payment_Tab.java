package testcase.Service_Genie;

import common_methods.api_tokens;
import common_methods.globalvariables;
import common_methods.token;
import org.openqa.selenium.Keys;
import org.openqa.selenium.Point;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;


public class Verify_Paybylink_Payment_Tab extends token {
        /*  SF2-465*/

    @Test
    public void verify_paybylink_paymenttab() throws IOException, InterruptedException {

        api_tokens booking_details = new api_tokens();
        String booking_id=booking_details.starship_booking().get(0);
        UAT_login();
        driver.navigate().to(globalvariables.Service_Genie);
        driver.navigate().to(globalvariables.Opportunities);
        Thread.sleep(4000);
        pageobject.common_objects.item_close_tab(driver).click();
        driver.navigate().refresh();
        Thread.sleep(8000);
        pageobject.common_objects.search(driver).sendKeys("7431046");
        pageobject.common_objects.search(driver).sendKeys(Keys.ENTER);
        Thread.sleep(8000);
        pageobject.common_objects.select_recored(driver).click();
        Thread.sleep(4000);
        pageobject.Service_Genie.opportunity_object.payment_tab(driver).click();
        String paybylink =pageobject.Service_Genie.opportunity_object.payby_link_word(driver).getText();
        Assert.assertEquals(paybylink,"Pay By Link");
        ////////Assert the postion/////////////////////////////
        Point location = pageobject.Service_Genie.opportunity_object.payby_link_word(driver).getLocation();
        String paybylink_position = String.valueOf(location);

        System.out.println(location);

        boolean x = paybylink_position.matches("(.*)698(.*)");
        boolean y = paybylink_position.matches("(.*)283(.*)");
        Assert.assertTrue(x);
        Assert.assertTrue(y);
        driver.quit();


    }
}
