package model;

import com.fasterxml.jackson.annotation.JsonProperty;

class Value {
    @JsonProperty("1. open")
    String open;
    @JsonProperty("2. high")
    String high;
    @JsonProperty("3. low")
    String low;
    @JsonProperty("4. close")
    String close;
    @JsonProperty("5. volume")
    String volume;

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
