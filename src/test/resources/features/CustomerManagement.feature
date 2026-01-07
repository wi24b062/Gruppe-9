Feature: Customer Management
  As an operator
  I want to create, update, delete and view customers
  So that I can maintain a correct customer database

  Background:
    Given the system is empty

  Scenario: Register multiple customers with personal data
    When the operator registers the following customers:
      | id | firstName | lastName   | address                          | email                       |
      | C1 | Max       | Mustermann | Simmeringer Hauptstraße 10, Wien | max.mustermann@mail.com     |
      | C2 | Anna      | Huber      | Favoritenstraße 45, Wien         | anna.huber@mail.com         |
      | C3 | Lukas     | Gruber     | Döblinger Hauptstraße 22, Wien   | lukas.gruber@mail.com       |
      | C4 | Sarah     | Novak      | Brigittenauer Lände 5, Wien      | sarah.novak@mail.com        |
      | C5 | Paul      | Steiner    | Ottakringer Straße 88, Wien      | paul.steiner@mail.com       |
    Then the system should contain the following customers:
      | id | firstName | lastName   | address                          | email                       |
      | C1 | Max       | Mustermann | Simmeringer Hauptstraße 10, Wien | max.mustermann@mail.com     |
      | C2 | Anna      | Huber      | Favoritenstraße 45, Wien         | anna.huber@mail.com         |
      | C3 | Lukas     | Gruber     | Döblinger Hauptstraße 22, Wien   | lukas.gruber@mail.com       |
      | C4 | Sarah     | Novak      | Brigittenauer Lände 5, Wien      | sarah.novak@mail.com        |
      | C5 | Paul      | Steiner    | Ottakringer Straße 88, Wien      | paul.steiner@mail.com       |

  Scenario: Update customer address
    Given the following customers exist:
      | id | firstName | lastName |
      | C5 | Paul      | Steiner  |
    When the operator updates the address of customer "C5" to "Meidlinger Hauptstraße 1, Wien"
    Then the customer "C5" should have address "Meidlinger Hauptstraße 1, Wien"

  Scenario: Delete a customer
    Given the following customers exist:
      | id |
      | C1 |
      | C2 |
      | C3 |
      | C4 |
      | C5 |
    When the operator deletes customer "C3"
    Then the system should contain the following customers:
      | id |
      | C1 |
      | C2 |
      | C4 |
      | C5 |

  Scenario: Registering a customer with duplicate email is rejected
    Given a customer with email "max.mustermann@mail.com" exists
    When the operator tries to register another customer with the same email
    Then the registration should be rejected
