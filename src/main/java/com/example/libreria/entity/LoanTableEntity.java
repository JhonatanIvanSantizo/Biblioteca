package com.example.libreria.entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name="loans")
public class LoanTableEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserTableEntity user;

    @ManyToOne
    @JoinColumn(name = "book_id", referencedColumnName = "id")
    private BookTableEntity book;

    private LocalDate loanDate;
    private LocalDate returnDate;

    public LoanTableEntity(Long id, UserTableEntity user, BookTableEntity book, LocalDate loanDate, LocalDate returnDate) {
        this.id = id;
        this.user = user;
        this.book = book;
        this.loanDate = loanDate;
        this.returnDate = returnDate;
    }
    public LoanTableEntity() {

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

    public LocalDate getLoanDate() {
        return loanDate;
    }

    public void setLoanDate(LocalDate loanDate) {
        this.loanDate = loanDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }
}
