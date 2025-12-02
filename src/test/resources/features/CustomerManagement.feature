Feature: Customer Management
  As an operator
  I want to manage customers
  So that I can maintain the customer database

  Background:
    Given the system is empty

  Scenario: Register a new customer
    When I register a customer with id "C1" and name "Max"
    Then the customer "C1" exists

  Scenario: View customer info
    Given a customer with id "C1" and name "Max" exists
    When I request the customer info for "C1"
    Then the customer name should be "Max"

  Scenario: Edit customer
    Given a customer with id "C1" and name "Max" exists
    When I change customer "C1" name to "Maximilian"
    Then the customer name should be "Maximilian"

  Scenario: Deactivate customer
    Given a customer with id "C1" and name "Max" exists
    When I deactivate customer "C1"
    Then customer "C1" should be inactive

  Scenario: Delete customer
    Given a customer with id "C1" and name "Max" exists
    When I delete customer "C1"
    Then customer "C1" should not exist