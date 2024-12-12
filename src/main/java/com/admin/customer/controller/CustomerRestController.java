package com.admin.customer.controller;

import com.admin.customer.service.CustomerService;
import com.admin.customer.entities.Customer;
import com.admin.customer.exception.BussinesRuleException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.BindingResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/customers")
public class CustomerRestController {

    private static final Logger logger = LoggerFactory.getLogger(CustomerRestController.class);

    @Autowired
    private CustomerService customerService;

    @PostMapping
    public ResponseEntity<?> createCustomer(@Valid @RequestBody Customer customer, BindingResult result) {
        if (result.hasErrors()) {
            logger.error("Errores de validación al crear el cliente: {}", result.getAllErrors());
            return ResponseEntity.badRequest().body(result.getAllErrors());
        }
        try {
            validateCustomer(customer);
            logger.info("Creando cliente con nombre de usuario: {}", customer.getUserName());
            return ResponseEntity.ok(customerService.saveCustomer(customer));
        } catch (BussinesRuleException e) {
            logger.error("Excepción de regla de negocio al crear un cliente: {}", e.getMessage());
            return ResponseEntity.status(e.getHttpStatus()).body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Customer> getCustomer(@PathVariable Long id) {
        logger.info("Obteniendo cliente con ID: {}", id);
        return customerService.getCustomerById(id)
                .map(customer -> {
                    logger.info("Cliente encontrado: {}", customer);
                    return ResponseEntity.ok(customer);
                })
                .orElseGet(() -> {
                    logger.warn("Cliente con ID {} ​​no encontrado", id);
                    return ResponseEntity.notFound().build();
                });
    }

    @GetMapping
    public ResponseEntity<List<Customer>> getAllCustomers() {
        logger.info("Obteniendo a todos los clientes");
        List<Customer> customers = customerService.getAllCustomers();
        logger.info("Número de clientes recuperados: {}", customers.size());
        return ResponseEntity.ok(customers);
    }

    @PutMapping("/{id}")
    public ResponseEntity<? extends Object> updateCustomer(@PathVariable Long id, @RequestBody Customer customer) {
        logger.info("Actualización de cliente con ID: {}", id);
        return customerService.getCustomerById(id)
                .map(existingCustomer -> {
                    try {
                        validateCustomer(customer);
                        customer.setId(id);
                        Customer updatedCustomer = customerService.saveCustomer(customer);
                        logger.info("Cliente actualizado: {}", updatedCustomer);
                        return ResponseEntity.ok(updatedCustomer);
                    } catch (BussinesRuleException e) {
                        logger.error("Excepción de regla de negocio al actualizar el cliente: {}", e.getMessage());
                        return ResponseEntity.status(e.getHttpStatus()).body(null);
                    }
                })
                .orElseGet(() -> {
                    logger.warn("Cliente con ID {} ​​no encontrado", id);
                    return ResponseEntity.notFound().build();
                });
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteCustomer(@PathVariable Long id) {
        logger.info("Eliminación de cliente con ID: {}", id);
        return customerService.getCustomerById(id)
                .map(customer -> {
                    customerService.deleteCustomer(id);
                    logger.info("Cliente eliminado con ID: {}", id);
                    return ResponseEntity.noContent().build();
                })
                .orElseGet(() -> {
                    logger.warn("Cliente con ID {} ​​no encontrado", id);
                    return ResponseEntity.notFound().build();
                });
    }

    @GetMapping("/search")
    public ResponseEntity<List<Customer>> searchByUserName(@RequestParam String userName) {
        logger.info("Búsqueda de clientes por nombre de usuario: {}", userName);
        List<Customer> customers = customerService.findByUserName(userName);
        logger.info("Número de clientes encontrados: {}", customers.size());
        return ResponseEntity.ok(customers);
    }

    @GetMapping("/advanced-search")
    public ResponseEntity<List<Customer>> advancedSearch(
            @RequestParam(required = false) String userName,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String phone,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) LocalDate startDate,
            @RequestParam(required = false) LocalDate endDate) {
        logger.info("Realizar búsqueda avanzada con parámetros - userName: {}, name: {}, phone: {}, email: {}, startDate: {}, endDate: {}",
                userName, name, phone, email, startDate, endDate);
        List<Customer> customers = customerService.advancedSearch(userName, name, phone, email, startDate, endDate);
        logger.info("Número de clientes encontrados: {}", customers.size());
        return ResponseEntity.ok(customers);
    }

    private void validateCustomer(Customer customer) throws BussinesRuleException {
        if (customer.getName() == null || customer.getName().isEmpty()) {
            logger.error("Error de validación: se requiere el nombre del cliente");
            throw new BussinesRuleException(
                customer.getUserName(), customer.getName(), customer.getPhone(), customer.getEmail(), 
                customer.getStartDate(), customer.getEndDate(), "El nombre es obligatorio.", HttpStatus.BAD_REQUEST
            );
        }
        if (customer.getEmail() == null || !customer.getEmail().contains("@")) {
            logger.error("Error de validación: el correo electrónico del cliente debe ser válido");
            throw new BussinesRuleException(
                customer.getUserName(), customer.getName(), customer.getPhone(), customer.getEmail(), 
                customer.getStartDate(), customer.getEndDate(), "El correo electrónico debe ser válido.", HttpStatus.BAD_REQUEST
            );
        }
        if (customer.getStartDate() == null) {
            logger.error("Error de validación: se requiere la fecha de inicio del cliente");
            throw new BussinesRuleException(
                customer.getUserName(), customer.getName(), customer.getPhone(), customer.getEmail(), 
                customer.getStartDate(), customer.getEndDate(), "Se requiere fecha de inicio.", HttpStatus.BAD_REQUEST
            );
        }
    }
}
