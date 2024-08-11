package com.example.libreria.controller;

import com.example.libreria.dto.UserDto;
import com.example.libreria.service.UserDocumentService;
import com.example.libreria.service.UserDocumentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserDocumentController {
    @Autowired
    private UserDocumentService userService;

    @GetMapping("/")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<UserDto> lista = this.userService.getAllUsers();
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserDto> getUserById(@PathVariable("id") String id) {
        UserDto usuario = this.userService.getUserById(id);
        return ResponseEntity.ok(usuario);
    }

    @GetMapping("/name/{name}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserDto>> getUsersByName(@PathVariable("name") String name) {
        List<UserDto> usuarios = this.userService.getUsersByName(name);
        return ResponseEntity.ok(usuarios);
    }

    @GetMapping("/email/{email}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserDto> getUsersByEmail(@PathVariable("email") String email) {
        UserDto usuario = this.userService.getUsersByEmail(email);
        return ResponseEntity.ok(usuario);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteUser(@PathVariable("id") String id) {
        this.userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
