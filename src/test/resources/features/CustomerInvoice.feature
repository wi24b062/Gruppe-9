Feature: Customer Invoices
  As a registered customer
  I want to view my invoices and charging history
  So that I can understand my charging costs and payments

  Background:
    Given the following customers exist:
      | id | firstName | lastName |
      | C1 | Max       | Mustermann |
      | C2 | Anna      | Huber      |
      | C3 | Lukas     | Gruber     |
      | C4 | Sarah     | Novak      |
      | C5 | Paul      | Steiner    |
    And the following invoices exist:
      | invoiceId | customerId | location     | chargerId | chargerType | duration | energy | total |
      | I-001     | C1         | Simmering    | SIM-AC-1  | AC          | 45       | 12.0   | 9.60  |
      | I-002     | C2         | Favoriten    | FAV-DC-1  | DC          | 35       | 18.0   | 21.60 |
      | I-003     | C3         | Döbling      | DOB-AC-1  | AC          | 40       | 10.0   | 8.00  |
      | I-004     | C4         | Brigittenau  | BRI-DC-1  | DC          | 50       | 22.0   | 26.40 |
      | I-005     | C5         | Ottakring    | OTT-AC-1  | AC          | 30       | 9.0    | 7.20  |

  Scenario: View invoices of a specific customer
    When customer "C1" requests their invoices
    Then the system should return the following invoices:
      | invoiceId | total |
      | I-001     | 9.60  |

  Scenario: View all invoices
    When the operator requests all invoices
    Then the system should return all invoices

  Scenario: Invoice contains full charging details
    When customer "C2" requests invoice "I-002"
    Then the invoice should contain the following details:
      | location  | chargerId | chargerType | duration | energy | total |

  Scenario: Viewing invoices when none exist
    Given customer "C5" has no invoices
    When customer "C5" requests their invoices
    Then the system should return an empty invoice list

