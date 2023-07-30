package com.bank.antifraud.controller;

import com.bank.antifraud.dto.SuspiciousAccountTransferDto;
import com.bank.antifraud.service.SuspiciousAccountTransferService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Тесты {@link SuspiciousAccountTransferController}
 */
@ExtendWith(MockitoExtension.class)
public class SuspiciousAccountTransferControllerTest {

    @Mock
    private SuspiciousAccountTransferService service;

    @InjectMocks
    private SuspiciousAccountTransferController controller;

    @BeforeEach
    public void setup() {
        controller = new SuspiciousAccountTransferController(service);
    }

    @Test
    @DisplayName("Чтение по id, позитивный сценарий")
    void readByIdPositiveTest() {

        Long id = 1L;
        SuspiciousAccountTransferDto dto = new SuspiciousAccountTransferDto();

        when(service.findById(id)).thenReturn(dto);

        ResponseEntity<SuspiciousAccountTransferDto> response = controller.read(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(dto, response.getBody());
    }

    @Test
    @DisplayName("Чтение по id равному null, негативный сценарий")
    void readByIdNullNegativeTest() {

        Long id = 1L;

        when(service.findById(id)).thenReturn(null);

        ResponseEntity<SuspiciousAccountTransferDto> response = controller.read(id);

        assertNull(response.getBody());
    }

    @Test
    @DisplayName("Чтение по нескольким id, позитивный сценарий")
    void readAllPositiveTest() {

        Long id1 = 1L;
        Long id2 = 2L;
        List<Long> usersId = Arrays.asList(id1, id2);

        List<SuspiciousAccountTransferDto> dto = new ArrayList<>();
        dto.add(new SuspiciousAccountTransferDto());
        dto.add(new SuspiciousAccountTransferDto());

        when(service.findAllById(usersId)).thenReturn(dto);

        ResponseEntity<List<SuspiciousAccountTransferDto>> response = controller.readAll(usersId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(dto, response.getBody());
    }

    @Test
    @DisplayName("Чтение по нескольким id равным null, позитивный сценарий")
    void readAllByIdNullNegativeTest() {

        Long id1 = 1L;
        Long id2 = 2L;
        List<Long> usersId = Arrays.asList(id1, id2);

        when(service.findAllById(usersId)).thenReturn(null);

        ResponseEntity<List<SuspiciousAccountTransferDto>> response = controller.readAll(usersId);
        assertNull(response.getBody());
    }

    @Test
    @DisplayName("Создание, позитивный сценарий")
    public void createPositiveTest() {

        SuspiciousAccountTransferDto dto1 = new SuspiciousAccountTransferDto();
        SuspiciousAccountTransferDto dto2 = new SuspiciousAccountTransferDto();

        when(service.save(dto1)).thenReturn(dto2);

        ResponseEntity<SuspiciousAccountTransferDto> response = controller.create(dto1);

        verify(service).save(dto1);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(dto2);
    }

    @Test
    @DisplayName("Создание, негативный сценарий")
    public void createNegativeTest() {

        SuspiciousAccountTransferDto dto = new SuspiciousAccountTransferDto();

        when(service.save(dto)).thenThrow(RuntimeException.class);

        assertThatExceptionOfType(RuntimeException.class)
                .isThrownBy(() -> controller.create(dto));
    }

    @Test
    @DisplayName("Обновление по id, позитивный сценарий")
    public void updateByIdPositiveTest() {

        Long id = 1L;
        SuspiciousAccountTransferDto dto1 = new SuspiciousAccountTransferDto();
        SuspiciousAccountTransferDto dto2 = new SuspiciousAccountTransferDto();
        dto1.setId(id);
        dto2.setId(id);

        when(service.update(id, dto1)).thenReturn(dto2);

        ResponseEntity<SuspiciousAccountTransferDto> response = controller.update(dto1, id);

        verify(service).update(id, dto1);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(dto2);
    }

    @Test
    @DisplayName("Обновление по несуществующему id, негативный сценарий")
    public void updateByNonExistIdNegativeTest() {

        Long id = 1L;
        SuspiciousAccountTransferDto dto = new SuspiciousAccountTransferDto();

        when(service.update(id, dto)).thenThrow(EntityNotFoundException.class);

        assertThatExceptionOfType(EntityNotFoundException.class)
                .isThrownBy(() -> controller.update(dto, id));
    }
}
