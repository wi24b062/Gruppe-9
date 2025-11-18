package model;


import java.util.*;

public class Location {
    private final String name;
    private final String address;
    private final List<Charger> points = new ArrayList<>();
    private Price price;

    public Location(String name, String address){
        this.name = name; this.address = address;
    }
    public String getName(){ return name; }
    public String getAddress(){ return address; }
    public void addChargingPoint(Charger p){ points.add(p); }
    public List<Charger> getChargingPoints(){ return Collections.unmodifiableList(points); }
    public void setPrice(Price p){ this.price = p; }
    public Price getPrice(){ return price; }
}
