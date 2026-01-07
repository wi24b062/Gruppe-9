Feature: Network Monitoring
  As an operator
  I want to monitor locations, chargers, statuses and prices
  So that I have a complete overview of the charging network

  Background:
    Given the following locations exist:
      | name         |
      | Simmering    |
      | Favoriten    |
      | Döbling      |
      | Brigittenau  |
      | Ottakring    |
    And the following chargers exist:
      | id        | type | location     | status        |
      | SIM-AC-1  | AC   | Simmering    | free          |
      | SIM-DC-1  | DC   | Simmering    | occupied      |
      | FAV-AC-1  | AC   | Favoriten    | free          |
      | FAV-DC-1  | DC   | Favoriten    | out_of_order  |
      | DOB-AC-1  | AC   | Döbling      | free          |
      | DOB-DC-1  | DC   | Döbling      | free          |
      | BRI-AC-1  | AC   | Brigittenau  | occupied      |
      | BRI-DC-1  | DC   | Brigittenau  | free          |
      | OTT-AC-1  | AC   | Ottakring    | free          |
      | OTT-DC-1  | DC   | Ottakring    | free          |

  Scenario: View all locations
    When the operator requests all locations
    Then the system should return the following locations:
      | name         |
      | Simmering    |
      | Favoriten    |
      | Döbling      |
      | Brigittenau  |
      | Ottakring    |

  Scenario: View all chargers with their status
    When the operator requests all chargers
    Then the system should return the following chargers:
      | id        | type | location     | status        |
      | SIM-AC-1  | AC   | Simmering    | free          |
      | SIM-DC-1  | DC   | Simmering    | occupied      |
      | FAV-AC-1  | AC   | Favoriten    | free          |
      | FAV-DC-1  | DC   | Favoriten    | out_of_order  |
      | DOB-AC-1  | AC   | Döbling      | free          |
      | DOB-DC-1  | DC   | Döbling      | free          |
      | BRI-AC-1  | AC   | Brigittenau  | occupied      |
      | BRI-DC-1  | DC   | Brigittenau  | free          |
      | OTT-AC-1  | AC   | Ottakring    | free          |
      | OTT-DC-1  | DC   | Ottakring    | free          |

  Scenario: Filter chargers by location
    When the operator filters chargers by location "Simmering"
    Then the system should return the following chargers:
      | id        |
      | SIM-AC-1  |
      | SIM-DC-1  |

  Scenario: Filter chargers by status
    When the operator filters chargers by status "free"
    Then the system should return only chargers with status "free"

  Scenario: Filter chargers by type
    When the operator filters chargers by type "DC"
    Then the system should return only chargers with type "DC"

  Scenario: Monitoring an empty network
    Given no locations exist
    When the operator requests network overview
    Then the system should return an empty result

