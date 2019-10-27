package verticles;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.eventbus.EventBus;
import model.StockResponse;
import model.StockResponseCodec;
import service.StockService;
import worker.Worker;

import java.util.LinkedList;
import java.util.List;

public class ScraperVerticle extends AbstractVerticle {
    private static final String PREFIX = "[SCRAPER]";
    private List<Worker> workers;

    @Override
    public void start(Future<Void> startFuture) {
        EventBus eventBus = vertx.eventBus();
        initWorkers();
        eventBus.publish(PREFIX, "Scraper is ready");
        eventBus.registerDefaultCodec(StockResponse.class, new StockResponseCodec());
        workers.forEach(w -> w.schedule(vertx));
    }

    private void initWorkers() {
        StockService stockService = new StockService(vertx);
        stockService.init();
        workers = new LinkedList<>();
        workers.add(new Worker(l -> stockService.query("TIME_SERIES_INTRADAY", "MSFT", "1min"), 5000));
    }
}
