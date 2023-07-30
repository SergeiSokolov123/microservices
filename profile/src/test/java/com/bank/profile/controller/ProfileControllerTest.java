package com.bank.profile.controller;

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
    @DisplayName("Позитивный сценарий read()")
    void successRead() {
        ProfileDto profileDto = DtoTestData.getProfileDto();
        doReturn(profileDto).when(service).findById(profileDto.getId());

        ResponseEntity<ProfileDto> actualResult = profileController.read(profileDto.getId());

        assertThat(actualResult.getStatusCodeValue()).isEqualTo(200);
        assertThat(actualResult.getBody()).isEqualTo(profileDto);
    }

    @Test
    @DisplayName("Негативный сценарий read() c исключением")
    void shouldThrowExceptionIfProfileIsNotReadable() {
        doThrow(RuntimeException.class).when(service).findById(any());

        assertThrows(RuntimeException.class, () -> profileController.read(any()));
    }

    @Test
    @DisplayName("Позитивный сценарий create()")
    void successCreate() {
        ProfileDto profileDto = DtoTestData.getProfileDto();
        doReturn(profileDto).when(service).save(profileDto);

        ResponseEntity<ProfileDto> actualResult = profileController.create(profileDto);

        assertThat(actualResult.getStatusCodeValue()).isEqualTo(200);
        assertThat(actualResult.getBody()).isEqualTo(profileDto);
    }

    @Test
    @DisplayName("Негативный сценарий create() c исключением")
    void shouldThrowExceptionIfProfileIsNotCreated() {
        doThrow(RuntimeException.class).when(service).save(any());

        assertThrows(RuntimeException.class, () -> profileController.create(any()));
    }

    @Test
    @DisplayName("Позитивный сценарий update()")
    void successUpdate() {
        ProfileDto profileDto = DtoTestData.getProfileDto();
        doReturn(profileDto).when(service).update(1L, profileDto);

        ResponseEntity<ProfileDto> actualResult = profileController.update(1L, profileDto);

        assertThat(actualResult.getStatusCodeValue()).isEqualTo(200);
        assertThat(actualResult.getBody()).isEqualTo(profileDto);
    }

    @Test
    @DisplayName("Негативный сценарий update() c исключением")
    void shouldThrowExceptionIfProfileIsNotUpdated() {
        doThrow(RuntimeException.class).when(service).update(any(), any());

        assertThrows(RuntimeException.class, () -> profileController.update(any(), any()));
    }

    @Test
    @DisplayName("Позитивный сценарий findAllById()")
    void successFindAllById() {
        List<ProfileDto> listOfProfileDto = DtoTestData.getListOfProfileDto();
        doReturn(listOfProfileDto).when(service).findAllById(List.of(1L, 2L));

        ResponseEntity<List<ProfileDto>> actualResult = profileController.readAllById(List.of(1L, 2L));

        assertThat(actualResult.getStatusCodeValue()).isEqualTo(200);
        assertThat(actualResult.getBody()).isEqualTo(listOfProfileDto);
    }

    @Test
    @DisplayName("Негативный сценарий findAllById() c исключением")
    void failFindAllById() {
        doThrow(RuntimeException.class).when(service).findAllById(anyList());

        assertThrows(RuntimeException.class, () -> profileController.readAllById(anyList()));
    }
}