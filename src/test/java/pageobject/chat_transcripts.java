package pageobject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class chat_transcripts {
    private static WebElement element = null;
    public static WebElement omnichannel(WebDriver driver)
    {
        element = driver.findElement(By.xpath("/html/body/div[4]/div[1]/section/div[2]/div[1]/div[4]/div[2]/div/div[2]/div[1]/div/button/lightning-primitive-icon"));
        return element;
    }
    public static WebElement omnichannel_online(WebDriver driver)
    {
        element = driver.findElement(By.xpath("/html[1]/body[1]/div[4]/div[1]/section[1]/div[2]/div[1]/div[4]/div[1]/div[1]/div[2]/div[1]/div[1]/button[1]/lightning-primitive-icon[1]/*[name()='svg'][1]"));
        return element;
    }
    public static WebElement select_avalable (WebDriver driver)
    {
        element = driver.findElement(By.xpath("//span[text()='Available']"));
        return element;
    }
    public static WebElement advance_button (WebDriver driver)
    {
        element = driver.findElement(By.xpath("/html/body/div/div[2]/button[3]"));
        return element;
    }
    public static WebElement Proceed_link (WebDriver driver)
    {
        element = driver.findElement(By.xpath(".//*[@id='proceed-link']"));
        return element;
    }
    public static WebElement chat_button (WebDriver driver)
    {
        element = driver.findElement(By.xpath("//span[normalize-space()='Live chat us now']"));
        return element;
    }
    public static WebElement accept_all_cookies (WebDriver driver)
    {
        element = driver.findElement(By.xpath(".//*[@id='onetrust-accept-btn-handler']"));
        return element;
    }
    public static WebElement chat_fname (WebDriver driver)
    {
        element = driver.findElement(By.xpath(".//*[@id='input-8']"));
        return element;
    }
    public static WebElement chat_Lname (WebDriver driver)
    {
        element = driver.findElement(By.xpath(".//*[@id='input-9']"));
        return element;
    }
    public static WebElement chat_email (WebDriver driver)
    {
        element = driver.findElement(By.xpath(".//*[@id='input-10']"));
        return element;
    }
    public static WebElement chat_enquiry_reason (WebDriver driver)
    {
        element = driver.findElement(By.xpath(".//*[@id='combobox-input-13']"));
        return element;
    }
    public static WebElement chat_select_enquiry_reason (WebDriver driver)
    {
        element = driver.findElement(By.xpath(".//*[@title='I have a question about my existing booking']"));
        return element;
    }
    public static WebElement chat_booking_referance (WebDriver driver)
    {
        element = driver.findElement(By.xpath(".//*[@id='input-12']"));
        return element;
    }
    public static WebElement chat_start_chatting_button (WebDriver driver)
    {
        element = driver.findElement(By.xpath(".//*[text()='Start Chatting']"));
        return element;
    }
    public static WebElement accept_chat_message(WebDriver driver)
    {
        element = driver.findElement(By.xpath("/html/body/div[4]/div[1]/section/div[2]/div[1]/div[4]/div/div/div[2]/div[2]/div/section[1]/div/ul/li/div/div[2]/button[2]/lightning-primitive-icon"));
        return element;
    }
    public static WebElement chat_opportunity (WebDriver driver)
    {
        element = driver.findElement(By.xpath("(//span[@class='title'][normalize-space()='Opportunity'])[2]"));
        return element;
    }
    public static WebElement chat_minimize (WebDriver driver)
    {
        element = driver.findElement(By.xpath("(.//*[@title='Minimize'])[2]"));
        return element;
    }
    public static WebElement chat_Opportunity_tab(WebDriver driver)
    {
        element = driver.findElement(By.xpath("//a[@data-tab-name='customTab6']"));
        return element;
    }
    public static WebElement chat_payment (WebDriver driver)
    {
        element = driver.findElement(By.xpath(".//*[@data-tab-name='customTab4']"));
        return element;
    }
    public static WebElement chat_invoice_action (WebDriver driver)
    {
        element = driver.findElement(By.xpath("//*[@class='slds-form-element__control']"));
        return element;
    }
    public static WebElement chat_generate_invoice (WebDriver driver)
    {
        element = driver.findElement(By.xpath("//span[text()='Generate Invoice']"));
        return element;
    }
    public static WebElement chat_ok_button (WebDriver driver)
    {
        element = driver.findElement(By.xpath(".//*[@class='slds-button slds-button_neutral']"));
        return element;
    }
    public static WebElement chat_doc_owner (WebDriver driver)
    {
        element = driver.findElement(By.xpath("(.//*[@title='Sachin Indradasa'])[2]"));
        return element;
    }
    ////////////passenger details tab pageobjects ////////////////////////////////////////////
    public static WebElement chat_passenger_details_tab (WebDriver driver)
    {
        element = driver.findElement(By.xpath("(.//*[@class='tabHeader'])[6]"));
        return element;
    }
    public static WebElement chat_passenger_details_edit_button (WebDriver driver)
    {
        element = driver.findElement(By.xpath(".//button[@name='edit_action' ]"));
        return element;
    }
    public static WebElement chat_passenger_details_edit_email (WebDriver driver)
    {
        element = driver.findElement(By.xpath("(//div[@class='slds-form-element__control slds-grow'])[12]"));
        return element;
    }
    public static WebElement chat_passenger_details_edit_mobile (WebDriver driver)
    {
        element = driver.findElement(By.xpath("(//div[@class='slds-form-element__control slds-grow'])[13]"));
        return element;
    }
    public static WebElement chat_passenger_details_edit_save_button (WebDriver driver)
    {
        element = driver.findElement(By.xpath(".//*[@name='SaveEdit']"));
        return element;
    }
    public static WebElement payment_generate_link_amount (WebDriver driver)
    {
        element = driver.findElement(By.xpath("(.//div[@class='slds-form-element__control slds-grow'])[1]"));
        return element;
    }
    public static WebElement payment_generate_link_button (WebDriver driver)
    {
        element = driver.findElement(By.xpath(".//*[@type='submit']"));
        return element;
    }
    public static WebElement payment_generate_link_success_message (WebDriver driver)
    {
        element = driver.findElement(By.xpath(".//*[@class='toastTitle slds-text-heading--small']"));
        return element;
    }
    public static WebElement cloase_tab (WebDriver driver)
    {
        element = driver.findElement(By.xpath("(.//*[@class='slds-button__icon'])[4]"));
        return element;
    }
    public static WebElement cloase_tab_verify (WebDriver driver)
    {
        element = driver.findElement(By.xpath("//button[@class='slds-button slds-button_brand saveBtn']"));
        return element;
    }

}
