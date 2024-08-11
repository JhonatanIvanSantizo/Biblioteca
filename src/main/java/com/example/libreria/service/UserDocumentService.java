package com.example.libreria.service;

import com.example.libreria.dto.UserDto;

import java.util.List;

public interface UserDocumentService {
    UserDto getUserById(String id);
    List<UserDto> getUsersByName(String name);
    UserDto getUsersByEmail(String email);
    List<UserDto> getAllUsers();
    void deleteUser(String id);
}
