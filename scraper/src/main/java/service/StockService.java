package service;

import io.vertx.core.Vertx;
import model.StockResponse;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class StockService {
    private static final String API_KEY = "QCRLNNA4PUOMCM8D";
    private StockClient client;
    private Vertx vertx;

    public StockService(Vertx vertx) {
        this.vertx = vertx;
    }


    public void init() {
        Retrofit retrofit = new Retrofit.Builder()
                .client(new OkHttpClient.Builder().build())
                .baseUrl("https://www.alphavantage.co/")
                .addConverterFactory(JacksonConverterFactory.create())
                .build();
        client = retrofit.create(StockClient.class);
    }

    public void query(String function, String symbol, String interval) {
        client
                .query(function, symbol, interval, API_KEY)
                .enqueue(new Callback<StockResponse>() {
                    @Override
                    public void onResponse(Call<StockResponse> call, Response<StockResponse> response) {
                        vertx.eventBus().publish("[SCRAPER]", response.body().toString());
                    }

                    @Override
                    public void onFailure(Call<StockResponse> call, Throwable throwable) {
                        vertx.eventBus().publish("[SCRAPER]", throwable.getCause());
                    }
                });

    }
}
