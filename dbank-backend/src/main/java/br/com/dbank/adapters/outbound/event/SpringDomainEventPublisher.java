package br.com.dbank.adapters.outbound.event;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import br.com.dbank.application.event.DomainEventPublisher;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class SpringDomainEventPublisher implements DomainEventPublisher {

    private final ApplicationEventPublisher publisher;

    @Override
    public void publish(Object event) {
        publisher.publishEvent(event);
    }
}	
