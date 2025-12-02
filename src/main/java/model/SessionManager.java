package model;
import java.time.LocalDateTime;
import java.util.UUID;

public class SessionManager {
    private final InMemoryRepo repo = InMemoryRepo.instance();

    public ChargingSession startSession(String customerId, String chargerId){
        Customer cust = repo.findCustomerById(customerId);
        Charger ch = repo.findChargerById(chargerId);
        if(cust==null ||  ch==null || ch.getStatus()!=Status.FREE) return null;
        String id = UUID.randomUUID().toString();
        ChargingSession s = new ChargingSession(id, customerId, chargerId);
        ch.setStatus(Status.OCCUPIED);
        repo.saveSession(s);
        return s;
    }

    public void endSession(String sessionId, double energyKwh){
        ChargingSession s = repo.getSessions().stream().filter(x->x.getId().equals(sessionId)).findFirst().orElse(null);
        if(s==null) return;
        // find location price for charger
        Location loc = repo.findLocationByChargerId(s.getChargerId()); // we need to add helper (see note)
        Price p = loc!=null && loc.getPrice()!=null ? loc.getPrice() : new Price(0.3,0.05);
        double cost = energyKwh * p.getPerKwh();
        s.endSession(LocalDateTime.now(), energyKwh, cost);
        Customer c = repo.findCustomerById(s.getCustomerId());
        if(c!=null) c.deduct(cost);
        Charger ch = repo.findChargerById(s.getChargerId());
        if(ch!=null) ch.setStatus(Status.FREE);
    }
}
