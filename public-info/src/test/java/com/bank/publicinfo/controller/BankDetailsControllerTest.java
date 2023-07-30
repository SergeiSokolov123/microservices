package com.bank.publicinfo.controller;

import com.bank.publicinfo.dto.BankDetailsDto;
import com.bank.publicinfo.service.BankDetailsService;
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
 * Тесты {@link BankDetailsController}
 */
@ExtendWith(MockitoExtension.class)
class BankDetailsControllerTest {

    @Mock
    private BankDetailsService bankDetailsService;

    @InjectMocks
    private BankDetailsController bankDetailsController;

    @BeforeEach
    void setUp() {
        bankDetailsController = new BankDetailsController(bankDetailsService);
    }

    @Test
    @DisplayName("чтение по id, позитивный сценарий")
    void readByIdPositiveTest() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Long id = 1L;
        BankDetailsDto bankDetailsDto = new BankDetailsDto();

        Method readById = BankDetailsController.class.getDeclaredMethod("readById", Long.class);
        readById.setAccessible(true);

        when(bankDetailsService.findById(id)).thenReturn(bankDetailsDto);

        ResponseEntity<?> bankDetailsDtoResponseEntity = (ResponseEntity<BankDetailsDto>) readById.invoke(bankDetailsController, id);

        assertEquals(HttpStatus.OK, bankDetailsDtoResponseEntity.getStatusCode());
        assertEquals(bankDetailsDto, bankDetailsDtoResponseEntity.getBody());
    }

    @Test
    @DisplayName("чтение по несуществующему id, негативный сценарий")
    void readByNonExistIdNegativeTest() throws NoSuchMethodException {
        Long id = 1L;

        Method readById = BankDetailsController.class.getDeclaredMethod("readById", Long.class);
        readById.setAccessible(true);

        when(bankDetailsService.findById(id)).thenThrow(new EntityNotFoundException());

        assertThrows(EntityNotFoundException.class, () -> {
            try {
                readById.invoke(bankDetailsController, id);
            } catch (InvocationTargetException e) {
                throw e.getCause();
            }
        });
    }

    @Test
    @DisplayName("чтение по нескольким id, позитивный сценарий")
    void readAllByIdPositiveTest() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        List<Long> ids = List.of(1L, 2L, 3L);
        List<BankDetailsDto> detailsDtoList = new ArrayList<>();

        Method readAllById = BankDetailsController.class.getDeclaredMethod("readAllById", List.class);
        readAllById.setAccessible(true);

        when(bankDetailsService.findAllById(ids)).thenReturn(detailsDtoList);

        ResponseEntity<?> bankDetailsDtoResponseEntity = (ResponseEntity<List<BankDetailsDto>>) readAllById.invoke(bankDetailsController, ids);

        assertEquals(HttpStatus.OK, bankDetailsDtoResponseEntity.getStatusCode());
        assertEquals(detailsDtoList, bankDetailsDtoResponseEntity.getBody());
    }

    @Test
    @DisplayName("чтение по нескольким несуществующим id, негативный сценарий")
    void readAllByNonExistIdNegativeTest() throws NoSuchMethodException {
        List<Long> ids = List.of(1L, 2L, 3L);

        Method readAllById = BankDetailsController.class.getDeclaredMethod("readAllById", List.class);
        readAllById.setAccessible(true);

        when(bankDetailsService.findAllById(ids)).thenThrow(new EntityNotFoundException());

        assertThrows(EntityNotFoundException.class, () -> {
            try {
                readAllById.invoke(bankDetailsController, ids);
            } catch (InvocationTargetException e) {
                throw e.getCause();
            }
        });
    }

    @Test
    @DisplayName("создание, позитивный сценарий")
    void createPositiveTest() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        BankDetailsDto bankDetailsDto = new BankDetailsDto();
        BankDetailsDto createdBankDetailsDto = new BankDetailsDto();

        Method create = BankDetailsController.class.getDeclaredMethod("create", BankDetailsDto.class);
        create.setAccessible(true);

        when(bankDetailsService.create(bankDetailsDto)).thenReturn(createdBankDetailsDto);
        ResponseEntity<?> bankDetailsDtoResponseEntity = (ResponseEntity<BankDetailsDto>) create
                .invoke(bankDetailsController, bankDetailsDto);

        verify(bankDetailsService).create(bankDetailsDto);

        assertEquals(HttpStatus.OK, bankDetailsDtoResponseEntity.getStatusCode());
        assertEquals(createdBankDetailsDto, bankDetailsDtoResponseEntity.getBody());
    }

    @Test
    @DisplayName("Создание с некорректными данными,  негативный сценарий")
    void createWithIncorrectDataNegativeTest() throws NoSuchMethodException {
        BankDetailsDto bankDetailsDto = null;

        Method create = BankDetailsController.class.getDeclaredMethod("create", BankDetailsDto.class);
        create.setAccessible(true);

        when(bankDetailsService.create(null)).thenThrow(new IllegalArgumentException());

        assertThrows(IllegalArgumentException.class, () -> {
            try {
                create.invoke(bankDetailsController, bankDetailsDto);
            } catch (InvocationTargetException e) {
                throw e.getCause();
            }
        });
    }

    @Test
    @DisplayName("обновление, позитивный сценарий")
    void updatePositiveTest() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Long id = 1L;
        BankDetailsDto bankDetailsDto = new BankDetailsDto();
        BankDetailsDto updatedBankDetailsDto = new BankDetailsDto();
        bankDetailsDto.setId(id);
        updatedBankDetailsDto.setId(id);

        Method update = BankDetailsController.class.getDeclaredMethod("update", Long.class, BankDetailsDto.class);
        update.setAccessible(true);

        when(bankDetailsService.update(id, bankDetailsDto)).thenReturn(updatedBankDetailsDto);
        ResponseEntity<?> bankDetailsDtoResponseEntity = (ResponseEntity<BankDetailsDto>) update
                .invoke(bankDetailsController, id, bankDetailsDto);

        verify(bankDetailsService).update(id, bankDetailsDto);

        assertEquals(HttpStatus.OK, bankDetailsDtoResponseEntity.getStatusCode());
        assertEquals(updatedBankDetailsDto, bankDetailsDtoResponseEntity.getBody());
    }

    @Test
    @DisplayName("обновление по id, равному null, негативный сценарий")
    void updateByIdNullNegativeTest() throws NoSuchMethodException {
        Long id = null;
        BankDetailsDto bankDetailsDto = null;

        Method update = BankDetailsController.class.getDeclaredMethod("update", Long.class, BankDetailsDto.class);
        update.setAccessible(true);

        when(bankDetailsService.update(id, bankDetailsDto)).thenThrow(new IllegalArgumentException());

        assertThrows(IllegalArgumentException.class, () -> {
            try {
                update.invoke(bankDetailsController, id, bankDetailsDto);
            } catch (InvocationTargetException e) {
                throw e.getCause();
            }
        });
    }
}