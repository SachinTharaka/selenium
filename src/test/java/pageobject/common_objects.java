package pageobject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class common_objects {
    private static WebElement element = null;

    public static WebElement search(WebDriver driver)
    {
        element = driver.findElement(By.xpath(".//*[@class='slds-input']"));
        return element;
    }
    public static WebElement select_recored(WebDriver driver)
    {
        element = driver.findElement(By.xpath("//*[@data-refid=\"recordId\"]"));
        return element;
    }
    public static WebElement item_close_tab (WebDriver driver)
    {
        element = driver.findElement(By.xpath("(.//*[@class='slds-button__icon'])[4]"));
        return element;
    }
}
