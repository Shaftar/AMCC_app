package retrofitApi;

import model.ApiDataModel;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface AmccApi {
    @GET("costs.php")
    Call<ApiDataModel> GetCosts(
            @Query("city") String city,
            @Query("engine_size") int engineSize,
            @Query("reg_date") String regDate,
            @Query("emission") int emission,
            @Query("fuel_type") String fuelType,
            @Query("avg_consume") double avgConsume,
            @Query("yearly_mileage") int yearlyMileage
    );
}
