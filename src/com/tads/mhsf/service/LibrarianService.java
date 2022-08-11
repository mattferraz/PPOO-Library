package com.tads.mhsf.service;

import com.tads.mhsf.model.Librarian;
import com.tads.mhsf.repository.LibrarianRepository;

import java.util.List;

// FACADE:
public class LibrarianService {

    private final LibrarianRepository librarianRepository;

    public LibrarianService() {
        librarianRepository = LibrarianRepository.getInstance();
    }

    public void createLibrarian(Librarian librarian) {
        librarianRepository.create(librarian);
    }

    public Librarian findLibrarianById(Long id) throws Exception {
        return librarianRepository
                .findById(id)
                .orElseThrow(() -> new Exception("Librarian not found."));
    }

    public Librarian findLibrarianByLoginData(String email, String password) throws Exception {
        Librarian librarian = librarianRepository
                .findByEmail(email)
                .orElseThrow(() -> new Exception("The email you entered doesn't belong to an account. " +
                        "Please check your email and try again."));
        if (librarian.getPassword().equals(password)) {
            librarianRepository.librarianSignedIn = librarian;
            return librarian;
        }
        throw new Exception("The password was incorrect. Please check your password and try again.");
    }

    public void updateLibrarian(Librarian librarian) {
        librarianRepository.update(librarian);
    }

    public void deleteLibrarianById(Long id) throws Exception {
        boolean wasLibrarianDeleted = librarianRepository.deleteById(id);
        if (!wasLibrarianDeleted) {
            throw new Exception("Librarian was not deleted successfully. Try again.");
        }
    }

    public List<Librarian> findAllLibrarians() {
        return librarianRepository.findAll();
    }

}
