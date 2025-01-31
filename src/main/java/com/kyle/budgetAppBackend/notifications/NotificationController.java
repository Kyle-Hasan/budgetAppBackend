package com.kyle.budgetAppBackend.notifications;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
// mainly used to tell subscribers to invalidate cache in a pub/sub way
@Controller
public class NotificationController {

    private final SimpMessagingTemplate messagingTemplate;

    public NotificationController(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @MessageMapping("/notify") // Prefix is "/app/notify"
    public void notifyClients(String message) {

        messagingTemplate.convertAndSend("/entity/entity-type-1", message);
    }

    public void invalidateCache(String entityType, Object payload) {
        messagingTemplate.convertAndSend("/entity/" + entityType, payload);
    }
}
