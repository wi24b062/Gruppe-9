package model;

public class Customer {
    private String id;
    private String name;
    private double balance;

    public Customer(String id, String name) {
        this.id = id;
        this.name = name;
        this.balance = 0;
    }

    public String getId() { return id; }
    public double getBalance() { return balance; }
    public void addBalance(double amount) { balance += amount; }
    public void deduct(double amount) { balance -= amount; }
}