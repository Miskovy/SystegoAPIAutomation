Feature: an Admin is able to control the Transfers

  Background: A super admin logs in successfully
    Given the API base URL
    When I send a POST request with email "ola@gmail.com" and password "123456"
    Then the response status code should be 200
    Then the response should contain an authentication token
    Then the response should contain "message" field
    Then the response should contain user with email "ola@gmail.com"
    Then the user role should be "superadmin"
    Then the user status should be "active"
    Then I should receive a login success message

    @SuccessTest
    Scenario Outline: the Admin is able to create a transfer
      Given the API base URL
      When I send a POST request with "<fromWarehouseId>" , "<toWarehouseId>" , <quantity> , "<productId>" to create the transfer
      Then the response status code is 200 when creating the transfer
      Then the response should contain success message of the creation of transfer
      Examples:
        | fromWarehouseId | toWarehouseId | quantity | productId |
        | 6702208a33fc5e001f1f9a00 | 6702208a33fc5e001f1f9a01 | 5 | 6702211b33fc5e001f1f9a02 |
