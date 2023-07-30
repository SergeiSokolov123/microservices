package com.bank.antifraud.service.impl;

import com.bank.antifraud.dto.AuditDto;
import com.bank.antifraud.entity.AuditEntity;
import com.bank.antifraud.mappers.AuditMapper;
import com.bank.antifraud.repository.AuditRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.when;

/**
 * Тесты {@link AuditServiceImpl}
 */
@ExtendWith(MockitoExtension.class)
public class AuditServiceImplTest {

    @Mock
    AuditRepository repository;

    @Mock
    AuditMapper mapper;

    @InjectMocks
    AuditServiceImpl auditServiceImpl;

    @BeforeEach
    public void setup() {
        auditServiceImpl = new AuditServiceImpl(repository, mapper);
    }

    @Test
    @DisplayName("Поиск по id, позитивный сценарий")
    public void findByIdPositiveTest() {

        Long id = 1L;
        AuditEntity auditEntity = new AuditEntity();
        AuditDto auditDto = new AuditDto();
        auditEntity.setId(id);
        auditDto.setId(id);

        when(repository.findById(id)).thenReturn(Optional.of(auditEntity));
        when(mapper.toDto(auditEntity)).thenReturn(auditDto);

        AuditDto result = auditServiceImpl.findById(id);

        assertThat(result).isNotNull();
        assertThat(auditDto).isEqualTo(result);
    }

    @Test
    @DisplayName("Поиск по несуществующему id, негативный сценарий")
    public void findByNonExistIdNegativeTest() {

        Long id = 1L;

        when(repository.findById(id)).thenReturn(Optional.empty());

        assertThatExceptionOfType(EntityNotFoundException.class)
                .isThrownBy(() -> auditServiceImpl.findById(id));
    }
}
