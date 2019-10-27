package model.stock;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashMap;

public class StockResponse {
    @JsonProperty("Meta Data")
    @SuppressWarnings({"unused", "MismatchedQueryAndUpdateOfCollection", "for serialization"})
    private HashMap<String, String> metadata;

    @JsonProperty("Time Series (1min)")
    @SuppressWarnings({"unused", "MismatchedQueryAndUpdateOfCollection", "for serialization"})
    private HashMap<String, Value> timeSeries;

    public HashMap<String, String> getMetadata() {
        return metadata;
    }

    public HashMap<String, Value> getTimeSeries() {
        return timeSeries;
    }

    @Override
    public String toString() {
        return "StockResponse{" +
                "timeSeries=" + timeSeries +
                '}';
    }
}
