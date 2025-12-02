package steps;

import io.cucumber.java.Before;
import io.cucumber.java.en.*;
import org.junit.jupiter.api.Assertions;

import java.util.Map;

public class CustomerManagementSteps {

    private SharedTestContext ctx;
    private String lastRequestedCustomerId;

    @Before
    public void setup() {
        ctx = SharedTestContext.getInstance();
        lastRequestedCustomerId = null;
    }

    @When("I register a customer with id {string} and name {string}")
    public void i_register_a_customer_with_id_and_name(String id, String name) {
        Map<String, Object> c = new java.util.HashMap<>();
        c.put("id", id);
        c.put("name", name);
        c.put("status", "active");
        ctx.customers.put(id, c);
    }

    @Then("the customer {string} exists")
    public void the_customer_exists(String id) {
        Assertions.assertTrue(ctx.customers.containsKey(id), "Expected customer to exist: " + id);
    }

    @When("I request the customer info for {string}")
    public void i_request_the_customer_info_for(String id) {
        Assertions.assertTrue(ctx.customers.containsKey(id), "Customer not found: " + id);
        lastRequestedCustomerId = id;
    }

    @Then("the customer name should be {string}")
    public void the_customer_name_should_be(String expectedName) {
        Assertions.assertNotNull(lastRequestedCustomerId, "No customer was requested");
        Map<String, Object> c = ctx.customers.get(lastRequestedCustomerId);
        Assertions.assertNotNull(c, "Requested customer not found: " + lastRequestedCustomerId);
        Assertions.assertEquals(expectedName, c.get("name"));
    }

    @When("I change customer {string} name to {string}")
    public void i_change_customer_name_to(String id, String newName) {
        Map<String, Object> c = ctx.customers.get(id);
        Assertions.assertNotNull(c, "Customer not found: " + id);
        c.put("name", newName);
    }

    @When("I deactivate customer {string}")
    public void i_deactivate_customer(String id) {
        Map<String, Object> c = ctx.customers.get(id);
        Assertions.assertNotNull(c, "Customer not found: " + id);
        c.put("status", "inactive");
    }

    @Then("customer {string} should be inactive")
    public void customer_should_be_inactive(String id) {
        Map<String, Object> c = ctx.customers.get(id);
        Assertions.assertNotNull(c, "Customer not found: " + id);
        Assertions.assertEquals("inactive", c.get("status"));
    }

    @When("I delete customer {string}")
    public void i_delete_customer(String id) {
        ctx.customers.remove(id);
        if (id.equals(lastRequestedCustomerId)) lastRequestedCustomerId = null;
    }

    @Then("customer {string} should not exist")
    public void customer_should_not_exist(String id) {
        Assertions.assertFalse(ctx.customers.containsKey(id), "Customer still exists: " + id);
    }
}
