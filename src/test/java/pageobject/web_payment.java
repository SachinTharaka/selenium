package pageobject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class web_payment {

    private static WebElement element = null;

    public static WebElement email(WebDriver driver)
    {
        element = driver.findElement(By.xpath(".//*[@id='emailAddress']"));
        return element;
    }
    public static WebElement booking_number(WebDriver driver)
    {
        element = driver.findElement(By.xpath(".//*[@id='bookingNumber']"));
        return element;
    }
    public static WebElement search_booking(WebDriver driver)
    {
        element = driver.findElement(By.xpath(".//*[@type='submit']"));
        return element;
    }
    public static WebElement make_payment(WebDriver driver)
    {
        element = driver.findElement(By.xpath(".//*[@class='button button--primary']"));
        return element;
    }
    public static WebElement card_holder(WebDriver driver)
    {
        element = driver.findElement(By.xpath(".//*[@name='transactionForm[cardHolderName]']"));
        return element;
    }
    public static WebElement card_number(WebDriver driver)
    {
        element = driver.findElement(By.xpath(".//*[@id='encryptedCardNumber']"));
        return element;
    }
    public static WebElement expire_date(WebDriver driver)
    {
        element = driver.findElement(By.xpath(".//*[@id='encryptedExpiryDate']"));
        return element;
    }
    public static WebElement  cvv(WebDriver driver)
    {
        element = driver.findElement(By.xpath(".//*[@id='encryptedSecurityCode']"));
        return element;
    }
    public static WebElement Continue_button(WebDriver driver)
    {
        element = driver.findElement(By.xpath("(.//*[@type='submit'])[1]"));
        return element;
    }

}
