package com.example.libreria.service;

import com.example.libreria.dto.BookDto;
import com.example.libreria.dto.ReservationDto;
import com.example.libreria.entity.ReservationDocumentEntity;
import com.example.libreria.exception.ResourceNotFoundException;

import java.util.List;

public interface ReservationDocumentService {
    ReservationDto createReservation(ReservationDto reservationDto);
    List<ReservationDto> getReservationsByUserId(String userId);
    List<ReservationDto> getReservationsByBookId(String bookId);
    ReservationDto getReservationById(String id);
    List<ReservationDto> getAllReservations();
    void deleteReservation(String reservationId);
}
