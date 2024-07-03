package testcase.Intrepid_Service;


import common_methods.api_tokens;
import common_methods.globalvariables;
import common_methods.token;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.testng.Assert;
import org.testng.annotations.Test;


import java.util.HashMap;
import java.util.Map;

public class Verify_Duplicate_Case_Merge_by_Subject extends token {
    /* SF2-810*/
    @Test
    public void verify_duplicate_case_merge_by_subject() throws InterruptedException {

        String get_case_id = "SELECT Id FROM Case WHERE CaseNumber=";
        ////////// Get the Access_token /////////////////////////////////////////////////////////

        RequestSpecification httpRequest = RestAssured.given();
        Response get_token = httpRequest.post(globalvariables.google_access_token_post_request);
        String access_token = get_token.path("access_token");
        String body = get_token.getBody().prettyPrint();

        /////////////////Email send //////////////////////////////////////////

        httpRequest.header("Authorization", "Bearer " + access_token);
        httpRequest.header("scope", "https://www.googleapis.com/auth/drive.metadata.readonly");
        httpRequest.header("Content-type", globalvariables.ContantType);

        Thread.sleep(10000);
        /////////////////////////////////////////////////////////////////////////


        for (int i = 0; i < 2; i++) {

            if (i == 0) {
                Map<String, Object> email_body = new HashMap<String, Object>();
                email_body.put("raw", globalvariables.first_email_body);

                Response send_email = httpRequest.body(email_body).post(globalvariables.google_emailsend_post_request);
                String first_email = send_email.getBody().prettyPrint();
                Thread.sleep(20000);

            } else {
                Map<String, Object> email_body = new HashMap<String, Object>();
                email_body.put("raw", globalvariables.second_email_body);

                Response send_email = httpRequest.body(email_body).post(globalvariables.google_emailsend_post_request);
                String second_email = send_email.getBody().prettyPrint();
            }
        }

        UAT_login();
        driver.navigate().to(globalvariables.Intrepid_Service);
        driver.navigate().to(globalvariables.all_opencases);

        Thread.sleep(4000);
        pageobject.common_objects.item_close_tab(driver).click();

        pageobject.common_objects.search(driver).sendKeys("Test automation | testing");
        Thread.sleep(4000);
        pageobject.common_objects.search(driver).sendKeys(Keys.ENTER);
        Thread.sleep(4000);
        String case_number = pageobject.common_objects.select_recored(driver).getText();

        pageobject.common_objects.select_recored(driver).click();
        Thread.sleep(8000);
        String cases_merge = driver.findElement(By.xpath("(.//*[@class=\"slds-truncate uiOutputTextArea\"])[2]"))
                .getText();
        driver.quit();
        ////////////Verify the both cases merge///////////////////////////////

        Assert.assertEquals(cases_merge, "Duplicate Case found and Merged");
        //////////Delete the created merge case ////////////////////////////////
        RequestSpecification delele_merge_request = RestAssured.given();
        api_tokens header_details = new api_tokens();
        delele_merge_request.headers(header_details.alldata());


        Response s = delele_merge_request.get(globalvariables.instance_url + "/services/data/v56.0/query/?q=" + get_case_id + "'" + case_number + "'");
        String case_id = s.path("records[0].Id");


        //////////////////////////delete the case////////////////////////////////////////////////case_number

        Response delete_case = delele_merge_request.delete(globalvariables.instance_url + "/services/data/v56.0/sobjects/Case/" + case_id);
        System.out.println("Case Successfully  deleted");


    }
}
