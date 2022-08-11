package com.tads.mhsf.repository;

import com.tads.mhsf.model.Librarian;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class LibrarianRepository implements Repository<Librarian, Long> {

    public Librarian librarianSignedIn;

    private Long currentId;
    private final List<Librarian> librarians;
    private static volatile LibrarianRepository instance;

    private LibrarianRepository() {
        this.currentId = 1L;
        this.librarians = new ArrayList<>();
    }

    // THREAD-SAFE SINGLETON:
    public static LibrarianRepository getInstance() {
        if (instance == null) {
            synchronized (LibrarianRepository.class) {
                if (instance == null) {
                    instance = new LibrarianRepository();
                }
            }
        }
        return instance;
    }

    @Override
    public void create(Librarian librarian) {
        librarian.setId(currentId++);
        librarians.add(librarian);
    }

    @Override
    public Optional<Librarian> findById(Long id) {
        Librarian librarian = null;
        for (Librarian current : librarians) {
            if (current.getId().equals(id)) {
                librarian = current;
                break;
            }
        }
        return Optional.ofNullable(librarian);
    }

    public Optional<Librarian> findByEmail(String email) {
        Librarian librarian = null;
        for (Librarian current : librarians) {
            if (current.getEmail().equals(email)) {
                librarian = current;
                break;
            }
        }
        return Optional.ofNullable(librarian);
    }

    @Override
    public void update(Librarian librarian) {
        for (Librarian current : librarians) {
            if (current.getId().equals(librarian.getId())) {
                current.setName(librarian.getName());
                current.setEmail(librarian.getEmail());
                current.setPassword(librarian.getPassword());
                break;
            }
        }
    }

    @Override
    public boolean deleteById(Long id) {
        return librarians.removeIf(librarian -> librarian.getId().equals(id));
    }

    @Override
    public List<Librarian> findAll() {
        return librarians;
    }
}
