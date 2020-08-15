package retrofitApi;

import model.ApiDataModel;
import model.CarDetails;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static RetrofitClient INSTANCE;
    private final String BASE_Url = "https://amcosts.azurewebsites.net/";
    private AmccApi amccApi;
    private Retrofit retrofit;

    private RetrofitClient() {
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_Url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        amccApi = retrofit.create(AmccApi.class);
    }

    public static RetrofitClient getINSTANCE() {
        if (INSTANCE == null)
            INSTANCE = new RetrofitClient();
        return INSTANCE;
    }


    public Call<ApiDataModel> getCosts(CarDetails car) {
        return amccApi.GetCosts(car.getCity(), car.getEngineSize(), car.getRegDate(),
                car.getEmission(), car.getFuelType(), car.getAvgConsume(), car.getYearlyMileage());
    }
}
