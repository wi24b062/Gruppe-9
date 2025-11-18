package model;

public class Customer {
    private final String id;
    private String name;
    private double balance;

    public Customer(String id, String name) {
        this.id = id; this.name = name; this.balance = 0.0;
    }

    public String getId(){ return id; }
    public String getName(){ return name; }
    public double getBalance(){ return balance; }

    public void topUp(double amount){ balance += amount; }
    public boolean deduct(double amount){
        if(balance >= amount){ balance -= amount; return true; }
        return false;
    }
}