package com.github.gaskapiotr.eventhub.ticketing.mapper;

import com.github.gaskapiotr.eventhub.ticketing.TicketDTO;
import com.github.gaskapiotr.eventhub.ticketing.model.Ticket;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface TicketMapper {
    TicketDTO toDto(Ticket entity);
}
