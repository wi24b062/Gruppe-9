package model;
import java.time.LocalDateTime;

public class ChargingSession {
    private final String sessionId;
    private final String customerId;
    private final String pointId;
    private final LocalDateTime startTime;
    private LocalDateTime endTime;
    private double totalCost;

    public ChargingSession(String sessionId, String customerId, String pointId){
        this.sessionId = sessionId; this.customerId = customerId; this.pointId = pointId;
        this.startTime = LocalDateTime.now();
    }
    public String getCustomerId(){ return customerId; }
    public String getPointId(){ return pointId; }
    public void endSession(LocalDateTime end, double cost){ this.endTime = end; this.totalCost = cost; }
}
