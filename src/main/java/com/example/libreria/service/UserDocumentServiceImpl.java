package com.example.libreria.service;

import com.example.libreria.dto.UserDto;
import com.example.libreria.entity.UserDocumentEntity;
import com.example.libreria.exception.ResourceNotFoundException;
import com.example.libreria.repository.UserDocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Profile("mongodb")
public class UserDocumentServiceImpl implements UserDocumentService {
    @Autowired
    private UserDocumentRepository userMongoRepository;

    public List<UserDto> getAllUsers() {
        List<UserDto> users = this.userMongoRepository.findAll().stream()
                .map(this::toDto)
                .toList();

        if (users.isEmpty()) {
            throw new ResourceNotFoundException("No se han encontrado usuarios");
        }

        return users;
    }

    public UserDto getUserById(String id) {
        return this.userMongoRepository.findById(id)
                .map(this::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));
    }

    @Override
    public List<UserDto> getUsersByName(String name) {
        List<UserDto> users = this.userMongoRepository.findByName(name).stream()
                .map(this::toDto)
                .toList();

        if (users.isEmpty()) {
            throw new ResourceNotFoundException("No se han encontrado usuarios");
        }

        return users;
    }

    @Override
    public UserDto getUsersByEmail(String email) {
        return this.userMongoRepository.findByEmail(email)
                .map(this::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("Email no encontrado"));
    }

    public void deleteUser(String id) {
        if (id == null) {
            throw new IllegalArgumentException("El id no puede ser nulo");
        }
        UserDocumentEntity entity = this.userMongoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));
        if (entity != null) {
            this.userMongoRepository.delete(entity);
        }
    }

    private UserDto toDto(UserDocumentEntity entity) {
        return new UserDto(entity.getId(), entity.getName(), entity.getEmail());
    }
}
