package com.github.gaskapiotr.eventhub.catalog;

import java.util.List;

public interface CatalogExternalAPI {
    List<WorkshopDTO> getAllWorkshops();
    WorkshopDTO createWorkshop(WorkshopDTO dto);
}
