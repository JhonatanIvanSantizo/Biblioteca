package com.example.libreria.entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name="reservations")
public class ReservationTableEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserTableEntity user;

    @ManyToOne
    @JoinColumn(name = "book_id", referencedColumnName = "id")
    private BookTableEntity book;

    private LocalDate reservationDate;

    public ReservationTableEntity(Long id, UserTableEntity user, BookTableEntity book, LocalDate reservationDate) {
        this.id = id;
        this.user = user;
        this.book = book;
        this.reservationDate = reservationDate;
    }

    public ReservationTableEntity() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserTableEntity getUser() {
        return user;
    }

    public void setUser(UserTableEntity user) {
        this.user = user;
    }

    public BookTableEntity getBook() {
        return book;
    }

    public void setBook(BookTableEntity book) {
        this.book = book;
    }

    public LocalDate getReservationDate() {
        return reservationDate;
    }

    public void setReservationDate(LocalDate reservationDate) {
        this.reservationDate = reservationDate;
    }
}
