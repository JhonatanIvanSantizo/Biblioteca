package com.example.libreria.service;

import com.example.libreria.dto.BookDto;
import com.example.libreria.entity.BookDocumentEntity;
import com.example.libreria.repository.BookDocumentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.hamcrest.Matchers.any;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class BookDocumentServiceImplTest {

    @Mock
    private BookDocumentRepository bookDocumentRepository;

    @InjectMocks
    private BookDocumentServiceImpl bookDocumentService;

    @Test
    public void testUpdateBook() {
        String bookId = "1";
        BookDto bookDto = new BookDto(bookId, "New Title", "New Author", "New Gender", 10, 20.0);
        BookDocumentEntity bookEntity = new BookDocumentEntity();
        bookEntity.setId(bookId);
        bookEntity.setTitle("Old Title");
        bookEntity.setAuthor("Old Author");
        bookEntity.setGender("Old Gender");
        bookEntity.setAmount(5);
        bookEntity.setPrice(15.0);

        when(bookDocumentRepository.findById(bookId)).thenReturn(Optional.of(bookEntity));
        when(bookDocumentRepository.save(Mockito.any(BookDocumentEntity.class))).thenReturn(bookEntity);

        BookDto updatedBook = bookDocumentService.updateBook(bookDto, bookId);

        assertNotNull(updatedBook);
        assertEquals(bookDto.getTitle(), updatedBook.getTitle());
        assertEquals(bookDto.getAuthor(), updatedBook.getAuthor());
    }
}