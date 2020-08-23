package com.example.amcc.model;

public enum FuelType {
    diesel("Diesel"), e5("Super"), e10("E10");

    private String text;

    FuelType(String text) {
        this.text = text;
    }

    public static FuelType fromString(String text) {
        for (FuelType fuelType : FuelType.values()) {
            if (fuelType.text.equalsIgnoreCase(text)) {
                return fuelType;
            }
        }
        return null;
    }
}