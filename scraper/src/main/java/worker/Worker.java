package worker;

import io.vertx.core.Handler;
import io.vertx.core.Vertx;

import java.util.Random;

public class Worker {
    private Handler<Long> r;
    private long delay;

    public Worker(Handler<Long> r, long delay) {
        this.r = r;
        this.delay = delay;
    }

    public void schedule(Vertx timer){
        r.handle(new Random().nextLong());
        timer.setPeriodic(this.delay, this.r);
    }
}
