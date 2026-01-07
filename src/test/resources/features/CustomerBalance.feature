Feature: Customer Balance Management
  As a registered customer
  I want to view and add balance to my account
  So that I can pay for charging sessions in advance

  Background:
    Given the following customers exist:
      | id | firstName | lastName   |
      | C1 | Max       | Mustermann |
      | C2 | Anna      | Huber      |
      | C3 | Lukas     | Gruber     |
      | C4 | Sarah     | Novak      |
      | C5 | Paul      | Steiner    |
    And the following customer balances exist:
      | id | balance |
      | C1 | 20.0    |
      | C2 | 15.0    |
      | C3 | 30.0    |
      | C4 | 50.0    |
      | C5 | 10.0    |

  Scenario: View balance of all customers
    When the operator requests all customer balances
    Then the system should return the following balances:
      | id | balance |
      | C1 | 20.0    |
      | C2 | 15.0    |
      | C3 | 30.0    |
      | C4 | 50.0    |
      | C5 | 10.0    |

  Scenario: Add balance to a customer
    When customer "C2" adds 25.0 to the balance
    Then customer "C2" should have balance 40.0

  Scenario: Charging is rejected when balance is insufficient
    Given customer "C5" has balance 0.0
    When customer "C5" tries to start a charging session
    Then the charging session should be rejected
