package com.bank.publicinfo.controller;

import com.bank.publicinfo.dto.BranchDto;
import com.bank.publicinfo.service.BranchService;
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
 * Тесты {@link BranchController}
 */
@ExtendWith(MockitoExtension.class)
class BranchControllerTest {

    @Mock
    private BranchService branchService;

    @InjectMocks
    private BranchController branchController;

    @BeforeEach
    void setUp() {
        branchController = new BranchController(branchService);
    }

    @Test
    @DisplayName("чтение по id, позитивный сценарий")
    void readByIdPositiveTest() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Long id = 1L;
        BranchDto branchDto = new BranchDto();

        Method readById = BranchController.class.getDeclaredMethod("readById", Long.class);
        readById.setAccessible(true);

        when(branchService.findById(id)).thenReturn(branchDto);

        ResponseEntity<?> branchDtoResponseEntity = (ResponseEntity<BranchDto>) readById.invoke(branchController, id);

        assertEquals(HttpStatus.OK, branchDtoResponseEntity.getStatusCode());
        assertEquals(branchDto, branchDtoResponseEntity.getBody());
    }

    @Test
    @DisplayName("чтение по несуществующему id, негативный сценарий")
    void readByNonExistIdNegativeTest() throws NoSuchMethodException {
        Long id = 1L;

        Method readById = BranchController.class.getDeclaredMethod("readById", Long.class);
        readById.setAccessible(true);

        when(branchService.findById(id)).thenThrow(new EntityNotFoundException());

        assertThrows(EntityNotFoundException.class, () -> {
            try {
                readById.invoke(branchController, id);
            } catch (InvocationTargetException e) {
                throw e.getCause();
            }
        });
    }

    @Test
    @DisplayName("чтение по нескольким id, позитивный сценарий")
    void readAllByIdPositiveTest() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        List<Long> ids = List.of(1L, 2L, 3L);
        List<BranchDto> branchDtoList = new ArrayList<>();

        Method readAllById = BranchController.class.getDeclaredMethod("readAllById", List.class);
        readAllById.setAccessible(true);

        when(branchService.findAllById(ids)).thenReturn(branchDtoList);

        ResponseEntity<?> branchDtoResponseEntity = (ResponseEntity<List<BranchDto>>) readAllById
                .invoke(branchController, ids);

        assertEquals(HttpStatus.OK, branchDtoResponseEntity.getStatusCode());
        assertEquals(branchDtoList, branchDtoResponseEntity.getBody());
    }

    @Test
    @DisplayName("чтение по нескольким несуществующим id, негативный сценарий")
    void readAllByNonExistIdNegativeTest() throws NoSuchMethodException {
        List<Long> ids = List.of(1L, 2L, 3L);

        Method readAllById = BranchController.class.getDeclaredMethod("readAllById", List.class);
        readAllById.setAccessible(true);

        when(branchService.findAllById(ids)).thenThrow(new EntityNotFoundException());

        assertThrows(EntityNotFoundException.class, () -> {
            try {
                readAllById.invoke(branchController, ids);
            } catch (InvocationTargetException e) {
                throw e.getCause();
            }
        });
    }

    @Test
    @DisplayName("создание, позитивный сценарий")
    void createPositiveTest() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        BranchDto branchDto = new BranchDto();
        BranchDto createdBranchDto = new BranchDto();

        Method create = BranchController.class.getDeclaredMethod("create", BranchDto.class);
        create.setAccessible(true);

        when(branchService.create(branchDto)).thenReturn(createdBranchDto);

        ResponseEntity<?> branchDtoResponseEntity = (ResponseEntity<BranchDto>) create
                .invoke(branchController, branchDto);

        verify(branchService).create(branchDto);

        assertEquals(HttpStatus.OK, branchDtoResponseEntity.getStatusCode());
        assertEquals(createdBranchDto, branchDtoResponseEntity.getBody());
    }

    @Test
    @DisplayName("Создание с некорректными данными,  негативный сценарий")
    void createWithIncorrectDataNegativeTest() throws NoSuchMethodException {
        BranchDto branchDto = null;

        Method create = BranchController.class.getDeclaredMethod("create", BranchDto.class);
        create.setAccessible(true);

        when(branchService.create(null)).thenThrow(new IllegalArgumentException());

        assertThrows(IllegalArgumentException.class, () -> {
            try {
                create.invoke(branchController, branchDto);
            } catch (InvocationTargetException e) {
                throw e.getCause();
            }
        });
    }

    @Test
    @DisplayName("обновление, позитивный сценарий")
    void updatePositiveTest() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Long id = 1L;
        BranchDto branchDto = new BranchDto();
        BranchDto updatedBranchDto = new BranchDto();
        branchDto.setId(id);
        updatedBranchDto.setId(id);

        Method update = BranchController.class.getDeclaredMethod("update", Long.class, BranchDto.class);
        update.setAccessible(true);

        when(branchService.update(id, branchDto)).thenReturn(updatedBranchDto);

        ResponseEntity<?> branchDtoResponseEntity = (ResponseEntity<BranchDto>) update
                .invoke(branchController, id, branchDto);

        verify(branchService).update(id, branchDto);

        assertEquals(HttpStatus.OK, branchDtoResponseEntity.getStatusCode());
        assertEquals(updatedBranchDto, branchDtoResponseEntity.getBody());
    }

    @Test
    @DisplayName("обновление по id, равному null, негативный сценарий")
    void updateByIdNullNegativeTest() throws NoSuchMethodException {
        Long id = null;
        BranchDto branchDto = null;

        Method update = BranchController.class.getDeclaredMethod("update", Long.class, BranchDto.class);
        update.setAccessible(true);

        when(branchService.update(id, branchDto)).thenThrow(new IllegalArgumentException());

        assertThrows(IllegalArgumentException.class, () -> {
            try {
                update.invoke(branchController, id, branchDto);
            } catch (InvocationTargetException e) {
                throw e.getCause();
            }
        });
    }
}