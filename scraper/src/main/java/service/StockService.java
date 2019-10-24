package service;

import model.StockResponse;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;
import retrofit2.http.Query;

import java.io.IOException;

public class StockService {
    public static final String API_KEY = "QCRLNNA4PUOMCM8D";
    private StockClient client;

    public void init() {
        Retrofit retrofit = new Retrofit.Builder()
                .client(new OkHttpClient.Builder().build())
                .baseUrl("https://www.alphavantage.co/")
                .addConverterFactory(JacksonConverterFactory.create())
                .build();
        client = retrofit.create(StockClient.class);
    }

    public StockResponse query(String function, String symbol, String interval) throws IOException {
        return client
                .query(function, symbol, interval, API_KEY)
                .execute()
                .body();
    }
}
