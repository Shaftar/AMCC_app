package model;

import java.util.Date;

public class CarDetails {

    private int  emission, yearlyMileage, engineSize;
    private Date regDate;
    private double  avgConsume;
    private FuelType fuelType;
    private String city;

    public CarDetails(int emission, int yearlyMileage, int engineSize, Date regDate, double avgConsume, FuelType fuelType, String city) {
        this.emission = emission;
        this.yearlyMileage = yearlyMileage;
        this.engineSize = engineSize;
        this.regDate = regDate;
        this.avgConsume = avgConsume;
        this.fuelType = fuelType;
        this.city = city;
    }

    public int getEmission() {
        return emission;
    }

    public void setEmission(int emission) {
        this.emission = emission;
    }

    public int getYearlyMileage() {
        return yearlyMileage;
    }

    public void setYearlyMileage(int yearlyMileage) {
        this.yearlyMileage = yearlyMileage;
    }

    public int getEngineSize() {
        return engineSize;
    }

    public void setEngineSize(int engineSize) {
        this.engineSize = engineSize;
    }

    public Date getRegDate() {
        return regDate;
    }

    public void setRegDate(Date regDate) {
        this.regDate = regDate;
    }

    public double getAvgConsume() {
        return avgConsume;
    }

    public void setAvgConsume(double avgConsume) {
        this.avgConsume = avgConsume;
    }

    public FuelType getFuelType() {
        return fuelType;
    }

    public void setFuelType(FuelType fuelType) {
        this.fuelType = fuelType;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
