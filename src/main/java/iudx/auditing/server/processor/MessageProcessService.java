package iudx.auditing.server.processor;

import io.vertx.codegen.annotations.Fluent;
import io.vertx.codegen.annotations.GenIgnore;
import io.vertx.codegen.annotations.ProxyGen;
import io.vertx.codegen.annotations.VertxGen;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;

@VertxGen
@ProxyGen
public interface MessageProcessService {

  @Fluent
  MessageProcessService process(final JsonObject message, Handler<AsyncResult<JsonObject>> handler);

  @GenIgnore
  static MessageProcessService createProxy(Vertx vertx, String address) {
    return new MessageProcessServiceVertxEBProxy(vertx, address);
  }

}
