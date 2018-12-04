package com.business.webSocket;

import com.business.dao.users.UserDTORepository;
import com.business.pojo.dto.message.BaseMessageEntity;
import com.business.pojo.dto.message.ChatMessageEntity;
import com.business.pojo.dto.user.UserDTO;
import com.jokers.common.json.JsonUtil;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.util.Date;

import javax.annotation.Resource;

/**
 * @author yutong
 * @version 1.0
 * @description
 * @since 2018/2/13 23:06
 */
@Controller
public class MessageWebSocket {

    @Resource
    private SimpMessagingTemplate simpMessagingTemplate;
    @Resource
    private UserDTORepository userDTORepository;

    @MessageMapping("/change-notice")
    public void greeting(String value) {
        simpMessagingTemplate.convertAndSend("/topic/notice", value);
    }

    @MessageMapping("/chat")
    public void point(String message) throws IOException {
        BaseMessageEntity baseMessageEntity = JsonUtil.getBean(message, BaseMessageEntity.class);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDTO userDTO = (UserDTO) authentication.getPrincipal();
        baseMessageEntity.setSender(userDTO.getUsername());
        ChatMessageEntity chatMessage = createMessage(baseMessageEntity.getSender(), baseMessageEntity.getContent());
        simpMessagingTemplate.convertAndSendToUser(baseMessageEntity.getReceiver(), "/topic/chat", JsonUtil.toJson(chatMessage));
    }

    @MessageMapping("/all")
    public void all(String message) throws IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDTO userDTO = (UserDTO) authentication.getPrincipal();
        ChatMessageEntity chatMessageEntity = createMessage(userDTO.getUsername(), message);
        simpMessagingTemplate.convertAndSend("/topic/notice", JsonUtil.toJson(chatMessageEntity));
    }

    private ChatMessageEntity createMessage(String username, String message) {
        ChatMessageEntity entity = new ChatMessageEntity();
        UserDTO userDTO = userDTORepository.findByUsername(username);
        entity.setAvatar(userDTO.getUsername());
        entity.setContent(message);
        entity.setSendTime(new Date());
        return entity;
    }
}
