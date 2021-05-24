package com.example.fulldev.manager;

import com.example.fulldev.manager.redis.TopMessageListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.Topic;

@Configuration
public class MessageListenerConfig {

    @Bean
    public RedisMessageListenerContainer listenerContainer(RedisConnectionFactory factory){
        RedisMessageListenerContainer container=new RedisMessageListenerContainer();
        container.setConnectionFactory(factory);
        Topic topic=new PatternTopic("__keyevent@7__:expired");

        container.addMessageListener(new TopMessageListener(),topic);
        return container;
    }
}
