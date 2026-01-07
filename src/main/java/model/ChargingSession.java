package model;
import java.util.UUID;

public class ChargingSession {
    private String id = UUID.randomUUID().toString();
    private Customer customer;
    private Charger charger;

    public ChargingSession(Customer c, Charger ch) {
        this.customer = c;
        this.charger = ch;
    }

    public String getId() { return id; }
    public Customer getCustomer() { return customer; }
    public Charger getCharger() { return charger; }
}
