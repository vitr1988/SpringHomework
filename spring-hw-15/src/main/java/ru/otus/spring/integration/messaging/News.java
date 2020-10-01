package ru.otus.spring.integration.messaging;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import ru.otus.spring.integration.domain.Content;
import ru.otus.spring.integration.domain.Information;

import java.util.Collection;

@MessagingGateway
public interface News {

    @Gateway(requestChannel = "rbcChannel", replyChannel = "audienceChannel")
    Collection<Information> gather(Collection<Content> contents);
}
