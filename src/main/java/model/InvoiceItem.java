package model;
public class InvoiceItem {
    private final String desc;
    private final double amount;
    public InvoiceItem(String desc, double amount){ this.desc = desc; this.amount = amount; }
    public double getAmount(){ return amount; }
}
