Feature: Price Management
  As an operator
  I want to create, update, delete and view charging prices
  So that customers are billed correctly and transparently

  Background:
    Given the following locations exist:
      | name         |
      | Simmering    |
      | Favoriten    |
      | Döbling      |
      | Brigittenau  |
      | Ottakring    |

  Scenario: Create prices for each location and charger type
    When the operator creates the following prices:
      | location     | chargerType | pricePerKwh |
      | Simmering    | AC          | 0.35        |
      | Simmering    | DC          | 0.55        |
      | Favoriten    | AC          | 0.34        |
      | Favoriten    | DC          | 0.54        |
      | Döbling      | AC          | 0.33        |
      | Döbling      | DC          | 0.53        |
      | Brigittenau  | AC          | 0.36        |
      | Brigittenau  | DC          | 0.56        |
      | Ottakring    | AC          | 0.32        |
      | Ottakring    | DC          | 0.52        |
    Then the system should contain the following prices:
      | location     | chargerType | pricePerKwh |
      | Simmering    | AC          | 0.35        |
      | Simmering    | DC          | 0.55        |
      | Favoriten    | AC          | 0.34        |
      | Favoriten    | DC          | 0.54        |
      | Döbling      | AC          | 0.33        |
      | Döbling      | DC          | 0.53        |
      | Brigittenau  | AC          | 0.36        |
      | Brigittenau  | DC          | 0.56        |
      | Ottakring    | AC          | 0.32        |
      | Ottakring    | DC          | 0.52        |

  Scenario: Update price for a location and charger type
    Given the following prices exist:
      | location  | chargerType | pricePerKwh |
      | Ottakring | AC          | 0.32        |
    When the operator updates the price for "Ottakring" and charger type "AC" to 0.38
    Then the price for "Ottakring" and charger type "AC" should be 0.38

  Scenario: Delete a price configuration
    Given the following prices exist:
      | location  | chargerType | pricePerKwh |
      | Favoriten | DC          | 0.54        |
    When the operator deletes the price for "Favoriten" and charger type "DC"
    Then the system should not contain a price for "Favoriten" and charger type "DC"

  Scenario: View all prices
    When the operator requests all prices
    Then the system should return all prices

  Scenario: Charging fails if no price is defined
    Given no price exists for location "Simmering" and charger type "DC"
    When a customer tries to start a charging session
    Then the charging should be rejected
