package steps;


import io.cucumber.java.en.*;
import static org.junit.jupiter.api.Assertions.*;
import model.*;

public class Mvp1Steps {

    // Use simple in-memory repositories (maps) for tests
    private InMemoryRepo repo = InMemoryRepo.instance();

    @Given("the system is empty")
    public void systemIsEmpty() {
        repo.clear();
    }

    @When("I add a location {string} with address {string}")
    public void addLocation(String name, String address) {
        Location loc = new Location(name, address);
        repo.saveLocation(loc);
    }

    @When("I add a charging point {string} of type {string} to location {string}")
    public void addCharger(String pointId, String type, String locationName) {
        Location loc = repo.findLocationByName(locationName);
        assertNotNull("Location must exist");
        Charger cp = new Charger(pointId, ChargerType.valueOf(type));
        loc.addChargingPoint(cp);
        repo.saveChargingPoint(cp);
    }

    @Then("location {string} exists with {int} charging points")
    public void locationHasChargingPoints(String name, int expected) {
        Location loc = repo.findLocationByName(name);
        assertNotNull(loc);
        assertEquals(expected, loc.getChargingPoints().size());
    }

    @When("I set the price for location {string} to {double} per kWh and {double} per minute")
    public void setPrice(String locName, double perKwh, double perMin) {
        Location loc = repo.findLocationByName(locName);
        assertNotNull(loc);
        Price p = new Price(perKwh, perMin);
        loc.setPrice(p);
    }

    @Then("the location {string} has pricePerKwh {double}")
    public void checkPrice(String locName, double expected) {
        Location loc = repo.findLocationByName(locName);
        assertNotNull(loc);
        assertEquals(expected, loc.getPrice().getPricePerKwh(), 0.0001);
    }

    @When("I register a customer with id {string} name {string}")
    public void registerCustomer(String id, String name) {
        Customer c = new Customer(id, name);
        repo.saveCustomer(c);
    }

    @When("I top up customer {string} with {double}")
    public void topUp(String id, double amount) {
        Customer c = repo.findCustomerById(id);
        assertNotNull(c);
        c.topUp(amount);
    }

    @Then("customer {string} has balance {double}")
    public void checkBalance(String id, double expected) {
        Customer c = repo.findCustomerById(id);
        assertNotNull(c);
        assertEquals(expected, c.getBalance(), 0.0001);
    }

    @When("the customer {string} starts a charging session at point {string} at time now")
    public void startSession(String customerId, String pointId) {
        ChargingSession s = SessionManager.startSession(customerId, pointId, repo);
        assertNotNull(s);
        repo.saveSession(s);
    }

    @Then("a charging session is created for customer {string} at point {string}")
    public void sessionCreated(String customerId, String pointId) {
        boolean found = repo.getSessions().stream()
                .anyMatch(s -> s.getCustomerId().equals(customerId) && s.getPointId().equals(pointId));
        assertTrue(found);
    }
}