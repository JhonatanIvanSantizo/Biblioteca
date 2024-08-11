package com.example.libreria.service;

import com.example.libreria.dto.BookDto;
import com.example.libreria.dto.UserDto;
import com.example.libreria.entity.BookDocumentEntity;
import com.example.libreria.entity.UserDocumentEntity;
import com.example.libreria.exception.ResourceNotFoundException;
import com.example.libreria.repository.BookDocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Profile("mongodb")
public class BookDocumentServiceImpl implements BookDocumentService {
    @Autowired
    private BookDocumentRepository bookDocumentRepository;

    public List<BookDto> getAllBooks() {
        List<BookDto> books = this.bookDocumentRepository.findAll().stream()
                .map(this::toDto)
                .toList();

        if (books.isEmpty()) {
            throw new ResourceNotFoundException("No se han encontrado libros");
        }

        return books;
    }

    public BookDto getBookById(String id) {
        return this.bookDocumentRepository.findById(id)
                .map(this::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("Libro no encontrado"));
    }

    @Override
    public List<BookDto> getBooksByTitle(String title) {
        List<BookDto> books = this.bookDocumentRepository.findByTitle(title).stream()
                .map(this::toDto)
                .collect(Collectors.toList());

        if (books.isEmpty()) {
            throw new ResourceNotFoundException("Titulo de libro no encontrado");
        }

        return books;
    }

    public BookDto createBook(BookDto book) {
        BookDocumentEntity entity = new BookDocumentEntity();
        entity.setTitle(book.getTitle());
        entity.setAuthor(book.getAuthor());
        entity.setGender(book.getGender());
        entity.setAmount(book.getAmount());
        entity.setPrice(book.getPrice());
        BookDocumentEntity savedEntity = this.bookDocumentRepository.save(entity);
        BookDto saved = this.toDto(savedEntity);
        return saved;
    }

    public BookDto updateBook(BookDto book, String id) {
        BookDocumentEntity entity = bookDocumentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Libro no encontrado"));
        if(entity == null){
            return null;
        }
        entity.setTitle(book.getTitle());
        entity.setAuthor(book.getAuthor());
        entity.setGender(book.getGender());
        entity.setAmount(book.getAmount());
        entity.setPrice(book.getPrice());
        BookDocumentEntity savedEntity = this.bookDocumentRepository.save(entity);
        BookDto saved = this.toDto(savedEntity);
        return saved;
    }

    public void deleteBook(String id) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }
        BookDocumentEntity entity = this.bookDocumentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Libro no encontrado"));
        if (entity != null) {
            this.bookDocumentRepository.delete(entity);
        }
    }

    private BookDto toDto(BookDocumentEntity entity) {
        return new BookDto(entity.getId(), entity.getTitle(), entity.getAuthor(), entity.getGender(), entity.getAmount(), entity.getPrice());
    }
}
