package com.bank.history.mapper;

import com.bank.history.dto.HistoryDto;
import com.bank.history.entity.HistoryEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Тесты {@link HistoryMapper}
 **/
class HistoryMapperTest {

    HistoryMapper mapper = Mappers.getMapper(HistoryMapper.class);

    @Test
    @DisplayName("Маппинг в entity")
    public void toEntityTest() {
        HistoryDto dto = new HistoryDto();
        HistoryEntity entity = mapper.toEntity(dto);
        assertThat(entity).isNotNull();
    }

    @Test
    @DisplayName("Маппинг в entity, на вход подан null")
    public void toEntityNullTest() {
        HistoryEntity entity = mapper.toEntity(null);
        assertThat(entity).isNull();
    }

    @Test
    @DisplayName("Маппинг в dto")
    public void toDtoTest() {
        HistoryEntity entity = new HistoryEntity();
        HistoryDto dto = mapper.toDto(entity);
        assertThat(dto).isNotNull();
    }

    @Test
    @DisplayName("Маппинг в dto, на вход подан null")
    public void toDtoNullTest() {
        HistoryDto dto = mapper.toDto(null);
        assertThat(dto).isNull();
    }

    @Test
    @DisplayName("Слияние в entity")
    public void mergeToEntityTest() {
        HistoryEntity entity = new HistoryEntity();
        HistoryDto dto = new HistoryDto();
        HistoryEntity mergedEntity = mapper.mergeToEntity(dto, entity);
        assertThat(mergedEntity).isNotNull();
    }

    @Test
    @DisplayName("Слияние в entity, на вход поданы null")
    public void mergeToEntityNullTest() {
        HistoryEntity entity = mapper.mergeToEntity(null, null);
        assertThat(entity).isNull();
    }

    @Test
    @DisplayName("Маппинг в список dto")
    public void toListDtoTest() {
        List<HistoryEntity> entities = Arrays.asList(
                new HistoryEntity(),
                new HistoryEntity(),
                new HistoryEntity()
        );
        List<HistoryDto> dtoList = mapper.toListDto(entities);
        assertThat(dtoList).isNotNull();
        assertThat(dtoList).hasSize(entities.size());
    }

    @Test
    @DisplayName("Маппинг в список dto, на вход подан null")
    public void toListDtoNullTest() {
        List<HistoryDto> dtoList = mapper.toListDto(null);
        assertThat(dtoList).isNull();
    }
}