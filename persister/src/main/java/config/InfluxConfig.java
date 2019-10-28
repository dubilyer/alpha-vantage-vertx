package config;

import java.util.function.Supplier;

public enum InfluxConfig {
    URL(() -> "http://192.168.99.100:8086");

    private final Supplier<String> s;

    InfluxConfig(Supplier<String> s) {
        this.s = s;
    }

    @Override
    public String toString() {
        return s.get();
    }
}
