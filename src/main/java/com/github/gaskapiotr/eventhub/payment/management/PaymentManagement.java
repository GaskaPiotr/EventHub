package com.github.gaskapiotr.eventhub.payment.management;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Slf4j
@Service
class PaymentManagement {

    void processPayment(Long ticketId, String email, BigDecimal price) {
        log.info("=======================================");
        log.info("INITIATING PAYMENT FOR TICKET ID : {}", ticketId);
        log.info("CHARGING ACCOUNT : {}", email);
        log.info("AMOUNT : ${}", price);
        log.info("STATUS : SUCCESS [MOCKED]");
        log.info("=======================================");
    }
}
