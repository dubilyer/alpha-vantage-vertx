package service;

import exceptions.RestRequestException;
import io.vertx.core.Vertx;
import model.StockResponse;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;
import java.util.Optional;

import static config.Sources.*;

public class StockService{
    private StockClient client;
    private Vertx vertx;

    public StockService(Vertx vertx) {
        this.vertx = vertx;
    }

    public void init() {
        Retrofit retrofit = new Retrofit.Builder()
                .client(new OkHttpClient.Builder().build())
                .baseUrl(ALPHA_VANTAGE.url())
                .addConverterFactory(JacksonConverterFactory.create())
                .build();
        client = retrofit.create(StockClient.class);
    }

    public void query(String function, String symbol, String interval) {
        client
                .query(function, symbol, interval, ALPHA_VANTAGE.secret())
                .enqueue(new Callback<StockResponse>() {
                    @Override
                    public void onResponse(Call<StockResponse> call, Response<StockResponse> response) {
                        vertx.eventBus().publish(
                                "[SCRAPER]",
                                Optional
                                        .ofNullable(response.body())
                                        .orElseThrow(() -> new RestRequestException("Response body is null"))
                                        );
                    }

                    @Override
                    public void onFailure(Call<StockResponse> call, Throwable throwable) {
                        vertx.eventBus().publish("[SCRAPER]", throwable.getCause());
                    }
                });
    }
}
