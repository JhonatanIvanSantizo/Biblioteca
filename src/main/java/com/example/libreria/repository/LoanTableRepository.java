package com.example.libreria.repository;

import com.example.libreria.entity.BookTableEntity;
import com.example.libreria.entity.LoanTableEntity;
import com.example.libreria.entity.UserTableEntity;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

@Profile("postgres")
public interface LoanTableRepository extends JpaRepository<LoanTableEntity, Long> {
    List<LoanTableEntity> findByUser(UserTableEntity user);
    List<LoanTableEntity> findByBook(BookTableEntity book);
}
