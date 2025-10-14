Feature: a User is able to Login
  @SuccessTest
  Scenario: A user is able to login successfully using his credentials
    Given the API base URL
    When I send a POST request with email "ola@gmail.com" and password "123456"
    Then the response status code should be 200
    Then the response should contain an authentication token
    Then the response should contain "message" field
    Then the response should contain user with email "ola@gmail.com"
    Then the user role should be "superadmin"
    Then the user status should be "active"
    Then I should receive a login success message

  @FailedTest
  Scenario: A user isn't able to login because of wrong credentials
    Given the API base URL
    When I send a POST request with email "ola@gmail.com" and password "1234567"
    Then the response status code should be 401
    Then I should receive a fail message

  @FailedTest
  Scenario: A user isn't able to login because of non-existent email
    Given the API base URL
    When I send a POST request with email "faker@gmail.com" and password "1234567"
    Then the response status code should be 404
    Then I should receive a non-existent message
