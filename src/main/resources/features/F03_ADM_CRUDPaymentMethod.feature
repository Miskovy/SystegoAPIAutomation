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
    Then the response status code should be 200 due to success
    Then the response should contain success message of creation
    Examples:
      | name          | discription                    | icon                           | type   |
      | Fawry | Fawry Test Description | iVBORw0KGgoAAAANSUhEUgAAAOE... | manual |

  @FailTest
  Scenario Outline: an Admin creates an existing payment method
    Given the API base URL
    When I send POST request to create Payment Method with "<name>" , "<discription>" , "<icon>" , "<type>"
    Then the response status code should be 400 due to already exists
    Then the response should contain fail message of creation due to already exists
    Examples:
      | name          | discription                    | icon                           | type   |
      | Vodafone Cash | Vodafone Cash Test Description | iVBORw0KGgoAAAANSUhEUgAAAOE... | manual |

  @FailTest
  Scenario Outline: an Admin POST a request with an empty field
    Given the API base URL
    When I send POST request to create Payment Method with "<name>" , "<discription>" , "<icon>" , "<type>"
    Then the response status code should be 400 due to empty field
    Then the response should contain fail message of not allowed
    Examples:
      | name          | discription                    | icon                           | type   |
      |  | Vodafone Cash Test Description | iVBORw0KGgoAAAANSUhEUgAAAOE... | manual |

  @SuccessTest
  Scenario: an Admin is able to get all the payment Methods
    Given  the API base URL
    When I send a GET request to get all the payment methods
    Then the response status code should be 200 when getting all payment fields
    And all payment methods should be shown
  @SuccessTest
  Scenario Outline: an Admin is able to get a payment method by id
    Given the API base URL
    When I send a GET request with a parameter ID "<paymentMethodID>"
    Then the response status code should be 200 when getting a payment method
    And the requested payment is shown
    Examples:
      | paymentMethodID |
      | 68f4fc7192992b812dd8db09 |
  @FailTest
  Scenario Outline: an Admin isn't able to get a payment method
    Given the API base URL
    When I send a GET request with a parameter ID "<paymentMethodID>"
    Then the response status code should be 404 when getting a payment method
    And the error message is shown that it's not found
    Examples:
      | paymentMethodID |
      | 68f4fc7192992b812dd9db09 |


  @SuccessTest
  Scenario Outline: an Admin is able to update the requested payment method
    Given the API base URL
    When I send a PUT request with "<name>" in body , with id "<paymentMethodId>" to update payment method
    Then the response status code should be 200 due to update successful
    And the success message is shown for updating a payment method
    Examples:
      | name | paymentMethodId |
      | Changed name Payment Test | 68f4fc7192992b812dd8db09 |

  @FailTest
  Scenario Outline: an Admin isn't able to update the requested payment method
    Given the API base URL
    When I send a PUT request with "<name>" in body , with id "<paymentMethodId>" to update payment method
    Then the response status code should be 400 due to payment method not found
    And the fail message is shown for updating a payment method
    Examples:
      | name | paymentMethodId |
      | Changed name Payment Test | 68f4fc7102982b812dd8db09 |

  @FailTest
  Scenario Outline: an Admin isn't able to delete the requested payment method
    Given the API base URL
    When I send a DEL request with "<id>" to delete the payment method
    Then the response status code should be 404 due to payment method not found
    And the fail message is shown for deleting a payment method
    Examples:
      | id |
      | 68d25543af666e01035ce6d5 |

  @SuccessTest
  Scenario Outline: an Admin is able to delete the requested payment method
    Given the API base URL
    When I send a DEL request with "<id>" to delete the payment method
    Then the response status code should be 200 due to payment method deleted
    And the success message is shown for deleting a payment method
    Examples:
      | id |
      | 68d25543af666e01035ce6d5 |