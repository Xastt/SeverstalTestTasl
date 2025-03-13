package ru.xast.task.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "product")
public class Product {

    @Id
    @Column(name = "product_id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID product_id;

    @Column(name = "product_name")
    @NotEmpty(message = "Enter the name of the product!")
    @Size(min = 2, max = 100, message = "Your product name should be between 2 and 100!")
    private String product_name;

    @Column(name = "product_type")
    @NotEmpty(message = "Enter the type of the product!")
    @Size(min = 2, max = 100, message = "Your product type should be between 2 and 100!")
    private String product_type;

    @ManyToOne
    @JoinColumn(name = "supplier_id")
    private Supplier supplier;

    @ManyToOne
    @JoinColumn(name = "delivery_id")
    private Deliver delivery;

    public Product() {}

    public Product(String product_name, String product_type, Supplier supplier, Deliver delivery){
        this.product_name = product_name;
        this.product_type = product_type;
        this.supplier = supplier;
        this.delivery = delivery;
    }


}
