package spring.simple_chat_sping.model;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class MessageResponse {
    
    private String sender;

    private String receiver;

    private String content;

    private LocalDateTime dateTime;
}
