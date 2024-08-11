package com.example.libreria.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import com.example.libreria.config.JwtService;
import com.example.libreria.dto.LoanDto;
import com.example.libreria.service.LoanDocumentService;
import com.example.libreria.service.LoanDocumentServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@WebMvcTest(LoanDocumentController.class)
public class LoanDocumentControllerTest {

    private MockMvc mockMvc;

    @MockBean
    private LoanDocumentServiceImpl loanDocumentService;

    @MockBean
    private JwtService jwtService;

    @InjectMocks
    private LoanDocumentController loanDocumentController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(loanDocumentController).build();
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testGetLoansByUserId() throws Exception {
        LoanDto loan1 = new LoanDto("1", "user1", "book1", LocalDate.parse("2023-01-01"), LocalDate.parse("2023-01-10"));
        LoanDto loan2 = new LoanDto("2", "user1", "book2", LocalDate.parse("2023-02-01"), LocalDate.parse("2023-02-10"));
        List<LoanDto> loans = Arrays.asList(loan1, loan2);

        when(loanDocumentService.getLoansByUserId("user1")).thenReturn(loans);

        mockMvc.perform(get("/loans/user/user1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value("1"))
                .andExpect(jsonPath("$[0].userId").value("user1"))
                .andExpect(jsonPath("$[1].id").value("2"))
                .andExpect(jsonPath("$[1].userId").value("user1"));
    }
}