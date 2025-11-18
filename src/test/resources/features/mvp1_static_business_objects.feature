Feature: Static business objects for Charging Station Network (MVP Part1)

  Background:
    Given the system is empty

  Scenario: Add a Location with two ChargingPoints
    When I add a location "Central Station" with address "Main St 1"
    And I add a charging point "CP-1" of type "AC" to location "Central Station"
    And I add a charging point "CP-2" of type "DC" to location "Central Station"
    Then location "Central Station" exists with 2 charging points