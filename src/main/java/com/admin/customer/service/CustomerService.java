package com.admin.customer.service;

import com.admin.customer.entities.Customer;
import com.admin.customer.exception.BussinesRuleException;
import com.admin.customer.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    private static final Logger logger = LoggerFactory.getLogger(CustomerService.class);

    @Autowired
    private CustomerRepository customerRepository;

    public Customer saveCustomer(Customer customer) throws BussinesRuleException {
        try {
            logger.info("Cliente guardado: {}", customer);
            return customerRepository.save(customer);
        } catch (DataIntegrityViolationException e) {
            logger.error("Violación de la integridad de los datos al guardar al cliente: {}", e.getMessage());
            throw new BussinesRuleException(customer.getUserName(), customer.getName(), customer.getPhone(), customer.getEmail(), customer.getStartDate(), customer.getEndDate(), "El correo electrónico ya existe.", HttpStatus.BAD_REQUEST);
        }
    }

    public Optional<Customer> getCustomerById(Long id) {
        logger.info("Obteniendo cliente por ID: {}", id);
        return customerRepository.findById(id);
    }

    public List<Customer> getAllCustomers() {
        logger.info("Obteniendo a todos los clientes");
        return customerRepository.findAll();
    }

    public void deleteCustomer(Long id) {
        logger.info("Eliminación de cliente por ID: {}", id);
        customerRepository.deleteById(id);
    }

    public List<Customer> findByUserName(String userName) {
        logger.info("Búsqueda de clientes por nombre de usuario: {}", userName);
        return customerRepository.findByUserName(userName);
    }

    public List<Customer> advancedSearch(String userName, String name, String phone, String email, LocalDate startDate, LocalDate endDate) {
        logger.info("Realizar búsqueda avanzada con parámetros - userName: {}, name: {}, phone: {}, email: {}, startDate: {}, endDate: {}",
                userName, name, phone, email, startDate, endDate);
        return customerRepository.findByUserNameOrNameOrPhoneOrEmailOrStartDateOrEndDate(userName, name, phone, email, startDate, endDate);
    }
}
