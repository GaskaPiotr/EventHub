package com.github.gaskapiotr.eventhub.gateway.request;

import com.github.gaskapiotr.eventhub.catalog.WorkshopDTO;

import java.time.LocalDateTime;

public record CreateWorkshopRequest(
        String title,
        String description,
        LocalDateTime date,
        int maxCapacity
) {
    public WorkshopDTO toDomainDto() {
        return new WorkshopDTO(
                null,
                this.title(),
                this.description(),
                this.date(),
                this.maxCapacity(),
                0
        );
    }
}
