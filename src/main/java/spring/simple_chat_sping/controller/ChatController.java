package spring.simple_chat_sping.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.MessageHandlingException;

import spring.simple_chat_sping.component.JwtUtil;
import spring.simple_chat_sping.entity.Contact;
import spring.simple_chat_sping.entity.Message;
import spring.simple_chat_sping.model.MessageRequest;
import spring.simple_chat_sping.model.TokenData;
import spring.simple_chat_sping.service.ChatService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;


@RestController
@Slf4j
public class ChatController {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private ChatService chatService;
    
    @PostMapping("/send")
    public void sendMessage(TokenData tokenData, @RequestBody MessageRequest messageRequest) throws MessageHandlingException {
        chatService.sendMessageService(messageRequest);
    }

    @GetMapping("/generate")
    public String getToken(@RequestParam String username) throws Exception {
        return jwtUtil.generateTokenFromUsername(username);
    }

    @GetMapping("/messages")
    public List<Message> getMessages(TokenData tokenData) throws Exception {
        
        return chatService.showMessagesService(tokenData.getClaim().get("username").asString());
    }
    
    @GetMapping("/contacts")
    public List<Contact> showContacts(TokenData tokenData) throws Exception {
        return chatService.showContactsService(tokenData.getClaim().get("username").asString());
    }
    
}
