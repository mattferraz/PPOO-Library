package com.tads.mhsf.service;

import com.tads.mhsf.model.Book;
import com.tads.mhsf.model.Customer;
import com.tads.mhsf.repository.CustomerRepository;

import java.util.List;

// FACADE:
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService() {
        this.customerRepository = CustomerRepository.getInstance();
    }

    public Customer findCustomerById(Long id) throws Exception {
        return customerRepository
                .findById(id)
                .orElseThrow(() -> new Exception("Customer not found."));
    }

    public Customer findCustomerBySignInData(String email, String password) throws Exception {
        Customer customer = customerRepository
                .findByEmail(email)
                .orElseThrow(() -> new Exception("O email que você inseriu não pertence a uma conta. " +
                        "Por favor, verifique-o e tente novamente."));
        if (customer.getPassword().equals(password)) {
            CustomerRepository.customerSignedIn = customer;
            return customer;
        }
        throw new Exception("A senha que você inseriu estava incorreta. Por favor, verifique-a e tente novamente.");
    }

    public void addBookToCustomerWishList(Book book) {
        CustomerRepository
                .customerSignedIn
                .getWishList()
                .add(book);
    }

    public void createCustomer(Customer customer) {
        customerRepository.create(customer);
    }

    public void updateCustomer(Customer customer) {
        customerRepository.update(customer);
    }

    public void deleteCustomerById(Long id) throws Exception {
        boolean wasCustomerDeleted = customerRepository.deleteById(id);
        if (!wasCustomerDeleted) {
            throw new Exception("Customer was not deleted successfully. Try again.");
        }
    }

    public List<Customer> findAllCustomers() {
        return customerRepository.findAll();
    }

}
