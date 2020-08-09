package model;

import  org.json.JSONException;
import org.json.JSONObject;

public class ApiDataModel {

    private int taxVal, costVal, typeVal;
    private String StrVal1, StrVal2, StrVal3;

    // Get Data from Api jason object
    public static ApiDataModel fromJson(JSONObject jsonObject){
        int val1, val2, val3;
        ApiDataModel apiDataModel = new ApiDataModel();
        try {
            // Fetch Data from json Api
            val1 = apiDataModel.taxVal = jsonObject.getJSONArray("value1_name").getJSONObject(0).getInt("id");
            val2 = apiDataModel.costVal = jsonObject.getJSONArray("value2_name").getJSONObject(0).getInt("id");
            val3 = apiDataModel.typeVal = jsonObject.getJSONArray("value3_name").getJSONObject(0).getInt("id");
            // To String
            apiDataModel.StrVal1 = Integer.toString(val1);
            apiDataModel.StrVal2 = Integer.toString(val2);
            apiDataModel.StrVal3 = Integer.toString(val3);
            return apiDataModel;
        } catch (JSONException e){
            e.printStackTrace();
            return null;
        }
    }

    public String getStrVal1() {
        return StrVal1;
    }

    public String getStrVal2() {
        return StrVal2;
    }

    public String getStrVal3() {
        return StrVal3;
    }
}
