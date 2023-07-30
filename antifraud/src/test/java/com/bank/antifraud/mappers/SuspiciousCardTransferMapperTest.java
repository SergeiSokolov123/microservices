package com.bank.antifraud.mappers;

import com.bank.antifraud.dto.SuspiciousCardTransferDto;
import com.bank.antifraud.entity.SuspiciousCardTransferEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Тесты {@link SuspiciousCardTransferMapper}
 */
public class SuspiciousCardTransferMapperTest {

    private final SuspiciousCardTransferMapper mapper = Mappers.getMapper(SuspiciousCardTransferMapper.class);

    @Test
    @DisplayName("Маппинг в дто")
    public void toDtoTest() {

        SuspiciousCardTransferEntity entity = new SuspiciousCardTransferEntity();

        SuspiciousCardTransferDto dto = mapper.toDto(entity);

        assertThat(dto).isNotNull();
    }

    @Test
    @DisplayName("Маппинг в дто, на вход подан null")
    public void toDtoNullTest() {

        SuspiciousCardTransferDto dto = mapper.toDto(null);

        assertThat(dto).isNull();
    }

    @Test
    @DisplayName("маппинг в энтити")
    public void toEntityTest() {

        SuspiciousCardTransferDto dto = new SuspiciousCardTransferDto();

        SuspiciousCardTransferEntity entity = mapper.toEntity(dto);

        assertThat(entity).isNotNull();
    }

    @Test
    @DisplayName("Маппинг в энтити, на вход подан null")
    public void toEntityNullTest() {

        SuspiciousCardTransferEntity entity = mapper.toEntity(null);

        assertThat(entity).isNull();
    }

    @Test
    @DisplayName("Маппинг листа в дто")
    public void toDtoListTest() {

        List<SuspiciousCardTransferEntity> listEntity = Arrays.asList(
                new SuspiciousCardTransferEntity(),
                new SuspiciousCardTransferEntity()
        );

        List<SuspiciousCardTransferDto> listDto = mapper.toListDto(listEntity);

        assertThat(listDto).isNotNull();
        assertThat(listDto).hasSize(listEntity.size());
    }

    @Test
    @DisplayName("Маппинг листа в дто, на вход подан null")
    public void toDtoListNullTest() {

        List<SuspiciousCardTransferDto> listDto = mapper.toListDto(null);

        assertThat(listDto).isNull();
    }

    @Test
    @DisplayName("Слияние в энтити")
    public void mergeToEntityTest() {

        SuspiciousCardTransferEntity entity = new SuspiciousCardTransferEntity();

        SuspiciousCardTransferDto dto = new SuspiciousCardTransferDto();

        SuspiciousCardTransferEntity mergedEntity = mapper.mergeToEntity(dto, entity);

        assertThat(mergedEntity).isNotNull();
    }

    @Test
    @DisplayName("Слияние в энтити, на вход поданы null")
    public void mergeToEntityNullTest() {

        SuspiciousCardTransferEntity mergedEntity = mapper.mergeToEntity(null, null);

        assertThat(mergedEntity).isNull();
    }
}
