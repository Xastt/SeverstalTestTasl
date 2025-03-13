package ru.xast.task.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.xast.task.models.Supplier;
import ru.xast.task.repositories.SupplierRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@Transactional
public class SupplierService {

    private final SupplierRepository supplierRepository;

    @Autowired
    public SupplierService(SupplierRepository supplierRepository) {
        this.supplierRepository = supplierRepository;
    }

    public void save(Supplier supplier) {
        try {
            supplierRepository.save(supplier);
            log.info("Supplier saved, id: {}", supplier.getSupplier_id());
        }catch (Exception e) {
            log.error("Error while saving supplier: {}", e.getMessage());
            throw new RuntimeException("Error while saving supplier: " + e.getMessage());
        }
    }

    @Transactional(readOnly = true)
    public List<Supplier> findAll() {
        try {
            List<Supplier> supplierList = supplierRepository.findAll();
            log.info("Found {} PersInf records", supplierList.size());
            return supplierList;
        } catch (Exception e) {
            log.error("Error finding all PersInf records", e);
            throw new RuntimeException("Failed to find all PersInf records", e);
        }
    }

    public void update(UUID id, Supplier updatedSupplier){
        try {
            updatedSupplier.setSupplier_id(id);
            supplierRepository.save(updatedSupplier);
            log.info("Supplier updated successfully with id: {}", id);
        } catch (Exception e) {
            log.error("Error updating Supplier with id: {}", id, e);
            throw new RuntimeException("Failed to update Supplier", e);
        }
    }

    @Transactional(readOnly = true)
    public Supplier findOne(UUID id) {
        try{
            Optional<Supplier> supplier = supplierRepository.findById(id);
            if(supplier.isPresent()){
                log.info("Supplier found, id: {}", id);
                return supplier.get();
            }else{
                log.warn("Supplier not found, id: {}", id);
                return null;
            }
        }catch(Exception e){
            log.error("Error finding Supplier with id: {}, {}", id, e.getMessage());
            throw new RuntimeException("Failed to find Supplier with id: " + id);
        }
    }
}
