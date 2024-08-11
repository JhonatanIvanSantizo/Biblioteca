package com.example.libreria.repository;

import com.example.libreria.entity.LoanDocumentEntity;
import com.example.libreria.entity.ReservationDocumentEntity;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

@Profile("mongodb")
public interface ReservationDocumentRepository extends MongoRepository<ReservationDocumentEntity, String> {
    List<ReservationDocumentEntity> findByUserId(String userId);
    List<ReservationDocumentEntity> findByBookId(String bookId);
}
