package com.bank.antifraud.mappers;

import com.bank.antifraud.dto.SuspiciousAccountTransferDto;
import com.bank.antifraud.entity.SuspiciousAccountTransferEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Тесты {@link SuspiciousAccountTransferMapper}
 */
public class SuspiciousAccountTransferMapperTest {

    private final SuspiciousAccountTransferMapper mapper = Mappers.getMapper(SuspiciousAccountTransferMapper.class);

    @Test
    @DisplayName("Маппинг в дто")
    public void toDtoTest() {

        SuspiciousAccountTransferEntity entity = new SuspiciousAccountTransferEntity();

        SuspiciousAccountTransferDto dto = mapper.toDto(entity);

        assertThat(dto).isNotNull();
    }

    @Test
    @DisplayName("Маппинг в дто, на вход подан null")
    public void toDtoNullTest() {

        SuspiciousAccountTransferDto dto = mapper.toDto(null);

        assertThat(dto).isNull();
    }

    @Test
    @DisplayName("маппинг в энтити")
    public void toEntityTest() {

        SuspiciousAccountTransferDto dto = new SuspiciousAccountTransferDto();

        SuspiciousAccountTransferEntity entity = mapper.toEntity(dto);

        assertThat(entity).isNotNull();
    }

    @Test
    @DisplayName("Маппинг в энтити, на вход подан null")
    public void toEntityNullTest() {

        SuspiciousAccountTransferEntity entity = mapper.toEntity(null);

        assertThat(entity).isNull();
    }

    @Test
    @DisplayName("Маппинг листа в дто")
    public void toDtoListTest() {

        List<SuspiciousAccountTransferEntity> listEntity = Arrays.asList(
                new SuspiciousAccountTransferEntity(),
                new SuspiciousAccountTransferEntity()
        );

        List<SuspiciousAccountTransferDto> listDto = mapper.toListDto(listEntity);

        assertThat(listDto).isNotNull();
        assertThat(listDto).hasSize(listEntity.size());
    }

    @Test
    @DisplayName("Маппинг листа в дто, на вход подан null")
    public void toDtoListNullTest() {

        List<SuspiciousAccountTransferDto> listDto = mapper.toListDto(null);

        assertThat(listDto).isNull();
    }

    @Test
    @DisplayName("Слияние в энтити")
    public void mergeToEntityTest() {

        SuspiciousAccountTransferEntity entity = new SuspiciousAccountTransferEntity();

        SuspiciousAccountTransferDto dto = new SuspiciousAccountTransferDto();

        SuspiciousAccountTransferEntity mergedEntity = mapper.mergeToEntity(dto, entity);

        assertThat(mergedEntity).isNotNull();
    }

    @Test
    @DisplayName("Слияние в энтити, на вход поданы null")
    public void mergeToEntityNullTest() {

        SuspiciousAccountTransferEntity mergedEntity = mapper.mergeToEntity(null, null);

        assertThat(mergedEntity).isNull();
    }
}
