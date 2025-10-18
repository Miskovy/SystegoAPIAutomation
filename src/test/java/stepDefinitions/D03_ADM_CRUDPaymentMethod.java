package stepDefinitions;

import API.Variables;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.apache.http.impl.conn.SystemDefaultRoutePlanner;

import java.util.HashMap;
import java.util.Map;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class D03_ADM_CRUDPaymentMethod {
    Variables apiVariables = new Variables();
    private Response response;
    @When("I send POST request to create Payment Method with {string} , {string} , {string} , {string}")
    public void sendPOSTReqPaymentMethod(String name , String description , String icon , String type){

        Map<String , Object> layoutBody = new HashMap<>();
        layoutBody.put("name" , name);
        layoutBody.put("discription" , description);
        layoutBody.put("icon" , icon);
        layoutBody.put("type" , type);

        String authToken = AuthTokenHolder.getToken();

        response = given()
                .header("Content-Type" ,"application/json")
                .header("Authorization", "Bearer " + authToken)
                .body(layoutBody)
                .log().all()
                .when()
                .post(apiVariables.getPAYEMNT_METHOD_END())
                .then()
                .log().all()
                .extract()
                .response();
        System.out.println("\n=== CREATE PAYMENT METHOD ===");
        System.out.println("Status Code: "+ response.getStatusCode());
        System.out.println("Response Body: "+ response.body().asString());

    }
}
