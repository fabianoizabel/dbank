package br.com.dbank.adapters.outbound.notification;

import org.springframework.stereotype.Service;

import br.com.dbank.domain.event.TransferCompletedEvent;
import br.com.dbank.domain.notification.NotificationService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class NotificationServiceImpl implements NotificationService {

    @Override
    public void notifyTransfer(TransferCompletedEvent event) {
        log.info("""
                
                ==================================
                NOTIFICAÇÃO ENVIADA
                ==================================
                TransactionId: {}
                Conta Origem: {}
                Conta Destino: {}
                Valor: {}
                Data: {}
                ==================================
                """,
                event.transactionId(),
                event.sourceAccountId(),
                event.destinationAccountId(),
                event.amount(),
                event.occurredAt());
    }
}