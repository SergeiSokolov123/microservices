package profile.controller;

import com.bank.profile.controller.AccountDetailsIdController;
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
    @DisplayName("Чтение по id, позитивный сценарий")
    void readByIdPositiveTest() {
        AccountDetailsIdDto accountDetailsIdDto = DtoTestData.getAccountDetailsIdDto();
        doReturn(accountDetailsIdDto).when(service).findById(accountDetailsIdDto.getId());

        ResponseEntity<AccountDetailsIdDto> actualResult = accountDetailsIdController.read(accountDetailsIdDto.getId());

        assertThat(actualResult.getStatusCodeValue()).isEqualTo(200);
        assertThat(actualResult.getBody()).isEqualTo(accountDetailsIdDto);
    }

    @Test
    @DisplayName("Чтение по id, негативный сценарий")
    void readByIdNegativeTest() {
        doThrow(RuntimeException.class).when(service).findById(any());

        assertThrows(RuntimeException.class, () -> accountDetailsIdController.read(any()));
    }

    @Test
    @DisplayName("Создание, позитивный сценарий")
    void createPositiveTest() {
        AccountDetailsIdDto accountDetailsIdDto = DtoTestData.getAccountDetailsIdDto();
        doReturn(accountDetailsIdDto).when(service).save(accountDetailsIdDto);

        ResponseEntity<AccountDetailsIdDto> actualResult = accountDetailsIdController.create(accountDetailsIdDto);

        assertThat(actualResult.getStatusCodeValue()).isEqualTo(200);
        assertThat(actualResult.getBody()).isEqualTo(accountDetailsIdDto);
    }

    @Test
    @DisplayName("Создание, негативный сценарий")
    void createNegativeTest() {
        doThrow(RuntimeException.class).when(service).save(any());

        assertThrows(RuntimeException.class, () -> accountDetailsIdController.create(any()));
    }

    @Test
    @DisplayName("Обновление по id, позитивный сценарий")
    void updateByIdPositiveTest() {
        AccountDetailsIdDto accountDetailsIdDto = DtoTestData.getAccountDetailsIdDto();
        doReturn(accountDetailsIdDto).when(service).update(1L, accountDetailsIdDto);

        ResponseEntity<AccountDetailsIdDto> actualResult = accountDetailsIdController
                .update(1L, accountDetailsIdDto);

        assertThat(actualResult.getStatusCodeValue()).isEqualTo(200);
        assertThat(actualResult.getBody()).isEqualTo(accountDetailsIdDto);
    }

    @Test
    @DisplayName("Обновление по id, негативный сценарий")
    void updateByIdNegativeTest() {
        doThrow(RuntimeException.class).when(service).update(any(), any());

        assertThrows(RuntimeException.class, () -> accountDetailsIdController.update(any(), any()));
    }

    @Test
    @DisplayName("Чтение по нескольким id, позитивный сценарий")
    void readAllByIdPositiveTest() {
        List<AccountDetailsIdDto> listOfAccountDetailsIdDto = DtoTestData.getListOfAccountDetailsIdDto();
        doReturn(listOfAccountDetailsIdDto).when(service).findAllById(List.of(1L, 2L));

        ResponseEntity<List<AccountDetailsIdDto>> actualResult = accountDetailsIdController
                .readAllById(List.of(1L, 2L));

        assertThat(actualResult.getStatusCodeValue()).isEqualTo(200);
        assertThat(actualResult.getBody()).isEqualTo(listOfAccountDetailsIdDto);
    }

    @Test
    @DisplayName("Чтение по нескольким id, негативный сценарий")
    void readAllByIdNegativeTest() {
        doThrow(RuntimeException.class).when(service).findAllById(anyList());

        assertThrows(RuntimeException.class, () -> accountDetailsIdController.readAllById(anyList()));
    }
}