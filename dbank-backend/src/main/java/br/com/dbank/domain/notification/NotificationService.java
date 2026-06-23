package br.com.dbank.domain.notification;

import br.com.dbank.domain.event.TransferCompletedEvent;

public interface NotificationService {

    void notifyTransfer(TransferCompletedEvent event);

}