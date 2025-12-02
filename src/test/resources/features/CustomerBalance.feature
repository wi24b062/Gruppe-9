Feature: Customer Balance
  Background:
    Given the system is empty
    And a customer with id "C1" and name "Max" exists

  Scenario: Add balance
    When I add 50.0 to customer "C1"
    Then customer "C1" should have balance 50.0

  Scenario: View balance
    When I request the balance of "C1"
    Then the balance should be 0.0