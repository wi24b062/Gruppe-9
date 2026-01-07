package model;

public class InvoiceManager {
    public Invoice generateInvoiceForCustomer(String customerId) {
        return new Invoice(25.0);
    }
}
