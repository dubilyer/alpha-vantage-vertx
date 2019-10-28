package repository;

import io.vertx.core.Handler;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.eventbus.Message;
import model.stock.StockResponse;
import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.Point;
import org.influxdb.dto.Query;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import static config.InfluxConfig.URL;
import static config.Verticles.PERSISTER;

public class InfluxRepo {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private InfluxDB influx;
    private EventBus eventBus;

    public InfluxRepo(EventBus eventBus) {
        this.eventBus = eventBus;
        influx = InfluxDBFactory.connect(URL.toString());
        String dbName = "metrics";
        influx.setDatabase(dbName);
        if (Optional.ofNullable(System.getProperty("Create DB")).orElse("false").equals("true")) {
            influx.query(new Query(String.format("CREATE DATABASE %s;", dbName)));
        }
    }

    public final Handler<Message<StockResponse>> stockPersistHandler = message -> {
        try {
            message
                    .body()
                    .getTimeSeries()
                    .forEach(
                            (key, value) -> influx.write(
                                    Point
                                            .measurement(message.body().getMetadata().get("2. Symbol"))
                                            .time(
                                                    LocalDateTime
                                                            .parse(key, FORMATTER)
                                                            .toEpochSecond(ZoneOffset.UTC),
                                                    TimeUnit.SECONDS)
                                            .addField("open", value.getOpen())
                                            .addField("high", value.getHigh())
                                            .addField("low", value.getLow())
                                            .addField("close", value.getClose())
                                            .addField("volume", value.getVolume())
                                            .build()));
        } catch (ClassCastException e) {
            eventBus.publish(PERSISTER.index(), "message is not of class StockResponse");
        }
    };
}
