package com.example.libreria.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name="books")
public class BookTableEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String author;
    private String gender;
    private int amount;
    private Double price;

    @OneToMany(mappedBy = "book", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<LoanTableEntity> loans;

    @OneToMany(mappedBy = "book", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<ReservationTableEntity> reservations;

    public BookTableEntity(Long id, String title, String author, String gender, int amount, Double price) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.gender = gender;
        this.amount = amount;
        this.price = price;
    }
    public BookTableEntity() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
