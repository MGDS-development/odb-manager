package de.fraunhofer.fokus.ids.services.dcatTransformerService;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.serviceproxy.ServiceBinder;

public class DCATTransformerServiceVerticle extends AbstractVerticle {
    private Logger LOGGER = LoggerFactory.getLogger(DCATTransformerServiceVerticle.class.getName());

    @Override
    public void start(Future<Void> startFuture) {

        DCATTransformerService.create(ready -> {
            if (ready.succeeded()) {
                ServiceBinder binder = new ServiceBinder(vertx);
                binder
                        .setAddress("dcatTransformerService")
                        .register(DCATTransformerService.class, ready.result());
                LOGGER.info("DCATTransformerService successfully started.");
                startFuture.complete();
            } else {
                LOGGER.error(ready.cause());
                startFuture.fail(ready.cause());
            }
        });
    }
}
