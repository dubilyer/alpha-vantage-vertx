package model.stock;

import java.util.function.Supplier;

public enum Interval {
    ONE(() -> "1min"), FIVE(() -> "5min"), FIFTEEN(() -> "15min"), THIRTY(() -> "30min"), SIXTY(() -> "60min");

    private Supplier<String> s;

    Interval(Supplier<String> s) {
        this.s = s;
    }

    public String get(){
        return s.get();
    }
}
