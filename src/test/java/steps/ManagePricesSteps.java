package steps;

import io.cucumber.java.Before;
import io.cucumber.java.en.*;
import org.junit.jupiter.api.Assertions;

import java.util.Map;

public class ManagePricesSteps {

    private SharedTestContext ctx;

    @Before
    public void setup() {
        ctx = SharedTestContext.getInstance();
    }

    @When("I set price for location {string} to {double} perKwh and {double} perMinute")
    public void i_set_price_for_location(String locationId, Double perKwh, Double perMinute) {
        Assertions.assertTrue(ctx.locations.containsKey(locationId), "Location does not exist: " + locationId);
        Map<String, Double> priceMap = new java.util.HashMap<>();
        priceMap.put("perKwh", perKwh);
        priceMap.put("perMinute", perMinute);
        ctx.prices.put(locationId, priceMap);
    }

    @Then("location {string} has price {double} perKwh")
    public void location_has_price_perKwh(String locationId, Double expectedPerKwh) {
        Assertions.assertTrue(ctx.prices.containsKey(locationId), "No prices for location: " + locationId);
        Double actual = ctx.prices.get(locationId).get("perKwh");
        Assertions.assertEquals(expectedPerKwh, actual, 0.0001);
    }

    @When("I update price for location {string} to {double} perKwh and {double} perMinute")
    public void i_update_price_for_location(String locationId, Double perKwh, Double perMinute) {
        Assertions.assertTrue(ctx.prices.containsKey(locationId), "No existing price entry to update for location: " + locationId);
        Map<String, Double> priceMap = ctx.prices.get(locationId);
        priceMap.put("perKwh", perKwh);
        priceMap.put("perMinute", perMinute);
    }

    @Given("location {string} has price {double} perKwh and {double} perMinute")
    public void location_has_price_perKwh_and_perMinute(String locationId, Double perKwh, Double perMinute) {
        Map<String, Double> priceMap = new java.util.HashMap<>();
        priceMap.put("perKwh", perKwh);
        priceMap.put("perMinute", perMinute);
        ctx.prices.put(locationId, priceMap);
        ctx.locations.putIfAbsent(locationId, new java.util.HashMap<>());
    }
}
