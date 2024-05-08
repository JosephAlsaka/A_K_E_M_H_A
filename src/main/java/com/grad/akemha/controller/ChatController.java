package com.grad.akemha.controller;

import com.grad.akemha.entity.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*")
@Slf4j
@RestController
public class ChatController {
    //    @Autowired
//    ChatMessageModelRepository chatMessageModelRepository;
//    two methods.
//    the first one is to add user. when new user connect to our chat app, we need to hit that endpoint and inform ALL the users that we have a new user joined the chat
//i think it is the same like when the doctor start the conversaion
//
//    second method is to send the msg
//
//    payload == object

//    @MessageMapping("/chat.sendMessage") // tells what is the url you want to use to invoke this method
//    @SendTo("/topic/public")
//    // to which topic or to which queue you want to send this one // the topic comes from method configureMessageBroker in WebSocketConfig class // it will send automaticly to /topic/public
//    public ChatMessageModel sendMessage(
//            @Payload ChatMessageModel chatMessage // in http we just put @RequestBody (which is an object) but in websocket we use @Payload they are the same
//    ) {
//        System.out.println("hello I'm sami in sendMessage");
//        return chatMessage;
//    }

//    @MessageMapping("/chat.addUser") //note: messageMapping is the endPoint.  the end point is /app/chat.addUser   app from prefix config
//    @SendTo("/topic/public")
//    public ChatMessageModel addUser(
//            @Payload ChatMessageModel chatMessage,
//            SimpMessageHeaderAccessor headerAccessor
//    ) {
//        System.out.println("hello I'm sami in addUser");
//        // Add username in WebSocket session
//        headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());
//        return chatMessage;
//        // the return Model will return to /topic/  so the client should subscribe on topic to read the msg
//    }


    @MessageMapping("/consultation/{consultationId}/chat")
    @SendTo("/topic/consultation/{consultationId}/messages")
    public Message sendMessageWithWebsocket(@DestinationVariable String consultationId,
                                         @Payload Message message) {
        log.info("new message arrived in chat with id {}", consultationId);
        System.out.println(message);
        return message;
//        var messages = this.chats.getOrDefault(chatId);
//        messages.add(message.getPayload());
//        chats.put(chatId, messages);
//        return messages;
    }

//    @MessageMapping("/gifts")
//    @SendTo("/topic/messages")
//    public ChatMessageModel send(@Payload ChatMessageModel messageModel){
//        log.info("received chat message");
//        System.out.println(messageModel);
//        return messageModel; // it will return to     @SendTo("/topic/messages") //note: the client subscrieb this pat
//
//    }

}
