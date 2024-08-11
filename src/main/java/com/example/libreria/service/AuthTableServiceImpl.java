package com.example.libreria.service;

import com.example.libreria.config.JwtService;
import com.example.libreria.dto.auth.AuthDto;
import com.example.libreria.dto.auth.LoginDto;
import com.example.libreria.dto.auth.RegisterDto;
import com.example.libreria.entity.UserDocumentEntity;
import com.example.libreria.entity.UserTableEntity;
import com.example.libreria.exception.ResourceNotFoundException;
import com.example.libreria.repository.UserDocumentRepository;
import com.example.libreria.repository.UserTableRepository;
import com.example.libreria.util.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Profile("postgres")
public class AuthTableServiceImpl implements AuthService{
    @Autowired
    private UserTableRepository userRepository;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    public AuthDto login(final LoginDto request){
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        UserDetails user = userRepository.findByEmail(request.getUsername()).orElseThrow();
        String token = jwtService.getToken(user);
        return new AuthDto(token);
    }

    public AuthDto register (final RegisterDto request) {
        // Verificar si el email ya existe
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new ResourceNotFoundException("El email ya está en uso");
        }
        UserTableEntity user = new UserTableEntity();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.USER);

        userRepository.save(user);
        return new AuthDto(this.jwtService.getToken(user));
    }
}
