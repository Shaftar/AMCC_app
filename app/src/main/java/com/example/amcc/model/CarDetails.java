package com.example.amcc.model;

import android.os.Parcel;
import android.os.Parcelable;

public class CarDetails implements Parcelable {

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

    public static final Creator<CarDetails> CREATOR = new Creator<CarDetails>() {
        @Override
        public CarDetails createFromParcel(Parcel in) {
            return new CarDetails(in);
        }

        @Override
        public CarDetails[] newArray(int size) {
            return new CarDetails[size];
        }
    };

    protected CarDetails(Parcel in) {
        emission = in.readInt();
        yearlyMileage = in.readInt();
        engineSize = in.readInt();
        regDate = in.readString();
        avgConsume = in.readDouble();
        fuelType = in.readString();
        city = in.readString();
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(emission);
        parcel.writeInt(yearlyMileage);
        parcel.writeInt(engineSize);
        parcel.writeString(regDate);
        parcel.writeDouble(avgConsume);
        parcel.writeString(fuelType);
        parcel.writeString(city);
    }
}
