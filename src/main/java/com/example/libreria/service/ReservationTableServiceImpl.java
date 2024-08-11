package com.example.libreria.service;

import com.example.libreria.dto.ReservationDto;
import com.example.libreria.entity.BookTableEntity;
import com.example.libreria.entity.ReservationTableEntity;
import com.example.libreria.entity.UserTableEntity;
import com.example.libreria.exception.ResourceNotFoundException;
import com.example.libreria.repository.BookTableRepository;
import com.example.libreria.repository.ReservationTableRepository;
import com.example.libreria.repository.UserTableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Profile("postgres")
public class ReservationTableServiceImpl implements ReservationDocumentService {
    @Autowired
    private ReservationTableRepository reservationTableRepository;

    @Autowired
    private BookTableRepository bookTableRepository;

    @Autowired
    private UserTableRepository userTableRepository;

    public ReservationDto createReservation(ReservationDto reservationDto) {
        UserTableEntity user = userTableRepository.findById(Long.valueOf(reservationDto.getUserId()))
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));
        BookTableEntity book = bookTableRepository.findById(Long.valueOf(reservationDto.getBookId()))
                .orElseThrow(() -> new ResourceNotFoundException("Libro no encontrado"));

        ReservationTableEntity entity = new ReservationTableEntity();
        entity.setUser(user);
        entity.setBook(book);
        entity.setReservationDate(LocalDate.now());

        ReservationTableEntity savedReservation = reservationTableRepository.save(entity);
        return toDto(savedReservation);
    }

    public void deleteReservation(String reservationId) {
        ReservationTableEntity reservation = reservationTableRepository.findById(Long.valueOf(reservationId))
                .orElseThrow(() -> new ResourceNotFoundException("Reserva no encontrada"));
        reservationTableRepository.delete(reservation);
    }

    public List<ReservationDto> getReservationsByUserId(String userId) {
        UserTableEntity user = userTableRepository.findById(Long.valueOf(userId))
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));

        List<ReservationDto> reservations = reservationTableRepository.findByUser(user).stream()
                .map(this::toDto)
                .collect(Collectors.toList());

        if (reservations.isEmpty()) {
            throw new ResourceNotFoundException("Reservas no encontradas");
        }

        return reservations;
    }

    public List<ReservationDto> getReservationsByBookId(String bookId) {
        BookTableEntity book = bookTableRepository.findById(Long.valueOf(bookId))
                .orElseThrow(() -> new ResourceNotFoundException("Libro no encontrado"));

        List<ReservationDto> reservations = reservationTableRepository.findByBook(book).stream()
                .map(this::toDto)
                .collect(Collectors.toList());

        if (reservations.isEmpty()) {
            throw new ResourceNotFoundException("Reservas no encontradas");
        }

        return reservations;
    }

    public List<ReservationDto> getAllReservations() {
        List<ReservationDto> reservations = reservationTableRepository.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());

        if (reservations.isEmpty()) {
            throw new ResourceNotFoundException("No se han encontrado reservas");
        }

        return reservations;
    }

    public ReservationDto getReservationById(String id) {
        ReservationTableEntity reservation = reservationTableRepository.findById(Long.valueOf(id))
                .orElseThrow(() -> new ResourceNotFoundException("Reserva no encontrada"));
        return toDto(reservation);
    }

    private ReservationDto toDto(ReservationTableEntity entity) {
        return new ReservationDto(
                entity.getId().toString(),
                entity.getUser().getId().toString(),
                entity.getBook().getId().toString(),
                entity.getReservationDate()
        );
    }
}