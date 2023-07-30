package com.bank.account.controller;

import com.bank.account.dto.AuditDto;
import com.bank.account.service.AccountAuditService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityNotFoundException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Тесты {@link AccountAuditController}
 */
@ExtendWith(MockitoExtension.class)
public class AccountAuditControllerTest {

    @Mock
    private AccountAuditService service;

    @InjectMocks
    private AccountAuditController controller;

    @BeforeEach
    public void setup() {
        controller = new AccountAuditController(service);
    }

    @Test
    @DisplayName("Чтение по id, позитивный сценарий")
    public void readByIdPositiveTest() {

        Long auditId = 1L;
        AuditDto auditDto = new AuditDto();

        when(service.findById(auditId)).thenReturn(auditDto);

        AuditDto result = controller.read(auditId);

        verify(service).findById(auditId);

        assertThat(result).isEqualTo(auditDto);
    }

    @Test
    @DisplayName("Чтение по несуществующему id, негативный сценарий")
    public void readByNonExistIdNegativeTest() {

        Long auditId = 1L;

        when(service.findById(auditId)).thenThrow(new EntityNotFoundException());

        assertThatExceptionOfType(EntityNotFoundException.class)
                .isThrownBy(() -> controller.read(auditId));
    }
}
