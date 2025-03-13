package ru.xast.task.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.xast.task.models.Product;

import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {
}
