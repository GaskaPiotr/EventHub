package com.github.gaskapiotr.eventhub.catalog.repository;

import com.github.gaskapiotr.eventhub.catalog.model.Workshop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkshopRepository extends JpaRepository<Workshop, Long> {
}
