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
      | Mazen Khairy8  | mazenkhairyadmin8@gmail.com   | 123456780 | 68ecc84e1321afd0e9450d0c | WegoStation  | 01034678908 |

  @SuccessTest
  Scenario Outline: the super admin is able to update admin
    Given the API base URL
    When I send a PUT request with "<username>" in body , with id "<id>"
    Then the response status code should be 200
    Then the body contains the username "<username>", email "<email>", with the role returned, and status is "active"
    Examples:
      | username      | email                       | id                       |
      | Mazen Khairy Changed | mazenkhairyadmin8@gmail.com | 68f0d2dbba0a9eb34e1c8c11 |

  @SuccessTest
  Scenario: the super admin is able to get and view all admins
    Given the API base URL
    When I send GET request to get all admins
    Then the response status code should be 200
    Then the body contains the users list with users inside it

  @SuccessTest
  Scenario Outline: the super admin is able to get admins by id
    Given the API base URL
    When I send GET request to get admin by the id "<id>"
    Then the response status code should be 200
    Then the body contains the user with his id and username
    Examples:
      | id                       |
      | 68f0d2dbba0a9eb34e1c8c11 |

  @FailTest
  Scenario Outline: the super admin is able to get admins by id but fails
    Given the API base URL
    When I send GET request to get admin by the id "<id>"
    Then the body contains the error message, with response 404
    Examples:
      | id                       |
      | 68f0d2dbba0a9eb34e1c8c12 |

  @SuccessTest
  Scenario Outline: the super admin is able to delete an admin
    Given the API base URL
    When I send DEL request to delete admin by the id "<id>"
    Then the response status code should be 200
    Then the success message appears and shows delete is successful
    Examples:
      | id                       |
      | 68ee54c6981025b5ef3afc43 |