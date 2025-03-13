package ru.xast.task.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.xast.task.models.Deliver;
import ru.xast.task.models.Supplier;
import ru.xast.task.repositories.DeliverRepository;
import ru.xast.task.repositories.SupplierRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@Transactional
public class DeliverService {

    private final DeliverRepository deliverRepository;
    private final SupplierRepository supplierRepository;

    @Autowired
    public DeliverService(DeliverRepository deliverRepository, SupplierRepository supplierRepository) {
        this.deliverRepository = deliverRepository;
        this.supplierRepository = supplierRepository;
    }

    public void save(Deliver deliver) {
        try {
            deliverRepository.save(deliver);
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

    public void update(UUID id, Deliver updatedDeliver){
        try {
            updatedDeliver.setDelivery_id(id);
            deliverRepository.save(updatedDeliver);
            log.info("Deliver updated successfully with id: {}", id);
        } catch (Exception e) {
            log.error("Error updating Deliver with id: {}", id, e);
            throw new RuntimeException("Failed to update Deliver", e);
        }
    }

    @Transactional(readOnly = true)
    public Deliver findOne(UUID id) {
        try{
            Optional<Deliver> deliver = deliverRepository.findById(id);
            if(deliver.isPresent()){
                log.info("Deliver found, id: {}", id);
                return deliver.get();
            }else{
                log.warn("Deliver not found, id: {}", id);
                return null;
            }
        }catch(Exception e){
            log.error("Error finding Deliver with id: {}, {}", id, e.getMessage());
            throw new RuntimeException("Failed to find Deliver with id: " + id);
        }
    }

    @Transactional(readOnly = true)
    public List<Deliver> getDeliveriesByDateRange(LocalDate startDate, LocalDate endDate) {
        return deliverRepository.findByDeliveryDateBetween(startDate, endDate);
    }

    @Transactional(readOnly = true)
    public List<Supplier> getAllSuppliers() {
        return supplierRepository.findAll();
    }

}
