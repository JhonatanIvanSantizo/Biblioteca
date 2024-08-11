package com.example.libreria.repository;

import com.example.libreria.entity.UserTableEntity;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

@Profile("postgres")
public interface UserTableRepository extends JpaRepository <UserTableEntity, Long> {
    Optional<UserTableEntity> findByEmail(String email);
    List<UserTableEntity> findByName(String name);
}
