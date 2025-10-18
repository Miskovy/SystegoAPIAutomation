package stepDefinitions;

import API.Variables;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.*;

import static io.restassured.RestAssured.given;

public class D01_ADM_Login {
    Variables apiVariables = new Variables();
    private RequestSpecification request;
    private Response response;
    private String authToken;

    @Given("the API base URL")
    public void setBaseUrl() {
        RestAssured.baseURI = apiVariables.getBASE_URL();
    }
    @When("I send a POST request with email {string} and password {string}")
    public void sendLoginRequest(String email, String password) {

        Map<String, String> loginBody = new HashMap<>();
        loginBody.put("email", email);
        loginBody.put("password", password);

        response = given()
                .header("Content-Type", "application/json")
                .body(loginBody)
                .log().all()  // Log the request
                .when()
                .post(apiVariables.getADMIN_AUTH_END())
                .then()
                .log().all()  // Log the response
                .extract()
                .response();

        // Print response details
        System.out.println("\n=== RESPONSE DETAILS ===");
        System.out.println("Status Code: " + response.getStatusCode());
        System.out.println("Response Body: " + response.body().asString());
    }
    @Then("the response status code should be {int}")
    public void verifyStatusCode(int expectedStatusCode) {
        response.then().statusCode(expectedStatusCode);
    }
    @Then("the response should contain an authentication token")
    public void verifyAuthTokenExists() {
        authToken = response.jsonPath().getString("data.token");
        AuthTokenHolder.setToken(authToken);
        response.then()
                .body("data.token", notNullValue())
                .body("data.token", not(emptyString()));
    }
    @Then("the response should contain {string} field")
    public void verifyFieldExists(String fieldName) {
        response.then().body("data."+fieldName, notNullValue());
    }
    @Then("I should receive a login success message")
    public void verifyLoginSuccess() {
        response.then()
                .statusCode(200)
                .body("data.message", equalTo("Login successful"));
    }
    @Then("the response should contain user with email {string}")
    public void verifyUserEmail(String expectedEmail) {
        response.then().body("data.user.email", equalTo(expectedEmail));
    }

    @Then("the user role should be {string}")
    public void verifyUserRole(String expectedRole) {
        response.then().body("data.user.role", equalTo(expectedRole));
    }

    @Then("the user status should be {string}")
    public void verifyUserStatus(String expectedStatus) {
        response.then().body("data.user.status", equalTo(expectedStatus));
    }
    @Then("I should receive a fail message")
    public void assert_fail_message(){
        response.then().body("error.message",equalTo("Invalid email or password"));
        System.out.println(response.body().print());
    }
    @Then("I should receive a non-existent message")
    public void assert_nonexist_message(){
        response.then().body("error.message",equalTo("User not found"));
    }
}
