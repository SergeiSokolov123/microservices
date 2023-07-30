package com.bank.profile.controller;

import com.bank.profile.dto.RegistrationDto;
import com.bank.profile.service.RegistrationService;
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
class RegistrationControllerTest {
    @Mock
    private RegistrationService service;
    @InjectMocks
    private RegistrationController registrationController;

    @Test
    @DisplayName("Позитивный сценарий read()")
    void successRead() {
        RegistrationDto registrationDto = DtoTestData.getRegistrationDto();
        doReturn(registrationDto).when(service).findById(registrationDto.getId());

        ResponseEntity<RegistrationDto> actualResult = registrationController.read(registrationDto.getId());

        assertThat(actualResult.getStatusCodeValue()).isEqualTo(200);
        assertThat(actualResult.getBody()).isEqualTo(registrationDto);
    }

    @Test
    @DisplayName("Негативный сценарий read() c исключением")
    void shouldThrowExceptionIfRegistrationIsNotReadable() {
        doThrow(RuntimeException.class).when(service).findById(any());

        assertThrows(RuntimeException.class, () -> registrationController.read(any()));
    }

    @Test
    @DisplayName("Позитивный сценарий create()")
    void successCreate() {
        RegistrationDto registrationDto = DtoTestData.getRegistrationDto();
        doReturn(registrationDto).when(service).save(registrationDto);

        ResponseEntity<RegistrationDto> actualResult = registrationController.create(registrationDto);

        assertThat(actualResult.getStatusCodeValue()).isEqualTo(200);
        assertThat(actualResult.getBody()).isEqualTo(registrationDto);
    }

    @Test
    @DisplayName("Негативный сценарий create() c исключением")
    void shouldThrowExceptionIfRegistrationIsNotCreated() {
        doThrow(RuntimeException.class).when(service).save(any());

        assertThrows(RuntimeException.class, () -> registrationController.create(any()));
    }

    @Test
    @DisplayName("Позитивный сценарий update()")
    void successUpdate() {
        RegistrationDto registrationDto = DtoTestData.getRegistrationDto();
        doReturn(registrationDto).when(service).update(1L, registrationDto);

        ResponseEntity<RegistrationDto> actualResult = registrationController.update(1L, registrationDto);

        assertThat(actualResult.getStatusCodeValue()).isEqualTo(200);
        assertThat(actualResult.getBody()).isEqualTo(registrationDto);
    }

    @Test
    @DisplayName("Негативный сценарий update() c исключением")
    void shouldThrowExceptionIfRegistrationIsNotUpdated() {
        doThrow(RuntimeException.class).when(service).update(any(), any());

        assertThrows(RuntimeException.class, () -> registrationController.update(any(), any()));
    }

    @Test
    @DisplayName("Позитивный сценарий findAllById()")
    void successFindAllById() {
        List<RegistrationDto> listOfRegistrationDto = DtoTestData.getListOfRegistrationDto();
        doReturn(listOfRegistrationDto).when(service).findAllById(List.of(1L, 2L));

        ResponseEntity<List<RegistrationDto>> actualResult = registrationController.readAllById(List.of(1L, 2L));

        assertThat(actualResult.getStatusCodeValue()).isEqualTo(200);
        assertThat(actualResult.getBody()).isEqualTo(listOfRegistrationDto);
    }

    @Test
    @DisplayName("Негативный сценарий findAllById() c исключением")
    void failFindAllById() {
        doThrow(RuntimeException.class).when(service).findAllById(anyList());

        assertThrows(RuntimeException.class, () -> registrationController.readAllById(anyList()));
    }
}