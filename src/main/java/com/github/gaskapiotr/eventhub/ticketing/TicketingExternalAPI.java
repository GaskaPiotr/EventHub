package com.github.gaskapiotr.eventhub.ticketing;

public interface TicketingExternalAPI {
    TicketDTO buyTicket(Long workshopId, String attendeeEmail);
}
