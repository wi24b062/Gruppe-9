Feature: Charger Management
  As an operator
  I want to create, update, delete and view chargers at locations
  So that charging stations are configured correctly

  Background:
    Given the following locations exist:
      | name         |
      | Simmering    |
      | Favoriten    |
      | Döbling      |
      | Brigittenau  |
      | Ottakring    |

  Scenario: Create multiple AC and DC chargers for each location
    When the operator creates the following chargers:
      | id        | type | location     |
      | SIM-AC-1  | AC   | Simmering    |
      | SIM-AC-2  | AC   | Simmering    |
      | SIM-DC-1  | DC   | Simmering    |
      | SIM-DC-2  | DC   | Simmering    |
      | FAV-AC-1  | AC   | Favoriten    |
      | FAV-AC-2  | AC   | Favoriten    |
      | FAV-DC-1  | DC   | Favoriten    |
      | FAV-DC-2  | DC   | Favoriten    |
      | DOB-AC-1  | AC   | Döbling      |
      | DOB-AC-2  | AC   | Döbling      |
      | DOB-DC-1  | DC   | Döbling      |
      | DOB-DC-2  | DC   | Döbling      |
      | BRI-AC-1  | AC   | Brigittenau  |
      | BRI-AC-2  | AC   | Brigittenau  |
      | BRI-DC-1  | DC   | Brigittenau  |
      | BRI-DC-2  | DC   | Brigittenau  |
      | OTT-AC-1  | AC   | Ottakring    |
      | OTT-AC-2  | AC   | Ottakring    |
      | OTT-DC-1  | DC   | Ottakring    |
      | OTT-DC-2  | DC   | Ottakring    |

    Then the system should contain the following chargers:
      | id        | type | location     |
      | SIM-AC-1  | AC   | Simmering    |
      | SIM-AC-2  | AC   | Simmering    |
      | SIM-DC-1  | DC   | Simmering    |
      | SIM-DC-2  | DC   | Simmering    |
      | FAV-AC-1  | AC   | Favoriten    |
      | FAV-AC-2  | AC   | Favoriten    |
      | FAV-DC-1  | DC   | Favoriten    |
      | FAV-DC-2  | DC   | Favoriten    |
      | DOB-AC-1  | AC   | Döbling      |
      | DOB-AC-2  | AC   | Döbling      |
      | DOB-DC-1  | DC   | Döbling      |
      | DOB-DC-2  | DC   | Döbling      |
      | BRI-AC-1  | AC   | Brigittenau  |
      | BRI-AC-2  | AC   | Brigittenau  |
      | BRI-DC-1  | DC   | Brigittenau  |
      | BRI-DC-2  | DC   | Brigittenau  |
      | OTT-AC-1  | AC   | Ottakring    |
      | OTT-AC-2  | AC   | Ottakring    |
      | OTT-DC-1  | DC   | Ottakring    |
      | OTT-DC-2  | DC   | Ottakring    |

  Scenario: Update charger type
    Given the following chargers exist:
      | id        | type | location   |
      | OTT-AC-1  | AC   | Ottakring |
    When the operator updates charger "OTT-AC-1" to type "DC"
    Then the charger "OTT-AC-1" should have type "DC"

  Scenario: Delete a charger
    Given the following chargers exist:
      | id        | type | location     |
      | SIM-AC-1  | AC   | Simmering    |
      | SIM-DC-1  | DC   | Simmering    |
    When the operator deletes charger "SIM-DC-1"
    Then the system should contain the following chargers:
      | id        |
      | SIM-AC-1  |

  Scenario: Deleting a non-existing charger fails
    When the operator tries to delete charger "UNKNOWN"
    Then the deletion should be rejected