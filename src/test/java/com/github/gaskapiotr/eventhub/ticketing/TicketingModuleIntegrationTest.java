package com.github.gaskapiotr.eventhub.ticketing;

import com.github.gaskapiotr.eventhub.AbstractIntegrationTest;
import com.github.gaskapiotr.eventhub.catalog.CatalogInternalAPI;
import com.github.gaskapiotr.eventhub.catalog.ReservationResponse;
import com.github.gaskapiotr.eventhub.catalog.ReservationResult;
import com.github.gaskapiotr.eventhub.ticketing.management.TicketingManagement;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.modulith.test.ApplicationModuleTest;
import org.springframework.modulith.test.PublishedEvents;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ApplicationModuleTest
class TicketingModuleIntegrationTest extends AbstractIntegrationTest {
    @Autowired
    private TicketingManagement ticketingManagement;

    @MockitoBean
    private CatalogInternalAPI catalogInternalAPI;

    @Test
    void shouldBuyTicketAndPublishEventToOutbox(PublishedEvents events) {
        // Arrange
        Long workshopId = 1L;
        String attendeeEmail = "test@example.com";
        String workshopTitle = "Java Architecture Workshop";

        ReservationResponse fakeResponse = new ReservationResponse(
                ReservationResult.SUCCESS,
                workshopTitle,
                BigDecimal.valueOf(50.00)
        );

        when(catalogInternalAPI.reserveSeat(workshopId)).thenReturn(fakeResponse);

        // Act
        TicketDTO savedTicket = ticketingManagement.buyTicket(workshopId, attendeeEmail);

        // Assert
        verify(catalogInternalAPI).reserveSeat(workshopId);

        assertThat(savedTicket).isNotNull();
        assertThat(savedTicket.attendeeEmail()).isEqualTo(attendeeEmail);

        var matchingEvents = events.ofType(TicketPurchasedEvent.class);
        assertThat(matchingEvents).hasSize(1);

        TicketPurchasedEvent publishedEvent = matchingEvents.iterator().next();
        assertThat(publishedEvent.attendeeEmail()).isEqualTo(attendeeEmail);
        assertThat(publishedEvent.workshopTitle()).isEqualTo(workshopTitle);
    }

}