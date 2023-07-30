package com.bank.antifraud.controller;

import com.bank.antifraud.dto.AuditDto;
import com.bank.antifraud.service.AuditService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Тесты {@link AuditController}
 */
@ExtendWith(MockitoExtension.class)
public class AuditControllerTest {

    @Mock
    private AuditService auditService;

    @InjectMocks
    private AuditController auditController;

    @BeforeEach
    public void setup() {
        auditController = new AuditController(auditService);
    }

    @Test
    @DisplayName("Чтение по id, позитивный сценарий")
    public void readByIdPositiveTest() {

        Long id = 1L;
        AuditDto auditDto = new AuditDto();

        when(auditService.findById(id)).thenReturn(auditDto);

        AuditDto result = auditController.read(id);

        verify(auditService).findById(id);

        assertThat(result).isEqualTo(auditDto);
    }

    @Test
    @DisplayName("Чтение по id равному null, негативный сценарий")
    public void readByIdNullNegativeTest() {

        Long id = 1L;

        when(auditService.findById(id)).thenReturn(null);

        AuditDto result = auditController.read(id);

        assertNull(result);
    }
}
