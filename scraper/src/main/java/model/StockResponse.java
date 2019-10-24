package model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashMap;

public class StockResponse {
    @JsonProperty("Meta Data")
    @SuppressWarnings({"unused", "MismatchedQueryAndUpdateOfCollection", "for serialization"})
    private HashMap<String, String> metadata;

    @JsonProperty("Time Series (1min)")
    @SuppressWarnings({"unused", "MismatchedQueryAndUpdateOfCollection", "for serialization"})
    private HashMap<String, Value> timeSeries;

    @Override
    public String toString() {
        return "StockResponse{\n" +
                "\ttimeSeries=" + timeSeries + "\n"+
                '}';
    }
}
