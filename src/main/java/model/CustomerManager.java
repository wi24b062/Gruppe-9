package model;

import java.util.List;

public class CustomerManager {

    private InMemoryRepo repo = InMemoryRepo.instance();

    public void register(String id, String name) {
        repo.customers.add(new Customer(id, name));
    }

    public void topUp(String id, double amount) {
        find(id).addBalance(amount);
    }

    public Customer find(String id) {
        return repo.customers.stream()
                .filter(c -> c.getId().equals(id))
                .findFirst()
                .orElseThrow();
    }

    public List<Customer> getAll() {
        return repo.customers;
    }
}
