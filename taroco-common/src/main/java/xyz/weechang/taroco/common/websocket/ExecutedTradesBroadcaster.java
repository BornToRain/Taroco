package xyz.weechang.taroco.common.websocket;

import org.codehaus.jackson.JsonFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class ExecutedTradesBroadcaster extends BroadcastingTextWebSocketHandler {
    private static final Logger logger = LoggerFactory.getLogger(ExecutedTradesBroadcaster.class);

    private JsonFactory jsonFactory = new JsonFactory();

//    @EventHandler
//    public void handle(TradeExecutedEvent event) {
//        try {
//            doHandle(event);
//        } catch (IOException e) {
//            logger.warn("Problem while sending TradeExecutedEvent to external system");
//        }
//    }
//
//    private void doHandle(TradeExecutedEvent event) throws IOException {
//        String jsonObjectAsString = createJsonInString(event);
//
//        this.broadcast(jsonObjectAsString);
//    }
//
//    private String createJsonInString(TradeExecutedEvent event) throws IOException {
//        Writer writer = new StringWriter();
//        JsonGenerator g = jsonFactory.createJsonGenerator(writer);
//        g.writeStartObject();
//        g.writeObjectFieldStart("tradeExecuted");
//        g.writeStringField("orderbookId", event.getOrderBookIdentifier().toString());
//        g.writeStringField("count", String.valueOf(event.getTradeCount()));
//        g.writeStringField("price", String.valueOf(event.getTradePrice()));
//        g.writeEndObject(); // for trade-executed
//        g.close();
//        return writer.toString();
//    }
}
