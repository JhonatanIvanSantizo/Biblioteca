package com.example.libreria.service;

import com.example.libreria.dto.BookDto;
import com.example.libreria.dto.ReservationDto;
import com.example.libreria.entity.BookDocumentEntity;
import com.example.libreria.entity.ReservationDocumentEntity;
import com.example.libreria.entity.UserDocumentEntity;
import com.example.libreria.exception.ResourceNotFoundException;
import com.example.libreria.repository.BookDocumentRepository;
import com.example.libreria.repository.ReservationDocumentRepository;
import com.example.libreria.repository.UserDocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Profile("mongodb")
public class ReservationDocumentServiceImpl implements ReservationDocumentService{
    @Autowired
    private ReservationDocumentRepository reservationDocumentRepository;

    @Autowired
    private BookDocumentRepository bookDocumentRepository;

    @Autowired
    private UserDocumentRepository userDocumentRepository;

    public ReservationDto createReservation(ReservationDto reservationDto){
        UserDocumentEntity user = userDocumentRepository.findById(reservationDto.getUserId()).orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));
        BookDocumentEntity book = bookDocumentRepository.findById(reservationDto.getBookId()).orElseThrow(() -> new ResourceNotFoundException("Libro no encontrado"));

        ReservationDocumentEntity entity = new ReservationDocumentEntity();
        entity.setUserId(reservationDto.getUserId());
        entity.setBookId(reservationDto.getBookId());
        entity.setReservationDate(LocalDate.now());
        ReservationDocumentEntity savedReservation = reservationDocumentRepository.save(entity);
        ReservationDto saved = this.toDto(savedReservation);
        return saved;
    }

    public void deleteReservation(String reservationId){
        ReservationDocumentEntity reservation = reservationDocumentRepository.findById(reservationId).orElseThrow(() -> new ResourceNotFoundException("Reserva no encontrado"));
        reservationDocumentRepository.delete(reservation);
    }

    public List<ReservationDto> getReservationsByUserId(String userId){
        List<ReservationDto> reservations = reservationDocumentRepository.findByUserId(userId).stream()
                .map(this::toDto)
                .collect(Collectors.toList());

        if (reservations.isEmpty()) {
            throw new ResourceNotFoundException("Reservas no encontradas");
        }

        return reservations;
    }

    public List<ReservationDto> getReservationsByBookId(String bookId){
        List<ReservationDto> reservations = reservationDocumentRepository.findByBookId(bookId).stream()
                .map(this::toDto)
                .collect(Collectors.toList());

        if (reservations.isEmpty()) {
            throw new ResourceNotFoundException("Reservas no encontradas");
        }

        return reservations;
    }

    public List<ReservationDto> getAllReservations(){
        List<ReservationDto> reservations = reservationDocumentRepository.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());

        if (reservations.isEmpty()) {
            throw new ResourceNotFoundException("No se han encontrado reservas");
        }

        return reservations;
    }

    public ReservationDto getReservationById(String id) {
        ReservationDocumentEntity reservation = reservationDocumentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reserva no encontrada"));
        return toDto(reservation);
    }

    private ReservationDto toDto(ReservationDocumentEntity entity){
        return new ReservationDto(entity.getId(),entity.getUserId(),entity.getBookId(),entity.getReservationDate());
    }
}
