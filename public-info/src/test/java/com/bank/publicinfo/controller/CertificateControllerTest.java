package com.bank.publicinfo.controller;

import com.bank.publicinfo.dto.CertificateDto;
import com.bank.publicinfo.service.CertificateService;
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
 * Тесты {@link CertificateController}
 */
@ExtendWith(MockitoExtension.class)
class CertificateControllerTest {

    @Mock
    private CertificateService certificateService;

    @InjectMocks
    private CertificateController certificateController;

    @BeforeEach
    void setUp() {
        certificateController = new CertificateController(certificateService);
    }

    @Test
    @DisplayName("чтение по id, позитивный сценарий")
    void readByIdPositiveTest() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Long id = 1L;
        CertificateDto certificateDto = new CertificateDto();

        Method readById = CertificateController.class.getDeclaredMethod("readById", Long.class);
        readById.setAccessible(true);

        when(certificateService.findById(id)).thenReturn(certificateDto);

        ResponseEntity<?> certificateDtoResponseEntity = (ResponseEntity<CertificateDto>) readById
                .invoke(certificateController, id);

        assertEquals(HttpStatus.OK, certificateDtoResponseEntity.getStatusCode());
        assertEquals(certificateDto, certificateDtoResponseEntity.getBody());
    }

    @Test
    @DisplayName("чтение по несуществующему id, негативный сценарий")
    void readByNonExistIdNegativeTest() throws NoSuchMethodException {
        Long id = 1L;

        Method readById = CertificateController.class.getDeclaredMethod("readById", Long.class);
        readById.setAccessible(true);

        when(certificateService.findById(id)).thenThrow(new EntityNotFoundException());

        assertThrows(EntityNotFoundException.class, () -> {
            try {
                readById.invoke(certificateController, id);
            } catch (InvocationTargetException e) {
                throw e.getCause();
            }
        });
    }

    @Test
    @DisplayName("чтение по нескольким id, позитивный сценарий")
    void readAllByIdPositiveTest() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        List<Long> ids = List.of(1L, 2L, 3L);
        List<CertificateDto> certificateDtoList = new ArrayList<>();

        Method readAllById = CertificateController.class.getDeclaredMethod("readAllById", List.class);
        readAllById.setAccessible(true);

        when(certificateService.findAllById(ids)).thenReturn(certificateDtoList);

        ResponseEntity<?> certificateDtoResponseEntity = (ResponseEntity<List<CertificateDto>>) readAllById
                .invoke(certificateController, ids);

        assertEquals(HttpStatus.OK, certificateDtoResponseEntity.getStatusCode());
        assertEquals(certificateDtoList, certificateDtoResponseEntity.getBody());
    }

    @Test
    @DisplayName("чтение по нескольким несуществующим id, негативный сценарий")
    void readAllByNonExistIdNegativeTest() throws NoSuchMethodException {
        List<Long> ids = List.of(1L, 2L, 3L);

        Method readAllById = CertificateController.class.getDeclaredMethod("readAllById", List.class);
        readAllById.setAccessible(true);

        when(certificateService.findAllById(ids)).thenThrow(new EntityNotFoundException());

        assertThrows(EntityNotFoundException.class, () -> {
            try {
                readAllById.invoke(certificateController, ids);
            } catch (InvocationTargetException e) {
                throw e.getCause();
            }
        });
    }

    @Test
    @DisplayName("создание, позитивный сценарий")
    void createPositiveTest() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        CertificateDto certificateDto = new CertificateDto();
        CertificateDto createdCertificateDto = new CertificateDto();

        Method create = CertificateController.class.getDeclaredMethod("create", CertificateDto.class);
        create.setAccessible(true);

        when(certificateService.create(certificateDto)).thenReturn(createdCertificateDto);

        ResponseEntity<?> certificateDtoResponseEntity = (ResponseEntity<CertificateDto>) create
                .invoke(certificateController, certificateDto);

        verify(certificateService).create(certificateDto);

        assertEquals(HttpStatus.OK, certificateDtoResponseEntity.getStatusCode());
        assertEquals(createdCertificateDto, certificateDtoResponseEntity.getBody());
    }

    @Test
    @DisplayName("Создание с некорректными данными,  негативный сценарий")
    void createWithIncorrectDataNegativeTest() throws NoSuchMethodException {
        CertificateDto certificateDto = null;

        Method create = CertificateController.class.getDeclaredMethod("create", CertificateDto.class);
        create.setAccessible(true);

        when(certificateService.create(null)).thenThrow(new IllegalArgumentException());

        assertThrows(IllegalArgumentException.class, () -> {
            try {
                create.invoke(certificateController, certificateDto);
            } catch (InvocationTargetException e) {
                throw e.getCause();
            }
        });
    }

    @Test
    @DisplayName("обновление, позитивный сценарий")
    void updatePositiveTest() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Long id = 1L;
        CertificateDto certificateDto = new CertificateDto();
        CertificateDto updatedCertificateDto = new CertificateDto();
        certificateDto.setId(id);
        updatedCertificateDto.setId(id);

        Method update = CertificateController.class.getDeclaredMethod("update", Long.class, CertificateDto.class);
        update.setAccessible(true);

        when(certificateService.update(id, certificateDto)).thenReturn(updatedCertificateDto);

        ResponseEntity<?> certificateDtoResponseEntity = (ResponseEntity<CertificateDto>) update
                .invoke(certificateController, id, certificateDto);

        verify(certificateService).update(id, certificateDto);

        assertEquals(HttpStatus.OK, certificateDtoResponseEntity.getStatusCode());
        assertEquals(updatedCertificateDto, certificateDtoResponseEntity.getBody());
    }

    @Test
    @DisplayName("обновление по id, равному null, негативный сценарий")
    void updateByIdNullNegativeTest() throws NoSuchMethodException {
        Long id = null;
        CertificateDto certificateDto = null;

        Method update = CertificateController.class.getDeclaredMethod("update", Long.class, CertificateDto.class);
        update.setAccessible(true);

        when(certificateService.update(id, certificateDto)).thenThrow(new IllegalArgumentException());

        assertThrows(IllegalArgumentException.class, () -> {
            try {
                update.invoke(certificateController, id, certificateDto);
            } catch (InvocationTargetException e) {
                throw e.getCause();
            }
        });
    }
}