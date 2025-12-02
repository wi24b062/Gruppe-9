package steps;

import io.cucumber.java.Before;
import io.cucumber.java.en.*;
import org.junit.jupiter.api.Assertions;

import java.util.*;

public class ChargingSessionsSteps {

    private SharedTestContext ctx;
    private Map<String, Object> lastSession;
    private Map<String, Object> lastEndedSession;

    @Before
    public void setup() {
        ctx = SharedTestContext.getInstance();
        lastSession = null;
        lastEndedSession = null;
    }

    @When("the customer {string} starts a charging session at {string}")
    public void the_customer_starts_a_charging_session_at(String customerId, String chargerId) {
        Assertions.assertTrue(ctx.customers.containsKey(customerId), "Customer must exist: " + customerId);
        Assertions.assertTrue(ctx.chargers.containsKey(chargerId), "Charger must exist: " + chargerId);

        Map<String, Object> ch = ctx.chargers.get(chargerId);
        String status = (String) ch.getOrDefault("status", "in operation free");
        Assertions.assertTrue(status.contains("free"), "Charger must be free: " + status);

        Map<String, Object> session = new HashMap<>();
        session.put("customerId", customerId);
        session.put("chargerId", chargerId);
        session.put("startTime", System.currentTimeMillis());
        session.put("endTime", null);
        session.put("kwh", 0.0);
        session.put("minutes", 0);
        ctx.sessions.add(session);
        lastSession = session;

        ch.put("status", "in operation occupied");
    }

    @When("the customer ends the charging session after {int} minutes with {double} kWh charged")
    public void the_customer_ends_the_charging_session_after_minutes_with_kwh(Integer minutes, Double kwh) {
        Optional<Map<String, Object>> opt = ctx.sessions.stream()
                .filter(s -> s.get("endTime") == null)
                .reduce((first, second) -> second);
        Assertions.assertTrue(opt.isPresent(), "No active session found");
        Map<String, Object> session = opt.get();

        String chargerId = (String) session.get("chargerId");
        String customerId = (String) session.get("customerId");
        Map<String, Object> ch = ctx.chargers.get(chargerId);

        double pricePerKwh = ch.get("pricePerKwh") instanceof Number ? ((Number) ch.get("pricePerKwh")).doubleValue() : 0.0;
        double cost = kwh * pricePerKwh;

        session.put("endTime", System.currentTimeMillis());
        session.put("minutes", minutes);
        session.put("kwh", kwh);
        session.put("cost", cost);

        double oldBal = ((Number) ctx.customers.get(customerId).getOrDefault("balance", 0.0)).doubleValue();
        ctx.customers.get(customerId).put("balance", oldBal - cost);

        Map<String, Object> invoice = ctx.invoices.computeIfAbsent(customerId, k -> new HashMap<>());
        double total = ((Number) invoice.getOrDefault("total", 0.0)).doubleValue() + cost;
        invoice.put("total", total);
        @SuppressWarnings("unchecked")
        List<Map<String, Object>> entries = (List<Map<String, Object>>) invoice.getOrDefault("entries", new ArrayList<Map<String,Object>>());
        Map<String, Object> entry = new HashMap<>();
        entry.put("charger", chargerId);
        entry.put("minutes", minutes);
        entry.put("kwh", kwh);
        entry.put("cost", cost);
        entries.add(entry);
        invoice.put("entries", entries);

        ch.put("status", "in operation free");
        lastEndedSession = session;
    }

    @Then("the customer's balance should be reduced by {string}")
    public void the_customers_balance_should_be_reduced_by(String amountExpression) {
        double expected = evaluateSimpleExpression(amountExpression);

        Assertions.assertNotNull(lastEndedSession, "No ended session recorded");
        String customerId = (String) lastEndedSession.get("customerId");
        double current = ((Number) ctx.customers.get(customerId).getOrDefault("balance", 0.0)).doubleValue();
        double initial = current + expected;
        double actualReduction = initial - current;

        Assertions.assertEquals(expected, actualReduction, 0.0001);
    }

    @Then("an invoice exists for {string} with total > {int}")
    public void an_invoice_exists_for_with_total_greater_than(String customerId, Integer threshold) {
        Assertions.assertTrue(ctx.invoices.containsKey(customerId), "No invoice found for " + customerId);
        double total = ((Number) ctx.invoices.get(customerId).getOrDefault("total", 0.0)).doubleValue();
        Assertions.assertTrue(total > threshold, "Invoice total not > " + threshold);
    }

    @Given("charger {string} is free")
    public void charger_is_free(String chargerId) {
        Map<String, Object> ch = ctx.chargers.get(chargerId);
        Assertions.assertNotNull(ch, "Charger must exist: " + chargerId);
        ch.put("status", "in operation free");
    }

    @Then("charger {string} should be occupied")
    public void charger_should_be_occupied(String chargerId) {
        Map<String, Object> ch = ctx.chargers.get(chargerId);
        Assertions.assertNotNull(ch, "Charger must exist: " + chargerId);
        Assertions.assertTrue(ch.get("status").toString().contains("occupied"));
    }

    @Then("charger {string} should be free")
    public void charger_should_be_free(String chargerId) {
        Map<String, Object> ch = ctx.chargers.get(chargerId);
        Assertions.assertNotNull(ch, "Charger must exist: " + chargerId);
        Assertions.assertTrue(ch.get("status").toString().contains("free"));
    }

    private double evaluateSimpleExpression(String expr) {
        String s = expr.trim();
        if (s.contains("*")) {
            String[] parts = s.split("\\*");
            double a = Double.parseDouble(parts[0].trim());
            double b = Double.parseDouble(parts[1].trim());
            return a * b;
        } else if (s.contains("+")) {
            String[] parts = s.split("\\+");
            double sum = 0.0;
            for (String p : parts) sum += Double.parseDouble(p.trim());
            return sum;
        } else {
            return Double.parseDouble(s);
        }
    }
}
