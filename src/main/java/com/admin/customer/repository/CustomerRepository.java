package com.admin.customer.repository;

import com.admin.customer.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    List<Customer> findByUserName(String userName);

    // Búsqueda avanzada
    List<Customer> findByUserNameOrNameOrPhoneOrEmailOrStartDateOrEndDate(String userName, String name, String phone, String email, LocalDate startDate, LocalDate endDate);
}
