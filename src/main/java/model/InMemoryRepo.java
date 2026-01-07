package model;

import java.util.*;

public class InMemoryRepo {

    private static InMemoryRepo INSTANCE = new InMemoryRepo();

    public List<Location> locations = new ArrayList<>();
    public List<Customer> customers = new ArrayList<>();
    public List<ChargingSession> sessions = new ArrayList<>();

    public static InMemoryRepo instance() {
        return INSTANCE;
    }

    public void clear() {
        locations.clear();
        customers.clear();
        sessions.clear();
    }
}

