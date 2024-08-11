package com.example.libreria.service;

import com.example.libreria.dto.BookDto;
import com.example.libreria.entity.BookTableEntity;
import com.example.libreria.entity.BookTableEntity;
import com.example.libreria.exception.ResourceNotFoundException;
import com.example.libreria.repository.BookTableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Profile("postgres")
public class BookTableServiceImpl implements BookDocumentService{
    @Autowired
    private BookTableRepository bookTableRepository;

    public List<BookDto> getAllBooks() {
        List<BookDto> books = this.bookTableRepository.findAll().stream()
                .map(this::toDto)
                .toList();

        if (books.isEmpty()) {
            throw new ResourceNotFoundException("No se han encontrado libros");
        }

        return books;
    }

    public BookDto getBookById(String id) {
        Long bookId = Long.parseLong(id);
        return this.bookTableRepository.findById(bookId)
                .map(this::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("Libro no encontrado"));
    }

    @Override
    public List<BookDto> getBooksByTitle(String title) {
        List<BookDto> books = this.bookTableRepository.findByTitle(title).stream()
                .map(this::toDto)
                .collect(Collectors.toList());

        if (books.isEmpty()) {
            throw new ResourceNotFoundException("Titulo de libro no encontrado");
        }

        return books;
    }

    public BookDto createBook(BookDto book) {
        BookTableEntity entity = new BookTableEntity();
        entity.setTitle(book.getTitle());
        entity.setAuthor(book.getAuthor());
        entity.setGender(book.getGender());
        entity.setAmount(book.getAmount());
        entity.setPrice(book.getPrice());
        BookTableEntity savedEntity = this.bookTableRepository.save(entity);
        BookDto saved = this.toDto(savedEntity);
        return saved;
    }

    public BookDto updateBook(BookDto book, String id) {
        Long bookId = Long.parseLong(id);
        BookTableEntity entity = bookTableRepository.findById(bookId)
                .orElseThrow(() -> new ResourceNotFoundException("Libro no encontrado"));
        if(entity == null){
            return null;
        }
        entity.setTitle(book.getTitle());
        entity.setAuthor(book.getAuthor());
        entity.setGender(book.getGender());
        entity.setAmount(book.getAmount());
        entity.setPrice(book.getPrice());
        BookTableEntity savedEntity = this.bookTableRepository.save(entity);
        BookDto saved = this.toDto(savedEntity);
        return saved;
    }

    public void deleteBook(String id) {
        Long bookId = Long.parseLong(id);
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }
        BookTableEntity book = this.bookTableRepository.findById(bookId)
                .orElseThrow(() -> new ResourceNotFoundException("Libro no encontrado"));
        if (book != null) {
            this.bookTableRepository.delete(book);
        }
    }

    private BookDto toDto(BookTableEntity entity) {
        return new BookDto(entity.getId().toString(), entity.getTitle(), entity.getAuthor(), entity.getGender(), entity.getAmount(), entity.getPrice());
    }
}
