package com.bank.profile.controller;

import com.bank.profile.dto.AccountDetailsIdDto;
import com.bank.profile.service.AccountDetailsIdService;
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
class AccountDetailsIdControllerTest {
    @Mock
    private AccountDetailsIdService service;
    @InjectMocks
    AccountDetailsIdController accountDetailsIdController;

    @Test
    @DisplayName("Позитивный сценарий read()")
    void successRead() {
        AccountDetailsIdDto accountDetailsIdDto = DtoTestData.getAccountDetailsIdDto();
        doReturn(accountDetailsIdDto).when(service).findById(accountDetailsIdDto.getId());

        ResponseEntity<AccountDetailsIdDto> actualResult = accountDetailsIdController.read(accountDetailsIdDto.getId());

        assertThat(actualResult.getStatusCodeValue()).isEqualTo(200);
        assertThat(actualResult.getBody()).isEqualTo(accountDetailsIdDto);
    }

    @Test
    @DisplayName("Негативный сценарий read() c исключением")
    void shouldThrowExceptionIfAccountIsNotReadable() {
        doThrow(RuntimeException.class).when(service).findById(any());

        assertThrows(RuntimeException.class, () -> accountDetailsIdController.read(any()));
    }

    @Test
    @DisplayName("Позитивный сценарий create()")
    void successCreate() {
        AccountDetailsIdDto accountDetailsIdDto = DtoTestData.getAccountDetailsIdDto();
        doReturn(accountDetailsIdDto).when(service).save(accountDetailsIdDto);

        ResponseEntity<AccountDetailsIdDto> actualResult = accountDetailsIdController.create(accountDetailsIdDto);

        assertThat(actualResult.getStatusCodeValue()).isEqualTo(200);
        assertThat(actualResult.getBody()).isEqualTo(accountDetailsIdDto);
    }

    @Test
    @DisplayName("Негативный сценарий create() c исключением")
    void shouldThrowExceptionIfAccountIsNotCreated() {
        doThrow(RuntimeException.class).when(service).save(any());

        assertThrows(RuntimeException.class, () -> accountDetailsIdController.create(any()));
    }

    @Test
    @DisplayName("Позитивный сценарий update()")
    void successUpdate() {
        AccountDetailsIdDto accountDetailsIdDto = DtoTestData.getAccountDetailsIdDto();
        doReturn(accountDetailsIdDto).when(service).update(1L, accountDetailsIdDto);

        ResponseEntity<AccountDetailsIdDto> actualResult = accountDetailsIdController.update(1L, accountDetailsIdDto);

        assertThat(actualResult.getStatusCodeValue()).isEqualTo(200);
        assertThat(actualResult.getBody()).isEqualTo(accountDetailsIdDto);
    }

    @Test
    @DisplayName("Негативный сценарий update() c исключением")
    void shouldThrowExceptionIfAccountIsNotUpdated() {
        doThrow(RuntimeException.class).when(service).update(any(), any());

        assertThrows(RuntimeException.class, () -> accountDetailsIdController.update(any(), any()));
    }

    @Test
    @DisplayName("Позитивный сценарий findAllById()")
    void successFindAllById() {
        List<AccountDetailsIdDto> listOfAccountDetailsIdDto = DtoTestData.getListOfAccountDetailsIdDto();
        doReturn(listOfAccountDetailsIdDto).when(service).findAllById(List.of(1L, 2L));

        ResponseEntity<List<AccountDetailsIdDto>> actualResult = accountDetailsIdController.readAllById(List.of(1L, 2L));

        assertThat(actualResult.getStatusCodeValue()).isEqualTo(200);
        assertThat(actualResult.getBody()).isEqualTo(listOfAccountDetailsIdDto);
    }

    @Test
    @DisplayName("Негативный сценарий findAllById() c исключением")
    void failFindAllById() {
        doThrow(RuntimeException.class).when(service).findAllById(anyList());

        assertThrows(RuntimeException.class, () -> accountDetailsIdController.readAllById(anyList()));
    }
}