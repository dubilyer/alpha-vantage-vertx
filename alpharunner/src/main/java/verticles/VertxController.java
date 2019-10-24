package verticles;

import io.vertx.core.Vertx;

import java.util.stream.Stream;

public class VertxController {
    public static void main(String[] args) {
        deployAll();
    }

    private static void deployAll() {
        Vertx vertx = Vertx.vertx();
        Stream.of(
                new LoggerVerticle(),
                new PersisterVerticle(),
                new ScraperVerticle(),
                new ExposerVerticle()
        ).forEach(vertx::deployVerticle);
    }
}
