package model;
import java.util.*;

public class Invoice {
    private final String id;
    private final String customerId;
    private final List<InvoiceItem> items = new ArrayList<>();
    public Invoice(String id, String customerId){ this.id = id; this.customerId = customerId; }
    public void addItem(InvoiceItem it){ items.add(it); }
    public double getTotal(){ return items.stream().mapToDouble(InvoiceItem::getAmount).sum(); }
}
