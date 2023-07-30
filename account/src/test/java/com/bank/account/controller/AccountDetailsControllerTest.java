package com.bank.account.controller;

import com.bank.account.dto.AccountDetailsDto;
import com.bank.account.service.AccountDetailsService;
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
 * Тесты {@link AccountDetailsController}
 */
@ExtendWith(MockitoExtension.class)
public class AccountDetailsControllerTest {

    @Mock
    private AccountDetailsService service;

    @InjectMocks
    private AccountDetailsController controller;

    @BeforeEach
    public void setup() {
        controller = new AccountDetailsController(service);
    }

    @Test
    @DisplayName("Чтение по id, позитивный сценарий")
    public void readByIdPositiveTest() {

        Long accountId = 1L;
        AccountDetailsDto accountDetailsDto = new AccountDetailsDto();

        when(service.findById(accountId)).thenReturn(accountDetailsDto);

        AccountDetailsDto result = controller.read(accountId);

        verify(service).findById(accountId);

        assertThat(result).isEqualTo(accountDetailsDto);
    }

    @Test
    @DisplayName("Чтение по несуществующему id, негативный сценарий")
    public void readByNonExistIdNegativeTest() {

        Long accountId = 1L;

        when(service.findById(accountId)).thenThrow(new EntityNotFoundException());

        assertThatExceptionOfType(EntityNotFoundException.class)
                .isThrownBy(() -> controller.read(accountId));
    }

    @Test
    @DisplayName("Создание, позитивный сценарий")
    public void createPositiveTest() {

        AccountDetailsDto accountDetailsDto = new AccountDetailsDto();
        AccountDetailsDto createdDto = new AccountDetailsDto();

        when(service.save(accountDetailsDto)).thenReturn(createdDto);

        ResponseEntity<AccountDetailsDto> response = controller.create(accountDetailsDto);

        verify(service).save(accountDetailsDto);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(createdDto);
    }

    @Test
    @DisplayName("Создание, негативный сценарий")
    public void createNegativeTest() {

        AccountDetailsDto accountDetailsDto = new AccountDetailsDto();

        when(service.save(accountDetailsDto)).thenThrow(RuntimeException.class);

        assertThatExceptionOfType(RuntimeException.class)
                .isThrownBy(() -> controller.create(accountDetailsDto));
    }

    @Test
    @DisplayName("Обновление по id, позитивный сценарий")
    public void updateByIdPositiveTest() {

        Long accountId = 1L;
        AccountDetailsDto accountDetailsDto = new AccountDetailsDto();
        AccountDetailsDto updatedDto = new AccountDetailsDto();
        accountDetailsDto.setId(accountId);
        updatedDto.setId(accountId);

        when(service.update(accountId, accountDetailsDto)).thenReturn(updatedDto);

        ResponseEntity<AccountDetailsDto> response = controller.update(accountId, accountDetailsDto);

        verify(service).update(accountId, accountDetailsDto);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(updatedDto);
    }

    @Test
    @DisplayName("Обновление по несуществующему id, негативный сценарий")
    public void updateByNonExistIdNegativeTest() {

        Long accountId = 1L;
        AccountDetailsDto accountDetailsDto = new AccountDetailsDto();

        when(service.update(accountId, accountDetailsDto)).thenThrow(EntityNotFoundException.class);

        assertThatExceptionOfType(EntityNotFoundException.class)
                .isThrownBy(() -> controller.update(accountId, accountDetailsDto));
    }

    @Test
    @DisplayName("Чтение по нескольким id, позитивный сценарий")
    public void readAllByIdPositiveTest() {

        Long accountId1 = 1L;
        Long accountId2 = 2L;
        List<Long> ids = Arrays.asList(accountId1, accountId2);
        AccountDetailsDto accountDetailsDto1 = new AccountDetailsDto();
        accountDetailsDto1.setId(accountId1);
        AccountDetailsDto accountDetailsDto2 = new AccountDetailsDto();
        accountDetailsDto2.setId(accountId2);
        List<AccountDetailsDto> dtoList = Arrays.asList(accountDetailsDto1, accountDetailsDto2);

        when(service.findAllById(ids)).thenReturn(dtoList);

        ResponseEntity<List<AccountDetailsDto>> response = controller.readAll(ids);

        verify(service).findAllById(ids);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).hasSize(dtoList.size());
    }

    @Test
    @DisplayName("Чтение по нескольким несуществующим id, негативный сценарий")
    public void readAllByNonExistIdNegativeTest() {

        List<Long> ids = Arrays.asList(1L, 2L);

        when(service.findAllById(ids)).thenThrow(EntityNotFoundException.class);

        assertThatExceptionOfType(EntityNotFoundException.class)
                .isThrownBy(() -> controller.readAll(ids));
    }
}
