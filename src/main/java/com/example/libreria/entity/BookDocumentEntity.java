package com.example.libreria.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.userdetails.UserDetails;

@Document(collection = "books")
public class BookDocumentEntity{
    @Id
    private String id;
    private String title;
    private String author;
    private String gender;
    private int amount;
    private Double price;

    // Constructor, getters y setters
    public BookDocumentEntity() {
    }

    public BookDocumentEntity(String id, String title, String author, String gender, int amount, Double price) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.gender = gender;
        this.amount = amount;
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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
