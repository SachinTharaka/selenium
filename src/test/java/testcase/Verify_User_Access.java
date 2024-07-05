package testcase;

import com.fasterxml.jackson.databind.ObjectMapper;
import common_methods.globalvariables;
import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;


public class Verify_User_Access {
    /*  SF2-757*/
    @Test
    public void verify_user_access() throws IOException {

        //////Create token for specific user profile(BBC)////////////////////////
        RequestSpecification httpRequest = given();
        httpRequest.header("Accept", globalvariables.ContantType);
        Response response = httpRequest.post("https://test.salesforce.com/services/oauth2/token?grant_type=password&client_id=3MVG9z6NAroNkeMlM8DYPFQvWqpSHAasRo3LsDHAm0SfUITfsRQf9s8zvvrmmJ7VhbtzzP9vbwX1anyoe0W5L&client_secret=FA90AF005BCA252AA938E7A170D93DEDBA9D8CE526EE51363C458E6E7D43F485&username=bbcintegration@intrepidtravel.com.uat&password=bbc1in3grat1onuat!TuqPW7ZKJcxHqosvprUBikyFc");
        String bearer_token = response.path("access_token");
        String token_type = response.path("token_type");


        /////////Create a customer interaction ////////////////////////////////////////////
        RequestSpecification create_subscription_details = RestAssured.given();
        create_subscription_details.header("Content-type", globalvariables.ContantType);
        create_subscription_details.header("Authorization", token_type + " " + bearer_token);
        byte[] inp = Files.readAllBytes(Paths.get(".//src//test//java//jsonfiles//customer_interaction.json"));
        String inputval = new String(inp);
        Response subscription_request = create_subscription_details.body(inputval).post(globalvariables.instance_url + "/services/data/v56.0/sobjects/CustomerInteraction__c");
        String customer_interaction_id = subscription_request.path("id");
        System.out.println(customer_interaction_id);

        /////////Verify the customer interaction created ////////////////////////////////////////////
        RequestSpecification get_subscription_details = RestAssured.given();
        get_subscription_details.header("Content-type", globalvariables.ContantType);
        get_subscription_details.header("Authorization", token_type + " " + bearer_token);
        Response get_subscription = get_subscription_details.get(globalvariables.instance_url + "/services/data/v56.0/sobjects/CustomerInteraction__c/" + customer_interaction_id);
        String interaction_name = get_subscription.path("Name");

        Assert.assertNotNull(interaction_name);
        System.out.println("Customer Interactions Name Successfully Created");

        //////////////Verify the customer interaction cannot delete//////////////////////////////
        RequestSpecification delete_subscription_details = RestAssured.given();
        delete_subscription_details.header("Content-type", globalvariables.ContantType);
        delete_subscription_details.header("Authorization", token_type + " " + bearer_token);
        Response delete_subscription = delete_subscription_details.delete(globalvariables.instance_url + "/services/data/v56.0/sobjects/CustomerInteraction__c/" + customer_interaction_id);

        String interaction_delete_message = delete_subscription.path("[0].message");
        Assert.assertEquals(interaction_delete_message, "insufficient access rights on object id");
        System.out.println("Interaction Record cannot be Delete");

        //////////////Verify the customer interaction cannot edit ///////////////////////

        RequestSpecification edit_subscription_details = RestAssured.given();
        edit_subscription_details.header("Content-type", globalvariables.ContantType);
        edit_subscription_details.header("Authorization", token_type + " " + bearer_token);

        JSONObject requestParams = new JSONObject();
        requestParams.put("First_Name__c", "sachin_edit_auto");
        edit_subscription_details.body(requestParams.toString());

        Response edit_subscription = edit_subscription_details.request(Method.PATCH, globalvariables.instance_url + "/services/data/v56.0/sobjects/CustomerInteraction__c/a14Hy000001tYONIA2");
        String interaction_edit_message = edit_subscription.path("[0].message");
        Assert.assertEquals(interaction_edit_message, "entity type cannot be updated: Customer Interaction");
        System.out.println("Interaction Record cannot be Edite");

        /////////////////Verify the subscribers without subscriber status//////////////////////////

        RequestSpecification without_subscriber_status = RestAssured.given();
        without_subscriber_status.header("Content-type", globalvariables.ContantType);
        without_subscriber_status.header("Authorization", token_type + " " + bearer_token);

        HashMap<String, Object> results = new ObjectMapper().readValue(new File(".//src//test//java//jsonfiles//customer_interaction.json"), HashMap.class);
        Map<String, Object> subscriber_status = new HashMap<String, Object>();
        subscriber_status.remove("Subscribed__c");
        subscriber_status.put("Subscribed__c", " ");


        Map<String, Object> combineRequest = new HashMap<String, Object>();
        combineRequest.putAll(results);
        combineRequest.putAll(subscriber_status);

        System.out.println(combineRequest);
        Response without_subscriber_status_request = without_subscriber_status.body(combineRequest).post(globalvariables.instance_url + "/services/data/v56.0/sobjects/CustomerInteraction__c");
        String without_sub_id = without_subscriber_status_request.path("id");
        System.out.println(without_sub_id);

        ///Assert the subscription ///////
        RequestSpecification without_subscriber_details = RestAssured.given();
        without_subscriber_details.header("Content-type", globalvariables.ContantType);
        without_subscriber_details.header("Authorization", token_type + " " + bearer_token);
        Response without_subscriber = without_subscriber_details.get(globalvariables.instance_url + "/services/data/v56.0/sobjects/CustomerInteraction__c/" + without_sub_id);
        boolean check_subscribe = without_subscriber.path("Subscribed__c");
        Assert.assertEquals(check_subscribe, false);

    }
}
