package com.github.gaskapiotr.eventhub.catalog.mapper;

import com.github.gaskapiotr.eventhub.catalog.WorkshopDTO;
import com.github.gaskapiotr.eventhub.catalog.model.Workshop;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface WorkshopMapper {
    WorkshopDTO toDto(Workshop entity);
    Workshop toEntity(WorkshopDTO dto);
}
