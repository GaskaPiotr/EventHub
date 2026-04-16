package com.github.gaskapiotr.eventhub.gateway;

import com.github.gaskapiotr.eventhub.catalog.CatalogExternalAPI;
import com.github.gaskapiotr.eventhub.catalog.WorkshopDTO;
import com.github.gaskapiotr.eventhub.gateway.request.BuyTicketRequest;
import com.github.gaskapiotr.eventhub.gateway.request.CreateWorkshopRequest;
import com.github.gaskapiotr.eventhub.ticketing.TicketDTO;
import com.github.gaskapiotr.eventhub.ticketing.TicketingExternalAPI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class GatewayController {
    private final CatalogExternalAPI catalogExternalAPI;
    private final TicketingExternalAPI ticketingExternalAPI;

    @GetMapping("/workshops")
    public List<WorkshopDTO> getAllWorkshops() {
        return catalogExternalAPI.getAllWorkshops();
    }

    @PostMapping("/workshops")
    @ResponseStatus(HttpStatus.CREATED)
    public WorkshopDTO createWorkshop(@RequestBody CreateWorkshopRequest request) {
        return catalogExternalAPI.createWorkshop(request.toDomainDto());
    }

    @PostMapping("/tickets")
    @ResponseStatus(HttpStatus.CREATED)
    public TicketDTO buyTicket(@RequestBody BuyTicketRequest request) {
        return ticketingExternalAPI.buyTicket(request.workshopId(), request.attendeeEmail());
    }
}
