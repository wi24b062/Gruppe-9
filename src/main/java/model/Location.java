package model;
import java.util.*;
public class Location {
    private final String name;
    private final String address;
    private final List<Charger> chargers = new ArrayList<>();
    private Price price;

    public Location(String name, String address){ this.name = name; this.address = address; }
    public String getName(){ return name; }
    public String getAddress(){ return address; }
    public void addCharger(Charger c){ chargers.add(c); }
    public List<Charger> getChargers(){ return Collections.unmodifiableList(chargers); }
    public void setPrice(Price p){ this.price = p; }
    public Price getPrice(){ return price; }
}
