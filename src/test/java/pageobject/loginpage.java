package pageobject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class loginpage {
    private static WebElement element = null;

    public static WebElement username(WebDriver driver)
    {
        element = driver.findElement(By.xpath(".//*[@id='username']"));
        return element;
    }
    public static WebElement password(WebDriver driver)
    {
        element = driver.findElement(By.xpath(".//*[@id='password']"));
        return element;
    }
    public static WebElement login_button(WebDriver driver)
    {
        element = driver.findElement(By.xpath(".//*[@id='Login']"));
        return element;
    }
}
