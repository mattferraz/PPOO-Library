package com.tads.mhsf.repository;

import com.tads.mhsf.model.Loan;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class LoanRepository implements Repository<Loan, Long> {

    private Long currentIndex;
    private final List<Loan> loans;
    private static volatile LoanRepository instance;

    private LoanRepository() {
        this.currentIndex = 1L;
        loans = new ArrayList<>();
    }

    // THREAD-SAFE SINGLETON:
    public static LoanRepository getInstance() {
        if (instance == null) {
            synchronized (LoanRepository.class) {
                if (instance == null) {
                    instance = new LoanRepository();
                }
            }
        }
        return instance;
    }

    @Override
    public void create(Loan loan) {
        loan.setId(currentIndex++);
        loans.add(loan);
    }

    @Override
    public Optional<Loan> findById(Long id) {
        Loan loan = null;
        for (Loan current : loans) {
            if (current.getId().equals(id)) {
                loan = current;
                break;
            }
        }
        return Optional.ofNullable(loan);
    }

    @Override
    public void update(Loan loan) {
        for (Loan current : loans) {
            if (current.getId().equals(loan.getId())) {
                current.setBook(loan.getBook());
                current.setCustomer(loan.getCustomer());
                current.setLoanDate(loan.getLoanDate());
                current.setReturnDate(loan.getReturnDate());
                break;
            }
        }
    }

    @Override
    public boolean deleteById(Long id) {
        return loans.removeIf(loan -> loan.getId().equals(id));
    }

    @Override
    public List<Loan> findAll() {
        return loans;
    }
}
