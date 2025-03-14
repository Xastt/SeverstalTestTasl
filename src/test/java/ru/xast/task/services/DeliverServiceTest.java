package ru.xast.task.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.xast.task.models.Deliver;
import ru.xast.task.models.Product;
import ru.xast.task.repositories.DeliverRepository;
import ru.xast.task.repositories.ProductRepository;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DeliverServiceTest {

    @Mock
    private DeliverRepository deliverRepository;

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private DeliverService deliverService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void save() {
        Deliver deliver = new Deliver();
        Product product = new Product();
        deliver.setProducts(Collections.singletonList(product));

        deliverService.save(deliver);

        verify(deliverRepository, times(1)).save(deliver);
        verify(productRepository, times(1)).save(product);
    }

    @Test
    void findAll() {
        when(deliverRepository.findAll()).thenReturn(Collections.singletonList(new Deliver()));

        List<Deliver> result = deliverService.findAll();

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(deliverRepository, times(1)).findAll();
    }

    @Test
    void getDeliveriesByDateRange() {
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = LocalDate.now().plusDays(1);
        when(deliverRepository.findByDeliveryDateBetween(startDate, endDate))
                .thenReturn(Collections.singletonList(new Deliver()));

        List<Deliver> result = deliverService.getDeliveriesByDateRange(startDate, endDate);

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(deliverRepository, times(1)).findByDeliveryDateBetween(startDate, endDate);
    }
}