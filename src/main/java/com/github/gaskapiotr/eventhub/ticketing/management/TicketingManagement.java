package com.github.gaskapiotr.eventhub.ticketing.management;

import com.github.gaskapiotr.eventhub.catalog.CatalogInternalAPI;
import com.github.gaskapiotr.eventhub.ticketing.TicketDTO;
import com.github.gaskapiotr.eventhub.ticketing.TicketingExternalAPI;
import com.github.gaskapiotr.eventhub.ticketing.mapper.TicketMapper;
import com.github.gaskapiotr.eventhub.ticketing.repository.TicketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        // TODO
    }
}
