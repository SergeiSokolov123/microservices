package profile.controller;

import com.bank.profile.controller.ProfileController;
import com.bank.profile.dto.ProfileDto;
import com.bank.profile.service.ProfileService;
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
class ProfileControllerTest {
    @Mock
    private ProfileService service;
    @InjectMocks
    private ProfileController profileController;

    @Test
    @DisplayName("Чтение по id, позитивный сценарий")
    void readByIdPositiveTest() {
        ProfileDto profileDto = DtoTestData.getProfileDto();
        doReturn(profileDto).when(service).findById(profileDto.getId());

        ResponseEntity<ProfileDto> actualResult = profileController.read(profileDto.getId());

        assertThat(actualResult.getStatusCodeValue()).isEqualTo(200);
        assertThat(actualResult.getBody()).isEqualTo(profileDto);
    }

    @Test
    @DisplayName("Чтение по id, негативный сценарий")
    void readByIdNegativeTest() {
        doThrow(RuntimeException.class).when(service).findById(any());

        assertThrows(RuntimeException.class, () -> profileController.read(any()));
    }

    @Test
    @DisplayName("Создание, позитивный сценарий")
    void createPositiveTest() {
        ProfileDto profileDto = DtoTestData.getProfileDto();
        doReturn(profileDto).when(service).save(profileDto);

        ResponseEntity<ProfileDto> actualResult = profileController.create(profileDto);

        assertThat(actualResult.getStatusCodeValue()).isEqualTo(200);
        assertThat(actualResult.getBody()).isEqualTo(profileDto);
    }

    @Test
    @DisplayName("Создание, негативный сценарий")
    void createNegativeTest() {
        doThrow(RuntimeException.class).when(service).save(any());

        assertThrows(RuntimeException.class, () -> profileController.create(any()));
    }

    @Test
    @DisplayName("Обновление по id, позитивный сценарий")
    void updateByIdPositiveTest() {
        ProfileDto profileDto = DtoTestData.getProfileDto();
        doReturn(profileDto).when(service).update(1L, profileDto);

        ResponseEntity<ProfileDto> actualResult = profileController.update(1L, profileDto);

        assertThat(actualResult.getStatusCodeValue()).isEqualTo(200);
        assertThat(actualResult.getBody()).isEqualTo(profileDto);
    }

    @Test
    @DisplayName("Обновление по id, негативный сценарий")
    void updateByIdNegativeTest() {
        doThrow(RuntimeException.class).when(service).update(any(), any());

        assertThrows(RuntimeException.class, () -> profileController.update(any(), any()));
    }

    @Test
    @DisplayName("Чтение по нескольким id, позитивный сценарий")
    void readAllByIdPositiveTest() {
        List<ProfileDto> listOfProfileDto = DtoTestData.getListOfProfileDto();
        doReturn(listOfProfileDto).when(service).findAllById(List.of(1L, 2L));

        ResponseEntity<List<ProfileDto>> actualResult = profileController.readAllById(List.of(1L, 2L));

        assertThat(actualResult.getStatusCodeValue()).isEqualTo(200);
        assertThat(actualResult.getBody()).isEqualTo(listOfProfileDto);
    }

    @Test
    @DisplayName("Чтение по нескольким id, негативный сценарий")
    void readAllByIdNegativeTest() {
        doThrow(RuntimeException.class).when(service).findAllById(anyList());

        assertThrows(RuntimeException.class, () -> profileController.readAllById(anyList()));
    }
}