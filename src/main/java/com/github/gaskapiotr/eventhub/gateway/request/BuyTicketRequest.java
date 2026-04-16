package com.github.gaskapiotr.eventhub.gateway.request;

public record BuyTicketRequest(
        Long workshopId,
        String attendeeEmail
) {}
