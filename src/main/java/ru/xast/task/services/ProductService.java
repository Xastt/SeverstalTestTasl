package ru.xast.task.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.xast.task.models.Product;
import ru.xast.task.repositories.ProductRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@Transactional
public class ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public void save(Product product) {
        try {
            productRepository.save(product);
            log.info("Product saved, id: {}", product.getProduct_id());
        }catch (Exception e) {
            log.error("Error while saving product: {}", e.getMessage());
            throw new RuntimeException("Error while saving product: " + e.getMessage());
        }
    }

    @Transactional(readOnly = true)
    public List<Product> findAll() {
        try {
            List<Product> productList = productRepository.findAll();
            log.info("Found {} Product records", productList.size());
            return productList;
        } catch (Exception e) {
            log.error("Error finding all Product records", e);
            throw new RuntimeException("Failed to find all Product records", e);
        }
    }

    public void update(UUID id, Product updatedProduct){
        try {
            updatedProduct.setProduct_id(id);
            productRepository.save(updatedProduct);
            log.info("Product updated successfully with id: {}", id);
        } catch (Exception e) {
            log.error("Error updating Product with id: {}", id, e);
            throw new RuntimeException("Failed to update Product", e);
        }
    }

    @Transactional(readOnly = true)
    public Product findOne(UUID id) {
        try{
            Optional<Product> product = productRepository.findById(id);
            if(product.isPresent()){
                log.info("Product found, id: {}", id);
                return product.get();
            }else{
                log.warn("Product not found, id: {}", id);
                return null;
            }
        }catch(Exception e){
            log.error("Error finding Product with id: {}, {}", id, e.getMessage());
            throw new RuntimeException("Failed to find Product with id: " + id);
        }
    }

    @Transactional(readOnly = true)
    public List<Product> findAllById(List<UUID> ids) {
        try {
            List<Product> productList = productRepository.findAllById(ids);
            log.info("Found {} Product records by ID", productList.size());
            return productList;
        }catch (Exception e) {
            log.error("Error finding all Product records by ID: {}", ids, e);
            throw new RuntimeException("Failed to find all Product records by ID: " + ids);
        }
    }
}
