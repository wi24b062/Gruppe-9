Feature: Charging Session
  As a registered customer
  I want to start and stop charging sessions
  So that I can charge my vehicle and pay for the consumed energy

  Background:
    Given the following locations exist:
      | name         |
      | Simmering    |
      | Favoriten    |
      | Döbling      |
      | Brigittenau  |
      | Ottakring    |
    And the following chargers exist:
      | id        | type | location     | status |
      | SIM-AC-1  | AC   | Simmering    | free   |
      | SIM-DC-1  | DC   | Simmering    | free   |
      | FAV-AC-1  | AC   | Favoriten    | free   |
      | FAV-DC-1  | DC   | Favoriten    | free   |
      | DOB-AC-1  | AC   | Döbling      | free   |
      | DOB-DC-1  | DC   | Döbling      | free   |
      | BRI-AC-1  | AC   | Brigittenau  | free   |
      | BRI-DC-1  | DC   | Brigittenau  | free   |
      | OTT-AC-1  | AC   | Ottakring    | free   |
      | OTT-DC-1  | DC   | Ottakring    | free   |
    And the following customers exist:
      | id | firstName | lastName | balance |
      | C1 | Max       | Mustermann | 60.0    |
      | C2 | Anna      | Huber      | 40.0    |
      | C3 | Lukas     | Gruber     | 30.0    |
      | C4 | Sarah     | Novak      | 80.0    |
      | C5 | Paul      | Steiner    | 20.0    |

  Scenario: Customer starts and ends a charging session successfully
    When customer "C1" starts a charging session at charger "SIM-AC-1"
    And the charging session runs for 45 minutes and consumes 12.0 kWh
    And customer "C1" ends the charging session
    Then the charger "SIM-AC-1" should have status "free"
    And the balance of customer "C1" should be reduced
    And a charging session should be stored for customer "C1"

  Scenario: Charger status changes during charging
    When customer "C2" starts a charging session at charger "FAV-DC-1"
    Then the charger "FAV-DC-1" should have status "occupied"
    When customer "C2" ends the charging session
    Then the charger "FAV-DC-1" should have status "free"

  Scenario: Charging is rejected when charger is out of order
    Given charger "DOB-AC-1" has status "out_of_order"
    When customer "C3" tries to start a charging session at charger "DOB-AC-1"
    Then the charging session should be rejected

  Scenario: Charging is rejected when customer has insufficient balance
    Given customer "C5" has balance 0.0
    When customer "C5" tries to start a charging session at charger "OTT-AC-1"
    Then the charging session should be rejected

  Scenario: View all charging sessions
    When the operator requests all charging sessions
    Then the system should return all charging sessions
