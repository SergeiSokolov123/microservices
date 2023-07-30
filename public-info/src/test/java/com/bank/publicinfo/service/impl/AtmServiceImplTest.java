package com.bank.publicinfo.service.impl;

import com.bank.publicinfo.dto.AtmDto;
import com.bank.publicinfo.dto.BranchDto;
import com.bank.publicinfo.entity.AtmEntity;
import com.bank.publicinfo.entity.BranchEntity;
import com.bank.publicinfo.mapper.AtmMapper;
import com.bank.publicinfo.repository.AtmRepository;
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
 * Тесты {@link AtmServiceImpl}
 */
@ExtendWith(MockitoExtension.class)
class AtmServiceImplTest {

    @Mock
    AtmRepository atmRepository;

    @Mock
    AtmMapper atmMapper;

    @Mock
    EntityNotFoundSupplier entityNotFoundSupplier;

    @InjectMocks
    AtmServiceImpl atmService;

    @BeforeEach
    void setUp() {
        atmService = new AtmServiceImpl(atmRepository, atmMapper, entityNotFoundSupplier);
    }

    @Test
    @DisplayName("поиск по нескольким id, позитивный сценарий")
    void findAllByIdPositiveTest() {
        Long id1 = 1L;
        Long id2 = 2L;

        AtmEntity atmEntity1 = new AtmEntity();
        AtmEntity atmEntity2 = new AtmEntity();
        atmEntity1.setId(id1);
        atmEntity2.setId(id2);

        AtmDto atmDto1 = new AtmDto();
        AtmDto atmDto2 = new AtmDto();
        atmDto1.setId(id1);
        atmDto2.setId(id2);

        List<Long> ids = Arrays.asList(id1, id2);
        List<AtmEntity> atmEntityList = Arrays.asList(atmEntity1, atmEntity2);
        List<AtmDto> atmDtoList = Arrays.asList(atmDto1, atmDto2);

        when(atmRepository.findAllById(ids)).thenReturn(atmEntityList);
        when(atmMapper.toDtoList(atmEntityList)).thenReturn(atmDtoList);

        List<AtmDto> result = atmService.findAllById(ids);

        assertNotNull(result);
        assertEquals(atmDtoList, result);
    }

    @Test
    @DisplayName("поиск по нескольким несуществующим id, негативный сценарий")
    void findAllByNonExistIdNegativeTest() {
        Long id1 = 1L;
        Long id2 = 2L;
        List<Long> ids = new ArrayList<>();
        ids.add(id1);
        ids.add(id2);

        when(atmRepository.findAllById(ids)).thenThrow(new EntityNotFoundException());

        assertThrows(EntityNotFoundException.class, () -> atmService.findAllById(ids));
    }

    @Test
    @DisplayName("создание, позитивный сценарий")
    void createPositiveTest() {
        AtmEntity atmEntity = new AtmEntity(
                1L,
                "Address",
                LocalTime.of(9, 0),
                LocalTime.of(17, 0),
                true,
                new BranchEntity());
        AtmDto atmDto = new AtmDto(
                1L,
                "Address",
                LocalTime.of(9, 0),
                LocalTime.of(17, 0),
                true,
                new BranchDto());

        when(atmMapper.toEntity(atmDto)).thenReturn(atmEntity);
        when(atmRepository.save(atmEntity)).thenReturn(atmEntity);
        when(atmMapper.toDto(atmEntity)).thenReturn(atmDto);

        AtmDto result = atmService.create(atmDto);

        assertNotNull(result);
        assertEquals(atmDto.getId(), result.getId());
    }

    @Test
    @DisplayName("создание, негативный сценарий")
    void createNegativeTest() {
        AtmEntity atmEntity = new AtmEntity(
                1L,
                "Address",
                LocalTime.of(9, 0),
                LocalTime.of(17, 0),
                true,
                new BranchEntity());
        AtmDto atmDto = new AtmDto(
                1L,
                "Address",
                LocalTime.of(9, 0),
                LocalTime.of(17, 0),
                true,
                new BranchDto());

        when(atmMapper.toEntity(atmDto)).thenReturn(atmEntity);
        when(atmRepository.save(atmEntity)).thenThrow(new RuntimeException());

        assertThrows(RuntimeException.class, () -> atmService.create(atmDto));
    }

    @Test
    @DisplayName("обновление, позитивный сценарий")
    void updatePositiveTest() {
        Long id = 1L;
        AtmEntity atmEntity = new AtmEntity(
                id,
                "Address",
                LocalTime.of(9, 0),
                LocalTime.of(17, 0),
                true,
                new BranchEntity());
        AtmDto atmDto = new AtmDto(
                id,
                "Address",
                LocalTime.of(9, 0),
                LocalTime.of(17, 0),
                true,
                new BranchDto());

        when(atmRepository.findById(id)).thenReturn(Optional.of(atmEntity));
        when(atmMapper.mergeToEntity(atmDto, atmEntity)).thenReturn(atmEntity);
        when(atmMapper.toDto(atmEntity)).thenReturn(atmDto);

        AtmDto result = atmService.update(id, atmDto);

        assertNotNull(result);
        assertEquals(atmDto, result);
    }

    @Test
    @DisplayName("обновление по несуществующему id, негативный сценарий")
    void updateByNoNExistIdNegativeTest() {
        Long id = 1L;
        AtmDto atmDto = new AtmDto(
                id,
                "Address",
                LocalTime.of(9, 0),
                LocalTime.of(17, 0),
                true,
                new BranchDto());

        when(atmRepository.findById(id)).thenThrow(new EntityNotFoundException());

        assertThrows(EntityNotFoundException.class, () -> atmService.update(id, atmDto));
    }

    @Test
    @DisplayName("поиск по id, позитивный сценарий")
    void findByIdPositiveTest() {
        Long id = 1L;
        AtmEntity atmEntity = new AtmEntity();
        AtmDto atmDto = new AtmDto();
        atmEntity.setId(id);
        atmDto.setId(id);

        when(atmRepository.findById(id)).thenReturn(Optional.of(atmEntity));
        when(atmMapper.toDto(atmEntity)).thenReturn(atmDto);

        AtmDto result = atmService.findById(id);

        assertNotNull(result);
        assertEquals(atmDto, result);
    }

    @Test
    @DisplayName("поиск по несуществующему id, негативный сценарий")
    void findByNoNExistIdNegativeTest() {
        Long id = 1L;

        when(atmRepository.findById(id)).thenThrow(new EntityNotFoundException());

        assertThrows(EntityNotFoundException.class, () -> atmService.findById(id));
    }
}