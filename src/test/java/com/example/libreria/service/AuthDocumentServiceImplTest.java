package com.example.libreria.service;

import static org.junit.jupiter.api.Assertions.*;

import com.example.libreria.config.JwtService;
import com.example.libreria.dto.auth.AuthDto;
import com.example.libreria.dto.auth.LoginDto;
import com.example.libreria.dto.auth.RegisterDto;
import com.example.libreria.entity.UserDocumentEntity;
import com.example.libreria.repository.UserDocumentRepository;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;


@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class AuthDocumentServiceImplTest {

    @Mock
    private UserDocumentRepository userRepository;

    @Mock
    private JwtService jwtService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private AuthDocumentServiceImpl authService;

    @Test
    public void testLogin() {
        String username = "test@example.com";
        String password = "password";
        LoginDto loginDto = new LoginDto(username, password);

        UserDocumentEntity userEntity = new UserDocumentEntity();
        userEntity.setEmail(username);
        userEntity.setPassword(password);

        when(userRepository.findByEmail(username)).thenReturn(Optional.of(userEntity));

        Authentication authentication = new UsernamePasswordAuthenticationToken(username, password);
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(authentication);

        when(jwtService.getToken(any(UserDocumentEntity.class))).thenReturn("token");

        AuthDto authDto = authService.login(loginDto);

        assertNotNull(authDto);
        assertEquals("token", authDto.getToken());
        verify(userRepository).findByEmail(username);
    }

    @Test
    public void testRegister() {
        String username = "test@example.com";
        String password = "password";
        String encodedPassword = "encodedPassword";
        RegisterDto registerDto = new RegisterDto("Test User", username, password);

        UserDocumentEntity userEntity = new UserDocumentEntity();
        userEntity.setEmail(username);
        userEntity.setPassword(encodedPassword);

        when(passwordEncoder.encode(password)).thenReturn(encodedPassword);

        when(userRepository.save(any(UserDocumentEntity.class))).thenReturn(userEntity);

        when(jwtService.getToken(any(UserDocumentEntity.class))).thenReturn("token");

        AuthDto authDto = authService.register(registerDto);

        assertNotNull(authDto);
        assertEquals("token", authDto.getToken());
        verify(passwordEncoder).encode(password);
    }
}