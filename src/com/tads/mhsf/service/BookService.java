package com.tads.mhsf.service;

import com.tads.mhsf.model.Book;
import com.tads.mhsf.repository.BookRepository;

import java.util.List;

// FACADE:
public class BookService {

    private final BookRepository bookRepository;

    public BookService() {
        this.bookRepository = BookRepository.getInstance();
    }

    public void createBook(Book book) {
        bookRepository.create(book);
    }

    public Book findBookById(Long id) throws Exception {
        return bookRepository
                .findById(id)
                .orElseThrow(() -> new Exception("Book not found."));
    }

    public void updateBook(Book book) {
        bookRepository.update(book);
    }

    public void deleteBookById(Long id) throws Exception {
        boolean wasBookDeleted = bookRepository.deleteById(id);
        if (!wasBookDeleted) {
            throw new Exception("Book was not deleted successfully. Try again.");
        }
    }

    public List<Book> findAllBooks() {
        return bookRepository.findAll();
    }

}
