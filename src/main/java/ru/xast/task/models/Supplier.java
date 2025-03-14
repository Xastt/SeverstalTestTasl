package ru.xast.task.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "supplier")
public class Supplier {

    @Id
    @Column(name = "supplier_id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID supplier_id;

    @Column(name = "company_name", unique = true)
    @NotEmpty(message = "Enter your company name!")
    @Size(min = 2, max = 100, message = "Your company name should be between 2 and 100!")
    private String company_name;

    @Column(name = "work_email", unique = true)
    @NotEmpty(message = "Enter your email!")
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$",
            message = "Please, enter correct email")
    private String work_email;


    public Supplier() {}

    public Supplier(String company_name, String work_email) {
        this.company_name = company_name;
        this.work_email = work_email;
    }
}
