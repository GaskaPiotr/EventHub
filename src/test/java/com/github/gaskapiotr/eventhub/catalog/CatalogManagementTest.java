package com.github.gaskapiotr.eventhub.catalog;

import com.github.gaskapiotr.eventhub.catalog.management.CatalogManagement;
import com.github.gaskapiotr.eventhub.catalog.repository.WorkshopRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CatalogManagementTest {

    @Mock
    private WorkshopRepository workshopRepository;

    @InjectMocks
    private CatalogManagement catalogManagement;

    @Test
    void shouldReturnNotFoundWhenWorkshopDoesNotExists() {
        // Arrange
        Long invalidWorkshopId = 999L;
        when(workshopRepository.findById(invalidWorkshopId)).thenReturn(Optional.empty());

        // Act
        ReservationResponse response = catalogManagement.reserveSeat(invalidWorkshopId);

        // Assert
        assertThat(response).isNotNull();
        assertThat(response.result()).isEqualTo(ReservationResult.NOT_FOUND);
        assertThat(response.price()).isNull();
    }
}
