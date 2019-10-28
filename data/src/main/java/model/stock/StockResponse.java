package model.stock;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashMap;
import java.util.Map;

public class StockResponse {
    @JsonProperty("Meta Data")
    @SuppressWarnings({"unused", "MismatchedQueryAndUpdateOfCollection", "for serialization"})
    private HashMap<String, String> metadata;

    @JsonProperty("Time Series (1min)")
    @SuppressWarnings({"unused", "MismatchedQueryAndUpdateOfCollection", "for serialization"})
    private HashMap<String, Value> timeSeries;

    public Map<String, String> getMetadata() {
        return metadata;
    }

    public Map<String, Value> getTimeSeries() {
        return timeSeries;
    }

    @Override
    public String toString() {
        return "StockResponse{" +
                "timeSeries=" + timeSeries +
                '}';
    }
}
