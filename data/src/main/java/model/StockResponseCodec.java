package model;

import io.vertx.core.buffer.Buffer;
import io.vertx.core.eventbus.MessageCodec;
import io.vertx.core.json.Json;

public class StockResponseCodec implements MessageCodec<StockResponse, StockResponse> {

    @Override
    public void encodeToWire(Buffer buffer, StockResponse stockResponse) {
        buffer.appendString(Json.encode(stockResponse));
    }

    @Override
    public StockResponse decodeFromWire(int i, Buffer buffer) {
        return Json.decodeValue(buffer, StockResponse.class);

    }

    @Override
    public StockResponse transform(StockResponse stockResponse) {
        return stockResponse;
    }

    @Override
    public String name() {
        return "codec";
    }

    @Override
    public byte systemCodecID() {
        return -1;
    }
}
