package profile.controller;

import com.bank.profile.controller.PassportController;
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
    @DisplayName("Чтение по id, позитивный сценарий")
    void readByIdPositiveTest() {
        PassportDto passportDto = DtoTestData.getPassportDto();
        doReturn(passportDto).when(service).findById(passportDto.getId());

        ResponseEntity<PassportDto> actualResult = passportController.read(passportDto.getId());

        assertThat(actualResult.getStatusCodeValue()).isEqualTo(200);
        assertThat(actualResult.getBody()).isEqualTo(passportDto);
    }

    @Test
    @DisplayName("Чтение по id, негативный сценарий")
    void readByIdNegativeTest() {
        doThrow(RuntimeException.class).when(service).findById(any());

        assertThrows(RuntimeException.class, () -> passportController.read(any()));
    }

    @Test
    @DisplayName("Создание, позитивный сценарий")
    void createPositiveTest() {
        PassportDto passportDto = DtoTestData.getPassportDto();
        doReturn(passportDto).when(service).save(passportDto);

        ResponseEntity<PassportDto> actualResult = passportController.create(passportDto);

        assertThat(actualResult.getStatusCodeValue()).isEqualTo(200);
        assertThat(actualResult.getBody()).isEqualTo(passportDto);
    }

    @Test
    @DisplayName("Создание, негативный сценарий")
    void createNegativeTest() {
        doThrow(RuntimeException.class).when(service).save(any());

        assertThrows(RuntimeException.class, () -> passportController.create(any()));
    }

    @Test
    @DisplayName("Обновление по id, позитивный сценарий")
    void updateByIdPositiveTest() {
        PassportDto passportDto = DtoTestData.getPassportDto();
        doReturn(passportDto).when(service).update(1L, passportDto);

        ResponseEntity<PassportDto> actualResult = passportController.update(1L, passportDto);

        assertThat(actualResult.getStatusCodeValue()).isEqualTo(200);
        assertThat(actualResult.getBody()).isEqualTo(passportDto);
    }

    @Test
    @DisplayName("Обновление по id, негативный сценарий")
    void updateByIdNegativeTest() {
        doThrow(RuntimeException.class).when(service).update(any(), any());

        assertThrows(RuntimeException.class, () -> passportController.update(any(), any()));
    }

    @Test
    @DisplayName("Чтение по нескольким id, позитивный сценарий")
    void readAllByIdPositiveTest() {
        List<PassportDto> listOfPassportDto = DtoTestData.getListOfPassportDto();
        doReturn(listOfPassportDto).when(service).findAllById(List.of(1L, 2L));

        ResponseEntity<List<PassportDto>> actualResult = passportController.readAllById(List.of(1L, 2L));

        assertThat(actualResult.getStatusCodeValue()).isEqualTo(200);
        assertThat(actualResult.getBody()).isEqualTo(listOfPassportDto);
    }

    @Test
    @DisplayName("Чтение по нескольким id, негативный сценарий")
    void readAllByIdNegativeTest() {
        doThrow(RuntimeException.class).when(service).findAllById(anyList());

        assertThrows(RuntimeException.class, () -> passportController.readAllById(anyList()));
    }
}