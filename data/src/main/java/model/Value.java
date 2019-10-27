package model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Value {
    @JsonProperty("1. open")
    private String open;
    @JsonProperty("2. high")
    private String high;
    @JsonProperty("3. low")
    private String low;
    @JsonProperty("4. close")
    private String close;
    @JsonProperty("5. volume")
    private String volume;

    public String getClose() {
        return close;
    }

    public String getOpen() {
        return open;
    }

    public String getHigh() {
        return high;
    }

    public String getLow() {
        return low;
    }

    public String getVolume() {
        return volume;
    }

    @Override
    public String toString() {
        return "\t{\n" +
                "\t\topen='" + open + "\'\n" +
                "\t\thigh='" + high + "\'\n" +
                "\t\tlow='" + low + "\'\n" +
                "\t\tclose='" + close + "\'\n" +
                "\t\tvolume='" + volume + "\'\n" +
                "\t}\n";

    }
}
