package com.github.gaskapiotr.eventhub.payment.management;

import com.github.gaskapiotr.eventhub.ticketing.TicketPurchasedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.modulith.events.ApplicationModuleListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class PaymentListener {
    private final PaymentManagement paymentManagement;

    @ApplicationModuleListener
    void on(TicketPurchasedEvent event) {
        paymentManagement.processPayment(
                event.ticketId(),
                event.attendeeEmail(),
                event.price()
        );
    }
}
