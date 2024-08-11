package com.example.libreria.service;

import com.example.libreria.dto.BookDto;
import com.example.libreria.dto.UserDto;

import java.util.List;

public interface BookDocumentService {
    BookDto createBook(BookDto bookDto);

    BookDto getBookById(String id);

    List<BookDto> getAllBooks();

    List<BookDto> getBooksByTitle(String title);

    BookDto updateBook(BookDto bookDto, String id);

    void deleteBook(String id);
}
