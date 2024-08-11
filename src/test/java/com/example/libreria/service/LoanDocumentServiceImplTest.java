package com.example.libreria.service;

import static org.junit.jupiter.api.Assertions.*;

import com.example.libreria.dto.LoanDto;
import com.example.libreria.entity.BookDocumentEntity;
import com.example.libreria.entity.LoanDocumentEntity;
import com.example.libreria.entity.UserDocumentEntity;
import com.example.libreria.repository.BookDocumentRepository;
import com.example.libreria.repository.LoanDocumentRepository;
import com.example.libreria.repository.UserDocumentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.Optional;

import static org.hamcrest.Matchers.any;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class LoanDocumentServiceImplTest {

    @Mock
    private LoanDocumentRepository loanDocumentRepository;

    @Mock
    private BookDocumentRepository bookDocumentRepository;

    @Mock
    private UserDocumentRepository userDocumentRepository;

    @InjectMocks
    private LoanDocumentServiceImpl loanDocumentService;

    @Test
    public void testCreateLoan() {
        String userId = "user1";
        String bookId = "book1";
        LoanDto loanDto = new LoanDto(null, userId, bookId, LocalDate.now(), LocalDate.now().plusDays(7));

        UserDocumentEntity user = new UserDocumentEntity();
        user.setId(userId);

        BookDocumentEntity book = new BookDocumentEntity();
        book.setId(bookId);
        book.setAmount(5);

        LoanDocumentEntity savedLoanEntity = new LoanDocumentEntity();
        savedLoanEntity.setId("loan1");
        savedLoanEntity.setUserId(userId);
        savedLoanEntity.setBookId(bookId);
        savedLoanEntity.setLoanDate(LocalDate.now());
        savedLoanEntity.setReturnDate(LocalDate.now().plusDays(7));

        when(userDocumentRepository.findById(userId)).thenReturn(Optional.of(user));
        when(bookDocumentRepository.findById(bookId)).thenReturn(Optional.of(book));
        when(loanDocumentRepository.save(Mockito.any(LoanDocumentEntity.class))).thenReturn(savedLoanEntity);

        LoanDto createdLoan = loanDocumentService.createLoan(loanDto);

        assertNotNull(createdLoan);
        assertEquals(userId, createdLoan.getUserId());
        assertEquals(bookId, createdLoan.getBookId());

        ArgumentCaptor<LoanDocumentEntity> loanEntityCaptor = ArgumentCaptor.forClass(LoanDocumentEntity.class);
        verify(loanDocumentRepository).save(loanEntityCaptor.capture());

        LoanDocumentEntity capturedLoanEntity = loanEntityCaptor.getValue();
        assertEquals(userId, capturedLoanEntity.getUserId());
        assertEquals(bookId, capturedLoanEntity.getBookId());
        assertEquals(loanDto.getLoanDate(), capturedLoanEntity.getLoanDate());
        assertEquals(loanDto.getReturnDate(), capturedLoanEntity.getReturnDate());
    }
}