package model;
public class LocationManager {
    private final InMemoryRepo repo = InMemoryRepo.instance();
    public Location addLocation(String name, String address){ Location l = new Location(name,address); repo.saveLocation(l); return l; }
    public void setPrice(String locationName, Price p){ Location l = repo.findLocationByName(locationName); if(l!=null) l.setPrice(p); }
    public void addCharger(String locationName, Charger c){ Location l = repo.findLocationByName(locationName); if(l!=null){ l.addCharger(c); repo.saveCharger(c);} }
}
