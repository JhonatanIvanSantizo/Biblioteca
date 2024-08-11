package com.example.libreria.service;

import com.example.libreria.entity.UserDocumentEntity;
import com.example.libreria.repository.UserDocumentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class UserDocumentServiceImplTest {

    @Mock
    private UserDocumentRepository userDocumentRepository;

    @InjectMocks
    private UserDocumentServiceImpl userDocumentService;

    @Test
    public void testDeleteUser() {
        String userId = "user1";
        UserDocumentEntity userEntity = new UserDocumentEntity();
        userEntity.setId(userId);

        when(userDocumentRepository.findById(userId)).thenReturn(Optional.of(userEntity));
        doNothing().when(userDocumentRepository).delete(userEntity);

        userDocumentService.deleteUser(userId);

        verify(userDocumentRepository).findById(userId);
        verify(userDocumentRepository).delete(userEntity);
    }
}