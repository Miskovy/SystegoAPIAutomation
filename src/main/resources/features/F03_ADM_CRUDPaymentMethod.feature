Feature: an Admin has the ability to CRUD a Payment Method

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
  Scenario Outline: an Admin is able to create a Payment Method Successfully
    Given the API base URL
    When I send POST request to create Payment Method with "<name>" , "<discription>" , "<icon>" , "<type>"
    Then the response status code should be 200
    Then the response should contain success message of creation
    Then the response should show the created payment method
    Examples:
      | name          | discription                    | icon                           | type   |
      | Vodafone Cash | Vodafone Cash Test Description | iVBORw0KGgoAAAANSUhEUgAAAOE... | manual |