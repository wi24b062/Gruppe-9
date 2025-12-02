package steps;

import io.cucumber.java.Before;
import io.cucumber.java.en.*;
import org.junit.jupiter.api.Assertions;

import java.util.Map;

public class CustomerBalanceSteps {

    private SharedTestContext ctx;
    private String lastRequestedCustomerId;

    @Before
    public void setup() {
        ctx = SharedTestContext.getInstance();
        lastRequestedCustomerId = null;
    }

    @When("I add {double} to customer {string}")
    public void i_add_to_customer(Double amount, String id) {
        Map<String, Object> c = ctx.customers.get(id);
        Assertions.assertNotNull(c, "Customer not found: " + id);
        double bal = ((Number) c.getOrDefault("balance", 0.0)).doubleValue();
        c.put("balance", bal + amount);
    }

    @Then("customer {string} should have balance {double}")
    public void customer_should_have_balance(String id, Double expected) {
        Map<String, Object> c = ctx.customers.get(id);
        Assertions.assertNotNull(c, "Customer not found: " + id);
        double bal = ((Number) c.getOrDefault("balance", 0.0)).doubleValue();
        Assertions.assertEquals(expected, bal, 0.0001);
    }

    @When("I request the balance of {string}")
    public void i_request_the_balance_of(String id) {
        Assertions.assertTrue(ctx.customers.containsKey(id), "Customer not found: " + id);
        lastRequestedCustomerId = id;
    }

    @Then("the balance should be {double}")
    public void the_balance_should_be(Double expected) {
        Assertions.assertNotNull(lastRequestedCustomerId, "No customer was requested");
        double bal = ((Number) ctx.customers.get(lastRequestedCustomerId).getOrDefault("balance", 0.0)).doubleValue();
        Assertions.assertEquals(expected, bal, 0.0001, "Balance mismatch");
    }
}
