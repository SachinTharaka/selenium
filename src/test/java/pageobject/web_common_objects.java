package pageobject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class web_common_objects {
    private static WebElement element = null;
    public static WebElement any_where(WebDriver driver)
    {
        element = driver.findElement(By.xpath(".//*[@placeholder='Anywhere']"));
        return element;
    }
    public static WebElement search(WebDriver driver)
    {
        element = driver.findElement(By.xpath(".//*[@aria-label='search']"));
        return element;
    }
    public static WebElement wishlist(WebDriver driver)
    {
        element = driver.findElement(By.xpath(".//*[@class='shortlist-count__word']"));
        return element;
    }
    public static WebElement login_wishlist(WebDriver driver)
    {
        element = driver.findElement(By.xpath("(.//*[@class='btn btn-action btn-block'])[1]"));
        return element;
    }
    public static WebElement wishlist_email(WebDriver driver)
    {
        element = driver.findElement(By.xpath(".//*[@name='email']"));
        return element;
    }
    public static WebElement wishlist_password(WebDriver driver)
    {
        element = driver.findElement(By.xpath(".//*[@name='password']"));
        return element;
    }
    public static WebElement login_to_wishlist(WebDriver driver)
    {
        element = driver.findElement(By.xpath(".//*[@type='submit']"));
        return element;
    }
    public static WebElement adding_wishlist_trip(WebDriver driver)
    {
        element = driver.findElement(By.xpath("(.//*[@class='shortlist-button'])[1]"));
        return element;
    }
    public static WebElement get_trip_name(WebDriver driver)
    {
        element = driver.findElement(By.xpath("(//div[@data-cy='card-heading'])[1]"));
        return element;
    }
    public static WebElement manage_my_wishlist(WebDriver driver)
    {
        element = driver.findElement(By.xpath("(.//*[@type='button'])[1]"));
        return element;
    }
    public static WebElement removed_all_trip(WebDriver driver)
    {
        element = driver.findElement(By.xpath("(.//*[@class='login-toggle__content-link'])[1]"));
        return element;
    }
    public static WebElement removed_wishlist(WebDriver driver)
    {
        element = driver.findElement(By.xpath("(.//*[@class='ng-isolate-scope'])[1]"));
        return element;
    }

}
