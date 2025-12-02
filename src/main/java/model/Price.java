package model;
public class Price {
    private final double perKwh;
    private final double perMinute;

    public Price(double perKwh, double perMinute){ this.perKwh = perKwh; this.perMinute = perMinute; }
    public double getPerKwh(){ return perKwh; }
    public double getPerMinute(){ return perMinute; }
}