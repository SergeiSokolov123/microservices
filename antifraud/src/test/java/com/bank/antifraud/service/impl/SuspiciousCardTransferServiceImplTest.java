package com.bank.antifraud.service.impl;

import com.bank.antifraud.dto.SuspiciousCardTransferDto;
import com.bank.antifraud.entity.SuspiciousCardTransferEntity;
import com.bank.antifraud.mappers.SuspiciousCardTransferMapper;
import com.bank.antifraud.repository.SuspiciousCardTransferRepository;
import com.bank.antifraud.service.common.ExceptionReturner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityNotFoundException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.when;

/**
 * Тесты {@link SuspiciousCardTransferServiceImpl}
 */
@ExtendWith(MockitoExtension.class)
public class SuspiciousCardTransferServiceImplTest {

    private static final String MESSAGE = "SuspiciousCardTransfer по данному id не существует";
    @Mock
    private SuspiciousCardTransferMapper mapper;

    @Mock
    private SuspiciousCardTransferRepository repository;

    @Mock
    private ExceptionReturner returner;

    @InjectMocks
    private SuspiciousCardTransferServiceImpl service;

    @BeforeEach
    public void setup() {
        service = new SuspiciousCardTransferServiceImpl(repository, mapper, returner);
    }

    @Test
    @DisplayName("Сохранение, позитивный сценарий")
    public void savePositiveTest() {

        SuspiciousCardTransferDto dto = new SuspiciousCardTransferDto(
                1L,
                2L,
                false,
                false,
                "blocked_reason",
                "suspicious_reason");
        SuspiciousCardTransferEntity entity = new SuspiciousCardTransferEntity(
                1L,
                2L,
                false,
                false,
                "blocked_reason",
                "suspicious_reason");


        when(mapper.toEntity(dto)).thenReturn(entity);
        when(repository.save(entity)).thenReturn(entity);
        when(mapper.toDto(entity)).thenReturn(dto);

        SuspiciousCardTransferDto result = service.save(dto);

        assertThat(result).isNotNull();
        assertThat(dto.getId()).isEqualTo(result.getId());
    }

    @Test
    @DisplayName("Сохранение, негативный сценарий")
    public void saveNegativeTest() {

        SuspiciousCardTransferDto dto = new SuspiciousCardTransferDto(
                1L,
                2L,
                false,
                false,
                "blocked_reason",
                "suspicious_reason");
        SuspiciousCardTransferEntity entity = new SuspiciousCardTransferEntity(
                1L,
                2L,
                false,
                false,
                "blocked_reason",
                "suspicious_reason");

        when(mapper.toEntity(dto)).thenReturn(entity);
        when(repository.save(entity)).thenThrow(new RuntimeException());

        assertThatExceptionOfType(RuntimeException.class)
                .isThrownBy(() -> service.save(dto));
    }

    @Test
    @DisplayName("Поиск по id, позитивный сценарий")
    public void findByIdPositiveTest() {

        Long id = 1L;
        SuspiciousCardTransferEntity entity = new SuspiciousCardTransferEntity();
        SuspiciousCardTransferDto dto = new SuspiciousCardTransferDto();
        entity.setId(id);
        dto.setId(id);

        when(repository.findById(id)).thenReturn(Optional.of(entity));
        when(mapper.toDto(entity)).thenReturn(dto);

        SuspiciousCardTransferDto result =  service.findById(id);

        assertThat(result).isNotNull();
        assertThat(dto).isEqualTo(result);
    }

    @Test
    @DisplayName("Поиск по несуществующему id, негативный сценарий")
    public void findByNonExistIdNegativeTest() {

        Long id = 1L;

        when(repository.findById(id)).thenReturn(Optional.empty());
        when(returner.getEntityNotFoundException(MESSAGE))
                .thenReturn(new EntityNotFoundException());

        assertThatExceptionOfType(EntityNotFoundException.class)
                .isThrownBy(() -> service.findById(id));
    }

    @Test
    @DisplayName("Обновление по id, позитивный сценарий")
    public void updateByIdPositiveTest() {

        Long id = 1L;
        SuspiciousCardTransferEntity entity = new SuspiciousCardTransferEntity(
                1L,
                2L,
                false,
                false,
                "blocked_reason",
                "suspicious_reason");

        SuspiciousCardTransferDto dto = new SuspiciousCardTransferDto(
                1L,
                2L,
                false,
                false,
                "blocked_reason",
                "suspicious_reason");

        when(repository.findById(id)).thenReturn(Optional.of(entity));
        when(repository.save(entity)).thenReturn(entity);
        when(mapper.mergeToEntity(dto, entity)).thenReturn(entity);
        when(mapper.toDto(entity)).thenReturn(dto);

        SuspiciousCardTransferDto result = service.update(id, dto);

        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(dto);
    }

    @Test
    @DisplayName("Обновление по несуществующему id, негативный сценарий")
    public void updateByNonExistIdNegativeTest() {

        Long id = 1L;
        SuspiciousCardTransferDto dto = new SuspiciousCardTransferDto(
                id,
                2L,
                false,
                false,
                "blocked_reason",
                "suspicious_reason");

        when(repository.findById(id)).thenReturn(Optional.empty());
        when(returner.getEntityNotFoundException(MESSAGE))
                .thenReturn(new EntityNotFoundException());

        assertThatExceptionOfType(EntityNotFoundException.class)
                .isThrownBy(() -> service.update(id, dto));
    }

    @Test
    @DisplayName("Поиск по нескольким id, позитивный сценарий")
    public void findAllByIdPositiveTest() {

        Long id1 = 1L;
        Long id2 = 2L;

        List<Long> ids = Arrays.asList(id1, id2);

        SuspiciousCardTransferDto dto1 = new SuspiciousCardTransferDto();
        dto1.setId(id1);
        SuspiciousCardTransferDto dto2 = new SuspiciousCardTransferDto();
        dto2.setId(id2);
        SuspiciousCardTransferEntity entity1 = new SuspiciousCardTransferEntity();
        entity1.setId(id1);
        SuspiciousCardTransferEntity entity2 = new SuspiciousCardTransferEntity();
        entity2.setId(id2);

        List<SuspiciousCardTransferEntity> listEntity = Arrays.asList(entity1, entity2);
        List<SuspiciousCardTransferDto> listDto = Arrays.asList(dto1, dto2);

        when(repository.findById(id1)).thenReturn(Optional.of(entity1));
        when(repository.findById(id2)).thenReturn(Optional.of(entity2));
        when(mapper.toListDto(listEntity)).thenReturn(listDto);

        List<SuspiciousCardTransferDto> result = service.findAllById(ids);

        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(listDto);
    }

    @Test
    @DisplayName("Поиск по нескольким несуществующим id, негативный сценарий")
    public void findAllByNonExistIdsNegativeTest() {

        Long id1 = 1L;
        Long id2 = 2L;
        List<Long> ids = Arrays.asList(id1, id2);

        when(repository.findById(id1)).thenReturn(Optional.empty());
        when(returner.getEntityNotFoundException(MESSAGE))
                .thenReturn(new EntityNotFoundException());

        assertThatExceptionOfType(EntityNotFoundException.class)
                .isThrownBy(() -> service.findAllById(ids));
    }
}
