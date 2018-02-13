package com.business.webSocket;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

/**
 * @author yutong
 * @version 1.0
 * @description
 * @since 2018/2/13 23:06
 */
@Controller
public class MessageWebSocket {

    @SendTo("/topic/notice")
    @MessageMapping("/change-notice")
    public String greeting(String value) {
        return value;
    }
}
