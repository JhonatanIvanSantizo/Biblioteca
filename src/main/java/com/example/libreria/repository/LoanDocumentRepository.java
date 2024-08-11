package com.example.libreria.repository;

import com.example.libreria.entity.LoanDocumentEntity;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

@Profile("mongodb")
public interface LoanDocumentRepository extends MongoRepository<LoanDocumentEntity, String> {
    List<LoanDocumentEntity> findByUserId(String userId);
    List<LoanDocumentEntity> findByBookId(String bookId);
}
