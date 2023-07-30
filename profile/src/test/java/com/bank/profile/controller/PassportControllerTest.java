package com.bank.profile.controller;

import com.bank.profile.dto.PassportDto;
import com.bank.profile.service.PassportService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import utils.DtoTestData;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;

@ExtendWith(MockitoExtension.class)
class PassportControllerTest {
    @Mock
    private PassportService service;
    @InjectMocks
    private PassportController passportController;

    @Test
    @DisplayName("Позитивный сценарий read()")
    void successRead() {
        PassportDto passportDto = DtoTestData.getPassportDto();
        doReturn(passportDto).when(service).findById(passportDto.getId());

        ResponseEntity<PassportDto> actualResult = passportController.read(passportDto.getId());

        assertThat(actualResult.getStatusCodeValue()).isEqualTo(200);
        assertThat(actualResult.getBody()).isEqualTo(passportDto);
    }

    @Test
    @DisplayName("Негативный сценарий read() c исключением")
    void shouldThrowExceptionIfPassportIsNotReadable() {
        doThrow(RuntimeException.class).when(service).findById(any());

        assertThrows(RuntimeException.class, () -> passportController.read(any()));
    }

    @Test
    @DisplayName("Позитивный сценарий create()")
    void successCreate() {
        PassportDto passportDto = DtoTestData.getPassportDto();
        doReturn(passportDto).when(service).save(passportDto);

        ResponseEntity<PassportDto> actualResult = passportController.create(passportDto);

        assertThat(actualResult.getStatusCodeValue()).isEqualTo(200);
        assertThat(actualResult.getBody()).isEqualTo(passportDto);
    }

    @Test
    @DisplayName("Негативный сценарий create() c исключением")
    void shouldThrowExceptionIfPassportIsNotCreated() {
        doThrow(RuntimeException.class).when(service).save(any());

        assertThrows(RuntimeException.class, () -> passportController.create(any()));
    }

    @Test
    @DisplayName("Позитивный сценарий update()")
    void successUpdate() {
        PassportDto passportDto = DtoTestData.getPassportDto();
        doReturn(passportDto).when(service).update(1L, passportDto);

        ResponseEntity<PassportDto> actualResult = passportController.update(1L, passportDto);

        assertThat(actualResult.getStatusCodeValue()).isEqualTo(200);
        assertThat(actualResult.getBody()).isEqualTo(passportDto);
    }

    @Test
    @DisplayName("Негативный сценарий update() c исключением")
    void shouldThrowExceptionIfPassportIsNotUpdated() {
        doThrow(RuntimeException.class).when(service).update(any(), any());

        assertThrows(RuntimeException.class, () -> passportController.update(any(), any()));
    }

    @Test
    @DisplayName("Позитивный сценарий findAllById()")
    void successFindAllById() {
        List<PassportDto> listOfPassportDto = DtoTestData.getListOfPassportDto();
        doReturn(listOfPassportDto).when(service).findAllById(List.of(1L, 2L));

        ResponseEntity<List<PassportDto>> actualResult = passportController.readAllById(List.of(1L, 2L));

        assertThat(actualResult.getStatusCodeValue()).isEqualTo(200);
        assertThat(actualResult.getBody()).isEqualTo(listOfPassportDto);
    }

    @Test
    @DisplayName("Негативный сценарий findAllById() c исключением")
    void failFindAllById() {
        doThrow(RuntimeException.class).when(service).findAllById(anyList());

        assertThrows(RuntimeException.class, () -> passportController.readAllById(anyList()));
    }
}