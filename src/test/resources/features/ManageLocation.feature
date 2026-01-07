Feature: Location Management
  As an operator
  I want to create, update, delete and view charging locations
  So that I can manage the charging network structure

  Background:
    Given the system is empty

  Scenario: Create multiple locations
    When the operator creates the following locations:
      | name        | address                         |
      | Simmering   | Simmeringer Hauptstraße 1        |
      | Favoriten   | Favoritenstraße 10               |
      | Döbling     | Döblinger Hauptstraße 5          |
      | Brigittenau | Brigittenauer Lände 3            |
      | Ottakring   | Ottakringer Straße 50            |
    Then the system should contain the following locations:
      | name        | address                         |
      | Simmering   | Simmeringer Hauptstraße 1        |
      | Favoriten   | Favoritenstraße 10               |
      | Döbling     | Döblinger Hauptstraße 5          |
      | Brigittenau | Brigittenauer Lände 3            |
      | Ottakring   | Ottakringer Straße 50            |

  Scenario: Update an existing location
    Given the following locations exist:
      | name       | address                |
      | Ottakring  | Ottakringer Straße 50  |
    When the operator updates location "Ottakring" to "Meidling" with address "Meidlinger Hauptstraße 20"
    Then the system should contain the following locations:
      | name      | address                      |
      | Meidling  | Meidlinger Hauptstraße 20    |

  Scenario: Delete a location
    Given the following locations exist:
      | name        | address                         |
      | Simmering   | Simmeringer Hauptstraße 1        |
      | Favoriten   | Favoritenstraße 10               |
      | Döbling     | Döblinger Hauptstraße 5          |
      | Brigittenau | Brigittenauer Lände 3            |
      | Ottakring   | Ottakringer Straße 50            |
    When the operator deletes location "Döbling"
    Then the system should contain the following locations:
      | name        | address                         |
      | Simmering   | Simmeringer Hauptstraße 1        |
      | Favoriten   | Favoritenstraße 10               |
      | Brigittenau | Brigittenauer Lände 3            |
      | Ottakring   | Ottakringer Straße 50            |

  Scenario: Creating a location that already exists is rejected
    Given the following locations exist:
      | name      | address                 |
      | Simmering | Simmeringer Hauptstraße 1 |
    When the operator tries to create location "Simmering"
    Then the location creation should be rejected
