package com.github.gaskapiotr.eventhub.notification.management;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
class NotificationManagement {

    void sendTicketConfirmation(String email, String workshopTitle, Long ticketId) {
        log.info("=====================================");
        log.info("SENDING EMAIL TO : {}", email);
        log.info("SUBJECT : Ticket Confirmed - {}", workshopTitle);
        log.info("BODY : Thank you for your purchase!");
        log.info("Your official Ticket ID is #{}.", ticketId);
        log.info("=====================================");
    }
}
