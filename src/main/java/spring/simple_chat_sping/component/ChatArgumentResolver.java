package spring.simple_chat_sping.component;


import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import spring.simple_chat_sping.entity.Message;
import spring.simple_chat_sping.model.TokenData;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.server.ResponseStatusException;

@Component
@Slf4j
public class ChatArgumentResolver implements HandlerMethodArgumentResolver {

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return TokenData.class.equals(parameter.getParameterType()) ||
            Message.class.equals(parameter.getParameterType()) 
        ;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        HttpServletRequest servletRequest = (HttpServletRequest) webRequest.getNativeRequest();
        String token = servletRequest.getHeader("Authorization");
        
        if (token == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized");
        }

        TokenData tokenData = new TokenData();
        tokenData.setClaim(jwtUtil.getUserNameFromJwtToken(token));


        // User user = userRepository.findFirstByToken(token)
        //         .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized"));

        // if (user.getTokenExpiredAt() < System.currentTimeMillis()) {
        //     throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized");
        // }

        return tokenData;
    }
}