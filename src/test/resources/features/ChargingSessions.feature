Feature: Charging Sessions and Network Status
  Background:
    Given the system is empty
    And a customer "C1" exists with balance 100.0
    And a location "Central" with charger "CP-1" of type "AC" and price 0.30 perKwh exists

  Scenario: Start and end a charging session
    When the customer "C1" starts a charging session at "CP-1"
    And the customer ends the charging session after 30 minutes with 5.0 kWh charged
    Then the customer's balance should be reduced by 5.0 * 0.30
    And an invoice exists for "C1" with total > 0

  Scenario: Network shows charger status
    Given charger "CP-1" is free
    When the customer "C1" starts a charging session at "CP-1"
    Then charger "CP-1" should be occupied
    When the customer ends the charging session after 10 minutes with 1.0 kWh charged
    Then charger "CP-1" should be free