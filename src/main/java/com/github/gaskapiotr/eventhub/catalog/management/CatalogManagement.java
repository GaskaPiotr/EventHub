package com.github.gaskapiotr.eventhub.catalog.management;

import com.github.gaskapiotr.eventhub.catalog.*;
import com.github.gaskapiotr.eventhub.catalog.mapper.WorkshopMapper;
import com.github.gaskapiotr.eventhub.catalog.model.Workshop;
import com.github.gaskapiotr.eventhub.catalog.repository.WorkshopRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.jdbc.Work;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CatalogManagement implements CatalogExternalAPI, CatalogInternalAPI {
    private final WorkshopRepository repository;
    private final WorkshopMapper mapper;

    @Override
    public List<WorkshopDTO> getAllWorkshops() {
        return repository.findAll().stream()
                .map(mapper::toDto)
                .toList();
    }

    @Override
    @Transactional
    public WorkshopDTO createWorkshop(WorkshopDTO dto) {
        Workshop entity = setupNewWorkshop(dto);
        Workshop savedEntity = repository.save(entity);
        return mapper.toDto(savedEntity);
    }

    private Workshop setupNewWorkshop(WorkshopDTO dto) {
        Workshop entity = mapper.toEntity(dto);
        entity.setCurrentAttendees(0);
        return entity;
    }

    @Override
    @Transactional
    public ReservationResponse reserveSeat(Long workshopId) {
        return findWorkshopById(workshopId)
                .map(this::processReservation)
                .orElse(createReservationResponse(ReservationResult.NOT_FOUND, null, null));
    }

    private Optional<Workshop> findWorkshopById(Long workshopId) {
        return repository.findById(workshopId);
    }

    private ReservationResponse processReservation(Workshop workshop) {
        if (isSoldOut(workshop)) {
            return createReservationResponse(ReservationResult.SOLD_OUT, workshop.getTitle(), workshop.getPrice());
        }
        incrementByOneCurrentAttendees(workshop);
        return saveWorkshopAndReturnSuccess(workshop);
    }

    private ReservationResponse createReservationResponse(
            ReservationResult result,
            String workshopTitle,
            BigDecimal price) {
        return new ReservationResponse(result, workshopTitle, price);
    }

    private boolean isSoldOut(Workshop workshop) {
        return workshop.getCurrentAttendees() >= workshop.getMaxCapacity();
    }


    private void incrementByOneCurrentAttendees(Workshop workshop) {
        workshop.setCurrentAttendees(workshop.getCurrentAttendees() + 1);
    }

    private ReservationResponse saveWorkshopAndReturnSuccess(Workshop workshop) {
        repository.save(workshop);
        return createReservationResponse(ReservationResult.SUCCESS, workshop.getTitle(), workshop.getPrice());
    }

}
