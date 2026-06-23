package br.com.dbank.listeners;

import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import br.com.dbank.domain.event.TransferCompletedEvent;
import br.com.dbank.domain.notification.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class TransferCompletedListener {
	
	private final NotificationService notificationService;

    @Async
    @EventListener
    public void handle(TransferCompletedEvent event) {
        log.info("Recebido evento de transferência {}", event.transactionId());

        notificationService.notifyTransfer(event);
    }
}