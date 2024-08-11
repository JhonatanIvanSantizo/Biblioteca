package com.example.libreria.service;

import com.example.libreria.dto.UserDto;
import com.example.libreria.entity.UserTableEntity;
import com.example.libreria.exception.ResourceNotFoundException;
import com.example.libreria.repository.UserTableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Profile("postgres")
public class UserTableServiceImpl implements UserDocumentService{
    @Autowired
    private UserTableRepository userTableRepository;

    @Override
    public List<UserDto> getAllUsers(){
        List<UserDto> users = this.userTableRepository.findAll().stream()
                .map(this::toDto)
                .toList();

        if(users.isEmpty()){
            throw new ResourceNotFoundException("No se han encontrado usuarios");
        }
        return users;
    }

    @Override
    public UserDto getUserById(String id){
        Long userId = Long.parseLong(id);
        return this.userTableRepository.findById(userId)
                .map(this::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));
    }

    @Override
    public List<UserDto> getUsersByName(String name){
        List<UserDto> users = this.userTableRepository.findByName(name).stream()
                .map(this::toDto)
                .toList();

        if(users.isEmpty()){
            throw new ResourceNotFoundException("No se han encontrado usuarios");
        }
        return users;
    }

    @Override
    public UserDto getUsersByEmail(String email){
        return this.userTableRepository.findByEmail(email)
                .map(this::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("Email no encontrado"));
    }

    @Override
    public void deleteUser(String id){
        Long userId = Long.parseLong(id);
        UserTableEntity user = this.userTableRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));
        this.userTableRepository.delete(user);
    }

    private UserDto toDto(UserTableEntity entity) {
        return new UserDto(entity.getId().toString(), entity.getName(), entity.getEmail());
    }
}
