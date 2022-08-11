package com.tads.mhsf.service;

import com.tads.mhsf.model.BookStatus;
import com.tads.mhsf.model.Loan;
import com.tads.mhsf.repository.LoanRepository;

import java.util.List;

// FACADE:
public class LoanService {

    private final LoanRepository loanRepository;

    public LoanService() {
        loanRepository = LoanRepository.getInstance();
    }

    public void createLoan(Loan loan) {
        loanRepository.create(loan);
        loan.getBook().setBookStatus(BookStatus.UNAVAILABLE);
    }

    public Loan findLoanById(Long id) throws Exception {
        return loanRepository
                .findById(id)
                .orElseThrow(() -> new Exception("Loan not found."));
    }

    public void updateLoan(Loan loan) {
        loanRepository.update(loan);
    }

    public void deleteLoanById(Long id) throws Exception {
        boolean wasLoanDeleted = loanRepository.deleteById(id);
        if (!wasLoanDeleted) {
            throw new Exception("Loan was not deleted successfully. Try again.");
        }
    }

    public List<Loan> findAllLoans() {
        return loanRepository.findAll();
    }

}
