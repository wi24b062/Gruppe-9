package model;

public class Charger {
    private String id;
    private ChargerType type;
    private Status status = Status.FREE;

    public Charger(String id, ChargerType type) {
        this.id = id;
        this.type = type;
    }

    public String getId() { return id; }
    public ChargerType getType() { return type; }
    public Status getStatus() { return status; }
    public void setStatus(Status status) { this.status = status; }
}
