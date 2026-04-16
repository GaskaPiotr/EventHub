package com.github.gaskapiotr.eventhub.catalog.management;

import com.github.gaskapiotr.eventhub.catalog.CatalogExternalAPI;
import com.github.gaskapiotr.eventhub.catalog.CatalogInternalAPI;
import com.github.gaskapiotr.eventhub.catalog.ReservationResult;
import com.github.gaskapiotr.eventhub.catalog.WorkshopDTO;
import com.github.gaskapiotr.eventhub.catalog.mapper.WorkshopMapper;
import com.github.gaskapiotr.eventhub.catalog.repository.WorkshopRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
        // TODO
    }

    @Override
    @Transactional
    public ReservationResult reserveSeat(Long workshopId) {
        // TODO
    }

}
