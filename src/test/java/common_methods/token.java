package common_methods;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;

import static io.restassured.RestAssured.given;


public class token {

    public static String data;
    public static String token_type,instance_url;
    public static String strship_token;

    @BeforeClass
    public void ressponseAssertion() {

        RequestSpecification httpRequest = given();
        httpRequest.header("Accept", globalvariables.ContantType);
        Response response = httpRequest.post("https://test.salesforce.com/services/oauth2/token?grant_type=password&client_id=3MVG9z6NAroNkeMnZOchEKaJ02LAkpd_lhvlIzyIjxCHXs31bM8YjUZyyXIuHaeOd4MKUsDbqEWB7WftOR3.c&client_secret=8B128CF0770993E176BFB09114F32C1B6BF3C58D7C7C6CADD18119FAD45E66F4&username=sachin.indradasa@intrepidtravel.com.uat&password=Tharaka999@");
        data =response.path("access_token");
        token_type =response.path("token_type");
        instance_url =response.path("instance_url");
        int code =response.statusCode();
        //System.out.println("Body data =: "+response.asString());

    }

     @BeforeClass
    public void starship_token() throws IOException {
        RequestSpecification httpRequest1 = given();
        httpRequest1.header("Content-Type",globalvariables.ContantType);
        httpRequest1.header("X-Api-ReferringWebsiteId", globalvariables.XApiReferringWebsiteId);
        httpRequest1.header("Connection", globalvariables.Connection);

        byte[] inp = Files.readAllBytes(Paths.get(".\\src\\test\\java\\jsonfiles\\gettoken.json"));
        String inputval = new String(inp);
        Response response_strship =httpRequest1.body(inputval).post(globalvariables.Starshiphost+"/token");
        strship_token =response_strship.path("access_token");

    }

    public static RequestSpecification indirrequestSpec;
    @BeforeClass
    public static void SetupRequestSpecBuilder(){
        RequestSpecBuilder builder = new RequestSpecBuilder();
        builder.addHeader("Content-type", globalvariables.ContantType);
        builder.addHeader("X-Api-ReferringWebsiteId", globalvariables.XApiReferringWebsiteId);
        builder.addHeader("X-Api-Key", globalvariables.XApiKey);
        builder.addHeader("X-Api-AgentId", globalvariables.XApiAgentId);
        indirrequestSpec = builder.build();


    }
    public static RequestSpecification dirrequestSpec;
    @BeforeMethod
    public static void StarshipDirectbookingBuilder(){
        RequestSpecBuilder builder = new RequestSpecBuilder();
        builder.addHeader("Content-type", globalvariables.ContantType);
        builder.addHeader("X-Api-ReferringWebsiteId", globalvariables.XApiReferringWebsiteId);
        builder.addHeader("Authorization", "Bearer"+ " " +strship_token);
        dirrequestSpec = builder.build();

    }

    public static RequestSpecification salesforcehedders;
    @BeforeMethod
    public  void salesforcebuilder(){

        RequestSpecBuilder builder = new RequestSpecBuilder();
        builder.addHeader("Content-type", globalvariables.ContantType);
        builder.addHeader("Authorization", token_type + " " + data);
        salesforcehedders = builder.build();
    }

    public WebDriver driver;
    @BeforeClass
    public void set(){

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-notifications");
        options.addArguments("disable-infobars");
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--headless");
        options.addArguments("--disable-gpu");
        options.addArguments("--window-size=1920,1080");
        options.addArguments("--allow-insecure-localhost");
        options.addArguments("--ignore-ssl-errors=yes");
        options.addArguments("--ignore-certificate-errors");
        System.setProperty("webdriver.chrome.driver",".\\src\\test\\java\\utils\\chromedriver.exe");
        driver = new ChromeDriver(options);
        /*Dimension screenSize = new Dimension(1920, 1080);
        driver.manage().window().setSize(screenSize);*/
        driver.manage().window().maximize();
    }
    public void UAT_login(){
        driver.get("https://test.salesforce.com/");
        pageobject.loginpage.username(driver).sendKeys("sachin.indradasa@intrepidtravel.com.uat");
        pageobject.loginpage.password(driver).sendKeys("Tharaka999@");
        pageobject.loginpage.login_button(driver).click();

    }



}
