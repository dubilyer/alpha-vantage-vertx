package verticles;

import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;

import java.util.stream.Stream;

public class VertxController {
    public static void main(String[] args) {
        deployAllOnCluster();
    }


    /**
     * For non-clustered deployment
     */
    private static void deploySingle() {
        Vertx vertx = Vertx.vertx();
        deployAll(vertx);
    }

    private static void deployAllOnCluster(){
        VertxOptions vOptions = new VertxOptions();
        vOptions.getEventBusOptions().setClustered(true);
        Vertx.clusteredVertx(vOptions, cluster -> {
            if (cluster.succeeded()) {
                final Vertx result = cluster.result();
                deployAll(result);
            }
        });
    }

    private static void deployAll(Vertx result) {
        Stream.of(
                new LoggerVerticle(),
//                new PersisterVerticle(),
                new ScraperVerticle(),
                new ExposerVerticle()
        ).forEach(result::deployVerticle);
    }
}
