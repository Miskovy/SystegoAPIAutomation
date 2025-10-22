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

public class D05_ADM_CRUDTransfer {
    private Response response;
    Variables apiVariables = new Variables();

    @When("I send a POST request with {string} , {string} , {int} , {string} to create the transfer")
    public void iSendAPOSTRequestWithQuantityToCreateTheTransfer(String fromWarehouseId, String toWarehouseId, int quantity,String productId) {

        String authToken = AuthTokenHolder.getToken();
        Map<String , Object> layoutBody = new HashMap<>();
        layoutBody.put("fromWarehouseId",fromWarehouseId);
        layoutBody.put("toWarehouseId", toWarehouseId);
        layoutBody.put("quantity",quantity);
        layoutBody.put("productId",productId);

        response = given()
                .header("Content-Type" ,"application/json")
                .header("Authorization", "Bearer " + authToken)
                .body(layoutBody)
                .when()
                .post(apiVariables.getTRANSFER_END())
                .then()
                .log().all()
                .extract().response();

        System.out.println("\n=== CREATE TRANSFER ===");
        System.out.println("Status Code: "+ response.getStatusCode());
        System.out.println("Response Body: "+ response.body().asString());
    }

    @Then("the response status code is {int} when creating the transfer")
    public void theResponseStatusCodeIsWhenCreatingTheTransfer(int statusCode) {
        response.then().statusCode(statusCode);
    }

    @Then("the response should contain success message of the creation of transfer")
    public void theResponseShouldContainSuccessMessageOfTheCreationOfTransfer() {
    }
}
