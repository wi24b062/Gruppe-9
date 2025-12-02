package model;
import java.time.LocalDateTime;

public class ChargingSession {
    private final String id;
    private final String customerId;
    private final String chargerId;
    private final LocalDateTime startTime;
    private LocalDateTime endTime;
    private double energyKwh;
    private double totalCost;

    public ChargingSession(String id, String customerId, String chargerId){
        this.id = id; this.customerId = customerId; this.chargerId = chargerId; this.startTime = LocalDateTime.now();
    }
    public String getId(){ return id; }
    public String getCustomerId(){ return customerId; }
    public String getChargerId(){ return chargerId; }
    public LocalDateTime getStartTime(){ return startTime; }
    public LocalDateTime getEndTime(){ return endTime; }
    public double getTotalCost(){ return totalCost; }
    public void endSession(LocalDateTime end, double energyKwh, double cost){
        this.endTime = end; this.energyKwh = energyKwh; this.totalCost = cost;
    }
}
