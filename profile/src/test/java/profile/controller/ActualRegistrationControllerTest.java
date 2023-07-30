package profile.controller;

import com.bank.profile.controller.ActualRegistrationController;
import com.bank.profile.dto.ActualRegistrationDto;
import com.bank.profile.service.ActualRegistrationService;
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
class ActualRegistrationControllerTest {
    @Mock
    private ActualRegistrationService service;
    @InjectMocks
    private ActualRegistrationController actualRegistrationController;

    @Test
    @DisplayName("Чтение по id, позитивный сценарий")
    void readByIdPositiveTest() {
        ActualRegistrationDto actualRegistrationDto = DtoTestData.getActualRegistrationDto();
        doReturn(actualRegistrationDto).when(service).findById(actualRegistrationDto.getId());

        ResponseEntity<ActualRegistrationDto> actualResult = actualRegistrationController
                .read(actualRegistrationDto.getId());

        assertThat(actualResult.getStatusCodeValue()).isEqualTo(200);
        assertThat(actualResult.getBody()).isEqualTo(actualRegistrationDto);
    }

    @Test
    @DisplayName("Чтение по id, негативный сценарий")
    void readByIdNegativeTest() {
        doThrow(RuntimeException.class).when(service).findById(any());

        assertThrows(RuntimeException.class, () -> actualRegistrationController.read(any()));
    }

    @Test
    @DisplayName("Создание, позитивный сценарий")
    void createPositiveTest() {
        ActualRegistrationDto actualRegistrationDto = DtoTestData.getActualRegistrationDto();
        doReturn(actualRegistrationDto).when(service).save(actualRegistrationDto);

        ResponseEntity<ActualRegistrationDto> actualResult = actualRegistrationController.create(actualRegistrationDto);

        assertThat(actualResult.getStatusCodeValue()).isEqualTo(200);
        assertThat(actualResult.getBody()).isEqualTo(actualRegistrationDto);
    }

    @Test
    @DisplayName("Создание, негативный сценарий")
    void createNegativeTest() {
        doThrow(RuntimeException.class).when(service).save(any());

        assertThrows(RuntimeException.class, () -> actualRegistrationController.create(any()));
    }

    @Test
    @DisplayName("Обновление по id, позитивный сценарий")
    void updateByIdPositiveTest() {
        ActualRegistrationDto actualRegistrationDto = DtoTestData.getActualRegistrationDto();
        doReturn(actualRegistrationDto).when(service).update(1L, actualRegistrationDto);

        ResponseEntity<ActualRegistrationDto> actualResult = actualRegistrationController
                .update(1L, actualRegistrationDto);

        assertThat(actualResult.getStatusCodeValue()).isEqualTo(200);
        assertThat(actualResult.getBody()).isEqualTo(actualRegistrationDto);
    }

    @Test
    @DisplayName("Обновление по id, негативный сценарий")
    void updateByIdNegativeTest() {
        doThrow(RuntimeException.class).when(service).update(any(), any());

        assertThrows(RuntimeException.class, () -> actualRegistrationController.update(any(), any()));
    }

    @Test
    @DisplayName("Чтение по нескольким id, позитивный сценарий")
    void readAllByIdPositiveTest() {
        List<ActualRegistrationDto> listOfActualRegistrationDto = DtoTestData.getListOfActualRegistrationDto();
        doReturn(listOfActualRegistrationDto).when(service).findAllById(List.of(1L, 2L));

        ResponseEntity<List<ActualRegistrationDto>> actualResult = actualRegistrationController
                .readAllById(List.of(1L, 2L));

        assertThat(actualResult.getStatusCodeValue()).isEqualTo(200);
        assertThat(actualResult.getBody()).isEqualTo(listOfActualRegistrationDto);
    }

    @Test
    @DisplayName("Чтение по нескольким id, негативный сценарий")
    void readAllByIdNegativeTest() {
        doThrow(RuntimeException.class).when(service).findAllById(anyList());

        assertThrows(RuntimeException.class, () -> actualRegistrationController.readAllById(anyList()));
    }
}