package pageobject.Service_Genie;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class opportunity_object {
    private static WebElement element = null;

    public static WebElement itinerary_tab(WebDriver driver)
    {
        element = driver.findElement(By.xpath(".//a[text()='Itinerary']"));
        return element;
    }
    public static WebElement payment_tab(WebDriver driver)
    {
        element = driver.findElement(By.xpath(".//a[text()='Payment/Files']"));
        return element;
    }
    public static WebElement transaction_notes_tab(WebDriver driver)
    {
        element = driver.findElement(By.xpath(".//a[text()='Transaction Notes']"));
        return element;
    }
    public static WebElement passenger_details_tab(WebDriver driver)
    {
        element = driver.findElement(By.xpath(".//a[text()='Passenger Details']"));
        return element;
    }
    public static WebElement itinerary_startdate(WebDriver driver)
    {
        element = driver.findElement(By.xpath("(.//span[@class='slds-grid slds-grid_align-spread'])[6]"));
        return element;
    }
    public static WebElement itinerary_enddate(WebDriver driver)
    {
        element = driver.findElement(By.xpath("(.//span[@class='slds-grid slds-grid_align-spread'])[7]"));
        return element;
    }
    public static WebElement passenger_details_DOB(WebDriver driver)
    {
        element = driver.findElement(By.xpath("(.//span[@class='slds-grid slds-grid_align-spread'])[11]"));
        return element;
    }
    public static WebElement passenger_details_edit_button (WebDriver driver)
    {
        element = driver.findElement(By.xpath(".//button[@name='edit_action']"));
        return element;
    }
    public static WebElement passenger_details_place_issue (WebDriver driver)
    {
        element = driver.findElement(By.xpath(".//input[@name='Passport_Place_Of_Issue__c']"));
        return element;
    }
    public static WebElement passenger_details_passport_number (WebDriver driver)
    {
        element = driver.findElement(By.xpath(".//input[@name='Passport_Number__c']"));
        return element;
    }
    public static WebElement passenger_details_passport_issuedate (WebDriver driver)
    {
        element = driver.findElement(By.xpath(".//input[@name='Passport_Issue_Date__c']"));
        return element;
    }
    public static WebElement passenger_details_passport_middlename (WebDriver driver)
    {
        element = driver.findElement(By.xpath(".//*[@name='Passport_Middle_Name__c']"));
        return element;
    }
    public static WebElement passenger_details_save_button (WebDriver driver)
    {
        element = driver.findElement(By.xpath("(.//button[@class='slds-button slds-button_brand'])[2]"));
        return element;
    }
    public static WebElement payby_link_word(WebDriver driver)
    {
        element = driver.findElement(By.xpath("(.//h2[@class='slds-card__header-title'])[2]"));
        return element;
    }
   /* public static WebElement opportunity_name(WebDriver driver)
    {
        element = driver.findElement(By.xpath("(.//*[@class='slds-input'])[2]"));
        return element;
    }*/
    public static WebElement lead_source(WebDriver driver)
    {
        element = driver.findElement(By.xpath("(.//*[@class='slds-combobox__input slds-input_faux'])[1]"));
        return element;
    }
    public static WebElement salutation(WebDriver driver)
    {
        element = driver.findElement(By.xpath("(.//*[@class='slds-combobox__input slds-input_faux'])[1]"));
        return element;
    }
    public static WebElement sales_model(WebDriver driver)
    {
        element = driver.findElement(By.xpath("(.//*[@class='slds-combobox__input slds-input_faux'])[1]"));
        return element;
    }
    public static WebElement first_name(WebDriver driver)
    {
        element = driver.findElement(By.xpath("(.//*[@class='slds-form-element__control slds-grow'])[1]"));
        return element;
    }
    public static WebElement last_name(WebDriver driver)
    {
        element = driver.findElement(By.xpath("(.//*[@class='slds-form-element__control slds-grow'])[2]"));
        return element;
    }
    public static WebElement email(WebDriver driver)
    {
        element = driver.findElement(By.xpath("(.//*[@class='slds-form-element__control slds-grow'])[3]"));
        return element;
    }
    public static WebElement country(WebDriver driver)
    {
        element = driver.findElement(By.xpath("(.//*[@class='slds-combobox__input slds-input_faux'])[1]"));
        return element;
    }
    public static WebElement ccurrency(WebDriver driver)
    {
        element = driver.findElement(By.xpath("(.//*[@class='slds-combobox__input slds-input_faux'])[1]"));
        return element;
    }
    public static WebElement submit_button(WebDriver driver)
    {
        element = driver.findElement(By.xpath(".//*[@class='slds-button slds-button_brand']"));
        return element;
    }



}
