package com.example.libreria.repository;

import com.example.libreria.entity.BookDocumentEntity;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

@Profile("mongodb")
public interface BookDocumentRepository extends MongoRepository<BookDocumentEntity, String> {
    List<BookDocumentEntity> findByTitle(String title);
}
