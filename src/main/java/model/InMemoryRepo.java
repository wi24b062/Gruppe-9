package model;
import java.util.*;

public class InMemoryRepo {
    private static final InMemoryRepo INSTANCE = new InMemoryRepo();
    private final Map<String, Customer> customers = new HashMap<>();
    private final Map<String, Location> locations = new HashMap<>();
    private final Map<String, Charger> points = new HashMap<>();
    private final List<ChargingSession> sessions = new ArrayList<>();

    private InMemoryRepo(){}

    public static InMemoryRepo instance(){ return INSTANCE; }
    public void clear(){ customers.clear(); locations.clear(); points.clear(); sessions.clear(); }

    // customers
    public void saveCustomer(Customer c){ customers.put(c.getId(), c); }
    public Customer findCustomerById(String id){ return customers.get(id); }

    // locations
    public void saveLocation(Location l){ locations.put(l.getName(), l); }
    public Location findLocationByName(String name){ return locations.get(name); }

    // points
    public void saveChargingPoint(Charger p){ points.put(p.getId(), p); }
    public Charger findPointById(String id){ return points.get(id); }

    // sessions
    public void saveSession(ChargingSession s){ sessions.add(s); }
    public List<ChargingSession> getSessions(){ return Collections.unmodifiableList(sessions); }
}
