package ru.xast.task.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.xast.task.models.Deliver;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface DeliverRepository extends JpaRepository<Deliver, UUID> {

    @Query("SELECT d FROM Deliver d JOIN FETCH d.products WHERE d.delivery_date BETWEEN :startDate AND :endDate")
    List<Deliver> findByDeliveryDateBetween(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

}
