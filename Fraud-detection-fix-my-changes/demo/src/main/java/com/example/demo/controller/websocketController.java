// src/main/java/com/example/fraud/controller/FraudStreamController.java
package com.example.demo.controller;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class websocketController {
    private final SimpMessagingTemplate messagingTemplate;

    public websocketController(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    public void sendFraudEvent(Object event) {
        // broadcast to all clients subscribed to /topic/fraud
        messagingTemplate.convertAndSend("/topic/fraud", event);
    }
}
