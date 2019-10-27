package config;

public enum Verticles {
    LOGGER,
    EXPOSER,
    PERSISTER,
    SCRAPER;

    public String index(){
        return '[' + name() + ']';
    }
}
