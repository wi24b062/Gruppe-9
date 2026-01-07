package model;

import java.util.List;

public class LocationManager {

    private InMemoryRepo repo = InMemoryRepo.instance();

    public void addLocation(String name, String address) {
        repo.locations.add(new Location(name, address));
    }

    public List<Location> getLocations() {
        return repo.locations;
    }

    public void addCharger(String locationName, Charger charger) {
        for (Location l : repo.locations) {
            if (l.getName().equals(locationName)) {
                l.getChargers().add(charger);
                return;
            }
        }
    }

    public List<Charger> getChargersByLocation(String locationName) {
        for (Location l : repo.locations) {
            if (l.getName().equals(locationName)) {
                return l.getChargers();
            }
        }
        return List.of();
    }

    public void updateChargerStatus(String chargerId, Status status) {
        for (Location l : repo.locations) {
            for (Charger c : l.getChargers()) {
                if (c.getId().equals(chargerId)) {
                    c.setStatus(status);
                    return;
                }
            }
        }
    }
}

