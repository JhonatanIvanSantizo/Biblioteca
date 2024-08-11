package com.example.libreria.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import com.example.libreria.config.JwtService;
import com.example.libreria.dto.BookDto;
import com.example.libreria.service.BookDocumentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@WebMvcTest(BookDocumentController.class)
public class BookDocumentControllerTest {
    private MockMvc mockMvc;

    @MockBean
    private BookDocumentService bookDocumentService;

    @MockBean
    private JwtService jwtService;

    @InjectMocks
    private BookDocumentController bookDocumentController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(bookDocumentController).build();
    }

    @Test
    @WithMockUser(roles = "USER")
    public void testGetAllBooks() throws Exception {
        BookDto book1 = new BookDto("1", "Title1", "Author1", "Gender1", 1, 10.0);
        BookDto book2 = new BookDto("2", "Title2", "Author2", "Gender2", 2, 20.0);
        List<BookDto> books = Arrays.asList(book1, book2);

        when(bookDocumentService.getAllBooks()).thenReturn(books);

        mockMvc.perform(get("/books/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value("1"))
                .andExpect(jsonPath("$[0].title").value("Title1"))
                .andExpect(jsonPath("$[1].id").value("2"))
                .andExpect(jsonPath("$[1].title").value("Title2"));
    }

    @Test
    @WithMockUser(roles = "USER")
    public void testGetBookById() throws Exception {
        BookDto book = new BookDto("1", "Title1", "Author1", "Gender1", 1, 10.0);

        when(bookDocumentService.getBookById("1")).thenReturn(book);

        mockMvc.perform(get("/books/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.title").value("Title1"));
    }

    @Test
    @WithMockUser(roles = "USER")
    public void testGetBooksByTitle() throws Exception {
        BookDto book1 = new BookDto("1", "Title1", "Author1", "Gender1", 1, 10.0);
        BookDto book2 = new BookDto("2", "Title1", "Author2", "Gender2", 2, 20.0);
        List<BookDto> books = Arrays.asList(book1, book2);

        when(bookDocumentService.getBooksByTitle("Title1")).thenReturn(books);

        mockMvc.perform(get("/books/title/Title1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value("1"))
                .andExpect(jsonPath("$[0].title").value("Title1"))
                .andExpect(jsonPath("$[1].id").value("2"))
                .andExpect(jsonPath("$[1].title").value("Title1"));
    }
}