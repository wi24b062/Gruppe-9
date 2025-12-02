package steps;

import io.cucumber.java.Before;
import io.cucumber.java.en.*;
import org.junit.jupiter.api.Assertions;

import java.util.*;

public class ManageLocationSteps {

    private SharedTestContext ctx;
    private List<Map<String, Object>> lastRequestedLocations;

    @Before
    public void setup() {
        ctx = SharedTestContext.getInstance();
        lastRequestedLocations = new ArrayList<>();
    }

    @When("I add a location {string} with address {string}")
    public void i_add_a_location_with_address(String id, String address) {
        Map<String, Object> loc = new HashMap<>();
        loc.put("id", id);
        loc.put("address", address);
        ctx.locations.put(id, loc);
    }

    @Then("location {string} should exist")
    public void location_should_exist(String id) {
        Assertions.assertTrue(ctx.locations.containsKey(id), "Expected location to exist: " + id);
    }

    @When("I request all locations")
    public void i_request_all_locations() {
        lastRequestedLocations.clear();
        for (Map<String, Object> loc : ctx.locations.values()) {
            lastRequestedLocations.add(new HashMap<>(loc));
        }
    }

    @Then("I should see {int} locations")
    public void i_should_see_locations(Integer expectedCount) {
        Assertions.assertEquals(expectedCount.intValue(), lastRequestedLocations.size());
    }

    @When("I rename location {string} to {string}")
    public void i_rename_location_to(String oldId, String newId) {
        Map<String, Object> loc = ctx.locations.remove(oldId);
        Assertions.assertNotNull(loc, "Location not found: " + oldId);
        loc.put("id", newId);
        ctx.locations.put(newId, loc);
    }

    @When("I delete location {string}")
    public void i_delete_location(String id) {
        ctx.locations.remove(id);
    }

    @Then("location {string} should not exist")
    public void location_should_not_exist(String id) {
        Assertions.assertFalse(ctx.locations.containsKey(id), "Location still exists: " + id);
    }
}
