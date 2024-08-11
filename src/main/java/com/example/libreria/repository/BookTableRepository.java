package com.example.libreria.repository;

import com.example.libreria.entity.BookTableEntity;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

@Profile("postgres")
public interface BookTableRepository extends JpaRepository<BookTableEntity, Long> {
    List<BookTableEntity> findByTitle(String title);
}
