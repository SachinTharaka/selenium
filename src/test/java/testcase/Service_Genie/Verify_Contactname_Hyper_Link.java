package testcase.Service_Genie;

import common_methods.api_tokens;
import common_methods.globalvariables;
import common_methods.token;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

public class Verify_Contactname_Hyper_Link  extends token {

    @Test
    public void Verify_Contactname_Hyper_Link() throws IOException, InterruptedException {
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
        Thread.sleep(6000);
        WebElement contact_name = driver.findElement(By.xpath("(.//div[@class='slds-form-element__control slds-grid itemBody'])[8]"));
        Actions saveactions = new Actions(driver);
        saveactions.moveToElement(contact_name).click().build().perform();
        Thread.sleep(4000);
        /////////Assert the contact page element////////////////////////////////////

        String email = driver.findElement(By.xpath("(.//*[@class='slds-show_inline'])[1]")).getText();
        Assert.assertEquals(email,"sachin.indradasa@intrepidtravel.com");

        String mobile_number =driver.findElement(By.xpath("(//a[contains(text(),'+61435485548')])[1]")).getText();
        System.out.println(mobile_number);
        Assert.assertEquals(mobile_number,"+61435485548");

        String name =driver.findElement(By.xpath("(.//*[text()='Mr sachin Tharaka testing indradasa'])[2]")).getText();
        Assert.assertEquals(name,"Mr sachin Tharaka testing indradasa");

        driver.quit();

    }

}
