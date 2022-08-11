package com.tads.mhsf.repository;

import com.tads.mhsf.model.Customer;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CustomerRepository implements Repository<Customer, Long> {

    public static Customer customerSignedIn;

    private Long currentId;
    private final List<Customer> customers;
    private static volatile CustomerRepository instance;

    private CustomerRepository() {
        this.currentId = 1L;
        this.customers = new ArrayList<>();
    }

    // THREAD-SAFE SINGLETON:
    public static CustomerRepository getInstance() {
        if (instance == null) {
            synchronized (CustomerRepository.class) {
                if (instance == null) {
                    instance = new CustomerRepository();
                }
            }
        }
        return instance;
    }

    @Override
    public void create(Customer customer) {
        customer.setId(currentId++);
        customers.add(customer);
    }

    @Override
    public Optional<Customer> findById(Long id) {
        Customer customer = null;
        for (Customer current : customers) {
            if (current.getId().equals(id)) {
                customer = current;
                break;
            }
        }
        return Optional.ofNullable(customer);
    }

    public Optional<Customer> findByEmail(String email) {
        Customer customer = null;
        for (Customer current : customers) {
            if (current.getEmail().equals(email)) {
                customer = current;
                break;
            }
        }
        return Optional.ofNullable(customer);
    }

    @Override
    public void update(Customer customer) {
        for (Customer current : customers) {
            if (current.getId().equals(customer.getId())) {
                current.setName(customer.getName());
                current.setEmail(customer.getEmail());
                current.setPassword(customer.getPassword());
                break;
            }
        }
    }

    @Override
    public boolean deleteById(Long id) {
        return customers.removeIf(current -> current.getId().equals(id));
    }

    @Override
    public List<Customer> findAll() {
        return customers;
    }
}
