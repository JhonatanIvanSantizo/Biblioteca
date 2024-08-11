package com.example.libreria.repository;

import com.example.libreria.entity.BookTableEntity;
import com.example.libreria.entity.LoanTableEntity;
import com.example.libreria.entity.ReservationTableEntity;
import com.example.libreria.entity.UserTableEntity;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

@Profile("postgres")
public interface ReservationTableRepository extends JpaRepository<ReservationTableEntity, Long> {
    List<ReservationTableEntity> findByUser(UserTableEntity user);
    List<ReservationTableEntity> findByBook(BookTableEntity book);
}
