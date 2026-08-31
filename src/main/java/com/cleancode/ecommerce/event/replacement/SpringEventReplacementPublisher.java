package com.cleancode.ecommerce.event.replacement;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class SpringEventReplacementPublisher implements EventReplacementPublisher{

	private final ApplicationEventPublisher applicationEventPublisher;

    public SpringEventReplacementPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Override
    public void publish(ReplacementEvent event) {
        applicationEventPublisher.publishEvent(event);
    }
}
