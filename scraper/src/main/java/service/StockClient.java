package service;

import model.StockResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface StockClient {
    @GET("query")
    Call<StockResponse> query(
            @Query("function") String function,
            @Query("symbol") String symbol,
            @Query("interval") String interval,
            @Query("apikey") String apikey,
            @Query("outputsize") String outputSize
    );
}
