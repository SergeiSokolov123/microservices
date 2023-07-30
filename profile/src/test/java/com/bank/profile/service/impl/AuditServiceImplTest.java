package com.bank.profile.service.impl;

import com.bank.profile.dto.AuditDto;
import com.bank.profile.entity.AuditEntity;
import com.bank.profile.mapper.AuditMapper;
import com.bank.profile.repository.AuditRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import utils.DtoTestData;
import utils.EntityTestData;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verifyNoInteractions;

@ExtendWith(MockitoExtension.class)
class AuditServiceImplTest {
    @Mock
    private AuditRepository repository;
    @Mock
    private AuditMapper mapper;
    @InjectMocks
    AuditServiceImpl auditService;

    @Test
    @DisplayName("Позитивный сценарий findById()")
    void successFindById() {
        AuditEntity auditEntity = EntityTestData.getAuditEntity();
        AuditDto auditDto = DtoTestData.getAuditDto();
        doReturn(Optional.of(auditEntity)).when(repository).findById(auditEntity.getId());
        doReturn(auditDto).when(mapper).toDto(auditEntity);

        AuditDto actualResult = auditService.findById(auditEntity.getId());

        assertThat(actualResult).isEqualTo(auditDto);
    }

    @Test
    @DisplayName("Негативный сценарий findById() c исключением")
    void shouldThrowExceptionIfEntityNotFound() {
        doReturn(Optional.empty()).when(repository).findById(any());

        assertThrows(EntityNotFoundException.class, () -> auditService.findById(1L));
        verifyNoInteractions(mapper);
    }
}