package com.github.gaskapiotr.eventhub.ticketing;

import java.math.BigDecimal;

public record TicketPurchasedEvent (
        Long ticketId,
        String attendeeEmail,
        String workshopTitle,
        BigDecimal price
) {}
