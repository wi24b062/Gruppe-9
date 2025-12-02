Feature: Customer Invoice
  Background:
    Given the system is empty
    And a customer with id "C1" and name "Max" exists
    And a location "Central" with charger "CP-1" of type "AC" and price 0.30 perKwh exists

  Scenario: Invoice after session
    When the customer "C1" starts a charging session at "CP-1"
    And the customer ends the charging session after 30 minutes with 5.0 kWh charged
    Then an invoice for "C1" exists with total > 0