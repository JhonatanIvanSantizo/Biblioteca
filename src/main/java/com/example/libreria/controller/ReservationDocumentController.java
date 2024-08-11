package com.example.libreria.controller;

import com.example.libreria.config.JwtService;
import com.example.libreria.dto.BookDto;
import com.example.libreria.dto.ReservationDto;
import com.example.libreria.service.ReservationDocumentService;
import com.example.libreria.service.ReservationDocumentServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reservations")
public class ReservationDocumentController {

    @Autowired
    private ReservationDocumentService reservationDocumentService;

    @Autowired
    private JwtService jwtService;

    @PostMapping("/")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<ReservationDto> createReservation(HttpServletRequest request, @RequestBody ReservationDto reservationDto){
        String tokenHeader = request.getHeader("Authorization");
        if (tokenHeader == null || !tokenHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        String token = tokenHeader.substring(7);
        String userId = jwtService.getUserIdFromToken(token);
        reservationDto.setUserId(userId);

        ReservationDto savedReservation = reservationDocumentService.createReservation(reservationDto);
        return ResponseEntity.ok(savedReservation);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<Void> deleteReservation(HttpServletRequest request, @PathVariable("id") String id){
        String tokenHeader = request.getHeader("Authorization");
        if (tokenHeader == null || !tokenHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        String token = tokenHeader.substring(7);
        String userId = jwtService.getUserIdFromToken(token);
        List<String> roles = jwtService.getRolesFromToken(token);

        ReservationDto existingReservation = reservationDocumentService.getReservationById(id);
        if (!existingReservation.getUserId().equals(userId) && !roles.contains("ADMIN")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        reservationDocumentService.deleteReservation(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/user/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<ReservationDto>> getReservationsByUserid(@PathVariable("userId") String userId){
        List<ReservationDto> reservatinos = reservationDocumentService.getReservationsByUserId(userId);
        return ResponseEntity.ok(reservatinos);
    }

    @GetMapping("/book/{bookId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<ReservationDto>> getReservationsByBookId(@PathVariable("bookId") String bookId){
        List<ReservationDto> reservations = reservationDocumentService.getReservationsByBookId(bookId);
        return ResponseEntity.ok(reservations);
    }

    @GetMapping("/")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<ReservationDto>> getAllReservations(){
        List<ReservationDto> reservations = reservationDocumentService.getAllReservations();
        return ResponseEntity.ok(reservations);
    }

    @GetMapping("/my-reservations")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<List<ReservationDto>> getMyReservations(HttpServletRequest request){
        String tokenHeader = request.getHeader("Authorization");
        if (tokenHeader == null || !tokenHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        String token = tokenHeader.substring(7);
        String userId = jwtService.getUserIdFromToken(token);

        List<ReservationDto> reservations = reservationDocumentService.getReservationsByUserId(userId);
        return ResponseEntity.ok(reservations);
    }
}
