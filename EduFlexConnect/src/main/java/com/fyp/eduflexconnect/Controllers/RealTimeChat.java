package com.fyp.eduflexconnect.Controllers;

import com.fyp.eduflexconnect.Models.Message;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;

//public class RealTimeChat {
//
////    private SimpMessagingTemplate simpMessagingTemplate;
////    @MessageMapping("/message")
////    @SendTo("/group/public")
////    public Message receivedMessage(@Payload Message message){
////        simpMessagingTemplate.convertAndSend("/group"+ message.getChat().getId(), message);
////        return message;
////    }
//}
