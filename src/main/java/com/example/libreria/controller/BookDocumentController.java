package com.example.libreria.controller;

import com.example.libreria.dto.BookDto;
import com.example.libreria.service.BookDocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookDocumentController {
    @Autowired
    private BookDocumentService bookDocumentService;

    @GetMapping("/")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<List<BookDto>> getAllBooks() {
        List<BookDto> books = bookDocumentService.getAllBooks();
        return ResponseEntity.ok(books);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<BookDto> getBookById(@PathVariable("id") String id) {
        BookDto bookDto = bookDocumentService.getBookById(id);
        return ResponseEntity.ok(bookDto);
    }

    @GetMapping("/title/{title}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<List<BookDto>> getBooksByTitle(@PathVariable("title") String title) {
        List<BookDto> books = bookDocumentService.getBooksByTitle(title);
        return ResponseEntity.ok(books);
    }

    @PostMapping("/")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BookDto> createBook(@RequestBody BookDto bookDto) {
        BookDto saved = bookDocumentService.createBook(bookDto);
        return ResponseEntity.ok(saved);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BookDto> updateBook(@PathVariable("id") String id, @RequestBody BookDto bookDto) {
        BookDto updated = bookDocumentService.updateBook(bookDto, id);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteBook(@PathVariable("id") String id) {
        bookDocumentService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }
}
