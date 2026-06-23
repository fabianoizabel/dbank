package br.com.dbank.application.event;

public interface DomainEventPublisher {

    void publish(Object event);

}