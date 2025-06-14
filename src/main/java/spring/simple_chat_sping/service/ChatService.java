package spring.simple_chat_sping.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import spring.simple_chat_sping.entity.Contact;
import spring.simple_chat_sping.entity.Message;
import spring.simple_chat_sping.model.MessageRequest;
import spring.simple_chat_sping.model.MessageResponse;
import spring.simple_chat_sping.repository.ContactRepository;
import spring.simple_chat_sping.repository.MessageRepository;

@Service
public class ChatService {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private ContactRepository contactRepository;

    @Autowired
    private SimpMessagingTemplate sTemplate;

    @Transactional
    public void sendMessageService(MessageRequest messageRequest) {
        Message message = new Message();
        message.setSender(messageRequest.getSender());
        message.setReceiver(messageRequest.getReceiver());
        message.setContent(messageRequest.getContent());
        messageRepository.save(message);

        Contact contact = contactRepository.findByUsernameAndContactUsername(messageRequest.getSender(), messageRequest.getReceiver())
                .orElseThrow();

        contact.setLatestChat(message.getDatetime());
        contactRepository.save(contact);

        //generate destination room
        // List<String> rooms = List.of(messageRequest.getSender(), messageRequest.getReceiver());
        // rooms.sort(null);
        // String room = String.join("_", rooms);

        MessageResponse response = MessageResponse.builder()
                .sender(message.getSender())
                .receiver(message.getReceiver())
                .content(message.getContent())
                .dateTime(message.getDatetime())
                .build();
        
        sTemplate.convertAndSendToUser(contact.getRoom(), "topic/messages", response);
    }

    public List<Message> showMessagesService(String sender) {
        return messageRepository.findAllBySenderOrderByDatetimeDesc(sender);
    }

    public List<Contact> showContactsService(String username) {
        
        return contactRepository.findAllByUsernameOrderByIdAsc(username);
    }

    // public boolean checkTokenBeforeSendingMessage(Tok){

    // }
}
