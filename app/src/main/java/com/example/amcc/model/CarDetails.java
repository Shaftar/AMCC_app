package com.example.amcc.model;

public class CarDetails {

    private int emission, yearlyMileage, engineSize;
    private String regDate;
    private double avgConsume;
    private String fuelType;
    private String city;

    //regDate should be as in German date format : dd.mm.yyyy
    public CarDetails(String city, int engineSize, int emission, FuelType fuelType, String regDate, double avgConsume, int yearlyMileage) {
        this.emission = emission;
        this.yearlyMileage = yearlyMileage;
        this.engineSize = engineSize;
        this.regDate = regDate;
        this.avgConsume = avgConsume;
        this.fuelType = fuelType.toString();
        this.city = city;
    }

    public CarDetails(int emission, int engineSize, String regDate, String fuelType, String city) {
        this.emission = emission;
        this.engineSize = engineSize;
        this.regDate = regDate;
        this.fuelType = fuelType;
        this.city = city;
    }

    public CarDetails(int emission, int engineSize, String regDate, String city) {
        this.emission = emission;
        this.engineSize = engineSize;
        this.regDate = regDate;
        this.city = city;
    }

    public int getEmission() {
        return emission;
    }

    public int getYearlyMileage() {
        return yearlyMileage;
    }

    public int getEngineSize() {
        return engineSize;
    }

    public String getRegDate() {
        return regDate;
    }

    public double getAvgConsume() {
        return avgConsume;
    }

    public String getFuelType() {
        return fuelType;
    }

    public String getCity() {
        return city;
    }

}
