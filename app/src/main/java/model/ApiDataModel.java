package model;

import com.google.gson.annotations.SerializedName;

public class ApiDataModel {
    @SerializedName("error_message")
    private String error;

    @SerializedName("annual_tax")
    private int annualTax;

    @SerializedName("fuel_price")
    private double fuelPrice;

    @SerializedName("annual_fuel_price")
    private int annualFuelCosts;

    public String getError() {
        return error;
    }

    public int getAnnualTax() {
        return annualTax;
    }

    public double getFuelPrice() {
        return fuelPrice;
    }

    public int getAnnualFuelCosts() {
        return annualFuelCosts;
    }

    @Override
    public String toString() {
        return "ApiDataModel{" +
                "error='" + error + '\'' +
                ", annualTax=" + annualTax +
                ", fuelPrice=" + fuelPrice +
                ", annualFuelCosts=" + annualFuelCosts +
                '}';
    }
}
