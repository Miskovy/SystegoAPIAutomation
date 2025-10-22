Feature: an Admin is able to CRUD a Warehouse

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
  Scenario Outline: an Admin is able to create a warehouse successfully
    Given the API base URL
    When I send a POST request with "<name>" , "<address>" , "<phone>" , "<email>"
    Then the response status code should be 200 due to creating the warehouse successfully
    Then the response should contain the success message of creating the warehouse
    Examples:
      | name | address | phone | email |
      | Warehouse Name | alexandria, Egypt | 01012325678 | main@warehouse.com |

  @SuccessTest
  Scenario: an Admin is able to get all the warehouses
    Given the API base URL
    When I send a GET request to get all the warehouses
    Then the response should be 200 due to fetching all warehouses
    Then the response should contain the success message due to fetching all the warehouses

  @SuccessTest
  Scenario Outline: an Admin is able to update the requested warehouse
    Given the API base URL
    When I send a PUT request "<phone>" , to update a warehouse with "<id>"
    Then the response status code should be 200 due to successfully updating a warehouse
    Then the response should contain the success message and the body of updating the warehouse
    Examples:
      | phone | id |
      | 01098765432 | 68e616d32c4fc1f8db2f2668 |

  @FailTest
  Scenario Outline: an Admin isn't able to update the requested warehouse
    Given the API base URL
    When I send a PUT request "<phone>" , to update a warehouse with "<id>"
    Then the response status code should be 404 due to not finding a warehouse
    Then the response should contain the fail message due to not finding the warehouse
    Examples:
      | phone | id |
      | 01098765432 | 68e616d32c4fc1f8db2f2668 |


  @SuccessTest
  Scenario Outline: an Admin is able to DELETE the requested warehouse
    Given the API base URL
    When I send a DEL request with "<id>" , to delete the requested warehouse
    Then the response status code should be 200 to delete the warehouse
    Then  the response should contain the success message of deleting the warehouse
    Examples:
      | id |
      | 68e616d32c4fc1f8db2f2668 |


