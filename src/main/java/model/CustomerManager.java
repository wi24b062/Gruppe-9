package model;
public class CustomerManager {
    private final InMemoryRepo repo = InMemoryRepo.instance();
    public Customer register(String id, String name){
        Customer c = new Customer(id, name); repo.saveCustomer(c); return c;
    }
    public void topUp(String id, double amount){
        Customer c = repo.findCustomerById(id); if(c!=null) c.topUp(amount);
    }
    public Customer find(String id){ return repo.findCustomerById(id); }
}
