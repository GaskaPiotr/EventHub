package com.github.gaskapiotr.eventhub.ticketing.repository;

import com.github.gaskapiotr.eventhub.ticketing.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {
}
