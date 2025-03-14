package ru.xast.task.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.xast.task.models.Deliver;
import ru.xast.task.models.Product;
import ru.xast.task.models.Supplier;
import ru.xast.task.repositories.DeliverRepository;
import ru.xast.task.repositories.ProductRepository;
import ru.xast.task.repositories.SupplierRepository;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Service
@Transactional
public class DeliverService {

    private final DeliverRepository deliverRepository;
    private final ProductRepository productRepository;

    @Autowired
    public DeliverService(DeliverRepository deliverRepository, ProductRepository productRepository) {
        this.deliverRepository = deliverRepository;
        this.productRepository = productRepository;
    }

    public void save(Deliver deliver) {
        try {
            deliverRepository.save(deliver);
            for(Product product : deliver.getProducts()) {
                product.setDelivery(deliver);
                productRepository.save(product);
            }
            log.info("Deliver saved, id: {}", deliver.getDelivery_id());
        }catch (Exception e) {
            log.error("Error while saving Deliver: {}", e.getMessage());
            throw new RuntimeException("Error while saving Deliver: " + e.getMessage());
        }
    }

    @Transactional(readOnly = true)
    public List<Deliver> findAll() {
        try {
            List<Deliver> deliverList = deliverRepository.findAll();
            log.info("Found {} Deliver records", deliverList.size());
            return deliverList;
        } catch (Exception e) {
            log.error("Error finding all Deliver records", e);
            throw new RuntimeException("Failed to find all Deliver records", e);
        }
    }

    @Transactional(readOnly = true)
    public List<Deliver> getDeliveriesByDateRange(LocalDate startDate, LocalDate endDate) {
        try {
            List<Deliver> deliverList = deliverRepository.findByDeliveryDateBetween(startDate, endDate);
            log.info("Found {} Deliver records by DATE", deliverList.size());
            return deliverList;
        }catch (Exception e) {
            log.error("Error finding all Deliver records by DATE", e);
            throw new RuntimeException("Failed to find all Deliver records by DATE", e);
        }
    }
}
