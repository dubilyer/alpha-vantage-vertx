package verticles;

import io.prometheus.client.Gauge;
import io.prometheus.client.vertx.MetricsHandler;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.eventbus.EventBus;
import io.vertx.ext.web.Router;
import model.StockResponse;

import static config.Verticles.*;

public class ExposerVerticle extends AbstractVerticle {

    private static final Gauge MSFT = Gauge.build().name("MSFT").help("Microsoft").register();

    @Override
    public void start(Future<Void> startFuture) {
        EventBus eventBus = vertx.eventBus();
        final Router router = Router.router(vertx);
        router.route("/metrics").handler(new MetricsHandler());
        vertx.createHttpServer().requestHandler(router::accept).listen(8080);
        eventBus.publish(EXPOSER.index(), "Exposer is ready");
        eventBus.<StockResponse>consumer(SCRAPER.index(), message ->
                MSFT.set(Double.parseDouble(message.body()
                                .getTimeSeries()
                                .entrySet()
                                .stream()
                                .findFirst()
                                .get()
                                .getValue()
                                .getClose()

                        )
                ));
    }
}
