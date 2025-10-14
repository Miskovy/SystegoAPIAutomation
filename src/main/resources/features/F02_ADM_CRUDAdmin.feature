Feature: a Super Admin is able to do CRUD operations on the admins
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
  Scenario Outline: the super admin is able to create an admin
    Given the API base URL
    When I send a POST request with "<username>","<email>","<password>","<positionId>","<company_name>" and "<phone>"
    Then the response status code should be 200
    Then the body contains the username "<username>", email "<email>", with the role returned, and status is "active"

    Examples:
      | username      | email                        | password  | positionId               | company_name | phone      |
      | Mazen Khairy  | mazenkhairyadmin@gmail.com   | 123456789 | 68ecc84e1321afd0e9450d0c | WegoStation  | 0109258784 |
  @SuccessTest
  Scenario Outline: the super admin is able to update an admin
    Given the API base URL
    Examples:
      |  |
