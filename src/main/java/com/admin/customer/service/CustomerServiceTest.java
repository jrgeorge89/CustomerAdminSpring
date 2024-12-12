package com.admin.customer.service;

import com.admin.customer.entities.Customer;
import com.admin.customer.exception.BussinesRuleException;
import com.admin.customer.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerService customerService;

    private Customer customer;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        customer = new Customer(1L, "jrincon", "Jorge Rincón", "3122464876", "jrgeorge89@gmail.com", LocalDate.now(), null);
    }

    @Test
    public void testSaveCustomer_Success() throws BussinesRuleException {
        when(customerRepository.save(any(Customer.class))).thenReturn(customer);
        Customer savedCustomer = customerService.saveCustomer(customer);
        assertNotNull(savedCustomer);
        assertEquals(customer.getUserName(), savedCustomer.getUserName());
        verify(customerRepository, times(1)).save(customer);
    }

    @Test
    public void testSaveCustomer_DataIntegrityViolation() {
        when(customerRepository.save(any(Customer.class))).thenThrow(new DataIntegrityViolationException("Duplicate email"));
        BussinesRuleException exception = assertThrows(BussinesRuleException.class, () -> {
            customerService.saveCustomer(customer);
        });
        assertEquals("El correo electrónico ya existe.", exception.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, exception.getHttpStatus());
    }

    @Test
    public void testGetCustomerById_Success() {
        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
        Optional<Customer> retrievedCustomer = customerService.getCustomerById(1L);
        assertTrue(retrievedCustomer.isPresent());
        assertEquals(customer.getUserName(), retrievedCustomer.get().getUserName());
    }

    @Test
    public void testGetCustomerById_NotFound() {
        when(customerRepository.findById(1L)).thenReturn(Optional.empty());
        Optional<Customer> retrievedCustomer = customerService.getCustomerById(1L);
        assertFalse(retrievedCustomer.isPresent());
    }

    @Test
    public void testGetAllCustomers() {
        List<Customer> customers = Arrays.asList(customer, new Customer(2L, "oestrada", "Orlando Estrada", "3122464876", "rinconestrada89@hotmail.com", LocalDate.now(), null));
        when(customerRepository.findAll()).thenReturn(customers);
        List<Customer> retrievedCustomers = customerService.getAllCustomers();
        assertEquals(2, retrievedCustomers.size());
        verify(customerRepository, times(1)).findAll();
    }

    @Test
    public void testDeleteCustomer() {
        doNothing().when(customerRepository).deleteById(1L);
        customerService.deleteCustomer(1L);
        verify(customerRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testFindByUserName() {
        List<Customer> customers = Arrays.asList(customer);
        when(customerRepository.findByUserName("jrincon")).thenReturn(customers);
        List<Customer> retrievedCustomers = customerService.findByUserName("oestrada");
        assertEquals(1, retrievedCustomers.size());
        assertEquals(customer.getUserName(), retrievedCustomers.get(0).getUserName());
    }

    @Test
    public void testAdvancedSearch() {
        List<Customer> customers = Arrays.asList(customer);
        when(customerRepository.findByUserNameOrNameOrPhoneOrEmailOrStartDateOrEndDate(anyString(), anyString(), anyString(), anyString(), any(LocalDate.class), any(LocalDate.class)))
                .thenReturn(customers);
        List<Customer> retrievedCustomers = customerService.advancedSearch("jrincon", "Jorge Rincón", "3122464876", "jrgeorge89@gmail.com", LocalDate.now(), null);
        assertEquals(1, retrievedCustomers.size());
        assertEquals(customer.getUserName(), retrievedCustomers.get(0).getUserName());
    }
}
