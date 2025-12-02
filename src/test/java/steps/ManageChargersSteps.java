package steps;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.Before;
import io.cucumber.java.en.*;
import org.junit.jupiter.api.Assertions;

import java.util.*;

public class ManageChargersSteps {

    private SharedTestContext ctx;
    private String lastRequestedLocation;
    private List<Map<String, Object>> lastRequestedChargers;

    @Before
    public void setup() {
        ctx = SharedTestContext.getInstance();
        lastRequestedLocation = null;
        lastRequestedChargers = new ArrayList<>();
    }

    @When("I add a charger {string} of type {string} to location {string}")
    public void i_add_a_charger_of_type_to_location(String chargerId, String type, String locationId) {
        Map<String, Object> loc = ctx.locations.get(locationId);
        Assertions.assertNotNull(loc, "Location does not exist: " + locationId);

        Map<String, Object> ch = new HashMap<>();
        ch.put("id", chargerId);
        ch.put("type", type);
        ch.put("location", locationId);
        ctx.chargers.put(chargerId, ch);

        @SuppressWarnings("unchecked")
        List<String> list = (List<String>) loc.get("chargers");
        if (list == null) {
            list = new ArrayList<>();
            loc.put("chargers", list);
        }
        if (!list.contains(chargerId)) list.add(chargerId);
    }

    @Then("location {string} exists with {int} chargers")
    public void location_exists_with_chargers(String locationId, Integer expectedCount) {
        Map<String, Object> loc = ctx.locations.get(locationId);
        Assertions.assertNotNull(loc, "Location not found: " + locationId);
        @SuppressWarnings("unchecked")
        List<String> list = (List<String>) loc.getOrDefault("chargers", new ArrayList<String>());
        Assertions.assertEquals(expectedCount.intValue(), list.size());
    }

    @When("I request available chargers for {string}")
    public void i_request_available_chargers_for(String locationId) {
        lastRequestedLocation = locationId;
        lastRequestedChargers.clear();
        Map<String, Object> loc = ctx.locations.get(locationId);
        if (loc == null) return;
        @SuppressWarnings("unchecked")
        List<String> list = (List<String>) loc.getOrDefault("chargers", new ArrayList<String>());
        for (String chId : list) {
            Map<String, Object> ch = ctx.chargers.get(chId);
            if (ch != null) lastRequestedChargers.add(new HashMap<>(ch));
        }
    }

    @Then("I should see {int} chargers")
    public void i_should_see_chargers(Integer expected) {
        Assertions.assertEquals(expected.intValue(), lastRequestedChargers.size());
    }

    @When("I update charger {string} type to {string}")
    public void i_update_charger_type_to(String chargerId, String newType) {
        Map<String, Object> ch = ctx.chargers.get(chargerId);
        Assertions.assertNotNull(ch, "Charger not found: " + chargerId);
        ch.put("type", newType);
    }

    @Then("charger {string} should have type {string}")
    public void charger_should_have_type(String chargerId, String expectedType) {
        Map<String, Object> ch = ctx.chargers.get(chargerId);
        Assertions.assertNotNull(ch, "Charger not found: " + chargerId);
        Assertions.assertEquals(expectedType, ch.get("type"));
    }

    @Given("a location {string} with chargers:")
    public void a_location_with_chargers(String locationId, DataTable table) {
        Map<String, Object> loc = ctx.locations.computeIfAbsent(locationId, k -> {
            Map<String, Object> m = new HashMap<>();
            m.put("id", locationId);
            m.put("address", "");
            m.put("chargers", new ArrayList<String>());
            return m;
        });

        @SuppressWarnings("unchecked")
        List<String> list = (List<String>) loc.get("chargers");

        List<Map<String, String>> rows = table.asMaps(String.class, String.class);
        for (Map<String, String> row : rows) {
            String id = row.get("id");
            String type = row.get("type");
            if (id == null || id.trim().isEmpty()) continue;

            Map<String, Object> ch = new HashMap<>();
            ch.put("id", id);
            ch.put("type", type != null ? type : "");
            ch.put("location", locationId);
            ctx.chargers.put(id, ch);

            if (!list.contains(id)) list.add(id);
        }
    }

    @Given("a charger {string} of type {string} exists at location {string}")
    public void a_charger_of_type_exists_at_location(String chargerId, String type, String locationId) {
        Map<String, Object> loc = ctx.locations.computeIfAbsent(locationId, k -> {
            Map<String, Object> m = new HashMap<>();
            m.put("id", locationId);
            m.put("address", "");
            m.put("chargers", new ArrayList<String>());
            return m;
        });

        Map<String, Object> ch = new HashMap<>();
        ch.put("id", chargerId);
        ch.put("type", type);
        ch.put("location", locationId);
        ctx.chargers.put(chargerId, ch);

        @SuppressWarnings("unchecked")
        List<String> list = (List<String>) loc.get("chargers");
        if (!list.contains(chargerId)) list.add(chargerId);
    }
}
