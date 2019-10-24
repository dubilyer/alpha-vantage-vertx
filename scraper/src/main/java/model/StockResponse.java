package model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashMap;

public class StockResponse {
    @JsonProperty("Meta Data")
    HashMap<String, String> metadata;

    @JsonProperty("Time Series (1min)")
    HashMap<String, Value> timeSeries;

    @Override
    public String toString() {
        return "StockResponse{\n" +
                "\tmetadata=" + metadata + "\n" +
                "\ttimeSeries=" + timeSeries + "\n"+
                '}';
    }
}
