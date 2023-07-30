package com.bank.antifraud.service.impl;

import com.bank.antifraud.dto.SuspiciousPhoneTransferDto;
import com.bank.antifraud.entity.SuspiciousPhoneTransferEntity;
import com.bank.antifraud.mappers.SuspiciousPhoneTransferMapper;
import com.bank.antifraud.repository.SuspiciousPhoneTransferRepository;
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
 * Тесты {@link SuspiciousPhoneTransferServiceImpl}
 */
@ExtendWith(MockitoExtension.class)
public class SuspiciousPhoneTransferServiceImplTest {

    private static final String MESSAGE = "SuspiciousPhoneTransfer по данному id не существует";
    @Mock
    private SuspiciousPhoneTransferMapper mapper;

    @Mock
    private SuspiciousPhoneTransferRepository repository;

    @Mock
    private ExceptionReturner returner;

    @InjectMocks
    private SuspiciousPhoneTransferServiceImpl service;

    @BeforeEach
    public void setup() {
        service = new SuspiciousPhoneTransferServiceImpl(repository, mapper, returner);
    }

    @Test
    @DisplayName("Сохранение, позитивный сценарий")
    public void savePositiveTest() {

        SuspiciousPhoneTransferDto dto = new SuspiciousPhoneTransferDto(
                1L,
                2L,
                false,
                false,
                "blocked_reason",
                "suspicious_reason");
        SuspiciousPhoneTransferEntity entity = new SuspiciousPhoneTransferEntity(
                1L,
                2L,
                false,
                false,
                "blocked_reason",
                "suspicious_reason");


        when(mapper.toEntity(dto)).thenReturn(entity);
        when(repository.save(entity)).thenReturn(entity);
        when(mapper.toDto(entity)).thenReturn(dto);

        SuspiciousPhoneTransferDto result = service.save(dto);

        assertThat(result).isNotNull();
        assertThat(dto.getId()).isEqualTo(result.getId());
    }

    @Test
    @DisplayName("Сохранение, негативный сценарий")
    public void saveNegativeTest() {

        SuspiciousPhoneTransferDto dto = new SuspiciousPhoneTransferDto(
                1L,
                2L,
                false,
                false,
                "blocked_reason",
                "suspicious_reason");
        SuspiciousPhoneTransferEntity entity = new SuspiciousPhoneTransferEntity(
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
        SuspiciousPhoneTransferEntity entity = new SuspiciousPhoneTransferEntity();
        SuspiciousPhoneTransferDto dto = new SuspiciousPhoneTransferDto();
        entity.setId(id);
        dto.setId(id);

        when(repository.findById(id)).thenReturn(Optional.of(entity));
        when(mapper.toDto(entity)).thenReturn(dto);

        SuspiciousPhoneTransferDto result =  service.findById(id);

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
        SuspiciousPhoneTransferEntity entity = new SuspiciousPhoneTransferEntity(
                1L,
                2L,
                false,
                false,
                "blocked_reason",
                "suspicious_reason");

        SuspiciousPhoneTransferDto dto = new SuspiciousPhoneTransferDto(
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

        SuspiciousPhoneTransferDto result = service.update(id, dto);

        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(dto);
    }

    @Test
    @DisplayName("Обновление по несуществующему id, негативный сценарий")
    public void updateByNonExistIdNegativeTest() {

        Long id = 1L;
        SuspiciousPhoneTransferDto dto = new SuspiciousPhoneTransferDto(
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

        SuspiciousPhoneTransferDto dto1 = new SuspiciousPhoneTransferDto();
        dto1.setId(id1);
        SuspiciousPhoneTransferDto dto2 = new SuspiciousPhoneTransferDto();
        dto2.setId(id2);
        SuspiciousPhoneTransferEntity entity1 = new SuspiciousPhoneTransferEntity();
        entity1.setId(id1);
        SuspiciousPhoneTransferEntity entity2 = new SuspiciousPhoneTransferEntity();
        entity2.setId(id2);

        List<SuspiciousPhoneTransferEntity> listEntity = Arrays.asList(entity1, entity2);
        List<SuspiciousPhoneTransferDto> listDto = Arrays.asList(dto1, dto2);

        when(repository.findById(id1)).thenReturn(Optional.of(entity1));
        when(repository.findById(id2)).thenReturn(Optional.of(entity2));
        when(mapper.toListDto(listEntity)).thenReturn(listDto);

        List<SuspiciousPhoneTransferDto> result = service.findAllById(ids);

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
