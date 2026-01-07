package model;

import java.util.ArrayList;
import java.util.List;

public class Location {
    private String name;
    private String address;
    private List<Charger> chargers = new ArrayList<>();

    public Location(String name, String address) {
        this.name = name;
        this.address = address;
    }

    public String getName() { return name; }
    public List<Charger> getChargers() { return chargers; }
}
