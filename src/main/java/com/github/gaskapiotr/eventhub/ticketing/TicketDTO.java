package com.github.gaskapiotr.eventhub.ticketing;

import java.time.LocalDateTime;

public record TicketDTO(
        Long id,
        Long workshopId,
        String attendeeEmail,
        LocalDateTime purchaseDate
) {}
