package com.bank.publicinfo.controller;

import com.bank.publicinfo.dto.AuditDto;
import com.bank.publicinfo.service.AuditService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityNotFoundException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Тесты {@link AuditController}
 */
@ExtendWith(MockitoExtension.class)
class AuditControllerTest {

    @Mock
    private AuditService auditService;

    @InjectMocks
    private AuditController auditController;

    @BeforeEach
    void setUp() {
        auditController = new AuditController(auditService);
    }

    @Test
    @DisplayName("чтение по id, позитивный сценарий")
    void readByIdPositiveTest() {
        Long id = 1L;
        AuditDto auditDto = new AuditDto();

        when(auditService.findById(id)).thenReturn(auditDto);

        AuditDto auditDtoResult = auditController.read(id);

        verify(auditService).findById(id);

        assertEquals(auditDtoResult, auditDto);
    }

    @Test
    @DisplayName("чтение по несуществующему id, негативный сценарий")
    void readByNonExistIdNegativeTest() {
        Long id = 1L;

        when(auditService.findById(id)).thenThrow(new EntityNotFoundException());

        assertThrows(EntityNotFoundException.class, () -> auditController.read(id));
    }
}