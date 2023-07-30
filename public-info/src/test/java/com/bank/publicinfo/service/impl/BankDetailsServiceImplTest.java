package com.bank.publicinfo.service.impl;

import com.bank.publicinfo.dto.BankDetailsDto;
import com.bank.publicinfo.entity.BankDetailsEntity;
import com.bank.publicinfo.mapper.BankDetailsMapper;
import com.bank.publicinfo.repository.BankDetailsRepository;
import com.bank.publicinfo.util.EntityNotFoundSupplier;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

/**
 * Тесты {@link BankDetailsServiceImpl}
 */
@ExtendWith(MockitoExtension.class)
class BankDetailsServiceImplTest {

    @Mock
    BankDetailsRepository bankDetailsRepository;

    @Mock
    BankDetailsMapper bankDetailsMapper;

    @Mock
    EntityNotFoundSupplier entityNotFoundSupplier;

    @InjectMocks
    BankDetailsServiceImpl bankDetailsService;

    @BeforeEach
    void setUp() {
        bankDetailsService = new BankDetailsServiceImpl(
                bankDetailsRepository,
                bankDetailsMapper,
                entityNotFoundSupplier);
    }

    @Test
    @DisplayName("поиск по нескольким id, позитивный сценарий")
    void findAllByIdPositiveTest() {
        Long id1 = 1L;
        Long id2 = 2L;

        BankDetailsEntity bankDetailsEntity1 = new BankDetailsEntity();
        BankDetailsEntity bankDetailsEntity2 = new BankDetailsEntity();
        bankDetailsEntity1.setId(id1);
        bankDetailsEntity2.setId(id2);

        BankDetailsDto bankDetailsDto1 = new BankDetailsDto();
        BankDetailsDto bankDetailsDto2 = new BankDetailsDto();
        bankDetailsDto1.setId(id1);
        bankDetailsDto2.setId(id2);

        List<Long> ids = Arrays.asList(id1, id2);
        List<BankDetailsEntity> bankDetailsEntityList = Arrays.asList(bankDetailsEntity1, bankDetailsEntity2);
        List<BankDetailsDto> bankDetailsDtoList = Arrays.asList(bankDetailsDto1, bankDetailsDto2);

        when(bankDetailsRepository.findAllById(ids)).thenReturn(bankDetailsEntityList);
        when(bankDetailsMapper.toDtoList(bankDetailsEntityList)).thenReturn(bankDetailsDtoList);

        List<BankDetailsDto> result = bankDetailsService.findAllById(ids);

        assertNotNull(result);
        assertEquals(bankDetailsDtoList, result);
    }

    @Test
    @DisplayName("поиск по нескольким несуществующим id, негативный сценарий")
    void findAllByNonExistIdNegativeTest() {
        Long id1 = 1L;
        Long id2 = 2L;
        List<Long> ids = new ArrayList<>();
        ids.add(id1);
        ids.add(id2);

        when(bankDetailsRepository.findAllById(ids)).thenThrow(new EntityNotFoundException());

        assertThrows(EntityNotFoundException.class, () -> bankDetailsService.findAllById(ids));
    }

    @Test
    @DisplayName("создание, позитивный сценарий")
    void createPositiveTest() {
        BankDetailsEntity bankDetailsEntity = new BankDetailsEntity(
                1L,
                12345L,
                999999L,
                1000L,
                BigDecimal.ONE,
                "City",
                "StockCompany",
                "Name");
        BankDetailsDto bankDetailsDto = new BankDetailsDto(
                1L,
                12345L,
                999999L,
                1000L,
                BigDecimal.ONE,
                "City",
                "StockCompany",
                "Name");

        when(bankDetailsMapper.toEntity(bankDetailsDto)).thenReturn(bankDetailsEntity);
        when(bankDetailsRepository.save(bankDetailsEntity)).thenReturn(bankDetailsEntity);
        when(bankDetailsMapper.toDto(bankDetailsEntity)).thenReturn(bankDetailsDto);

        BankDetailsDto result = bankDetailsService.create(bankDetailsDto);

        assertNotNull(result);
        assertEquals(bankDetailsDto.getId(), result.getId());
    }

    @Test
    @DisplayName("создание, негативный сценарий")
    void createNegativeTest() {
        BankDetailsEntity bankDetailsEntity = new BankDetailsEntity(
                1L,
                12345L,
                999999L,
                1000L,
                BigDecimal.ONE,
                "City",
                "StockCompany",
                "Name");
        BankDetailsDto bankDetailsDto = new BankDetailsDto(
                1L,
                12345L,
                999999L,
                1000L,
                BigDecimal.ONE,
                "City",
                "StockCompany",
                "Name");

        when(bankDetailsMapper.toEntity(bankDetailsDto)).thenReturn(bankDetailsEntity);
        when(bankDetailsRepository.save(bankDetailsEntity)).thenThrow(new RuntimeException());

        assertThrows(RuntimeException.class, () -> bankDetailsService.create(bankDetailsDto));
    }

    @Test
    @DisplayName("обновление, позитивный сценарий")
    void updatePositiveTest() {
        Long id = 1L;
        BankDetailsEntity bankDetailsEntity = new BankDetailsEntity(
                id,
                12345L,
                999999L,
                1000L,
                BigDecimal.ONE,
                "City",
                "StockCompany",
                "Name");
        BankDetailsDto bankDetailsDto = new BankDetailsDto(
                id,
                12345L,
                999999L,
                1000L,
                BigDecimal.ONE,
                "City",
                "StockCompany",
                "Name");

        when(bankDetailsRepository.findById(id)).thenReturn(Optional.of(bankDetailsEntity));
        when(bankDetailsMapper.mergeToEntity(bankDetailsDto, bankDetailsEntity)).thenReturn(bankDetailsEntity);
        when(bankDetailsMapper.toDto(bankDetailsEntity)).thenReturn(bankDetailsDto);

        BankDetailsDto result = bankDetailsService.update(id, bankDetailsDto);

        assertNotNull(result);
        assertEquals(bankDetailsDto, result);
    }

    @Test
    @DisplayName("обновление по несуществующему id, негативный сценарий")
    void updateByNoNExistIdNegativeTest() {
        Long id = 1L;
        BankDetailsDto bankDetailsDto = new BankDetailsDto(
                id,
                12345L,
                999999L,
                1000L,
                BigDecimal.ONE,
                "City",
                "StockCompany",
                "Name");

        when(bankDetailsRepository.findById(id)).thenThrow(new EntityNotFoundException());

        assertThrows(EntityNotFoundException.class, () -> bankDetailsService.update(id, bankDetailsDto));
    }

    @Test
    @DisplayName("поиск по id, позитивный сценарий")
    void findByIdPositiveTest() {
        Long id = 1L;
        BankDetailsEntity bankDetailsEntity = new BankDetailsEntity();
        BankDetailsDto bankDetailsDto = new BankDetailsDto();
        bankDetailsEntity.setId(id);
        bankDetailsDto.setId(id);

        when(bankDetailsRepository.findById(id)).thenReturn(Optional.of(bankDetailsEntity));
        when(bankDetailsMapper.toDto(bankDetailsEntity)).thenReturn(bankDetailsDto);

        BankDetailsDto result = bankDetailsService.findById(id);

        assertNotNull(result);
        assertEquals(bankDetailsDto, result);
    }

    @Test
    @DisplayName("поиск по несуществующему id, негативный сценарий")
    void findByNoNExistIdNegativeTest() {
        Long id = 1L;

        when(bankDetailsRepository.findById(id)).thenThrow(new EntityNotFoundException());

        assertThrows(EntityNotFoundException.class, () -> bankDetailsService.findById(id));
    }
}