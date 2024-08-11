package com.example.libreria.service;

import com.example.libreria.dto.auth.AuthDto;
import com.example.libreria.dto.auth.LoginDto;
import com.example.libreria.dto.auth.RegisterDto;
import com.example.libreria.config.JwtService;
import com.example.libreria.entity.UserDocumentEntity;
import com.example.libreria.repository.UserDocumentRepository;
import com.example.libreria.util.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

public interface AuthService {
    AuthDto login(final LoginDto request);
    AuthDto register(final RegisterDto request);
}
