package com.github.gaskapiotr.eventhub.ticketing.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "ticket")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long workshopId;
    private String attendeeEmail;
    private LocalDateTime purchaseDate;
    private BigDecimal price;
}
