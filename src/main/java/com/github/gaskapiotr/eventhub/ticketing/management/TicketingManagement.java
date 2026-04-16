package com.github.gaskapiotr.eventhub.ticketing.management;

import com.github.gaskapiotr.eventhub.catalog.CatalogInternalAPI;
import com.github.gaskapiotr.eventhub.catalog.ReservationResponse;
import com.github.gaskapiotr.eventhub.catalog.ReservationResult;
import com.github.gaskapiotr.eventhub.ticketing.TicketDTO;
import com.github.gaskapiotr.eventhub.ticketing.TicketPurchasedEvent;
import com.github.gaskapiotr.eventhub.ticketing.TicketingExternalAPI;
import com.github.gaskapiotr.eventhub.ticketing.mapper.TicketMapper;
import com.github.gaskapiotr.eventhub.ticketing.model.Ticket;
import com.github.gaskapiotr.eventhub.ticketing.repository.TicketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class TicketingManagement implements TicketingExternalAPI {
    private final TicketRepository repository;
    private final TicketMapper mapper;
    private final ApplicationEventPublisher eventPublisher;
    private final CatalogInternalAPI catalogInternalAPI;

    @Override
    @Transactional
    public TicketDTO buyTicket(Long workshopId, String attendeeEmail) {
        ReservationResponse response = catalogInternalAPI.reserveSeat(workshopId);
        throwExceptionIfUnsuccessful(response, workshopId);
        Ticket savedTicket = createAndSaveTicket(workshopId, attendeeEmail, response.price());
        publishTicketPurchasedEvent(savedTicket.getId(), attendeeEmail, response);
        return mapper.toDto(savedTicket);
    }
    
    private void throwExceptionIfUnsuccessful(ReservationResponse response, Long workshopId) {
        if (response.result() == ReservationResult.NOT_FOUND) {
            throw new IllegalArgumentException("Workshop with ID: " + workshopId + " does not exists.");
        } else if (response.result() == ReservationResult.SOLD_OUT) {
            throw new IllegalStateException("Workshop " + response.workshopTitle() + " is completely sold out.");
        }
    }

    private Ticket createAndSaveTicket(Long workshopId, String attendeeEmail, BigDecimal price) {
        Ticket ticket = createTicket(workshopId, attendeeEmail, price);
        return repository.save(ticket);
    }

    private Ticket createTicket(Long workshopId, String attendeeEmail, BigDecimal price) {
        return Ticket.builder()
                .workshopId(workshopId)
                .attendeeEmail(attendeeEmail)
                .purchaseDate(LocalDateTime.now())
                .price(price)
                .build();
    }

    public void publishTicketPurchasedEvent(Long ticketId, String attendeeEmail, ReservationResponse response) {
        eventPublisher.publishEvent(new TicketPurchasedEvent(
                ticketId,
                attendeeEmail,
                response.workshopTitle(),
                response.price()
        ));
    }
}
