package com.bank.publicinfo.service.impl;

import com.bank.publicinfo.dto.BranchDto;
import com.bank.publicinfo.entity.BranchEntity;
import com.bank.publicinfo.mapper.BranchMapper;
import com.bank.publicinfo.repository.BranchRepository;
import com.bank.publicinfo.util.EntityNotFoundSupplier;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityNotFoundException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

/**
 * Тесты {@link BranchServiceImpl}
 */
@ExtendWith(MockitoExtension.class)
class BranchServiceImplTest {

    @Mock
    BranchRepository branchRepository;

    @Mock
    BranchMapper branchMapper;

    @Mock
    EntityNotFoundSupplier entityNotFoundSupplier;

    @InjectMocks
    BranchServiceImpl branchService;

    @BeforeEach
    void setUp() {
        branchService = new BranchServiceImpl(branchRepository, branchMapper, entityNotFoundSupplier);
    }

    @Test
    @DisplayName("поиск по нескольким id, позитивный сценарий")
    void findAllByIdPositiveTest() {
        Long id1 = 1L;
        Long id2 = 2L;

        BranchEntity branchEntity1 = new BranchEntity();
        BranchEntity branchEntity2 = new BranchEntity();
        branchEntity1.setId(id1);
        branchEntity2.setId(id2);

        BranchDto branchDto1 = new BranchDto();
        BranchDto branchDto2 = new BranchDto();
        branchDto1.setId(id1);
        branchDto2.setId(id2);

        List<Long> ids = Arrays.asList(id1, id2);
        List<BranchEntity> branchEntityList = Arrays.asList(branchEntity1, branchEntity2);
        List<BranchDto> branchDtoList = Arrays.asList(branchDto1, branchDto2);

        when(branchRepository.findAllById(ids)).thenReturn(branchEntityList);
        when(branchMapper.toDtoList(branchEntityList)).thenReturn(branchDtoList);

        List<BranchDto> result = branchService.findAllById(ids);

        assertNotNull(result);
        assertEquals(branchDtoList, result);
    }

    @Test
    @DisplayName("поиск по нескольким несуществующим id, негативный сценарий")
    void findAllByNonExistIdNegativeTest() {
        Long id1 = 1L;
        Long id2 = 2L;
        List<Long> ids = new ArrayList<>();
        ids.add(id1);
        ids.add(id2);

        when(branchRepository.findAllById(ids)).thenThrow(new EntityNotFoundException());

        assertThrows(EntityNotFoundException.class, () -> branchService.findAllById(ids));
    }

    @Test
    @DisplayName("создание, позитивный сценарий")
    void createPositiveTest() {
        BranchEntity branchEntity = new BranchEntity(
                1L,
                "Address",
                79991234567L,
                "City",
                LocalTime.of(9, 0),
                LocalTime.of(17, 0));
        BranchDto branchDto = new BranchDto(
                1L,
                "Address",
                79991234567L,
                "City",
                LocalTime.of(9, 0),
                LocalTime.of(17, 0));

        when(branchMapper.toEntity(branchDto)).thenReturn(branchEntity);
        when(branchRepository.save(branchEntity)).thenReturn(branchEntity);
        when(branchMapper.toDto(branchEntity)).thenReturn(branchDto);

        BranchDto result = branchService.create(branchDto);

        assertNotNull(result);
        assertEquals(branchDto.getId(), result.getId());
    }

    @Test
    @DisplayName("создание, негативный сценарий")
    void createNegativeTest() {
        BranchEntity branchEntity = new BranchEntity(
                1L,
                "Address",
                79991234567L,
                "City",
                LocalTime.of(9, 0),
                LocalTime.of(17, 0));
        BranchDto branchDto = new BranchDto(
                1L,
                "Address",
                79991234567L,
                "City",
                LocalTime.of(9, 0),
                LocalTime.of(17, 0));

        when(branchMapper.toEntity(branchDto)).thenReturn(branchEntity);
        when(branchRepository.save(branchEntity)).thenThrow(new RuntimeException());

        assertThrows(RuntimeException.class, () -> branchService.create(branchDto));
    }

    @Test
    @DisplayName("обновление, позитивный сценарий")
    void updatePositiveTest() {
        Long id = 1L;
        BranchEntity branchEntity = new BranchEntity(
                id,
                "Address",
                79991234567L,
                "City",
                LocalTime.of(9, 0),
                LocalTime.of(17, 0));
        BranchDto branchDto = new BranchDto(
                id,
                "Address",
                79991234567L,
                "City",
                LocalTime.of(9, 0),
                LocalTime.of(17, 0));

        when(branchRepository.findById(id)).thenReturn(Optional.of(branchEntity));
        when(branchMapper.mergeToEntity(branchDto, branchEntity)).thenReturn(branchEntity);
        when(branchMapper.toDto(branchEntity)).thenReturn(branchDto);

        BranchDto result = branchService.update(id, branchDto);

        assertNotNull(result);
        assertEquals(branchDto, result);
    }

    @Test
    @DisplayName("обновление по несуществующему id, негативный сценарий")
    void updateByNoNExistIdNegativeTest() {
        Long id = 1L;
        BranchDto branchDto = new BranchDto(
                id,
                "Address",
                79991234567L,
                "City",
                LocalTime.of(9, 0),
                LocalTime.of(17, 0));

        when(branchRepository.findById(id)).thenThrow(new EntityNotFoundException());

        assertThrows(EntityNotFoundException.class, () -> branchService.update(id, branchDto));
    }

    @Test
    @DisplayName("поиск по id, позитивный сценарий")
    void findByIdPositiveTest() {
        Long id = 1L;
        BranchEntity branchEntity = new BranchEntity();
        BranchDto branchDto = new BranchDto();
        branchEntity.setId(id);
        branchDto.setId(id);

        when(branchRepository.findById(id)).thenReturn(Optional.of(branchEntity));
        when(branchMapper.toDto(branchEntity)).thenReturn(branchDto);

        BranchDto result = branchService.findById(id);

        assertNotNull(result);
        assertEquals(branchDto, result);
    }

    @Test
    @DisplayName("поиск по несуществующему id, негативный сценарий")
    void findByNoNExistIdNegativeTest() {
        Long id = 1L;

        when(branchRepository.findById(id)).thenThrow(new EntityNotFoundException());

        assertThrows(EntityNotFoundException.class, () -> branchService.findById(id));
    }
}