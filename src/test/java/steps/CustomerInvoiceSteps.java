package steps;

import io.cucumber.java.Before;
import io.cucumber.java.en.*;
import org.junit.jupiter.api.Assertions;

import java.util.*;

public class CustomerInvoiceSteps {

    private SharedTestContext ctx;

    @Before
    public void setup() {
        ctx = SharedTestContext.getInstance();
    }

    @When("the customer {string} starts a charging session at {string}")
    public void the_customer_starts_a_charging_session_at(String customerId, String chargerId) {
        Assertions.assertTrue(ctx.customers.containsKey(customerId), "Customer does not exist");
        Assertions.assertTrue(ctx.chargers.containsKey(chargerId), "Charger does not exist");

        Map<String, Object> charger = ctx.chargers.get(chargerId);
        charger.put("status", "occupied");

        Map<String, Object> session = new HashMap<>();
        session.put("customerId", customerId);
        session.put("chargerId", chargerId);
        session.put("kwh", 0.0);
        session.put("minutes", 0);
        session.put("finished", false);
        ctx.sessions.add(session);
    }

    @When("the customer ends the charging session after {int} minutes with {double} kWh charged")
    public void the_customer_ends_the_charging_session_after(Integer minutes, Double kwh) {
        Map<String, Object> session = ctx.sessions.stream()
                .filter(s -> !((Boolean) s.getOrDefault("finished", false)))
                .reduce((first, second) -> second).orElseThrow(() -> new AssertionError("No active session found"));
        String chargerId = (String) session.get("chargerId");
        String customerId = (String) session.get("customerId");
        Map<String, Object> charger = ctx.chargers.get(chargerId);
        double price = charger.get("pricePerKwh") instanceof Number ? ((Number) charger.get("pricePerKwh")).doubleValue() : 0.0;
        double cost = kwh * price;
        session.put("minutes", minutes);
        session.put("kwh", kwh);
        session.put("cost", cost);
        session.put("finished", true);
        charger.put("status", "free");

        Map<String, Object> invoice = ctx.invoices.computeIfAbsent(customerId, k -> new HashMap<>());
        double total = ((Number) invoice.getOrDefault("total", 0.0)).doubleValue() + cost;
        invoice.put("total", total);
    }

    @Then("an invoice for {string} exists with total > {int}")
    public void an_invoice_for_exists_with_total_greater_than(String customerId, Integer threshold) {
        Assertions.assertTrue(ctx.invoices.containsKey(customerId), "No invoice for customer");
        double total = ((Number) ctx.invoices.get(customerId).getOrDefault("total", 0.0)).doubleValue();
        Assertions.assertTrue(total > threshold, "Invoice total " + total + " is not > " + threshold);
    }
}

