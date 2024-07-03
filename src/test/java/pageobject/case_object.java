package pageobject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class case_object {
    private static WebElement element = null;

    public static WebElement more_tab(WebDriver driver)
    {
        element = driver.findElement(By.xpath("//*[@id=\"tab-1\"]/slot/flexipage-component2/slot/flexipage-aura-wrapper/div/div/div[1]/div/ul/li[3]/a/span[2]"));
        return element;
    }
    public static WebElement CloseEnquiry_tab(WebDriver driver)
    {
        element = driver.findElement(By.xpath("(.//li[@class='uiMenuItem'])[1]"));
        return element;
    }
    public static WebElement feed_closeEnquiry_Follow_up_method(WebDriver driver)
    {
        element = driver.findElement(By.xpath("(.//*[@class='uiMenuItem uiRadioMenuItem'])[3]"));
        return element;
    }
    public static WebElement feed_closeEnquiry_enquiry_type(WebDriver driver)
    {
        element = driver.findElement(By.xpath(".//*[@title='Internal']"));
        return element;
    }
    public static WebElement assets_case_id(WebDriver driver)
    {
        element = driver.findElement(By.xpath("//*[@slot='primaryField']"));
        return element;
    }
    public static WebElement new_button(WebDriver driver)
    {
        element = driver.findElement(By.xpath("(.//*[@class='forceActionLink'])[1]"));
        return element;
    }
    public static WebElement next_button(WebDriver driver)
    {
        element = driver.findElement(By.xpath(".//span[text()='Next']"));
        return element;
    }
    public static WebElement departure_code(WebDriver driver)
    {
        element = driver.findElement(By.xpath("(.//*[@class='slds-form-element__control slds-grow'])[2]"));
        return element;
    }
    public static WebElement date(WebDriver driver)
    {
        element = driver.findElement(By.xpath("(.//*[@class='slds-input'])[4]"));
        return element;
    }
    public static WebElement operator(WebDriver driver)
    {
        element = driver.findElement(By.xpath("(.//*[@class='slds-combobox_container'])[13]"));
        return element;
    }
    public static WebElement operator_name(WebDriver driver)
    {
        element = driver.findElement(By.xpath("(//span[@class='slds-truncate'][normalize-space()='Doru Raduta'])[1]"));
        return element;
    }
    public static WebElement product_manager(WebDriver driver)
    {
        element = driver.findElement(By.xpath("(.//*[@class='slds-combobox_container'])[17]"));
        return element;
    }
    public static WebElement save_button(WebDriver driver)
    {
        element = driver.findElement(By.xpath(".//*[@class='slds-button slds-button_brand']"));
        return element;
    }
    public static WebElement case_number(WebDriver driver)
    {
        element = driver.findElement(By.xpath("(//p[@class='fieldComponent slds-text-body--regular slds-show_inline-block slds-truncate'])[2]"));
        return element;
    }
    public static WebElement case_by_departure_case_number(WebDriver driver)
    {
        element = driver.findElement(By.xpath("((//table)[3]//tbody//tr//th)[1]"));
        return element;
        //(.//*[@class='' and 'slds-grid slds-grid_align-spread'])[3]
        //(.//*[@class='slds-grid slds-grid_align-spread'])[1]
    }
}
