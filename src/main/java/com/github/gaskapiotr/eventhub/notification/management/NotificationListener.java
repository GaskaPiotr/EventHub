package com.github.gaskapiotr.eventhub.notification.management;

import com.github.gaskapiotr.eventhub.ticketing.TicketPurchasedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.modulith.events.ApplicationModuleListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class NotificationListener {
    private final NotificationListener notificationListener;

    @ApplicationModuleListener
    void on(TicketPurchasedEvent event) {
        // TODO
    }
}
