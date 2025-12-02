package model;
import java.util.UUID;

public class InvoiceManager {
    private final InMemoryRepo repo = InMemoryRepo.instance();
    public Invoice generateInvoiceForCustomer(String customerId){
        Invoice inv = new Invoice(UUID.randomUUID().toString(), customerId);
        repo.getSessions().stream()
                .filter(s -> s.getCustomerId().equals(customerId) && s.getEndTime()!=null)
                .forEach(s -> inv.addItem(new InvoiceItem("Session "+s.getId(), s.getTotalCost())));
        return inv;
    }
}
