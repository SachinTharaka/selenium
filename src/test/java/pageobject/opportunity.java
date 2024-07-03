package pageobject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class opportunity {
    private static WebElement element = null;
    public static WebElement search(WebDriver driver)
    {
        element = driver.findElement(By.xpath("(.//*[@type='search'])[2]"));
        return element;
    }
    public static WebElement drop_down(WebDriver driver)
    {
        element = driver.findElement(By.xpath("//*[@id=\"37802:0\"]/div"));
        return element;
    }
    public static WebElement title(WebDriver driver)
    {
        element = driver.findElement(By.xpath(".//*[@title='Intrepid Service']"));
        return element;
    }
    public static WebElement select_recored(WebDriver driver)
    {
        element = driver.findElement(By.xpath("//*[@data-refid=\"recordId\"]"));
        return element;
    }
    public static WebElement select_note(WebDriver driver)
    {
        element = driver.findElement(By.xpath(".//ul[@class='slds-tabs_default__nav']/li[3]/a[@aria-controls='tab-8']"));
        return element;
    }
    public static WebElement note_text(WebDriver driver)
    {
        element = driver.findElement(By.xpath(".//*[text()='Allergic_to_seafood']"));
        return element;
    }

}
