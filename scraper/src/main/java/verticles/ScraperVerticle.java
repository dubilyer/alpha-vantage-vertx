package verticles;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.eventbus.EventBus;
import model.StockResponse;
import service.StockService;

public class ScraperVerticle extends AbstractVerticle {
    private static final String PREFIX = "[SCRAPER]";
    private EventBus eventBus;
    private StockService stockService;

    @Override
    public void start(Future<Void> startFuture) throws Exception {
        eventBus = vertx.eventBus();
        stockService = new StockService();
        stockService.init();
        eventBus.publish(PREFIX, "Scraper is ready");
        StockResponse r = stockService.query("TIME_SERIES_INTRADAY", "MSFT", "1min");
        eventBus.publish(PREFIX, r.toString());
    }
}
