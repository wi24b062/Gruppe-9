package model;
public class PriceManager {
    private final LocationManager lm = new LocationManager();
    public void setLocationPrice(String locationName, double perKwh, double perMinute){
        lm.setPrice(locationName, new Price(perKwh, perMinute));
    }
}
