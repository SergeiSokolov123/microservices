package com.bank.account.service;

import com.bank.account.dto.AuditDto;
import com.bank.account.entity.AuditEntity;
import com.bank.account.mapper.AccountAuditMapper;
import com.bank.account.repository.AccountAuditRepository;
import com.bank.account.service.common.ExceptionReturner;
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
 * Тесты {@link AccountAuditServiceImpl}
 */
@ExtendWith(MockitoExtension.class)
public class AccountAuditServiceImplTest {

    @Mock
    AccountAuditRepository repository;

    @Mock
    AccountAuditMapper mapper;

    @Mock
    ExceptionReturner exceptionReturner;

    @InjectMocks
    AccountAuditServiceImpl accountAuditService;

    @BeforeEach
    public void setup() {
        accountAuditService = new AccountAuditServiceImpl(repository, mapper, exceptionReturner);
    }

    @Test
    @DisplayName("Поиск по id, позитивный сценарий")
    public void findByIdPositiveTest() {

        Long auditId = 1L;
        AuditEntity auditEntity = new AuditEntity();
        AuditDto auditDto = new AuditDto();
        auditEntity.setId(auditId);
        auditDto.setId(auditId);

        when(repository.findById(auditId)).thenReturn(Optional.of(auditEntity));
        when(mapper.toDto(auditEntity)).thenReturn(auditDto);

        AuditDto result = accountAuditService.findById(auditId);

        assertThat(result).isNotNull();
        assertThat(auditDto).isEqualTo(result);
    }

    @Test
    @DisplayName("Поиск по несуществующему id, негативный сценарий")
    public void findByNonExistIdNegativeTest() {

        Long auditId = 1L;

        when(repository.findById(auditId)).thenReturn(Optional.empty());
        when(exceptionReturner.getEntityNotFoundException("Не существующий id = " + auditId))
                .thenReturn(new EntityNotFoundException());

        assertThatExceptionOfType(EntityNotFoundException.class)
                .isThrownBy(() -> accountAuditService.findById(auditId));
    }

}
