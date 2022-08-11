package com.tads.mhsf.repository;

import com.tads.mhsf.model.Book;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BookRepository implements Repository<Book, Long> {

    private Long currentId;
    private final List<Book> books;
    private static volatile BookRepository instance;

    private BookRepository() {
        this.currentId = 1L;
        this.books = new ArrayList<>();
    }

    // THREAD-SAFE SINGLETON:
    public static BookRepository getInstance() {
        if (instance == null) {
            synchronized (BookRepository.class) {
                if (instance == null) {
                    instance = new BookRepository();
                }
            }
        }
        return instance;
    }

    @Override
    public void create(Book book) {
        book.setId(currentId++);
        books.add(book);
    }

    @Override
    public Optional<Book> findById(Long id) {
        Book book = null;
        for (Book current : books) {
            if (current.getId().equals(id)) {
                book = current;
                break;
            }
        }
        return Optional.ofNullable(book);
    }

    @Override
    public void update(Book book) {
        for (Book current : books) {
            if (book.getId().equals(current.getId())) {
                current = book;
            }
        }
    }

    @Override
    public boolean deleteById(Long id) {
        return books.removeIf(book -> book.getId().equals(id));
    }

    @Override
    public List<Book> findAll() {
        return books;
    }

}
