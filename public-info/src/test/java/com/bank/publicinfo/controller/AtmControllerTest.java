package com.bank.publicinfo.controller;

import com.bank.publicinfo.dto.AtmDto;
import com.bank.publicinfo.service.AtmService;
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
 * Тесты {@link AtmController}
 */
@ExtendWith(MockitoExtension.class)
class AtmControllerTest {

    @Mock
    private AtmService atmService;

    @InjectMocks
    private AtmController atmController;

    @BeforeEach
    void setUp() {
        atmController = new AtmController(atmService);
    }

    @Test
    @DisplayName("чтение по id, позитивный сценарий")
    void readByIdPositiveTest() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Long id = 1L;
        AtmDto atmDto = new AtmDto();

        Method readById = AtmController.class.getDeclaredMethod("readById", Long.class);
        readById.setAccessible(true);

        when(atmService.findById(id)).thenReturn(atmDto);

        ResponseEntity<?> atmDtoResponseEntity = (ResponseEntity<AtmDto>) readById.invoke(atmController, id);

        assertEquals(HttpStatus.OK, atmDtoResponseEntity.getStatusCode());
        assertEquals(atmDto, atmDtoResponseEntity.getBody());
    }

    @Test
    @DisplayName("чтение по несуществующему id, негативный сценарий")
    void readByNonExistIdNegativeTest() throws NoSuchMethodException {
        Long id = 1L;

        Method readById = AtmController.class.getDeclaredMethod("readById", Long.class);
        readById.setAccessible(true);

        when(atmService.findById(id)).thenThrow(new EntityNotFoundException());

        assertThrows(EntityNotFoundException.class, () -> {
            try {
                readById.invoke(atmController, id);
            } catch (InvocationTargetException e) {
                throw e.getCause();
            }
        });
    }

    @Test
    @DisplayName("чтение по нескольким id, позитивный сценарий")
    void readAllByIdPositiveTest() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        List<Long> ids = List.of(1L, 2L, 3L);
        List<AtmDto> atmDtoList = new ArrayList<>();

        Method readAllById = AtmController.class.getDeclaredMethod("readAllById", List.class);
        readAllById.setAccessible(true);

        when(atmService.findAllById(ids)).thenReturn(atmDtoList);

        ResponseEntity<?> atmDtoResponseEntity = (ResponseEntity<List<AtmDto>>) readAllById.invoke(atmController, ids);

        assertEquals(HttpStatus.OK, atmDtoResponseEntity.getStatusCode());
        assertEquals(atmDtoList, atmDtoResponseEntity.getBody());
    }

    @Test
    @DisplayName("чтение по нескольким несуществующим id, негативный сценарий")
    void readAllByNonExistIdNegativeTest() throws NoSuchMethodException {
        List<Long> ids = List.of(1L, 2L, 3L);

        Method readAllById = AtmController.class.getDeclaredMethod("readAllById", List.class);
        readAllById.setAccessible(true);

        when(atmService.findAllById(ids)).thenThrow(new EntityNotFoundException());

        assertThrows(EntityNotFoundException.class, () -> {
            try {
                readAllById.invoke(atmController, ids);
            } catch (InvocationTargetException e) {
                throw e.getCause();
            }
        });
    }

    @Test
    @DisplayName("создание, позитивный сценарий")
    void createPositiveTest() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        AtmDto atmDto = new AtmDto();
        AtmDto createdAtmDto = new AtmDto();

        Method create = AtmController.class.getDeclaredMethod("create", AtmDto.class);
        create.setAccessible(true);

        when(atmService.create(atmDto)).thenReturn(createdAtmDto);

        ResponseEntity<?> atmDtoResponseEntity = (ResponseEntity<AtmDto>) create.invoke(atmController, atmDto);

        verify(atmService).create(atmDto);

        assertEquals(HttpStatus.OK, atmDtoResponseEntity.getStatusCode());
        assertEquals(createdAtmDto, atmDtoResponseEntity.getBody());
    }

    @Test
    @DisplayName("Создание с некорректными данными,  негативный сценарий")
    void createWithIncorrectDataNegativeTest() throws NoSuchMethodException {
        AtmDto atmDto = null;

        Method create = AtmController.class.getDeclaredMethod("create", AtmDto.class);
        create.setAccessible(true);

        when(atmService.create(null)).thenThrow(new IllegalArgumentException());

        assertThrows(IllegalArgumentException.class, () -> {
            try {
                create.invoke(atmController, atmDto);
            } catch (InvocationTargetException e) {
                throw e.getCause();
            }
        });
    }

    @Test
    @DisplayName("обновление, позитивный сценарий")
    void updatePositiveTest() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Long id = 1L;
        AtmDto atmDto = new AtmDto();
        AtmDto updatedAtmDto = new AtmDto();
        atmDto.setId(id);
        updatedAtmDto.setId(id);

        Method update = AtmController.class.getDeclaredMethod("update", Long.class, AtmDto.class);
        update.setAccessible(true);

        when(atmService.update(id, atmDto)).thenReturn(updatedAtmDto);

        ResponseEntity<?> atmDtoResponseEntity = (ResponseEntity<AtmDto>) update.invoke(atmController, id, atmDto);

        verify(atmService).update(id, atmDto);

        assertEquals(HttpStatus.OK, atmDtoResponseEntity.getStatusCode());
        assertEquals(updatedAtmDto, atmDtoResponseEntity.getBody());
    }

    @Test
    @DisplayName("обновление по id, равному null, негативный сценарий")
    void updateByIdNullNegativeTest() throws NoSuchMethodException {
        Long id = null;
        AtmDto atmDto = null;

        Method update = AtmController.class.getDeclaredMethod("update", Long.class, AtmDto.class);
        update.setAccessible(true);

        when(atmService.update(id, atmDto)).thenThrow(new IllegalArgumentException());

        assertThrows(IllegalArgumentException.class, () -> {
            try {
                update.invoke(atmController, id, atmDto);
            } catch (InvocationTargetException e) {
                throw e.getCause();
            }
        });
    }
}