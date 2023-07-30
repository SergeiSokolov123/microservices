package com.bank.antifraud.service.impl;

import com.bank.antifraud.dto.SuspiciousAccountTransferDto;
import com.bank.antifraud.entity.SuspiciousAccountTransferEntity;
import com.bank.antifraud.mappers.SuspiciousAccountTransferMapper;
import com.bank.antifraud.repository.SuspiciousAccountTransferRepository;
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
 * Тесты {@link SuspiciousAccountTransferServiceImpl}
 */
@ExtendWith(MockitoExtension.class)
public class SuspiciousAccountTransferServiceImplTest {

    private static final String MESSAGE = "SuspiciousAccountTransfer по данному id не существует";
    @Mock
    private SuspiciousAccountTransferMapper mapper;

    @Mock
    private SuspiciousAccountTransferRepository repository;

    @Mock
    private ExceptionReturner returner;

    @InjectMocks
    private SuspiciousAccountTransferServiceImpl service;

    @BeforeEach
    public void setup() {
        service = new SuspiciousAccountTransferServiceImpl(repository, mapper, returner);
    }

    @Test
    @DisplayName("Сохранение, позитивный сценарий")
    public void savePositiveTest() {

        SuspiciousAccountTransferDto dto = new SuspiciousAccountTransferDto(
                1L,
                2L,
                false,
                false,
                "blocked_reason",
                "suspicious_reason");
        SuspiciousAccountTransferEntity entity = new SuspiciousAccountTransferEntity(
                1L,
                2L,
                false,
                false,
                "blocked_reason",
                "suspicious_reason");


        when(mapper.toEntity(dto)).thenReturn(entity);
        when(repository.save(entity)).thenReturn(entity);
        when(mapper.toDto(entity)).thenReturn(dto);

        SuspiciousAccountTransferDto result = service.save(dto);

        assertThat(result).isNotNull();
        assertThat(dto.getId()).isEqualTo(result.getId());
    }

    @Test
    @DisplayName("Сохранение, негативный сценарий")
    public void saveNegativeTest() {

        SuspiciousAccountTransferDto dto = new SuspiciousAccountTransferDto(
                1L,
                2L,
                false,
                false,
                "blocked_reason",
                "suspicious_reason");
        SuspiciousAccountTransferEntity entity = new SuspiciousAccountTransferEntity(
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
        SuspiciousAccountTransferEntity entity = new SuspiciousAccountTransferEntity();
        SuspiciousAccountTransferDto dto = new SuspiciousAccountTransferDto();
        entity.setId(id);
        dto.setId(id);

        when(repository.findById(id)).thenReturn(Optional.of(entity));
        when(mapper.toDto(entity)).thenReturn(dto);

        SuspiciousAccountTransferDto result =  service.findById(id);

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
        SuspiciousAccountTransferEntity entity = new SuspiciousAccountTransferEntity(
                1L,
                2L,
                false,
                false,
                "blocked_reason",
                "suspicious_reason");

        SuspiciousAccountTransferDto dto = new SuspiciousAccountTransferDto(
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

        SuspiciousAccountTransferDto result = service.update(id, dto);

        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(dto);
    }

    @Test
    @DisplayName("Обновление по несуществующему id, негативный сценарий")
    public void updateByNonExistIdNegativeTest() {

        Long id = 1L;
        SuspiciousAccountTransferDto dto = new SuspiciousAccountTransferDto(
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

        SuspiciousAccountTransferDto dto1 = new SuspiciousAccountTransferDto();
        dto1.setId(id1);
        SuspiciousAccountTransferDto dto2 = new SuspiciousAccountTransferDto();
        dto2.setId(id2);
        SuspiciousAccountTransferEntity entity1 = new SuspiciousAccountTransferEntity();
        entity1.setId(id1);
        SuspiciousAccountTransferEntity entity2 = new SuspiciousAccountTransferEntity();
        entity2.setId(id2);

        List<SuspiciousAccountTransferEntity> listEntity = Arrays.asList(entity1, entity2);
        List<SuspiciousAccountTransferDto> listDto = Arrays.asList(dto1, dto2);

        when(repository.findById(id1)).thenReturn(Optional.of(entity1));
        when(repository.findById(id2)).thenReturn(Optional.of(entity2));
        when(mapper.toListDto(listEntity)).thenReturn(listDto);

        List<SuspiciousAccountTransferDto> result = service.findAllById(ids);

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
