import common_methods.api_tokens;
import common_methods.globalvariables;
import common_methods.token;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class webbooking  {
    String query="SELECT Id,DM_ID__c FROM Opportunity WHERE DM_ID__c ='7277019'";
    @Test

    public void Salesforcebooking() throws IOException {

        RequestSpecification httpRequest = RestAssured.given();
        api_tokens header = new api_tokens();
        httpRequest.headers(header.alldata());
        Response getDmId = httpRequest.get(globalvariables.instance_url+"/services/data/v56.0/query/?q="+query);
        JsonPath extrator =getDmId.jsonPath();
        int totalSize =extrator.get("totalSize");
        Assert.assertEquals(0,totalSize);
        System.out.println("There are no any booking related this DM_ID ='7277019'");
        ///////////////////Insert the webbboking//////////////////////////////////
        byte[] inp =Files.readAllBytes(Paths.get(".\\src\\test\\java\\jsonfiles\\webbooking.json"));
        String inputval = new String(inp);
        Response responsebooking =httpRequest.body(inputval).post(globalvariables.instance_url+"/services/apexrest/Booking/v1/*");
        String body =responsebooking.getBody().prettyPrint();
        boolean message=responsebooking.path("success");
        Assert.assertEquals(true,message);
        System.out.println("Booking successfully added ");
        ////////////////////Get the insert salseforce_Id/////////////////////////////

        Response getinsertDmId = httpRequest.get(globalvariables.instance_url+"/services/data/v56.0/query/?q="+query);
        JsonPath insertDmId =getinsertDmId.jsonPath();
        String sId =insertDmId.get("records[0].Id");

        ///////////////////////////////Delete the recored & Assertion///////////////////////////////

        Response deleteSId = httpRequest.delete(globalvariables.instance_url+"/services/data/v56.0/sobjects/Opportunity/"+sId);
        int statusCode = deleteSId.statusCode();
        Assert.assertEquals(204,204);
        System.out.println("Booking successfully deleted");


        /////////////////Assertion//////////////////////////////////

    }
}
