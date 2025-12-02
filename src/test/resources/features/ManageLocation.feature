Feature: Manage Locations
  Background:
    Given the system is empty

  Scenario: Add location
    When I add a location "Central" with address "Main St 1"
    Then location "Central" should exist

  Scenario: View all locations
    Given a location "Central" with address "A" exists
    And a location "West" with address "B" exists
    When I request all locations
    Then I should see 2 locations

  Scenario: Update location
    Given a location "Central" with address "A" exists
    When I rename location "Central" to "Central Station"
    Then location "Central Station" should exist

  Scenario: Delete location
    Given a location "West" with address "B" exists
    When I delete location "West"
    Then location "West" should not exist