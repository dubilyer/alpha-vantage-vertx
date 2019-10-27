package service;

import config.Verticles;
import exceptions.RestRequestException;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.DeliveryOptions;
import model.stock.Interval;
import model.stock.StockResponse;
import model.stock.TimeFunction;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.util.Optional;

import static config.Sources.*;

public class StockService {
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

    public void query(String symbol) {
        client
                .query(
                        TimeFunction.TIME_SERIES_INTRADAY.name(),
                        symbol,
                        Interval.ONE.get(),
                        ALPHA_VANTAGE.secret(),
                        "compact")
                .enqueue(new Callback<StockResponse>() {
                    @Override
                    public void onResponse(Call<StockResponse> call, Response<StockResponse> response) {
                        DeliveryOptions options = new DeliveryOptions().setCodecName("codec");
                        vertx.eventBus().publish(
                                Verticles.SCRAPER.index(),
                                Optional
                                        .ofNullable(response.body())
                                        .orElseThrow(() -> new RestRequestException("Response body is null")),
                                options
                        );
                    }

                    @Override
                    public void onFailure(Call<StockResponse> call, Throwable throwable) {
                        vertx.eventBus().publish("[SCRAPER]", throwable.getCause());
                    }
                });
    }
}
