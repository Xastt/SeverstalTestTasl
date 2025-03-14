package ru.xast.task.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.xast.task.models.Product;
import ru.xast.task.repositories.ProductRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void save() {
        Product product = new Product();

        productService.save(product);

        verify(productRepository, times(1)).save(product);
    }

    @Test
    void findAll() {
        when(productRepository.findAll()).thenReturn(Collections.singletonList(new Product()));

        List<Product> result = productService.findAll();

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(productRepository, times(1)).findAll();
    }

    @Test
    void update() {
        UUID id = UUID.randomUUID();
        Product updatedProduct = new Product();

        productService.update(id, updatedProduct);

        assertEquals(id, updatedProduct.getProduct_id());
        verify(productRepository, times(1)).save(updatedProduct);
    }

    @Test
    void findOne() {
        UUID id = UUID.randomUUID();
        Product product = new Product();
        when(productRepository.findById(id)).thenReturn(Optional.of(product));

        Product result = productService.findOne(id);

        assertNotNull(result);
        verify(productRepository, times(1)).findById(id);
    }

    @Test
    void findAllById() {
        List<UUID> ids = Collections.singletonList(UUID.randomUUID());
        when(productRepository.findAllById(ids)).thenReturn(Collections.singletonList(new Product()));

        List<Product> result = productService.findAllById(ids);

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(productRepository, times(1)).findAllById(ids);
    }

}