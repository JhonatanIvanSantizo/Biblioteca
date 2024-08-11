package com.example.libreria.controller;

import com.example.libreria.config.JwtService;
import com.example.libreria.dto.LoanDto;
import com.example.libreria.service.LoanDocumentService;
import com.example.libreria.service.LoanDocumentServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/loans")
public class LoanDocumentController {
    @Autowired
    private LoanDocumentService loanDocumentService;

    @Autowired
    private JwtService jwtService;

    @PostMapping("/")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<LoanDto> createLoan(@RequestBody LoanDto loanDto){
        LoanDto savedLoan = loanDocumentService.createLoan(loanDto);
        return ResponseEntity.ok(savedLoan);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteLoan(@PathVariable("id") String id){
        loanDocumentService.deleteLoan(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/user/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<LoanDto>> getLoansByUserid(@PathVariable("userId") String userId){
        List<LoanDto> loans = loanDocumentService.getLoansByUserId(userId);
        return ResponseEntity.ok(loans);
    }

    @GetMapping("/book/{bookId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<LoanDto>> getLoansByBookId(@PathVariable("bookId") String bookId){
        List<LoanDto> loans = loanDocumentService.getLoansByBookId(bookId);
        return ResponseEntity.ok(loans);
    }

    @GetMapping("/")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<LoanDto>> getAllLoans(){
        List<LoanDto> loans = loanDocumentService.getAllLoans();
        return ResponseEntity.ok(loans);
    }

    @GetMapping("/my-loans")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<List<LoanDto>> getMyLoans(HttpServletRequest request){
        String tokenHeader = request.getHeader("Authorization");
        if (tokenHeader == null || !tokenHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        String token = tokenHeader.substring(7);
        String userId = jwtService.getUserIdFromToken(token);

        List<LoanDto> loans = loanDocumentService.getLoansByUserId(userId);
        return ResponseEntity.ok(loans);
    }

}
