package com.example.libreria.entity;

import org.springframework.security.core.userdetails.UserDetails;

public interface UserEntity extends UserDetails {
    String getIdUser();
}
