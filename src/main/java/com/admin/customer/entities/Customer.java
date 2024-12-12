package com.admin.customer.entities;

import jakarta.persistence.*;
//import jakarta.validation.constraints.Email;
//import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;    
    
    @Column(nullable = false)
//    @NotBlank
    private String userName;
    
    @Column(nullable = false)
//    @NotBlank
    private String name;
    
    private String phone;
    
    @Column(nullable = false, unique = true)
//    @Email
    private String email;
    
    @Column(nullable = false)
    private LocalDate startDate;
    
    private LocalDate endDate;
}
