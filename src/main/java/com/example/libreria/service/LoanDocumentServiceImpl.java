package com.example.libreria.service;

import com.example.libreria.dto.LoanDto;
import com.example.libreria.dto.UserDto;
import com.example.libreria.entity.BookDocumentEntity;
import com.example.libreria.entity.LoanDocumentEntity;
import com.example.libreria.entity.UserDocumentEntity;
import com.example.libreria.exception.ResourceNotFoundException;
import com.example.libreria.repository.BookDocumentRepository;
import com.example.libreria.repository.LoanDocumentRepository;
import com.example.libreria.repository.UserDocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Profile("mongodb")
public class LoanDocumentServiceImpl implements LoanDocumentService{
    @Autowired
    private LoanDocumentRepository loanDocumentRepository;

    @Autowired
    private BookDocumentRepository bookDocumentRepository;

    @Autowired
    private UserDocumentRepository userDocumentRepository;

    public LoanDto createLoan(LoanDto loanDto){
        UserDocumentEntity user = userDocumentRepository.findById(loanDto.getUserId()).orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));
        BookDocumentEntity book = bookDocumentRepository.findById(loanDto.getBookId()).orElseThrow(() -> new ResourceNotFoundException("Libro no encontrado"));

        if(book.getAmount()<= 0){
            throw new ResourceNotFoundException("No hay suficientes copias del libro disponibles");
        }

        book.setAmount(book.getAmount()-1);
        bookDocumentRepository.save(book);

        LoanDocumentEntity entity = new LoanDocumentEntity();
        entity.setUserId(loanDto.getUserId());
        entity.setBookId(loanDto.getBookId());
        entity.setLoanDate(LocalDate.now());
        entity.setReturnDate(loanDto.getReturnDate());
        LoanDocumentEntity savedLoan = loanDocumentRepository.save(entity);
        LoanDto saved = this.toDto(savedLoan);
        return saved;
    }

    public void deleteLoan(String loanId){
        LoanDocumentEntity loan = loanDocumentRepository.findById(loanId).orElseThrow(() -> new ResourceNotFoundException("Préstamo no encontrado"));
        BookDocumentEntity book = bookDocumentRepository.findById(loan.getBookId()).orElseThrow(() -> new ResourceNotFoundException("Libro no encontrado"));

        book.setAmount(book.getAmount()+1);
        bookDocumentRepository.save(book);

        loanDocumentRepository.delete(loan);
    }

    public List<LoanDto> getLoansByUserId(String userId){
        List<LoanDto> loans = loanDocumentRepository.findByUserId(userId).stream()
                .map(this::toDto)
                .collect(Collectors.toList());

        if (loans.isEmpty()) {
            throw new ResourceNotFoundException("Prestamos no encontrados");
        }

        return loans;
    }

    public List<LoanDto> getLoansByBookId(String bookId){
        List<LoanDto> loans = loanDocumentRepository.findByBookId(bookId).stream()
                .map(this::toDto)
                .collect(Collectors.toList());

        if (loans.isEmpty()) {
            throw new ResourceNotFoundException("Prestamos no encontrados");
        }

        return loans;

    }

    public List<LoanDto> getAllLoans(){
        List<LoanDto> loans = loanDocumentRepository.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());

        if (loans.isEmpty()) {
            throw new ResourceNotFoundException("No se han encontrado prestamos");
        }

        return loans;
    }

    private LoanDto toDto(LoanDocumentEntity entity){
        return new LoanDto(entity.getId(), entity.getUserId(), entity.getBookId(), entity.getLoanDate(), entity.getReturnDate());
    }
}
