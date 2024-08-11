package com.example.libreria.service;

import com.example.libreria.dto.BookDto;
import com.example.libreria.dto.LoanDto;

import java.util.List;

public interface LoanDocumentService {
    LoanDto createLoan(LoanDto loanDto);
    List<LoanDto> getLoansByUserId(String userId);
    List<LoanDto> getLoansByBookId(String bookId);
    List<LoanDto> getAllLoans();
    void deleteLoan(String loanId);
}
