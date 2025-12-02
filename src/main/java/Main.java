import model.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("Demo: Charging Network MVP2 - dynamic features");
        InMemoryRepo repo = InMemoryRepo.instance();
        repo.clear();

        CustomerManager cm = new CustomerManager();
        LocationManager lm = new LocationManager();
        SessionManager sm = new SessionManager();
        PriceManager pm = new PriceManager();
        InvoiceManager im = new InvoiceManager();

        cm.register("C-1","Max");
        lm.addLocation("Central Station","Main St 1");
        Charger ch = new Charger("CP-1", ChargerType.AC);
        lm.addCharger("Central Station", ch);
        pm.setLocationPrice("Central Station", 0.30, 0.05);
        cm.topUp("C-1", 50.0);

        ChargingSession s = sm.startSession("C-1","CP-1");
        if(s!=null){
            System.out.println("Session started: " + s.getId());
            // Simulate end
            sm.endSession(s.getId(), 5.0); // 5 kWh
            System.out.println("Session ended. Customer balance: " + cm.find("C-1").getBalance());
            Invoice inv = im.generateInvoiceForCustomer("C-1");
            System.out.println("Invoice total: " + inv.getTotal());
        } else {
            System.out.println("Could not start session");
        }
        System.out.println("Demo finished.");
    }
}
