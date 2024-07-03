package pageobject.CCLightning_Console;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class case_object {
    private static WebElement element = null;

    public static WebElement email_tab(WebDriver driver)
    {
        element = driver.findElement(By.xpath("(.//span[@class='title'])[10]"));
        return element;
    }
    public static WebElement email_to(WebDriver driver)
    {
        element = driver.findElement(By.xpath("(.//*[@class='orderedList slds-dl_horizontal slds-m-around_none slds-scrollable_none'])[1]"));
        return element;
    }
    public static WebElement email_subject(WebDriver driver)
    {
        element = driver.findElement(By.xpath(".//*[@class='standardField input']"));
        return element;
    }
    public static WebElement submit_button(WebDriver driver)
    {
        element = driver.findElement(By.xpath("(.//*[@class=' label bBody'])[10]"));
        return element;
    }
    public static WebElement email_successful(WebDriver driver)
    {
        element = driver.findElement(By.xpath(".//*[@class='toastMessage slds-text-heading--small forceActionsText']"));
        return element;
    }

}
