package model;

import org.json.JSONException;
import org.json.JSONObject;

public class CarDetails {

    private int first_register_date, avg_consum, emission, mileage_per_year;
    private double  engine_size, fuel_type;
    private String city;

    public static CarDetails fromJason(JSONObject jsonObject) {

        CarDetails carDetails = new CarDetails();
        try {
            carDetails.city = jsonObject.getJSONObject("city").getString("name");
            carDetails.first_register_date = jsonObject.getJSONObject("year").getInt("date");
            carDetails.mileage_per_year = jsonObject.getJSONObject("mile").getInt("distance");
            carDetails.emission = jsonObject.getJSONObject("co2").getInt("emission");
            carDetails.avg_consum = jsonObject.getJSONObject("consum").getInt("consum");
            carDetails.engine_size = jsonObject.getJSONObject("size").getDouble("engine");
            carDetails.fuel_type = jsonObject.getJSONObject("fuel").getDouble("fuel");
        } catch (JSONException e) {
            e.getMessage();
        }

        return carDetails;
    }

    public int getFirst_register_date() {
        return first_register_date;
    }

    public void setFirst_register_date(int first_register_date) {
        this.first_register_date = first_register_date;
    }

    public int getAvg_consum() {
        return avg_consum;
    }

    public void setAvg_consum(int avg_consum) {
        this.avg_consum = avg_consum;
    }

    public int getEmission() {
        return emission;
    }

    public void setEmission(int emission) {
        this.emission = emission;
    }

    public int getMileage_per_year() {
        return mileage_per_year;
    }

    public void setMileage_per_year(int mileage_per_year) {
        this.mileage_per_year = mileage_per_year;
    }

    public double getEngine_size() {
        return engine_size;
    }

    public void setEngine_size(double engine_size) {
        this.engine_size = engine_size;
    }

    public double getFuel_type() {
        return fuel_type;
    }

    public void setFuel_type(double fuel_type) {
        this.fuel_type = fuel_type;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
