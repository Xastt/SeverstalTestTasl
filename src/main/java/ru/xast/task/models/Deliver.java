package ru.xast.task.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "deliver")
public class Deliver {

    @Id
    @Column(name  = "delivery_id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID delivery_id;

    @ManyToOne
    @JoinColumn(name = "supplier_id")
    private Supplier supplier;

    @OneToMany(mappedBy = "delivery", cascade = CascadeType.ALL)
    private List<Product> products = new ArrayList<>();

    @Column(name = "delivery_date")
    private LocalDate delivery_date;

    @Column(name = "weight")
    @Min(value = 1, message = "Your weight should be more than 1!")
    @Max(value = 100000, message = "Your weight should be less than 100000!")
    private Double weight;

    @Column(name = "price_for_kg")
    @Min(value = 1, message = "Your price should be more than 1!")
    @Max(value = 100000, message = "Your price should be less than 100000!")
    private Double price_for_kg;

    public Deliver() {}

    public Deliver(Supplier supplier, List<Product> products) {
        this.supplier = supplier;
        this.products = products;
    }

}
