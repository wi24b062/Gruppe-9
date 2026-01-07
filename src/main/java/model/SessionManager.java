package model;

public class SessionManager {

    private InMemoryRepo repo = InMemoryRepo.instance();

    public ChargingSession startSession(String customerId, String chargerId) {
        Customer c = repo.customers.stream().filter(x -> x.getId().equals(customerId)).findFirst().orElseThrow();

        Charger ch = repo.locations.stream()
                .flatMap(l -> l.getChargers().stream())
                .filter(x -> x.getId().equals(chargerId))
                .findFirst().orElseThrow();

        if (ch.getStatus() != Status.FREE)
            throw new RuntimeException("Charger not available");

        ch.setStatus(Status.OCCUPIED);
        ChargingSession s = new ChargingSession(c, ch);
        repo.sessions.add(s);
        return s;
    }

    public void endSession(String sessionId, double kwh) {
        ChargingSession s = repo.sessions.stream()
                .filter(x -> x.getId().equals(sessionId))
                .findFirst().orElseThrow();

        double cost = kwh * 0.35;
        s.getCustomer().deduct(cost);
        s.getCharger().setStatus(Status.FREE);
    }
}
