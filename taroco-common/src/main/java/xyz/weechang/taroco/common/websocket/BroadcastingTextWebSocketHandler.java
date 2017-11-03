package xyz.weechang.taroco.common.websocket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.ConcurrentWebSocketSessionDecorator;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

class BroadcastingTextWebSocketHandler extends TextWebSocketHandler {

    private static final Logger logger = LoggerFactory.getLogger(BroadcastingTextWebSocketHandler.class);
    private Map<String, WebSocketSession> sessions = new ConcurrentHashMap();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        super.afterConnectionEstablished(session);

        sessions.put(session.getId(),
                     new ConcurrentWebSocketSessionDecorator(session, (int) TimeUnit.SECONDS.toMillis(10), 5 * 1024));
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        super.afterConnectionClosed(session, status);

        sessions.remove(session.getId());
    }

    protected void broadcast(String data) {
        final TextMessage message = new TextMessage(data);
        sessions.forEach((key, session) -> {
            try {
                session.sendMessage(message);
            } catch (IOException e) {
                logger.warn("An error occurred while trying to send a message to a WebSocket", e);
            }
        });
    }
}
