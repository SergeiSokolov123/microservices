package com.bank.history.controller;

import com.bank.history.dto.HistoryDto;
import com.bank.history.service.HistoryService;
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
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Тесты {@link HistoryController}
 **/
@ExtendWith(MockitoExtension.class)
class HistoryControllerTest {

    @Mock
    private HistoryService service;

    @InjectMocks
    private HistoryController controller;


    @BeforeEach
    void setup() {
        controller = new HistoryController(service);
    }

    @Test
    @DisplayName("Чтение по id, позитивный сценарий")
    void readByIdPositiveTest() {
        Long id = 1L;

        HistoryDto historyDto = new HistoryDto();
        when(service.readById(id)).thenReturn(historyDto);
        HistoryDto result = controller.read(id).getBody();
        verify(service).readById(id);
        assertThat(result).isEqualTo(historyDto);
    }

    @Test
    @DisplayName("Чтение по несуществующему id, негативный сценарий")
    void readByNonExistIdNegativeTest() {
        Long id = 1L;

        when(service.readById(id)).thenThrow(new EntityNotFoundException());
        assertThatExceptionOfType(EntityNotFoundException.class)
                .isThrownBy(() -> controller.read(id));
    }

    @Test
    @DisplayName("Чтение по нескольким id, позитивный сценарий")
    void readAllByIdPositiveTest() {
        Long id1 = 1L;
        Long id2 = 2L;
        List<Long> ids = Arrays.asList(id1, id2);

        HistoryDto historyDto1 = new HistoryDto();
        HistoryDto historyDto2 = new HistoryDto();
        historyDto1.setId(id1);
        historyDto2.setId(id2);
        List<HistoryDto> dto = Arrays.asList(historyDto1, historyDto2);

        when(service.readAllById(ids)).thenReturn(dto);
        ResponseEntity<List<HistoryDto>> response = controller.readAll(ids);
        verify(service).readAllById(ids);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).hasSize(dto.size());
    }

    @Test
    @DisplayName("Чтение по нескольким несуществующим id, негативный сценарий")
    void readAllByNonExistIdNegativeTest() {
        List<Long> ids = Arrays.asList(1L, 2L);

        when(service.readAllById(ids)).thenThrow(new EntityNotFoundException());
        assertThatExceptionOfType(EntityNotFoundException.class)
                .isThrownBy(() -> controller.readAll(ids));
    }

    @Test
    @DisplayName("Создание, позитивный сценарий")
    void createPositiveTest() {
        HistoryDto historyDto = new HistoryDto();
        HistoryDto newHistoryDto = new HistoryDto();

        when(service.create(historyDto)).thenReturn(newHistoryDto);
        ResponseEntity<HistoryDto> response = controller.create(historyDto);
        verify(service).create(historyDto);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(newHistoryDto);
    }

    @Test
    @DisplayName("Создание, негативный сценарий")
    void createNegativeTest() {
        HistoryDto dto = new HistoryDto(1L, 1L, 1L,
                1L, 1L, 1L, 1L);

        when(service.create(dto)).thenThrow(new RuntimeException());
        assertThatExceptionOfType(RuntimeException.class)
                .isThrownBy(() -> controller.create(dto));
    }

    @Test
    @DisplayName("Обновление по id, позитивный сценарий")
    void updateByIdPositiveTest() {
        Long id = 1L;
        HistoryDto historyDto = new HistoryDto();
        HistoryDto updateHistoryDto = new HistoryDto();
        historyDto.setId(id);
        updateHistoryDto.setId(id);

        when(service.update(id, historyDto)).thenReturn(updateHistoryDto);
        ResponseEntity<HistoryDto> response = controller.update(id, historyDto);
        verify(service).update(id, historyDto);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(updateHistoryDto);
    }

    @Test
    @DisplayName("Обновление по несуществующему id, негативный сценарий")
    void updateByNonExistIdNegativeTest() {
        Long id = 1L;
        HistoryDto dto = new HistoryDto(id, 1L, 1L,
                1L, 1L, 1L, 1L);

        when(service.update(id, dto)).thenThrow(new EntityNotFoundException());
        assertThatExceptionOfType(EntityNotFoundException.class)
                .isThrownBy(() -> controller.update(id, dto));
    }
}