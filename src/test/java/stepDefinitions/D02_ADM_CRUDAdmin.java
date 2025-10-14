package stepDefinitions;

import API.Variables;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


public class D02_ADM_CRUDAdmin {
    private Response response;
    Variables apiVariables = new Variables();
    @When("I send a POST request with {string},{string},{string},{string},{string} and {string}")
        public void post_Create_admin(String username,String email , String password , String positionId ,String company_name, String phonenum ){
        Map<String , Object> payload = new HashMap<>();
        payload.put("username", username);
        payload.put("email", email);
        payload.put("password", password);
        payload.put("positionId", positionId);
        payload.put("company_name", company_name);
        payload.put("phone", phonenum);

        String token = System.getProperty("AUTH_TOKEN",
                Optional.ofNullable(System.getenv("AUTH_TOKEN"))
                        .orElse());
        response = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(payload)
                .when().post(apiVariables.getPOST_ADM_END())
                .then().extract().response();
        System.out.println(response.getBody().print());
    }
    @Then("the body contains the username {string}, email {string}, with the role returned, and status is {string}")
    public void assert_body_of_creation(String username , String email , String status){
        response.then().body("data.user.username", equalTo(username));
        response.then().body("data.user.email", equalTo(email));
        response.then().body("data.user.status", equalTo(status));
        response.then().body("data.user.positionId", notNullValue());
    }
}
