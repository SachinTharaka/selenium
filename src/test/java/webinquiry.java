import common_methods.globalvariables;
import common_methods.api_tokens;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import utils.ExcelDataProvider;

import java.util.HashMap;
import java.util.Map;

public class webinquiry {

    @DataProvider(name="webinquirydata")
    public Object[][] getData(){
        String excelPath =".//src//test//java//excel//Webinquiry.xlsx";
        Object data[][]= ExcelDataProvider.testData(excelPath,"Sheet1");
        return data;
    }
    @Test(dataProvider = "webinquirydata",description = "Create a Web Inquiry with Data ")
    @Severity(SeverityLevel.MINOR)

    public void Webinquiry(String Fname,String Lname,String type,String status,String origin,String subject,
                           String description,String Eregion,String currency,String Rid,String Ctype,String Semail)  {

        RequestSpecification httpRequest = RestAssured.given();
        api_tokens header = new api_tokens();
        httpRequest.headers(header.alldata());
        Map<String, Object> requestParams = new HashMap<String, Object>();
        requestParams.put("First_Name__c",Fname);
        requestParams.put("Last_Name__c",Lname);
        requestParams.put("Type",type);
        requestParams.put("Status",status);
        requestParams.put("Origin",origin);
        requestParams.put("Subject",subject);
        requestParams.put("Description",description);
        requestParams.put("Enquiry_Region__c",Eregion);
        requestParams.put("CurrencyIsoCode",currency);
        requestParams.put("RecordTypeId",Rid);
        requestParams.put("Case_Type__c",Ctype);
        requestParams.put("SuppliedEmail",Semail);
        ////////////////////////////////////Read json////////////////////////////////////////////////////////

        Response response1 =httpRequest.body(requestParams).post(globalvariables.instance_url+"/services/data/v56.0/sobjects/Case");

        int statusCode =response1.getStatusCode();
        String body =response1.getBody().prettyPrint();

    }


}
