package stepDefinitions;

import API.Variables;
import io.cucumber.java.PendingException;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.apache.http.impl.conn.SystemDefaultRoutePlanner;
import org.testng.Assert;

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

    @Then("the response should contain fail message of creation due to already exists")
    public void theResponseShouldContainFailMessageOfCreation() {
        response.then().body("error.message",notNullValue());
        response.then().body("error.message",equalTo("Payment method already exists"));
    }

    @Then("the response status code should be {int} due to already exists")
    public void theResponseStatusCodeShouldBeDueToAlreadyExists(int statusCode) {
        response.then().body("error.code" , equalTo(statusCode));
        System.out.println("The Received Status Code: "+ response.statusCode());
    }

    @Then("the response should contain success message of creation")
    public void theResponseShouldContainSuccessMessageOfCreation() {
        response.then().body("data.message",equalTo("Payment method created successfully"));
        response.then().body("data.paymentMethod", notNullValue());
    }

    @Then("the response status code should be {int} due to success")
    public void theResponseStatusCodeShouldBeDueToSuccess(int statusCode) {
        response.then().statusCode(statusCode);
        System.out.println("The Received Response Code is: "+ response.getStatusCode());
    }

    @Then("the response status code should be {int} due to empty field")
    public void theResponseStatusCodeShouldBeDueToEmptyField(int statusCode) {
        response.then().statusCode(statusCode);
        System.out.println("The Received Response Code is: "+ response.getStatusCode());
    }

    @Then("the response should contain fail message of not allowed")
    public void theResponseShouldContainFailMessageOfIsRequired() {
        response.then().body("error.message", notNullValue());
        response.then().body("error.message", containsString("is not allowed to be empty"));
        response.then().body("error.details[0]", containsString("is not allowed to be empty"));
    }

    @When("I send a GET request to get all the payment methods")
    public void iSendAGETRequestToGetAllThePaymentMethods() {
        String authToken = AuthTokenHolder.getToken();
        response = given()
                .header("Content-Type" ,"application/json")
                .header("Authorization", "Bearer " + authToken)
                .when()
                .get(apiVariables.getPAYEMNT_METHOD_END())
                .then().log().all()
                .extract().response();
        System.out.println("\n=== GET ALL PAYMENT METHOD ===");
        System.out.println("Status Code: "+ response.getStatusCode());
        System.out.println("Response Body: "+ response.body().asString());
    }

    @Then("the response status code should be {int} when getting all payment fields")
    public void theResponseStatusCodeShouldBeWhenGettingAllPaymentFields(int statusCode) {
        response.then().statusCode(statusCode);
        System.out.println("The Received Response Code is: "+ response.getStatusCode());
    }

    @And("all payment methods should be shown")
    public void allPaymentMethodsShouldBeShown() {
        response.then().body("data.message", equalTo("All payment methods fetched successfully"));
        response.then().body("data.paymentMethods",notNullValue());
    }

    @When("I send a GET request with a parameter ID {string}")
    public void iSendAGETRequestWithAParameterID(String paymentMethodId) {
        String authToken = AuthTokenHolder.getToken();
        response = given()
                .header("Content-Type" ,"application/json")
                .header("Authorization", "Bearer " + authToken)
                .when()
                .get(apiVariables.getPAYEMNT_METHOD_END() +"/"+ paymentMethodId)
                .then().log().all().extract().response();
        System.out.println("\n=== GET A PAYMENT METHOD ===");
        System.out.println("Status Code: "+ response.getStatusCode());
        System.out.println("Response Body: "+ response.body().asString());
    }

    @Then("the response status code should be {int} when getting a payment method")
    public void theResponseStatusCodeShouldBeWhenGettingAPaymentMethod(int statusCode) {
        response.then().statusCode(statusCode);
    }

    @And("the requested payment is shown")
    public void theRequestedPaymentIsShown() {
        response.then().body("data.message" , equalTo("Payment method fetched successfully"));
        response.then().body("data.paymentMethod", notNullValue());
        response.then().body("data.paymentMethod._id", notNullValue());
        response.then().body("data.paymentMethod.name" , notNullValue());
    }

    @And("the error message is shown that it's not found")
    public void theErrorMessageIsShownThatItSNotFound() {
        response.then().body("error.message", notNullValue());
        response.then().body("error.message", equalTo("Payment method not found"));
    }

    @When("I send a PUT request with {string} in body , with id {string} to update payment method")
    public void iSendAPUTRequestWithInBodyWithIdToUpdatePaymentMethod(String arg0, String id) {
        String authToken = AuthTokenHolder.getToken();
        Map<String , Object> layoutBody = new HashMap<>();
        layoutBody.put("name",arg0);
        response = given()
                .header("Content-Type" ,"application/json")
                .header("Authorization", "Bearer " + authToken)
                .body(layoutBody)
                .when()
                .put(apiVariables.getPAYEMNT_METHOD_END()+"/"+id)
                .then().log().all().extract().response();
        System.out.println("\n=== UPDATE A PAYMENT METHOD ===");
        System.out.println("Status Code: "+ response.getStatusCode());
        System.out.println("Response Body: "+ response.body().asString());
    }

    @Then("the response status code should be {int} due to update successful")
    public void theResponseStatusCodeShouldBeDueToUpdateSuccessful(int statusCode) {
        response.then().statusCode(statusCode);
    }

    @And("the success message is shown for updating a payment method")
    public void theSuccessMessageIsShownForUpdatingAPaymentMethod() {
        response.then().body("data.message", equalTo("Payment method updated successfully"));
        response.then().body("data.paymentMethod",notNullValue());
    }

    @Then("the response status code should be {int} due to payment method not found")
    public void theResponseStatusCodeShouldBeDueToPaymentMethodNotFound(int statusCode) {
        response.then().statusCode(statusCode);
    }

    @And("the fail message is shown for updating a payment method")
    public void theFailMessageIsShownForUpdatingAPaymentMethod() {
        response.then().body("error", notNullValue());
        response.then().body("error.message" , equalTo("Payment method not found"));
    }

    @When("I send a DEL request with {string} to delete the payment method")
    public void iSendADELRequestWithToDeleteThePaymentMethod(String id) {
        String authToken = AuthTokenHolder.getToken();
        response = given()
                .header("Content-Type" ,"application/json")
                .header("Authorization", "Bearer " + authToken)
                .when()
                .delete(apiVariables.getPAYEMNT_METHOD_END()+"/"+id)
                .then().log().all().extract().response();
        System.out.println("\n=== DELETE A PAYMENT METHOD ===");
        System.out.println("Status Code: "+ response.getStatusCode());
        System.out.println("Response Body: "+ response.body().asString());
    }

    @And("the fail message is shown for deleting a payment method")
    public void theFailMessageIsShownForDeletingAPaymentMethod() {
        response.then().body("error",notNullValue());
        response.then().body("error.message", equalTo("Payment method not found"));
    }

    @Then("the response status code should be {int} due to payment method deleted")
    public void theResponseStatusCodeShouldBeDueToPaymentMethodDeleted(int statusCode) {
        response.then().statusCode(statusCode);
    }

    @And("the success message is shown for deleting a payment method")
    public void theSuccessMessageIsShownForDeletingAPaymentMethod() {
        response.then().body("data",notNullValue());
        response.then().body("data.message",equalTo("Payment method deleted successfully"));
    }
}
