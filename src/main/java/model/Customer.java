package model;

public class Customer {
    private final String id;
    private final String name;
    private double balance;
    private boolean active = true;

    public Customer(String id, String name) { this.id = id; this.name = name; this.balance = 0.0; }

    public String getId(){ return id; }
    public String getName(){ return name; }
    public double getBalance(){ return balance; }
    public boolean isActive(){ return active; }

    public void topUp(double amount){ this.balance += amount; }
    public boolean deduct(double amount){
        if(this.balance >= amount){ this.balance -= amount; return true; }
        return false;
    }
    public void deactivate(){ this.active = false; }
}