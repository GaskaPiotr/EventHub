package com.github.gaskapiotr.eventhub.catalog.management;

import com.github.gaskapiotr.eventhub.catalog.CatalogExternalAPI;
import com.github.gaskapiotr.eventhub.catalog.CatalogInternalAPI;
import com.github.gaskapiotr.eventhub.catalog.ReservationResult;
import com.github.gaskapiotr.eventhub.catalog.WorkshopDTO;
import com.github.gaskapiotr.eventhub.catalog.mapper.WorkshopMapper;
import com.github.gaskapiotr.eventhub.catalog.model.Workshop;
import com.github.gaskapiotr.eventhub.catalog.repository.WorkshopRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.jdbc.Work;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public ReservationResult reserveSeat(Long workshopId) {
        return findWorkshopById(workshopId)
                .map(this::processReservation)
                .orElse(ReservationResult.NOT_FOUND);
    }

    private Optional<Workshop> findWorkshopById(Long workshopId) {
        return repository.findById(workshopId);
    }

    private ReservationResult processReservation(Workshop workshop) {
        if (isSoldOut(workshop)) {
            return ReservationResult.SOLD_OUT;
        }
        incrementByOneCurrentAttendees(workshop);
        return saveWorkshopAndReturnSuccess(workshop);
    }

    private boolean isSoldOut(Workshop workshop) {
        return workshop.getCurrentAttendees() >= workshop.getMaxCapacity();
    }


    private void incrementByOneCurrentAttendees(Workshop workshop) {
        workshop.setCurrentAttendees(workshop.getCurrentAttendees() + 1);
    }

    private ReservationResult saveWorkshopAndReturnSuccess(Workshop workshop) {
        repository.save(workshop);
        return ReservationResult.SUCCESS;
    }

}
