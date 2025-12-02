Feature: Manage Chargers
  Background:
    Given the system is empty
    And a location "Central" with address "Main St 1" exists

  Scenario: Add a charger to a location
    When I add a charger "CP-1" of type "AC" to location "Central"
    Then location "Central" exists with 1 chargers

  Scenario: View available chargers
    Given a location "Central" with chargers:
      | id   | type |
      | CP-1 | AC   |
      | CP-2 | DC   |
    When I request available chargers for "Central"
    Then I should see 2 chargers

  Scenario: Update charger type
    Given a charger "CP-1" of type "AC" exists at location "Central"
    When I update charger "CP-1" type to "DC"
    Then charger "CP-1" should have type "DC"