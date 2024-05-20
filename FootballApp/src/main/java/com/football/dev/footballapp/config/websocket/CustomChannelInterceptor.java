package com.football.dev.footballapp.config.websocket;
import com.football.dev.footballapp.models.UserEntity;
import com.football.dev.footballapp.models.principal.UserPrincipal;
import com.football.dev.footballapp.security.CustomUserDetailsService;
import com.football.dev.footballapp.security.JWTGenerator;
import com.football.dev.footballapp.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
@Slf4j
@Component
public class CustomChannelInterceptor implements ChannelInterceptor {
    private final JWTGenerator jwtGenerator;
    private final CustomUserDetailsService customUserDetailsService;
    public CustomChannelInterceptor(JWTGenerator jwtGenerator,CustomUserDetailsService customUserDetailsService){
        this.jwtGenerator=jwtGenerator;
        this.customUserDetailsService=customUserDetailsService;
    }
    @SneakyThrows
    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
        log.debug("Accessor Get command" + accessor.getCommand());
        if (StompCommand.CONNECT.equals(accessor.getCommand())) {
            String authToken = accessor.getFirstNativeHeader("Authorization");
            log.debug("Auth Token: " + authToken);
            if (authToken != null && authToken.startsWith("Bearer ")) {
                String jwtToken = authToken.substring(7);
                String username = jwtGenerator.getUsernameFromJWT(jwtToken);
                UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);
                if(userDetails != null){
                    Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    log.debug("Authentication: " + authentication);
                    accessor.setUser(authentication);
                } else {
                // User not found or invalid token, handle the case accordingly
                    throw new Exception("Invalid token or user not found");
                }
            }else {
                // No token provided, handle the case accordingly
                throw new Exception("No token provided");
            }
        }
        return message;
    }
}
