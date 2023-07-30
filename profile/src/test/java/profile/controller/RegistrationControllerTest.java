package profile.controller;

import com.bank.profile.controller.RegistrationController;
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
    @DisplayName("Чтение по id, позитивный сценарий")
    void readByIdPositiveTest() {
        RegistrationDto registrationDto = DtoTestData.getRegistrationDto();
        doReturn(registrationDto).when(service).findById(registrationDto.getId());

        ResponseEntity<RegistrationDto> actualResult = registrationController.read(registrationDto.getId());

        assertThat(actualResult.getStatusCodeValue()).isEqualTo(200);
        assertThat(actualResult.getBody()).isEqualTo(registrationDto);
    }

    @Test
    @DisplayName("Чтение по id, негативный сценарий")
    void readByIdNegativeTest() {
        doThrow(RuntimeException.class).when(service).findById(any());

        assertThrows(RuntimeException.class, () -> registrationController.read(any()));
    }

    @Test
    @DisplayName("Создание, позитивный сценарий")
    void createPositiveTest() {
        RegistrationDto registrationDto = DtoTestData.getRegistrationDto();
        doReturn(registrationDto).when(service).save(registrationDto);

        ResponseEntity<RegistrationDto> actualResult = registrationController.create(registrationDto);

        assertThat(actualResult.getStatusCodeValue()).isEqualTo(200);
        assertThat(actualResult.getBody()).isEqualTo(registrationDto);
    }

    @Test
    @DisplayName("Создание, негативный сценарий")
    void createNegativeTest() {
        doThrow(RuntimeException.class).when(service).save(any());

        assertThrows(RuntimeException.class, () -> registrationController.create(any()));
    }

    @Test
    @DisplayName("Обновление по id, позитивный сценарий")
    void updateByIdPositiveTest() {
        RegistrationDto registrationDto = DtoTestData.getRegistrationDto();
        doReturn(registrationDto).when(service).update(1L, registrationDto);

        ResponseEntity<RegistrationDto> actualResult = registrationController.update(1L, registrationDto);

        assertThat(actualResult.getStatusCodeValue()).isEqualTo(200);
        assertThat(actualResult.getBody()).isEqualTo(registrationDto);
    }

    @Test
    @DisplayName("Обновление по id, негативный сценарий")
    void updateByIdNegativeTest() {
        doThrow(RuntimeException.class).when(service).update(any(), any());

        assertThrows(RuntimeException.class, () -> registrationController.update(any(), any()));
    }

    @Test
    @DisplayName("Чтение по нескольким id, позитивный сценарий")
    void readAllByIdPositiveTest() {
        List<RegistrationDto> listOfRegistrationDto = DtoTestData.getListOfRegistrationDto();
        doReturn(listOfRegistrationDto).when(service).findAllById(List.of(1L, 2L));

        ResponseEntity<List<RegistrationDto>> actualResult = registrationController.readAllById(List.of(1L, 2L));

        assertThat(actualResult.getStatusCodeValue()).isEqualTo(200);
        assertThat(actualResult.getBody()).isEqualTo(listOfRegistrationDto);
    }

    @Test
    @DisplayName("Чтение по нескольким id, негативный сценарий")
    void readAllByIdNegativeTest() {
        doThrow(RuntimeException.class).when(service).findAllById(anyList());

        assertThrows(RuntimeException.class, () -> registrationController.readAllById(anyList()));
    }
}