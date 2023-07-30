package com.bank.publicinfo.service.impl;

import com.bank.publicinfo.dto.AuditDto;
import com.bank.publicinfo.entity.AuditEntity;
import com.bank.publicinfo.mapper.AuditMapper;
import com.bank.publicinfo.repository.AuditRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Тесты {@link AuditServiceImpl}
 */
@ExtendWith(MockitoExtension.class)
class AuditServiceImplTest {

    @Mock
    AuditRepository auditRepository;

    @Mock
    AuditMapper auditMapper;

    @InjectMocks
    AuditServiceImpl auditService;

    @BeforeEach
    void setUp() {
        auditService = new AuditServiceImpl(auditRepository, auditMapper);
    }

    @Test
    @DisplayName("поиск по id, позитивный сценарий")
    void findByIdPositiveTest() {
        Long id = 1L;
        AuditEntity auditEntity = new AuditEntity();
        AuditDto auditDto = new AuditDto();
        auditEntity.setId(id);
        auditDto.setId(id);

        when(auditRepository.findById(id)).thenReturn(Optional.of(auditEntity));
        when(auditMapper.toDto(auditEntity)).thenReturn(auditDto);

        AuditDto result = auditService.findById(id);

        assertNotNull(result);
        assertEquals(result, auditDto);
    }

    @Test
    @DisplayName("поиск по несуществующему id, негативный сценарий")
    void findByNonExistIdNegativeTest() {
        Long id = 1L;

        when(auditRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> auditService.findById(id));
    }
}