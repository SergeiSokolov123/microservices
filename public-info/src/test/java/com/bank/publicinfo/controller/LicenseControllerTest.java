package com.bank.publicinfo.controller;

import com.bank.publicinfo.dto.LicenseDto;
import com.bank.publicinfo.service.LicenseService;
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
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Тесты {@link LicenseController}
 */
@ExtendWith(MockitoExtension.class)
class LicenseControllerTest {

    @Mock
    private LicenseService licenseService;

    @InjectMocks
    private LicenseController licenseController;

    @BeforeEach
    void setUp() {
        licenseController = new LicenseController(licenseService);
    }

    @Test
    @DisplayName("чтение по id, позитивный сценарий")
    void readByIdPositiveTest() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Long id = 1L;
        LicenseDto licenseDto = new LicenseDto();

        Method readById = LicenseController.class.getDeclaredMethod("readById", Long.class);
        readById.setAccessible(true);

        when(licenseService.findById(id)).thenReturn(licenseDto);

        ResponseEntity<?> licenseDtoResponseEntity = (ResponseEntity<LicenseDto>) readById
                .invoke(licenseController, id);

        assertEquals(HttpStatus.OK, licenseDtoResponseEntity.getStatusCode());
        assertEquals(licenseDto, licenseDtoResponseEntity.getBody());
    }

    @Test
    @DisplayName("чтение по несуществующему id, негативный сценарий")
    void readByNonExistIdNegativeTest() throws NoSuchMethodException {
        Long id = 1L;

        Method readById = LicenseController.class.getDeclaredMethod("readById", Long.class);
        readById.setAccessible(true);

        when(licenseService.findById(id)).thenThrow(new EntityNotFoundException());

        assertThrows(EntityNotFoundException.class, () -> {
            try {
                readById.invoke(licenseController, id);
            } catch (InvocationTargetException e) {
                throw e.getCause();
            }
        });
    }

    @Test
    @DisplayName("чтение по нескольким id, позитивный сценарий")
    void readAllByIdPositiveTest() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        List<Long> ids = List.of(1L, 2L, 3L);
        List<LicenseDto> licenseDtoList = new ArrayList<>();

        Method readAllById = LicenseController.class.getDeclaredMethod("readAllById", List.class);
        readAllById.setAccessible(true);

        when(licenseService.findAllById(ids)).thenReturn(licenseDtoList);

        ResponseEntity<?> licenseDtoResponseEntity = (ResponseEntity<List<LicenseDto>>) readAllById
                .invoke(licenseController, ids);

        assertEquals(HttpStatus.OK, licenseDtoResponseEntity.getStatusCode());
        assertEquals(licenseDtoList, licenseDtoResponseEntity.getBody());
    }

    @Test
    @DisplayName("чтение по нескольким несуществующим id, негативный сценарий")
    void readAllByNonExistIdNegativeTest() throws NoSuchMethodException {
        List<Long> ids = List.of(1L, 2L, 3L);

        Method readAllById = LicenseController.class.getDeclaredMethod("readAllById", List.class);
        readAllById.setAccessible(true);

        when(licenseService.findAllById(ids)).thenThrow(new EntityNotFoundException());

        assertThrows(EntityNotFoundException.class, () -> {
            try {
                readAllById.invoke(licenseController, ids);
            } catch (InvocationTargetException e) {
                throw e.getCause();
            }
        });
    }

    @Test
    @DisplayName("создание, позитивный сценарий")
    void createPositiveTest() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        LicenseDto licenseDto = new LicenseDto();
        LicenseDto createdLicenseDto = new LicenseDto();

        Method create = LicenseController.class.getDeclaredMethod("create", LicenseDto.class);
        create.setAccessible(true);

        when(licenseService.create(licenseDto)).thenReturn(createdLicenseDto);

        ResponseEntity<?> licenseDtoResponseEntity = (ResponseEntity<LicenseDto>) create
                .invoke(licenseController, licenseDto);

        verify(licenseService).create(licenseDto);

        assertEquals(HttpStatus.OK, licenseDtoResponseEntity.getStatusCode());
        assertEquals(createdLicenseDto, licenseDtoResponseEntity.getBody());
    }

    @Test
    @DisplayName("Создание с некорректными данными,  негативный сценарий")
    void createWithIncorrectDataNegativeTest() throws NoSuchMethodException {
        LicenseDto licenseDto = null;

        Method create = LicenseController.class.getDeclaredMethod("create", LicenseDto.class);
        create.setAccessible(true);

        when(licenseService.create(null)).thenThrow(new IllegalArgumentException());

        assertThrows(IllegalArgumentException.class, () -> {
            try {
                create.invoke(licenseController, licenseDto);
            } catch (InvocationTargetException e) {
                throw e.getCause();
            }
        });
    }

    @Test
    @DisplayName("обновление, позитивный сценарий")
    void updatePositiveTest() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Long id = 1L;
        LicenseDto licenseDto = new LicenseDto();
        LicenseDto updatedLicenseDto = new LicenseDto();
        licenseDto.setId(id);
        updatedLicenseDto.setId(id);

        Method update = LicenseController.class.getDeclaredMethod("update", Long.class, LicenseDto.class);
        update.setAccessible(true);

        when(licenseService.update(id, licenseDto)).thenReturn(updatedLicenseDto);

        ResponseEntity<?> licenseDtoResponseEntity = (ResponseEntity<LicenseDto>) update
                .invoke(licenseController, id, licenseDto);

        verify(licenseService).update(id, licenseDto);

        assertEquals(HttpStatus.OK, licenseDtoResponseEntity.getStatusCode());
        assertEquals(updatedLicenseDto, licenseDtoResponseEntity.getBody());
    }

    @Test
    @DisplayName("обновление по id, равному null, негативный сценарий")
    void updateByIdNullNegativeTest() throws NoSuchMethodException {
        Long id = null;
        LicenseDto licenseDto = null;

        Method update = LicenseController.class.getDeclaredMethod("update", Long.class, LicenseDto.class);
        update.setAccessible(true);

        when(licenseService.update(id, licenseDto)).thenThrow(new IllegalArgumentException());

        assertThrows(IllegalArgumentException.class, () -> {
            try {
                update.invoke(licenseController, id, licenseDto);
            } catch (InvocationTargetException e) {
                throw e.getCause();
            }
        });
    }
}