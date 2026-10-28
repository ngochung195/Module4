package com.codegym.service;

import com.codegym.exception.DuplicateEmailException;
import com.codegym.model.Customer;

public interface ICustomerService extends IGenerateService<Customer> {
    @Override
    void save(Customer customer) throws DuplicateEmailException;
}
