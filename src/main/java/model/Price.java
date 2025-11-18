package model;

public class Price {
    private final double pricePerKwh;
    private final double pricePerMinute;
    public Price(double kwh, double minute){ this.pricePerKwh = kwh; this.pricePerMinute = minute; }
    public double getPricePerKwh(){ return pricePerKwh; }
    public double getPricePerMinute(){ return pricePerMinute; }
}
