package com.codegym.customermanagement.service;

import com.codegym.customermanagement.model.Customer;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CustomerService implements ICustomerService {
    private static final Map<Integer, Customer> customers;

    static {
        customers = new HashMap<>();
        customers.put(1, new Customer(1, "John Doe", "john@codegym.vn", "Hanoi"));
        customers.put(2, new Customer(2, "Bill Gates", "bill@codegym.vn", "Danang"));
        customers.put(3, new Customer(3, "Alex Ferguson", "alex@codegym.vn", "Saigon"));
        customers.put(4, new Customer(4, "Adam Levine", "adam@codegym.vn", "Hue"));
        customers.put(5, new Customer(5, "Sophia Bush", "sophia@codegym.vn", "Cantho"));
    }

    @Override
    public List<Customer> findAll() {
        return new ArrayList<>(customers.values());
    }

    @Override
    public void save(Customer customer) {
        customers.put(customer.getId(), customer);
    }

    @Override
    public Customer findById(int id) {
        return customers.get(id);
    }

    @Override
    public void update(int id, Customer customer) {
        customers.put(id, customer);
    }

    @Override
    public void remove(int id) {
        customers.remove(id);
    }
}
