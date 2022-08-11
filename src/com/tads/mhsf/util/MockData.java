package com.tads.mhsf.util;

import com.tads.mhsf.model.*;
import com.tads.mhsf.repository.BookRepository;
import com.tads.mhsf.repository.CustomerRepository;
import com.tads.mhsf.repository.LibrarianRepository;
import com.tads.mhsf.repository.LoanRepository;

import java.time.LocalDate;


public class MockData {

    public static void mock() {

        BookRepository bookRepository = BookRepository.getInstance();
        LoanRepository loanRepository = LoanRepository.getInstance();
        CustomerRepository customerRepository = CustomerRepository.getInstance();
        LibrarianRepository librarianRepository = LibrarianRepository.getInstance();

        // Customer mock:
        Customer customer1 = new Customer("João Victor", "joao@gmail.com", "123");
        customerRepository.create(customer1);

        Customer customer2 = new Customer("Ana Beatriz", "ana@gmail.com", "123");
        customerRepository.create(customer2);

        Customer customer3 = new Customer("Luana Souza", "luana@hotmail.com", "123");
        customerRepository.create(customer3);

        // Librarian mock:
        Librarian librarian1 = new Librarian("Anderson Moreira", "anderson@gmail.com", "123");
        librarianRepository.create(librarian1);

        Librarian librarian2 = new Librarian("Paulo Gonçalves", "paulo@gmail.com", "123");
        librarianRepository.create(librarian2);

        Librarian librarian3 = new Librarian("Eduardo Vasconcelos", "eduardo@gmail.com", "123");
        librarianRepository.create(librarian3);

        // Book mock:
        Book book1 = new Book("O Hobbit", "Fantasia", "J. R. R. Tolkien", 300);
        bookRepository.create(book1);

        Book book2 = new Book("O Senhor dos Anéis: A Sociedade do Anel", "Fantasia", "J. R. R. Tolkien", 400);
        bookRepository.create(book2);

        Book book3 = new Book("O Senhor dos Anéis: As Duas Torres", "Fantasia", "J. R. R. Tolkien", 450);
        bookRepository.create(book3);

        Book book4 = new Book("O Senhor dos Anéis: O Retorno do Rei", "Fantasia", "J. R. R. Tolkien", 420);
        bookRepository.create(book4);

        Book book5 = new Book("Percy Jackson: O Ladrão de Raios", "Aventura", "Rick Riordan", 350);
        bookRepository.create(book5);

        Book book6 = new Book("Percy Jackson: O Mar de Monstros", "Aventura", "Rick Riordan", 380);
        bookRepository.create(book6);

        Book book7 = new Book("Percy Jackson: A Maldição do Titã", "Aventura", "Rick Riordan", 400);
        bookRepository.create(book7);

        Book book8 = new Book("Arsène Lupin: O Ladrão de Casaca", "Romance", "Maurice Leblanc", 325);
        bookRepository.create(book8);

        // Loan mock:
        Loan loan1 = new Loan(book3, customer2, LocalDate.now(), LocalDate.now().plusDays(7));
        book3.setBookStatus(BookStatus.UNAVAILABLE);
        loanRepository.create(loan1);

        Loan loan2 = new Loan(book6, customer1, LocalDate.now(), LocalDate.now().plusDays(4));
        book6.setBookStatus(BookStatus.UNAVAILABLE);
        loanRepository.create(loan2);

        Loan loan3 = new Loan(book2, customer3, LocalDate.now(), LocalDate.now().plusDays(5));
        book2.setBookStatus(BookStatus.UNAVAILABLE);
        loanRepository.create(loan3);

        Loan loan4 = new Loan(book1, customer2, LocalDate.now(), LocalDate.now().plusDays(7));
        book1.setBookStatus(BookStatus.UNAVAILABLE);
        loanRepository.create(loan4);

    }

}
