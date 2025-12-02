Feature: Manage Prices
  Background:
    Given the system is empty
    And a location "Central" with address "Main St 1" exists

  Scenario: Set price for a location
    When I set price for location "Central" to 0.30 perKwh and 0.05 perMinute
    Then location "Central" has price 0.30 perKwh

  Scenario: Update price for a location
    Given location "Central" has price 0.30 perKwh and 0.05 perMinute
    When I update price for location "Central" to 0.40 perKwh and 0.06 perMinute
    Then location "Central" has price 0.40 perKwh