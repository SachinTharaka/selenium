import common_methods.globalvariables;
import common_methods.token;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class indirectwebbooking extends token {
@Test
    public void indirectwebbooking () throws IOException {
        driver.quit();
        RequestSpecification httpRequest = RestAssured.given();
        httpRequest.header("Content-type", globalvariables.ContantType);
        httpRequest.header("Authorization", token_type + " " + data);
        byte[] inp =Files.readAllBytes(Paths.get(".\\src\\test\\java\\jsonfiles\\indirectwebbooking.json"));
        String inputval = new String(inp);
        Response responsebooking =httpRequest.body(inputval).post(instance_url+"/services/apexrest/Booking/v1/*");
        String body =responsebooking.getBody().prettyPrint();

        /////////////////Assertion//////////////////////////////////
        boolean message=responsebooking.path("success");
        Assert.assertEquals(true,message);
    }
}

