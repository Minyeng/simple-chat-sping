package spring.simple_chat_sping.model;


import java.util.Map;

import com.auth0.jwt.interfaces.Claim;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TokenData {
    
    private Map<String, Claim> claim;
}
