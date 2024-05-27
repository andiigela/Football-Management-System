package com.football.dev.footballapp.config.websocket;
import org.hibernate.Session;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.security.config.annotation.web.messaging.MessageSecurityMetadataSourceRegistry;
import org.springframework.security.config.annotation.web.socket.AbstractSecurityWebSocketMessageBrokerConfigurer;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.messaging.util.matcher.MessageMatcher;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketSecurityConfiguration extends AbstractSecurityWebSocketMessageBrokerConfigurer {
    @Override
    protected void configureInbound(MessageSecurityMetadataSourceRegistry messages) {
        messages
                .nullDestMatcher().authenticated()
                .simpDestMatchers("/app/notifications/askedpermission").hasRole("ADMIN_CLUB")
                .simpDestMatchers("/app/askedpermission/**").hasRole("ADMIN_CLUB")
                .simpSubscribeDestMatchers("/topic/notifications/askedpermission").hasRole("ADMIN_LEAGUE")
                .simpSubscribeDestMatchers("/topic/askedpermission/**").hasRole("ADMIN_CLUB")
                .anyMessage().authenticated();
    }
    @Override
    protected boolean sameOriginDisabled() {
        return true;
    }

}
