package config;

import java.util.function.Supplier;

public enum Sources {
    ALPHA_VANTAGE(
            () -> "https://www.alphavantage.co/",
            () -> "alpha_vantage_api_key"
    );

    private Supplier<String> urlSupplier;
    private Supplier<String> secretSupplier;

    Sources(Supplier<String> urlSupplier, Supplier<String> secretSupplier) {
        this.urlSupplier = urlSupplier;
        this.secretSupplier = secretSupplier;
    }

    public String url() {
        return urlSupplier.get();
    }

    public String secret() {
        return System.getProperty(secretSupplier.get());
    }
}
