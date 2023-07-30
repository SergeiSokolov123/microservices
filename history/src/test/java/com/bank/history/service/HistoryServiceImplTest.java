package com.bank.history.service;

import com.bank.history.dto.HistoryDto;
import com.bank.history.entity.HistoryEntity;
import com.bank.history.mapper.HistoryMapper;
import com.bank.history.repository.HistoryRepository;
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
 * Тесты {@link HistoryService}
 **/
@ExtendWith(MockitoExtension.class)
class HistoryServiceImplTest {

    @Mock
    private HistoryRepository repository;

    @Mock
    private HistoryMapper mapper;

    @InjectMocks
    private HistoryServiceImpl service;

    @BeforeEach
    public void setup() {
        service = new HistoryServiceImpl(mapper, repository);
    }

    @Test
    @DisplayName("Чтение по id, позитивный сценарий")
    void readByIdPositiveTest() {
        Long id = 1L;
        HistoryDto historyDto = new HistoryDto();
        HistoryEntity historyEntity = new HistoryEntity();
        historyDto.setId(id);
        historyEntity.setId(id);

        when(repository.findById(id)).thenReturn(Optional.of(historyEntity));
        when(mapper.toDto(historyEntity)).thenReturn(historyDto);
        HistoryDto result = service.readById(id);
        assertThat(result).isNotNull();
        assertThat(historyDto).isEqualTo(result);
    }

    @Test
    @DisplayName("Чтение по несуществующему id, негативный сценарий")
    void readByNonExistIdNegativeTest() {
        Long id = 1L;

        when(repository.findById(id)).thenReturn(Optional.empty());
        assertThatExceptionOfType(EntityNotFoundException.class)
                .isThrownBy(() -> service.readById(id));
    }

    @Test
    @DisplayName("Чтение по списку id, позитивный сценарий")
    void readAllByIdPositiveTest() {
        List<Long> ids = Arrays.asList(1L, 2L);
        List<HistoryDto> dto = Arrays.asList(new HistoryDto(), new HistoryDto());
        dto.get(0).setId(ids.get(0));
        dto.get(1).setId(ids.get(1));
        List<HistoryEntity> entities = Arrays.asList(new HistoryEntity(), new HistoryEntity());
        entities.get(0).setId(ids.get(0));
        entities.get(1).setId(ids.get(1));

        when(repository.findAllById(ids)).thenReturn(entities);
        when(mapper.toListDto(entities)).thenReturn(dto);
        List<HistoryDto> result = service.readAllById(ids);
        assertThat(result).isNotNull();
        assertThat(dto).isEqualTo(result);
    }

    @Test
    @DisplayName("Чтение по списку несуществующих id, негативный сценарий")
    void readAllByNonExistIdNegativeTest() {
        List<Long> ids = Arrays.asList(1L, 2L);

        when(repository.findAllById(ids)).thenThrow(new EntityNotFoundException());
        assertThatExceptionOfType(EntityNotFoundException.class)
                .isThrownBy(() -> service.readAllById(ids));
    }

    @Test
    @DisplayName("Создание, позитивный сценарий")
    void createPositiveTest() {
        HistoryDto dto = new HistoryDto(1L, 1L, 1L,
                1L, 1L, 1L, 1L);
        HistoryEntity entity = new HistoryEntity(1L, 1L, 1L,
                1L, 1L, 1L, 1L);

        when(mapper.toEntity(dto)).thenReturn(entity);
        when(repository.save(entity)).thenReturn(entity);
        when(mapper.toDto(entity)).thenReturn(dto);
        HistoryDto result = service.create(dto);
        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(dto);
    }

    @Test
    @DisplayName("Создание, негативный сценарий")
    void createNegativeTest() {
        HistoryDto dto = new HistoryDto(1L, 1L, 1L,
                1L, 1L, 1L, 1L);
        HistoryEntity entity = new HistoryEntity(1L, 1L, 1L,
                1L, 1L, 1L, 1L);

        when(mapper.toEntity(dto)).thenReturn(entity);
        when(repository.save(entity)).thenThrow(new RuntimeException());
        assertThatExceptionOfType(RuntimeException.class)
                .isThrownBy(() -> service.create(dto));
    }

    @Test
    @DisplayName("Обновление по id, позитивный сценарий")
    void updateByIdPositiveTest() {
        Long id = 1L;
        HistoryDto dto = new HistoryDto(id, 1L, 1L,
                1L, 1L, 1L, 1L);
        HistoryEntity entity = new HistoryEntity(id, 1L, 1L,
                1L, 1L, 1L, 1L);

        when(repository.findById(id)).thenReturn(Optional.of(entity));
        when(repository.save(entity)).thenReturn(entity);
        when(mapper.mergeToEntity(dto, entity)).thenReturn(entity);
        when(mapper.toDto(entity)).thenReturn(dto);
        HistoryDto result = service.update(id, dto);
        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(dto);
    }

    @Test
    @DisplayName("Обновление по несуществующему id, негативный сценарий")
    void updateByNonExistIdNegativeTest() {
        Long id = 1L;
        HistoryDto dto = new HistoryDto(id, 1L, 1L,
                1L, 1L, 1L, 1L);

        when(repository.findById(id)).thenReturn(Optional.empty());
        assertThatExceptionOfType(EntityNotFoundException.class)
                .isThrownBy(() -> service.update(id, dto));
    }
}