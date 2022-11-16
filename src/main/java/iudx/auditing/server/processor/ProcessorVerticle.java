package iudx.auditing.server.processor;

import static iudx.auditing.server.common.Constants.MSG_PROCESS_ADDRESS;
import static iudx.auditing.server.common.Constants.RMQ_SERVICE_ADDRESS;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.eventbus.MessageConsumer;
import io.vertx.core.json.JsonObject;
import io.vertx.serviceproxy.ServiceBinder;
import iudx.auditing.server.rabbitmq.RabbitMQService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ProcessorVerticle extends AbstractVerticle {

  private static final Logger LOGGER = LogManager.getLogger(ProcessorVerticle.class);
  private MessageProcessService processor;
  private RabbitMQService rabbitMQService;
  private ServiceBinder binder;
  private MessageConsumer<JsonObject> consumer;

  @Override
  public void start() throws Exception {

    rabbitMQService = RabbitMQService.createProxy(vertx, RMQ_SERVICE_ADDRESS);

    processor = new MessageProcessorImpl(vertx, rabbitMQService);

    binder = new ServiceBinder(vertx);

    consumer =
        binder.setAddress(MSG_PROCESS_ADDRESS).register(MessageProcessService.class, processor);
  }

  @Override
  public void stop() {
    binder.unregister(consumer);
  }
}