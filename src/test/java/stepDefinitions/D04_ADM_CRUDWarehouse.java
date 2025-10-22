package stepDefinitions;

import API.Variables;
import io.cucumber.java.PendingException;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;

import java.util.HashMap;
import java.util.Map;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class D04_ADM_CRUDWarehouse {
    Variables apiVariables = new Variables();
    private Response response;
@When("I send a POST request with {string} , {string} , {string} , {string}")
    public void CREATE_WAREHOUSE(String name , String address , String phone , String email){
    String authToken = AuthTokenHolder.getToken();
    Map<String , Object> layoutBody = new HashMap<>();
    layoutBody.put("name",name);
    layoutBody.put("address",address);
    layoutBody.put("phone",phone);
    layoutBody.put("email",email);
    response = given()
            .header("Content-Type" ,"application/json")
            .header("Authorization", "Bearer " + authToken)
            .body(layoutBody)
            .when()
            .post(apiVariables.getWAREHOUSE_END())
            .then()
            .log().all()
            .extract().response();
    System.out.println("\n=== CREATE WAREHOUSE ===");
    System.out.println("Status Code: "+ response.getStatusCode());
    System.out.println("Response Body: "+ response.body().asString());
}

    @Then("the response status code should be {int} due to creating the warehouse successfully")
    public void theResponseStatusCodeShouldBeDueToCreatingTheWarehouseSuccessfully(int statusCode) {
        response.then().statusCode(statusCode);
    }

    @Then("the response should contain the success message of creating the warehouse")
    public void theResponseShouldContainTheSuccessMessageOfCreatingTheWarehouse() {
       response.then().body("data.message",notNullValue());
       response.then().body("data.message",equalTo("Create warehouse successfully"));
       response.then().body("data.warehouse",notNullValue());
    }

    @When("I send a GET request to get all the warehouses")
    public void iSendAGETRequestToGetAllTheWarehouses() {
        String authToken = AuthTokenHolder.getToken();
        response = given()
                .header("Content-Type" ,"application/json")
                .header("Authorization", "Bearer " + authToken)
                .when()
                .get(apiVariables.getWAREHOUSE_END())
                .then()
                .log().all()
                .extract().response();

        System.out.println("\n=== GET ALL WAREHOUSE ===");
        System.out.println("Status Code: "+ response.getStatusCode());
        System.out.println("Response Body: "+ response.body().asString());
    }

    @Then("the response should be {int} due to fetching all warehouses")
    public void theResponseShouldBeDueToFetchingAllWarehouses(int statusCode) {
        response.then().statusCode(statusCode);
    }

    @Then("the response should contain the success message due to fetching all the warehouses")
    public void theResponseShouldContainTheSuccessMessageDueToFetchingAllTheWarehouses() {
        response.then().body("data.message",notNullValue());
        response.then().body("data.message",equalTo("Get warehouses successfully"));
        response.then().body("data.warehouses", notNullValue());
    }

    @When("I send a PUT request {string} , to update a warehouse with {string}")
    public void iSendAPUTRequestToUpdateAWarehouseWith(String phone, String id) {
        String authToken = AuthTokenHolder.getToken();
        Map<String , Object> layoutBody = new HashMap<>();
        layoutBody.put("phone",phone);

        response = given()
                .header("Content-Type" ,"application/json")
                .header("Authorization", "Bearer " + authToken)
                .body(layoutBody)
                .when()
                .put(apiVariables.getWAREHOUSE_END()+"/"+id)
                .then().log().all()
                .extract().response();

        System.out.println("\n=== UPDATE WAREHOUSE ===");
        System.out.println("Status Code: "+ response.getStatusCode());
        System.out.println("Response Body: "+ response.body().asString());
    }

    @Then("the response status code should be {int} due to successfully updating a warehouse")
    public void theResponseStatusCodeShouldBeDueToSuccessfullyUpdatingAWarehouse(int statusCode) {
        response.then().statusCode(statusCode);
    }

    @Then("the response should contain the success message and the body of updating the warehouse")
    public void theResponseShouldContainTheSuccessMessageAndTheBodyOfUpdatingTheWarehouse() {
        response.then().body("data.message",notNullValue());
        response.then().body("data.message",equalTo("Update warehouse successfully"));
        response.then().body("data.warehouse",notNullValue());
    }

    @Then("the response status code should be {int} due to not finding a warehouse")
    public void theResponseStatusCodeShouldBeDueToNotFindingAWarehouse(int statusCode) {
        response.then().statusCode(statusCode);
    }

    @Then("the response should contain the fail message due to not finding the warehouse")
    public void theResponseShouldContainTheFailMessageDueToNotFindingTheWarehouse() {
    response.then().body("error",notNullValue());
    response.then().body("error.message",equalTo("Warehouse not found"));
}

    @When("I send a DEL request with {string} , to delete the requested warehouse")
    public void iSendADELRequestWithToDeleteTheRequestedWarehouse(String id) {
       String authToken = AuthTokenHolder.getToken();
       response = given()
               .header("Content-Type" ,"application/json")
               .header("Authorization", "Bearer " + authToken)
               .when()
               .delete(apiVariables.getWAREHOUSE_END()+"/"+id)
               .then().log().all()
               .extract().response();

        System.out.println("\n=== DELETE WAREHOUSE ===");
        System.out.println("Status Code: "+ response.getStatusCode());
        System.out.println("Response Body: "+ response.body().asString());
    }

    @Then("the response status code should be {int} to delete the warehouse")
    public void theResponseStatusCodeShouldBeToDeleteTheWarehouse(int statusCode) {
       response.then().statusCode(statusCode);
    }

    @Then("the response should contain the success message of deleting the warehouse")
    public void theResponseShouldContainTheSuccessMessageOfDeletingTheWarehouse() {
    response.then().body("data.message",notNullValue());
    response.then().body("data.message",equalTo("Delete warehouse successfully"));
    }
}
