package stepDefinitions;

import API.Variables;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class D02_ADM_CRUDAdmin {
    Variables apiVariables = new Variables();
    private Response response;

    @When("I send a POST request with {string},{string},{string},{string},{string} and {string}")
    public void sendCreateAdminRequest(String username, String email, String password,
                                       String positionId, String companyName, String phone) {

        // Get the auth token from the holder
        String authToken = AuthTokenHolder.getToken();

        // Prepare request body
        Map<String, String> adminBody = new HashMap<>();
        adminBody.put("username", username);
        adminBody.put("email", email);
        adminBody.put("password", password);
        adminBody.put("positionId", positionId);
        adminBody.put("company_name", companyName);
        adminBody.put("phone", phone);

        // Send POST request with Authorization header
        response = given()
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + authToken)
                .body(adminBody)
                .log().all()  // Log the request
                .when()
                .post(apiVariables.get_ADMIN_END())
                .then()
                .log().all()  // Log the response
                .extract()
                .response();

        // Print response details
        System.out.println("\n=== CREATE ADMIN RESPONSE ===");
        System.out.println("Status Code: " + response.getStatusCode());
        System.out.println("Response Body: " + response.body().asString());
    }

    @Then("the body contains the username {string}, email {string}, with the role returned, and status is {string}")
    public void verifyCreatedAdminDetails(String expectedUsername, String expectedEmail, String expectedStatus) {
        response.then()
                .body("data.user.username", equalTo(expectedUsername))
                .body("data.user.email", equalTo(expectedEmail))
                .body("data.user.positionId", notNullValue())
                .body("data.user.status", equalTo(expectedStatus));

        System.out.println("\n=== VERIFICATION PASSED ===");
        System.out.println("Username: " + expectedUsername);
        System.out.println("Email: " + expectedEmail);
        System.out.println("Status: " + expectedStatus);
    }
    @When("I send a PUT request with {string} in body , with id {string}")
    public void sendUpdateAdminRequest(String username, String id) {

        // Get the auth token from the holder
        String authToken = AuthTokenHolder.getToken();

        // Prepare request body with only username
        Map<String, String> updateBody = new HashMap<>();
        updateBody.put("username", username);

        // Send PUT request with Authorization header
        response = given()
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + authToken)
                .body(updateBody)
                .log().all()  // Log the request
                .when()
                .put(apiVariables.getUPDATE_ADMIN_END(id))
                .then()
                .log().all()  // Log the response
                .extract()
                .response();

        // Print response details
        System.out.println("\n=== UPDATE ADMIN RESPONSE ===");
        System.out.println("Status Code: " + response.getStatusCode());
        System.out.println("Response Body: " + response.body().asString());
    }
    @When("I send GET request to get all admins")
    public void get_all_admins(){

        String authToken = AuthTokenHolder.getToken();

        response = given()
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + authToken)
                .log().all()  // Log the request
                .when()
                .get(apiVariables.get_ADMIN_END())
                .then()
                .log().all()  // Log the response
                .extract()
                .response();

        System.out.println("\n=== GET ALL ADMIN RESPONSE ===");
        System.out.println("Status Code: " + response.getStatusCode());
        System.out.println("Response Body: " + response.body().asString());
    }
    @Then("the body contains the users list with users inside it")
    public void assert_get_all_users_list(){
        response.then().
                body("data.users", notNullValue());
    }
    @When("I send GET request to get admin by the id {string}")
    public void get_admin_by_id(String id){
        String authToken = AuthTokenHolder.getToken();
        response = given()
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + authToken)
                .log().all()  // Log the request
                .when()
                .get(apiVariables.get_byID_ADMIN_END(id))
                .then()
                .log().all()  // Log the response
                .extract()
                .response();

        System.out.println("\n=== GET ADMIN RESPONSE ===");
        System.out.println("Status Code: " + response.getStatusCode());
        System.out.println("Response Body: " + response.body().asString());
    }
    @Then("the body contains the user with his id and username")
    public void assert_the_user_body_id(){
        response.then().body("data.user._id",notNullValue());
        response.then().body("data.user.username",notNullValue());
    }
    @Then("the body contains the error message, with response {int}")
    public void assert_error_message_not_found(int statusCode){
        response.then().body("error", notNullValue());
        response.then().body("error.message",equalTo("User not found"));
        response.then().statusCode(statusCode);
    }
    @When("I send DEL request to delete admin by the id {string}")
    public void send_delete_request_byid(String id){
        String authToken = AuthTokenHolder.getToken();
        response = given()
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + authToken)
                .log().all()  // Log the request
                .when()
                .delete(apiVariables.get_byID_ADMIN_END(id))
                .then()
                .log().all()  // Log the response
                .extract()
                .response();

        System.out.println("\n=== GET DELETE ADMIN RESPONSE ===");
        System.out.println("Status Code: " + response.getStatusCode());
        System.out.println("Response Body: " + response.body().asString());
    }
    @Then("the success message appears and shows delete is successful")
    public void assert_delete_admin_shown(){
        response.then().body("data.message",equalTo("User deleted successfully"));
    }
}