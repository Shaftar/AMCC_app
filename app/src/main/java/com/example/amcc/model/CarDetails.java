package com.example.amcc.model;

public class CarDetails {

    private int regYear, engineSize, co2Em, driDisIny;
    private double avgCons, fuelType;

    public CarDetails(int regYear, int engineSize, int co2Em, double fuelType) {
        this.regYear = regYear;
        this.engineSize = engineSize;
        this.co2Em = co2Em;
        this.fuelType = fuelType;
    }

    public int getRegYear() {
        return regYear;
    }

    public void setRegYear(int regYear) {
        this.regYear = regYear;
    }

    public int getEngineSize() {
        return engineSize;
    }

    public void setEngineSize(int engineSize) {
        this.engineSize = engineSize;
    }

    public int getCo2Em() {
        return co2Em;
    }

    public void setCo2Em(int co2Em) {
        this.co2Em = co2Em;
    }

    public int getDriDisIny() {
        return driDisIny;
    }

    public void setDriDisIny(int driDisIny) {
        this.driDisIny = driDisIny;
    }

    public double getAvgCons() {
        return avgCons;
    }

    public void setAvgCons(double avgCons) {
        this.avgCons = avgCons;
    }

    public double getFuelType() {
        return fuelType;
    }

    public void setFuelType(double fuelType) {
        this.fuelType = fuelType;
    }
}
