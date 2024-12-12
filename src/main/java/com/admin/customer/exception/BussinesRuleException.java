package com.admin.customer.exception;

import java.time.LocalDate;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;

@Data
@EqualsAndHashCode(callSuper=false)
public class BussinesRuleException extends Exception {
        
    private long id;
    private String userName;
    private String name;
    private String phone;
    private String email;
    private LocalDate startDate;
    private LocalDate endDate;
    private HttpStatus httpStatus;
    
    public BussinesRuleException(long id, String userName, String name, String phone, String email, LocalDate startDate, LocalDate endDate, String message, HttpStatus httpStatus) {
        super(message);
        this.id = id;
        this.userName = userName;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.startDate = startDate;
        this.endDate = endDate;
        this.httpStatus = httpStatus;
    }

    public BussinesRuleException(String userName, String name, String phone, String email, LocalDate startDate, LocalDate endDate, String message, HttpStatus httpStatus) {
        super(message);
        this.userName = userName;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.startDate = startDate;
        this.endDate = endDate;
        this.httpStatus = httpStatus;
    }

    public BussinesRuleException(String message, Throwable cause) {
        super(message, cause);
    }     
}
