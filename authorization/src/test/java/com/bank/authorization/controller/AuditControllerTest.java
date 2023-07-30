package com.bank.authorization.controller;

import com.bank.authorization.dto.AuditDto;
import com.bank.authorization.service.AuditService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

class AuditControllerTest {

    @Mock
    private AuditService auditService;

    @InjectMocks
    private AuditController auditController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Чтение по id - позитивный сценарий")
    void readByIdPositiveTest() {
        Long auditId = 1L;

        AuditDto auditDto = new AuditDto();
        auditDto.setId(auditId);
        auditDto.setEntityType("User");
        auditDto.setOperationType("CREATE");

        when(auditService.findById(auditId)).thenReturn(auditDto);

        AuditDto result = auditController.read(auditId);

        assertEquals(auditDto, result);
    }

    @Test
    @DisplayName("Чтение по несуществующему id - негативный сценарий")
    void readByIdNegativeTest() {
        Long id = 13567L;

        when(auditService.findById(id)).thenReturn(null);

        AuditDto result = auditController.read(id);

        assertNull(result);
    }

}
