package steps;

import io.cucumber.java.Before;
import io.cucumber.java.en.Given;

import java.util.*;

/**
 * Zentrale/gemeinsame Step-Definitions, die in mehreren Feature-Dateien
 * verwendet werden (verhindert DuplicateStepDefinitionException).
 *
 * Entferne die gleichen @Given(...) Methoden aus den anderen Step-Klassen,
 * damit nur diese Klasse die Definition besitzt.
 */
public class StepDefinitions {

    private final SharedTestContext ctx = SharedTestContext.getInstance();

    @Before
    public void beforeScenario() {
        // optional: nicht automatisch clearen hier — wir bieten clearAll() per Given an
    }

    @Given("the system is empty")
    public void the_system_is_empty() {
        ctx.clearAll();
    }

    @Given("a customer with id {string} and name {string} exists")
    public void a_customer_with_id_and_name_exists(String id, String name) {
        Map<String, Object> customer = new HashMap<>();
        customer.put("id", id);
        customer.put("name", name);
        customer.put("balance", 0.0);
        customer.put("status", "active");
        ctx.customers.put(id, customer);
    }

    @Given("a customer {string} exists with balance {double}")
    public void a_customer_exists_with_balance(String id, double balance) {
        Map<String, Object> customer = new HashMap<>();
        customer.put("id", id);
        customer.put("balance", balance);
        customer.put("status", "active");
        ctx.customers.put(id, customer);
    }

    @Given("a location {string} with address {string} exists")
    public void a_location_with_address_exists(String locationId, String address) {
        Map<String, Object> loc = new HashMap<>();
        loc.put("id", locationId);
        loc.put("address", address);
        loc.put("chargers", new ArrayList<String>());
        ctx.locations.put(locationId, loc);
    }

    @Given("a location {string} with charger {string} of type {string} and price {double} perKwh exists")
    public void a_location_with_charger_of_type_and_price_perKwh_exists(String locationId, String chargerId, String type, double pricePerKwh) {
        // create location if not present
        ctx.locations.putIfAbsent(locationId, new HashMap<>());
        Map<String, Object> loc = ctx.locations.get(locationId);
        loc.putIfAbsent("chargers", new ArrayList<String>());
        @SuppressWarnings("unchecked")
        List<String> list = (List<String>) loc.get("chargers");
        if (!list.contains(chargerId)) list.add(chargerId);

        // create charger
        Map<String, Object> ch = new HashMap<>();
        ch.put("id", chargerId);
        ch.put("type", type);
        ch.put("pricePerKwh", pricePerKwh);
        ch.put("status", "in operation free");
        ch.put("location", locationId);
        ctx.chargers.put(chargerId, ch);

        // ensure price map for location (optional)
        Map<String, Double> priceMap = ctx.prices.computeIfAbsent(locationId, k -> new HashMap<>());
        priceMap.put(type + "_perKwh", pricePerKwh);
    }

    // Du kannst hier noch weitere oft genutzte @Given Methoden hinzufügen,
    // z.B. "location X has price Y", "charger CP-1 is free", etc.
}

