package model;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        InMemoryRepo repo = InMemoryRepo.instance();
        repo.clear();

        CustomerManager cm = new CustomerManager();
        LocationManager lm = new LocationManager();
        SessionManager sm = new SessionManager();
        PriceManager pm = new PriceManager();
        InvoiceManager im = new InvoiceManager();

        // ================= INITIAL DATA =================

        // Locations
        lm.addLocation("Simmering", "Simmeringer Hauptstraße 1");
        lm.addLocation("Favoriten", "Favoritenstraße 10");
        lm.addLocation("Döbling", "Döblinger Hauptstraße 5");
        lm.addLocation("Brigittenau", "Brigittenauer Lände 3");
        lm.addLocation("Ottakring", "Ottakringer Straße 50");

        // Chargers (1 AC + 1 DC per location)
        lm.addCharger("Simmering", new Charger("SIM-AC-1", ChargerType.AC));
        lm.addCharger("Simmering", new Charger("SIM-DC-1", ChargerType.DC));

        lm.addCharger("Favoriten", new Charger("FAV-AC-1", ChargerType.AC));
        lm.addCharger("Favoriten", new Charger("FAV-DC-1", ChargerType.DC));

        lm.addCharger("Döbling", new Charger("DOB-AC-1", ChargerType.AC));
        lm.addCharger("Döbling", new Charger("DOB-DC-1", ChargerType.DC));

        lm.addCharger("Brigittenau", new Charger("BRI-AC-1", ChargerType.AC));
        lm.addCharger("Brigittenau", new Charger("BRI-DC-1", ChargerType.DC));

        lm.addCharger("Ottakring", new Charger("OTT-AC-1", ChargerType.AC));
        lm.addCharger("Ottakring", new Charger("OTT-DC-1", ChargerType.DC));

        // Customers
        cm.register("C1", "Max Mustermann");
        cm.register("C2", "Anna Huber");
        cm.register("C3", "Lukas Gruber");
        cm.register("C4", "Sarah Novak");
        cm.register("C5", "Paul Steiner");

        cm.topUp("C1", 100);
        cm.topUp("C2", 50);
        cm.topUp("C3", 30);
        cm.topUp("C4", 80);
        cm.topUp("C5", 10);

        // Prices
        pm.setLocationPrice("Simmering", 0.35, 0.05);
        pm.setLocationPrice("Favoriten", 0.34, 0.05);
        pm.setLocationPrice("Döbling", 0.33, 0.05);
        pm.setLocationPrice("Brigittenau", 0.36, 0.05);
        pm.setLocationPrice("Ottakring", 0.32, 0.05);

        // ================= ROLE SELECTION =================

        boolean running = true;

        while (running) {
            System.out.println("\nSelect role:");
            System.out.println("1 - Manager");
            System.out.println("2 - Customer");
            System.out.println("0 - Exit");

            int role = sc.nextInt();
            sc.nextLine();

            switch (role) {

                // ========== MANAGER ==========
                case 1 -> {
                    boolean managerMenu = true;
                    while (managerMenu) {
                        System.out.println("\n--- MANAGER MENU ---");
                        System.out.println("1 - Show all locations");
                        System.out.println("2 - Show chargers of a location");
                        System.out.println("3 - Update charger status");
                        System.out.println("4 - Show all customers");
                        System.out.println("0 - Back");

                        int m = sc.nextInt();
                        sc.nextLine();

                        switch (m) {
                            case 1 -> lm.getLocations()
                                    .forEach(l -> System.out.println(l.getName()));

                            case 2 -> {
                                System.out.print("Location name: ");
                                String loc = sc.nextLine();
                                lm.getChargersByLocation(loc)
                                        .forEach(c -> System.out.println(
                                                c.getId() + " | " + c.getType() + " | " + c.getStatus()));
                            }

                            case 3 -> {
                                System.out.print("Charger ID: ");
                                String cid = sc.nextLine();
                                System.out.print("New status (FREE/OCCUPIED/OUT_OF_ORDER): ");
                                String st = sc.nextLine();
                                lm.updateChargerStatus(cid, Status.valueOf(st));
                                System.out.println("Status updated.");
                            }

                            case 4 -> cm.getAll()
                                    .forEach(c -> System.out.println(
                                            c.getId() + " | Balance: " + c.getBalance()));

                            case 0 -> managerMenu = false;
                        }
                    }
                }

                // ========== CUSTOMER ==========
                case 2 -> {
                    System.out.print("Enter your customer ID: ");
                    String customerId = sc.nextLine();

                    boolean customerMenu = true;
                    while (customerMenu) {
                        System.out.println("\n--- CUSTOMER MENU ---");
                        System.out.println("1 - Show my balance");
                        System.out.println("2 - Top up balance");
                        System.out.println("3 - Start charging");
                        System.out.println("4 - End charging");
                        System.out.println("5 - View invoice");
                        System.out.println("0 - Back");

                        int c = sc.nextInt();
                        sc.nextLine();

                        switch (c) {
                            case 1 -> System.out.println(
                                    "Balance: " + cm.find(customerId).getBalance());

                            case 2 -> {
                                System.out.print("Amount: ");
                                double amt = sc.nextDouble();
                                cm.topUp(customerId, amt);
                                System.out.println("Balance updated.");
                            }

                            case 3 -> {
                                System.out.print("Charger ID: ");
                                String ch = sc.nextLine();
                                try {
                                    ChargingSession s = sm.startSession(customerId, ch);
                                    System.out.println("Session started: " + s.getId());
                                } catch (Exception e) {
                                    System.out.println("ERROR: " + e.getMessage());
                                }
                            }

                            case 4 -> {
                                System.out.print("Session ID: ");
                                String sid = sc.nextLine();
                                System.out.print("Energy (kWh): ");
                                double kwh = sc.nextDouble();
                                sm.endSession(sid, kwh);
                                System.out.println("Session ended.");
                            }

                            case 5 -> {
                                Invoice inv = im.generateInvoiceForCustomer(customerId);
                                System.out.println("Invoice total: " + inv.getTotal());
                            }

                            case 0 -> customerMenu = false;
                        }
                    }
                }

                case 0 -> running = false;
            }
        }

        System.out.println("Program terminated.");
    }
}

