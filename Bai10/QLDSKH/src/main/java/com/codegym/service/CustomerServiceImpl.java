package com.codegym.service;

import com.codegym.model.Customer;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CustomerServiceImpl implements ICustomerService {
    private static final Map<Long, Customer> customers;
    private static long autoIncrementId = 1L;

    static {
        customers = new HashMap<>();
        customers.put(autoIncrementId,
                new Customer(autoIncrementId++, "Nguyen Khac Nhat", "nhat@codegym.vn", "Ha Noi"));
        customers.put(autoIncrementId,
                new Customer(autoIncrementId++, "Dang Huy Hoa", "hoa.dang@codegym.vn", "Da Nang"));
        customers.put(autoIncrementId, new Customer(autoIncrementId++, "Le Thi Chau", "chau.le@codegym.vn", "Ha Noi"));
        customers.put(autoIncrementId,
                new Customer(autoIncrementId++, "Nguyen Thuy Duong", "duong.nguyen@codegym.vn", "Sai Gon"));
        customers.put(autoIncrementId, new Customer(autoIncrementId++, "CodeGym", "codegym@codegym.vn", "Viet Nam"));
    }

    @Override
    public List<Customer> findAll() {
        return new ArrayList<>(customers.values());
    }

    @Override
    public Customer findOne(Long id) throws Exception {
        Customer customer = customers.get(id);
        if (customer == null) {
            throw new Exception("Customer not found!");
        }
        return customer;
    }

    @Override
    public void save(Customer customer) {
        if (customer.getId() == null) {
            customer.setId(autoIncrementId++);
        }
        customers.put(customer.getId(), customer);
    }

    @Override
    public void remove(Long id) {
        customers.remove(id);
    }
}
