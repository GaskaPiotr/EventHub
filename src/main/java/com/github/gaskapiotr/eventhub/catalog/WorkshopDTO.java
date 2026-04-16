package com.github.gaskapiotr.eventhub.catalog;

import java.time.LocalDateTime;

public record WorkshopDTO(
        Long id,
        String title,
        String description,
        LocalDateTime date,
        int maxCapacity,
        int currentAttendees
) {}
