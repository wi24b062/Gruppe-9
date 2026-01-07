Feature: Charger Status Management
  As an operator
  I want to set and view the status of chargers
  So that I can monitor charger availability in real time

  Background:
    Given the following locations exist:
      | name         |
      | Simmering    |
      | Favoriten    |
      | Döbling      |
      | Brigittenau  |
      | Ottakring    |
    And the following chargers exist:
      | id        | type | location     |
      | SIM-AC-1  | AC   | Simmering    |
      | SIM-DC-1  | DC   | Simmering    |
      | FAV-AC-1  | AC   | Favoriten    |
      | FAV-DC-1  | DC   | Favoriten    |
      | DOB-AC-1  | AC   | Döbling      |
      | DOB-DC-1  | DC   | Döbling      |
      | BRI-AC-1  | AC   | Brigittenau  |
      | BRI-DC-1  | DC   | Brigittenau  |
      | OTT-AC-1  | AC   | Ottakring    |
      | OTT-DC-1  | DC   | Ottakring    |

  Scenario: Set charger status to occupied
    Given charger "SIM-AC-1" has status "free"
    When a charging session starts at charger "SIM-AC-1"
    Then the charger "SIM-AC-1" should have status "occupied"

  Scenario: Set charger status back to free after charging
    Given charger "SIM-AC-1" has status "occupied"
    When the charging session at charger "SIM-AC-1" ends
    Then the charger "SIM-AC-1" should have status "free"

  Scenario: Set charger status to out of order
    When the operator sets charger "FAV-DC-1" to status "out_of_order"
    Then the charger "FAV-DC-1" should have status "out_of_order"

  Scenario: View status of all chargers
    When the operator requests the status of all chargers
    Then the system should return the following charger statuses:
      | id        | status        |
      | SIM-AC-1  | free          |
      | SIM-DC-1  | free          |
      | FAV-AC-1  | free          |
      | FAV-DC-1  | out_of_order  |
      | DOB-AC-1  | free          |
      | DOB-DC-1  | free          |
      | BRI-AC-1  | free          |
      | BRI-DC-1  | free          |
      | OTT-AC-1  | free          |
      | OTT-DC-1  | free          |
