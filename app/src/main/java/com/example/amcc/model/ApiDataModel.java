package com.example.amcc.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class ApiDataModel implements Parcelable {
    @SerializedName("error_message")
    private String error;

    @SerializedName("annual_tax")
    private int annualTax;

    @SerializedName("fuel_price")
    private double fuelPrice;

    @SerializedName("annual_fuel_price")
    private int annualFuelCosts;

    public static final Creator<ApiDataModel> CREATOR = new Creator<ApiDataModel>() {
        @Override
        public ApiDataModel createFromParcel(Parcel in) {
            return new ApiDataModel(in);
        }

        @Override
        public ApiDataModel[] newArray(int size) {
            return new ApiDataModel[size];
        }
    };

    protected ApiDataModel(Parcel in) {
        error = in.readString();
        annualTax = in.readInt();
        fuelPrice = in.readDouble();
        annualFuelCosts = in.readInt();
    }

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(error);
        dest.writeInt(annualTax);
        dest.writeDouble(fuelPrice);
        dest.writeInt(annualFuelCosts);
    }
}
