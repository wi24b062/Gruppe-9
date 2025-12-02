package steps;

import java.util.*;

public class SharedTestContext {

    private static final SharedTestContext INSTANCE = new SharedTestContext();

    // public für einfachen Zugriff in Steps; du kannst auch Getter/Setter verwenden
    public final Map<String, Map<String, Object>> customers = new HashMap<>();
    public final Map<String, Map<String, Object>> locations = new HashMap<>();
    public final Map<String, Map<String, Object>> chargers = new HashMap<>();
    public final Map<String, Map<String, Double>> prices = new HashMap<>();
    public final List<Map<String, Object>> sessions = new ArrayList<>();
    public final Map<String, Map<String, Object>> invoices = new HashMap<>();

    private SharedTestContext() { }

    public static SharedTestContext getInstance() {
        return INSTANCE;
    }

    public void clearAll() {
        customers.clear();
        locations.clear();
        chargers.clear();
        prices.clear();
        sessions.clear();
        invoices.clear();
    }
}
