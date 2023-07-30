package profile.controller;

import com.bank.profile.controller.AuditController;
import com.bank.profile.dto.AuditDto;
import com.bank.profile.service.AuditService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import utils.DtoTestData;

import javax.persistence.EntityNotFoundException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;

@ExtendWith(MockitoExtension.class)
class AuditControllerTest {
    @Mock
    private AuditService service;
    @InjectMocks
    AuditController auditController;

    @Test
    @DisplayName("Чтение по id, позитивный сценарий")
    void readByIdPositiveTest() {
        AuditDto auditDto = DtoTestData.getAuditDto();
        doReturn(auditDto).when(service).findById(auditDto.getId());

        AuditDto actualResult = auditController.read(auditDto.getId());

        assertThat(actualResult).isEqualTo(auditDto);
    }

    @Test
    @DisplayName("Чтение по id, негативный сценарий")
    void readByIdNegativeTest() {
        doThrow(EntityNotFoundException.class).when(service).findById(any());

        assertThrows(EntityNotFoundException.class, () -> auditController.read(any()));
    }
}