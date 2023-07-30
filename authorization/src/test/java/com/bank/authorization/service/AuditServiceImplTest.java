package com.bank.authorization.service;

import com.bank.authorization.dto.AuditDto;
import com.bank.authorization.entity.AuditEntity;
import com.bank.authorization.mapper.AuditMapper;
import com.bank.authorization.repository.AuditRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

class AuditServiceImplTest {

    @Mock
    private AuditRepository repository;

    @Mock
    private AuditMapper mapper;

    @InjectMocks
    private AuditServiceImpl service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("поиск по id - позитивный сценарий")
    void findByIdPositiveTest() {
        Long auditId = 1L;
        AuditEntity auditEntity = createMockAuditEntity(auditId);
        AuditDto auditDto = createMockAuditDto(auditId);

        Mockito.when(repository.findById(auditId)).thenReturn(Optional.of(auditEntity));
        Mockito.when(mapper.toDto(auditEntity)).thenReturn(auditDto);

        AuditDto result = service.findById(auditId);

        Assertions.assertEquals(auditDto, result);
        Mockito.verify(repository, Mockito.times(1)).findById(auditId);
        Mockito.verify(mapper, Mockito.times(1)).toDto(auditEntity);
    }

    @Test
    @DisplayName("поиск по несуществующему id - негативный сценарий")
    void findByNonExistIdNegativeTest() {
        Long auditId = 1L;

        Mockito.when(repository.findById(auditId)).thenReturn(Optional.empty());

        Assertions.assertThrows(EntityNotFoundException.class, () -> service.findById(auditId));
        Mockito.verify(repository, Mockito.times(1)).findById(auditId);
    }

    private AuditEntity createMockAuditEntity(Long auditId) {
        AuditEntity auditEntity = new AuditEntity();
        auditEntity.setId(auditId);

        return auditEntity;
    }

    private AuditDto createMockAuditDto(Long auditId) {
        AuditDto auditDto = new AuditDto();
        auditDto.setId(auditId);

        return auditDto;
    }

}
