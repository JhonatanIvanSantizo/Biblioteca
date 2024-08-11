package com.example.libreria.service;

import com.example.libreria.dto.LoanDto;
import com.example.libreria.entity.BookTableEntity;
import com.example.libreria.entity.LoanTableEntity;
import com.example.libreria.entity.UserTableEntity;
import com.example.libreria.exception.ResourceNotFoundException;
import com.example.libreria.repository.BookTableRepository;
import com.example.libreria.repository.LoanTableRepository;
import com.example.libreria.repository.UserTableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Profile("postgres")
public class LoanTableServiceImpl implements LoanDocumentService {
    @Autowired
    private LoanTableRepository loanTableRepository;

    @Autowired
    private BookTableRepository bookTableRepository;

    @Autowired
    private UserTableRepository userTableRepository;

    @Override
    public LoanDto createLoan(LoanDto loanDto) {
        UserTableEntity user = userTableRepository.findById(Long.valueOf(loanDto.getUserId()))
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));
        BookTableEntity book = bookTableRepository.findById(Long.valueOf(loanDto.getBookId()))
                .orElseThrow(() -> new ResourceNotFoundException("Libro no encontrado"));

        if (book.getAmount() <= 0) {
            throw new ResourceNotFoundException("No hay suficientes copias del libro disponibles");
        }

        book.setAmount(book.getAmount() - 1);
        bookTableRepository.save(book);

        LoanTableEntity entity = new LoanTableEntity();
        entity.setUser(user);
        entity.setBook(book);
        entity.setLoanDate(LocalDate.now());
        entity.setReturnDate(loanDto.getReturnDate());

        LoanTableEntity savedLoan = loanTableRepository.save(entity);
        return toDto(savedLoan);
    }

    @Override
    public void deleteLoan(String loanId) {
        LoanTableEntity loan = loanTableRepository.findById(Long.valueOf(loanId))
                .orElseThrow(() -> new ResourceNotFoundException("Préstamo no encontrado"));
        BookTableEntity book = loan.getBook();

        book.setAmount(book.getAmount() + 1);
        bookTableRepository.save(book);

        loanTableRepository.delete(loan);
    }

    @Override
    public List<LoanDto> getLoansByUserId(String userId) {
        UserTableEntity user = userTableRepository.findById(Long.valueOf(userId))
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));
        List<LoanDto> loans = loanTableRepository.findByUser(user).stream()
                .map(this::toDto)
                .collect(Collectors.toList());

        if (loans.isEmpty()) {
            throw new ResourceNotFoundException("Préstamos no encontrados");
        }

        return loans;
    }

    @Override
    public List<LoanDto> getLoansByBookId(String bookId) {
        BookTableEntity book = bookTableRepository.findById(Long.valueOf(bookId))
                .orElseThrow(() -> new ResourceNotFoundException("Libro no encontrado"));
        List<LoanDto> loans = loanTableRepository.findByBook(book).stream()
                .map(this::toDto)
                .collect(Collectors.toList());

        if (loans.isEmpty()) {
            throw new ResourceNotFoundException("Préstamos no encontrados");
        }

        return loans;
    }

    @Override
    public List<LoanDto> getAllLoans() {
        List<LoanDto> loans = loanTableRepository.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());

        if (loans.isEmpty()) {
            throw new ResourceNotFoundException("No se han encontrado préstamos");
        }

        return loans;
    }

    private LoanDto toDto(LoanTableEntity entity) {
        return new LoanDto(entity.getId().toString(), entity.getUser().getId().toString(),
                entity.getBook().getId().toString(), entity.getLoanDate(), entity.getReturnDate());
    }
}
