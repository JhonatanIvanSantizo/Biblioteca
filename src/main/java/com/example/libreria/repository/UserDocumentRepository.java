package com.example.libreria.repository;

import com.example.libreria.entity.UserDocumentEntity;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Profile("mongodb")
public interface UserDocumentRepository extends MongoRepository<UserDocumentEntity, String> {
    Optional<UserDocumentEntity> findByEmail(String email);
    List<UserDocumentEntity> findByName(String name);
}
