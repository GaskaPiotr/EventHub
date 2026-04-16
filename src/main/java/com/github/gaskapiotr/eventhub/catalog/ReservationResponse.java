package com.github.gaskapiotr.eventhub.catalog;

import java.math.BigDecimal;

public record ReservationResponse(
        ReservationResult result,
        String workshopTitle,
        BigDecimal price
) {}
