package ru.xast.task.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.xast.task.models.Supplier;
import ru.xast.task.repositories.SupplierRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SupplierServiceTest {

    @Mock
    private SupplierRepository supplierRepository;

    @InjectMocks
    private SupplierService supplierService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void save() {
        Supplier supplier = new Supplier();

        supplierService.save(supplier);

        verify(supplierRepository, times(1)).save(supplier);
    }

    @Test
    void findAll() {
        when(supplierRepository.findAll()).thenReturn(Collections.singletonList(new Supplier("Name","Email")));

        List<Supplier> result = supplierService.findAll();

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(supplierRepository, times(1)).findAll();
    }

    @Test
    void update() {
        UUID id = UUID.randomUUID();
        Supplier updatedSupplier = new Supplier();

        supplierService.update(id, updatedSupplier);

        assertEquals(id, updatedSupplier.getSupplier_id());
        verify(supplierRepository, times(1)).save(updatedSupplier);
    }

    @Test
    void findOne() {
        UUID id = UUID.randomUUID();
        Supplier supplier = new Supplier();
        when(supplierRepository.findById(id)).thenReturn(Optional.of(supplier));

        Supplier result = supplierService.findOne(id);

        assertNotNull(result);
        verify(supplierRepository, times(1)).findById(id);
    }

}