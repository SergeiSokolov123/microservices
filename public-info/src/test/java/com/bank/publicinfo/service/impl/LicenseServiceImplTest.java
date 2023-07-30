package com.bank.publicinfo.service.impl;

import com.bank.publicinfo.dto.BankDetailsDto;
import com.bank.publicinfo.dto.LicenseDto;
import com.bank.publicinfo.entity.BankDetailsEntity;
import com.bank.publicinfo.entity.LicenseEntity;
import com.bank.publicinfo.mapper.LicenseMapper;
import com.bank.publicinfo.repository.LicenseRepository;
import com.bank.publicinfo.util.EntityNotFoundSupplier;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

/**
 * Тесты {@link LicenseServiceImpl}
 */
@ExtendWith(MockitoExtension.class)
class LicenseServiceImplTest {

    @Mock
    LicenseRepository licenseRepository;

    @Mock
    LicenseMapper licenseMapper;

    @Mock
    EntityNotFoundSupplier entityNotFoundSupplier;

    @InjectMocks
    LicenseServiceImpl licenseService;

    @BeforeEach
    void setUp() {
        licenseService = new LicenseServiceImpl(licenseRepository, licenseMapper, entityNotFoundSupplier);
    }

    @Test
    @DisplayName("поиск по нескольким id, позитивный сценарий")
    void findAllByIdPositiveTest() {
        Long id1 = 1L;
        Long id2 = 2L;

        LicenseEntity licenseEntity1 = new LicenseEntity();
        LicenseEntity licenseEntity2 = new LicenseEntity();
        licenseEntity1.setId(id1);
        licenseEntity2.setId(id2);

        LicenseDto licenseDto1 = new LicenseDto();
        LicenseDto licenseDto2 = new LicenseDto();
        licenseDto1.setId(id1);
        licenseDto2.setId(id2);

        List<Long> ids = Arrays.asList(id1, id2);
        List<LicenseEntity> licenseEntityList = Arrays.asList(licenseEntity1, licenseEntity2);
        List<LicenseDto> licenseDtoList = Arrays.asList(licenseDto1, licenseDto2);

        when(licenseRepository.findAllById(ids)).thenReturn(licenseEntityList);
        when(licenseMapper.toDtoList(licenseEntityList)).thenReturn(licenseDtoList);

        List<LicenseDto> result = licenseService.findAllById(ids);

        assertNotNull(result);
        assertEquals(licenseDtoList, result);
    }

    @Test
    @DisplayName("поиск по нескольким несуществующим id, негативный сценарий")
    void findAllByNonExistIdNegativeTest() {
        Long id1 = 1L;
        Long id2 = 2L;
        List<Long> ids = new ArrayList<>();
        ids.add(id1);
        ids.add(id2);

        when(licenseRepository.findAllById(ids)).thenThrow(new EntityNotFoundException());

        assertThrows(EntityNotFoundException.class, () -> licenseService.findAllById(ids));
    }

    @Test
    @DisplayName("создание, позитивный сценарий")
    void createPositiveTest() {
        LicenseEntity licenseEntity = new LicenseEntity(
                1L,
                new Byte[] {1, 2, 3},
                new BankDetailsEntity());
        LicenseDto licenseDto = new LicenseDto(
                1L,
                new Byte[] {1, 2, 3},
                new BankDetailsDto());

        when(licenseMapper.toEntity(licenseDto)).thenReturn(licenseEntity);
        when(licenseRepository.save(licenseEntity)).thenReturn(licenseEntity);
        when(licenseMapper.toDto(licenseEntity)).thenReturn(licenseDto);

        LicenseDto result = licenseService.create(licenseDto);

        assertNotNull(result);
        assertEquals(licenseDto.getId(), result.getId());
    }

    @Test
    @DisplayName("создание, негативный сценарий")
    void createNegativeTest() {
        LicenseEntity licenseEntity = new LicenseEntity(
                1L,
                new Byte[] {1, 2, 3},
                new BankDetailsEntity());
        LicenseDto licenseDto = new LicenseDto(
                1L,
                new Byte[] {1, 2, 3},
                new BankDetailsDto());

        when(licenseMapper.toEntity(licenseDto)).thenReturn(licenseEntity);
        when(licenseRepository.save(licenseEntity)).thenThrow(new RuntimeException());

        assertThrows(RuntimeException.class, () -> licenseService.create(licenseDto));
    }

    @Test
    @DisplayName("обновление, позитивный сценарий")
    void updatePositiveTest() {
        Long id = 1L;
        LicenseEntity licenseEntity = new LicenseEntity(
                id,
                new Byte[] {1, 2, 3},
                new BankDetailsEntity());
        LicenseDto licenseDto = new LicenseDto(
                id,
                new Byte[] {1, 2, 3},
                new BankDetailsDto());

        when(licenseRepository.findById(id)).thenReturn(Optional.of(licenseEntity));
        when(licenseMapper.mergeToEntity(licenseDto, licenseEntity)).thenReturn(licenseEntity);
        when(licenseMapper.toDto(licenseEntity)).thenReturn(licenseDto);

        LicenseDto result = licenseService.update(id, licenseDto);

        assertNotNull(result);
        assertEquals(licenseDto, result);
    }

    @Test
    @DisplayName("обновление по несуществующему id, негативный сценарий")
    void updateByNoNExistIdNegativeTest() {
        Long id = 1L;
        LicenseDto licenseDto = new LicenseDto(
                id,
                new Byte[] {1, 2, 3},
                new BankDetailsDto());

        when(licenseRepository.findById(id)).thenThrow(new EntityNotFoundException());

        assertThrows(EntityNotFoundException.class, () -> licenseService.update(id, licenseDto));
    }

    @Test
    @DisplayName("поиск по id, позитивный сценарий")
    void findByIdPositiveTest() {
        Long id = 1L;
        LicenseEntity licenseEntity = new LicenseEntity();
        LicenseDto licenseDto = new LicenseDto();
        licenseEntity.setId(id);
        licenseDto.setId(id);

        when(licenseRepository.findById(id)).thenReturn(Optional.of(licenseEntity));
        when(licenseMapper.toDto(licenseEntity)).thenReturn(licenseDto);

        LicenseDto result = licenseService.findById(id);

        assertNotNull(result);
        assertEquals(licenseDto, result);
    }

    @Test
    @DisplayName("поиск по несуществующему id, негативный сценарий")
    void findByNoNExistIdNegativeTest() {
        Long id = 1L;

        when(licenseRepository.findById(id)).thenThrow(new EntityNotFoundException());

        assertThrows(EntityNotFoundException.class, () -> licenseService.findById(id));
    }
}