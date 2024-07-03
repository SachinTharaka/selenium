package common_methods;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

import static io.restassured.RestAssured.given;

public class api_tokens {

    @BeforeClass
    public List<String> gettokenDetails() {

        RequestSpecification httpRequest = given();
        httpRequest.header("Accept",globalvariables.ContantType);
        Response response = httpRequest.post("https://test.salesforce.com/services/oauth2/token?grant_type=password&client_id=3MVG9z6NAroNkeMnZOchEKaJ02LAkpd_lhvlIzyIjxCHXs31bM8YjUZyyXIuHaeOd4MKUsDbqEWB7WftOR3.c&client_secret=8B128CF0770993E176BFB09114F32C1B6BF3C58D7C7C6CADD18119FAD45E66F4&username=sachin.indradasa@intrepidtravel.com.uat&password=Tharaka999@");
        String  data =response.path("access_token");
        String token_type =response.path("token_type");
        String instance_url =response.path("instance_url");
        int code =response.statusCode();
        return Arrays.asList(data, token_type);
    }
    @BeforeMethod
    public Map<String, Object> alldata(){
        Map<String, Object> originalMap = new HashMap<>();
        originalMap.put("Content-type","application/json");
        originalMap.put("Authorization", gettokenDetails().get(1)+" "+gettokenDetails().get(0));

        Map<String, Object> commonheader= new HashMap<String, Object>();
        commonheader.putAll(originalMap);
        return commonheader;


    }
    @BeforeMethod
     public List<String> starship_token() throws IOException {
         RequestSpecification starship_httpRequest = given();
         starship_httpRequest.header("Content-Type",globalvariables.ContantType);
         starship_httpRequest.header("X-Api-ReferringWebsiteId", globalvariables.XApiReferringWebsiteId);
         starship_httpRequest.header("Connection", globalvariables.Connection);
         byte[] inp = Files.readAllBytes(Paths.get(".\\src\\test\\java\\jsonfiles\\gettoken.json"));
         String inputval = new String(inp);
         Response response_strship =starship_httpRequest.body(inputval).post(globalvariables.Starshiphost+"/token");
         String strship_token =response_strship.path("access_token");
         return Arrays.asList(strship_token);
     }
    @BeforeMethod
      public Map<String, Object> starship_directheader() throws IOException {
          Map<String, Object> starship_head = new HashMap<>();
          starship_head.put("Content-type", globalvariables.ContantType);
          starship_head.put("X-Api-ReferringWebsiteId", globalvariables.XApiReferringWebsiteId);
          starship_head.put("Authorization", "Bearer"+ " " +starship_token().get(0));
          Map<String, Object> starship_direct_commonheader= new HashMap<String, Object>();
          starship_direct_commonheader.putAll(starship_head);
          return starship_direct_commonheader;


    }

    @BeforeMethod
    public List<String> starship_booking() throws IOException {
        RequestSpecification httpRequest = RestAssured.given();
        api_tokens header = new api_tokens();
        httpRequest.headers(header.starship_directheader());
        Map<String, Object> requestParams = new HashMap<String, Object>();
        requestParams.put("currency", globalvariables.Currency);
        requestParams.put("sellingAgentId", globalvariables.SellingAgentId);
        requestParams.put("bookingSource", globalvariables.bookingSource);
        Response Starship_booking = httpRequest.body(requestParams).post(globalvariables.Starshiphost+"/bookings");

        int booking_id = Starship_booking.path("id");
        String bookingId = Integer.toString(booking_id);

        //////////Add Customer Details //////////////////////////////////////////////
        byte[] inp = Files.readAllBytes(Paths.get(".\\src\\test\\java\\jsonfiles\\common_customer.json"));
        String inputval = new String(inp);
        Response addcustomer = httpRequest.body(inputval).post(globalvariables.Starshiphost+"/bookings/"+booking_id+"/customers");
        int cusid = addcustomer.path("id");
        String customer_id =Integer.toString(cusid);
        ///////////////////Add Trip details/////////////////////////////
        Map<String, Object> userDetails = new HashMap<>();
        Map<String, Object> customers = new HashMap<>();
        customers.put("href","/bookings/"+booking_id+"/customers/"+cusid);
        userDetails.put("departureCode",globalvariables.departureCode);
        userDetails.put("customers", Arrays.asList(customers, customers));
        Response addtrip =httpRequest.body(userDetails).post(globalvariables.Starshiphost+"/bookings/"+booking_id+"/trips");
        String body1 =addtrip.getBody().prettyPrint();
        int tripid = addtrip.path("id");
        String trip_id =Integer.toString(tripid);
        return Arrays.asList(bookingId,customer_id,trip_id);

    }
}
