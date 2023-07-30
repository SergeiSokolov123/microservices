package com.bank.publicinfo.service.impl;

import com.bank.publicinfo.dto.BankDetailsDto;
import com.bank.publicinfo.dto.CertificateDto;
import com.bank.publicinfo.entity.BankDetailsEntity;
import com.bank.publicinfo.entity.CertificateEntity;
import com.bank.publicinfo.mapper.CertificateMapper;
import com.bank.publicinfo.repository.CertificateRepository;
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
 * Тесты {@link CertificateServiceImpl}
 */
@ExtendWith(MockitoExtension.class)
class CertificateServiceImplTest {

    @Mock
    CertificateRepository certificateRepository;

    @Mock
    CertificateMapper certificateMapper;

    @Mock
    EntityNotFoundSupplier entityNotFoundSupplier;

    @InjectMocks
    CertificateServiceImpl certificateService;

    @BeforeEach
    void setUp() {
        certificateService = new CertificateServiceImpl(
                certificateRepository,
                certificateMapper,
                entityNotFoundSupplier);
    }

    @Test
    @DisplayName("поиск по нескольким id, позитивный сценарий")
    void findAllByIdPositiveTest() {
        Long id1 = 1L;
        Long id2 = 2L;

        CertificateEntity certificateEntity1 = new CertificateEntity();
        CertificateEntity certificateEntity2 = new CertificateEntity();
        certificateEntity1.setId(id1);
        certificateEntity2.setId(id2);

        CertificateDto certificateDto1 = new CertificateDto();
        CertificateDto certificateDto2 = new CertificateDto();
        certificateDto1.setId(id1);
        certificateDto2.setId(id2);

        List<Long> ids = Arrays.asList(id1, id2);
        List<CertificateEntity> certificateEntityList = Arrays.asList(certificateEntity1, certificateEntity2);
        List<CertificateDto> certificateDtoList = Arrays.asList(certificateDto1, certificateDto2);

        when(certificateRepository.findAllById(ids)).thenReturn(certificateEntityList);
        when(certificateMapper.toDtoList(certificateEntityList)).thenReturn(certificateDtoList);

        List<CertificateDto> result = certificateService.findAllById(ids);

        assertNotNull(result);
        assertEquals(certificateDtoList, result);
    }

    @Test
    @DisplayName("поиск по нескольким несуществующим id, негативный сценарий")
    void findAllByNonExistIdNegativeTest() {
        Long id1 = 1L;
        Long id2 = 2L;
        List<Long> ids = new ArrayList<>();
        ids.add(id1);
        ids.add(id2);

        when(certificateRepository.findAllById(ids)).thenThrow(new EntityNotFoundException());

        assertThrows(EntityNotFoundException.class, () -> certificateService.findAllById(ids));
    }

    @Test
    @DisplayName("создание, позитивный сценарий")
    void createPositiveTest() {
        CertificateEntity certificateEntity = new CertificateEntity(
                1L,
                new Byte[] {1, 2, 3},
                new BankDetailsEntity());
        CertificateDto certificateDto = new CertificateDto(
                1L,
                new Byte[] {1, 2, 3},
                new BankDetailsDto());

        when(certificateMapper.toEntity(certificateDto)).thenReturn(certificateEntity);
        when(certificateRepository.save(certificateEntity)).thenReturn(certificateEntity);
        when(certificateMapper.toDto(certificateEntity)).thenReturn(certificateDto);

        CertificateDto result = certificateService.create(certificateDto);

        assertNotNull(result);
        assertEquals(certificateDto.getId(), result.getId());
    }

    @Test
    @DisplayName("создание, негативный сценарий")
    void createNegativeTest() {
        CertificateEntity certificateEntity = new CertificateEntity(
                1L,
                new Byte[] {1, 2, 3},
                new BankDetailsEntity());
        CertificateDto certificateDto = new CertificateDto(
                1L,
                new Byte[] {1, 2, 3},
                new BankDetailsDto());

        when(certificateMapper.toEntity(certificateDto)).thenReturn(certificateEntity);
        when(certificateRepository.save(certificateEntity)).thenThrow(new RuntimeException());

        assertThrows(RuntimeException.class, () -> certificateService.create(certificateDto));
    }

    @Test
    @DisplayName("обновление, позитивный сценарий")
    void updatePositiveTest() {
        Long id = 1L;
        CertificateEntity certificateEntity = new CertificateEntity(
                id,
                new Byte[] {1, 2, 3},
                new BankDetailsEntity());
        CertificateDto certificateDto = new CertificateDto(
                id,
                new Byte[] {1, 2, 3},
                new BankDetailsDto());

        when(certificateRepository.findById(id)).thenReturn(Optional.of(certificateEntity));
        when(certificateMapper.mergeToEntity(certificateDto, certificateEntity)).thenReturn(certificateEntity);
        when(certificateMapper.toDto(certificateEntity)).thenReturn(certificateDto);

        CertificateDto result = certificateService.update(id, certificateDto);

        assertNotNull(result);
        assertEquals(certificateDto, result);
    }

    @Test
    @DisplayName("обновление по несуществующему id, негативный сценарий")
    void updateByNoNExistIdNegativeTest() {
        Long id = 1L;
        CertificateDto certificateDto = new CertificateDto(
                id,
                new Byte[] {1, 2, 3},
                new BankDetailsDto());

        when(certificateRepository.findById(id)).thenThrow(new EntityNotFoundException());

        assertThrows(EntityNotFoundException.class, () -> certificateService.update(id, certificateDto));
    }

    @Test
    @DisplayName("поиск по id, позитивный сценарий")
    void findByIdPositiveTest() {
        Long id = 1L;
        CertificateEntity certificateEntity = new CertificateEntity();
        CertificateDto certificateDto = new CertificateDto();
        certificateEntity.setId(id);
        certificateDto.setId(id);

        when(certificateRepository.findById(id)).thenReturn(Optional.of(certificateEntity));
        when(certificateMapper.toDto(certificateEntity)).thenReturn(certificateDto);

        CertificateDto result = certificateService.findById(id);

        assertNotNull(result);
        assertEquals(certificateDto, result);
    }

    @Test
    @DisplayName("поиск по несуществующему id, негативный сценарий")
    void findByNoNExistIdNegativeTest() {
        Long id = 1L;

        when(certificateRepository.findById(id)).thenThrow(new EntityNotFoundException());

        assertThrows(EntityNotFoundException.class, () -> certificateService.findById(id));
    }
}