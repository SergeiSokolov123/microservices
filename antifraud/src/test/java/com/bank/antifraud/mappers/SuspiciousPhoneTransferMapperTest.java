package com.bank.antifraud.mappers;

import com.bank.antifraud.dto.SuspiciousPhoneTransferDto;
import com.bank.antifraud.entity.SuspiciousPhoneTransferEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Тесты {@link SuspiciousPhoneTransferMapper}
 */
public class SuspiciousPhoneTransferMapperTest {

    private final SuspiciousPhoneTransferMapper mapper = Mappers.getMapper(SuspiciousPhoneTransferMapper.class);

    @Test
    @DisplayName("Маппинг в дто")
    public void toDtoTest() {

        SuspiciousPhoneTransferEntity entity = new SuspiciousPhoneTransferEntity();

        SuspiciousPhoneTransferDto dto = mapper.toDto(entity);

        assertThat(dto).isNotNull();
    }

    @Test
    @DisplayName("Маппинг в дто, на вход подан null")
    public void toDtoNullTest() {

        SuspiciousPhoneTransferDto dto = mapper.toDto(null);

        assertThat(dto).isNull();
    }

    @Test
    @DisplayName("маппинг в энтити")
    public void toEntityTest() {

        SuspiciousPhoneTransferDto dto = new SuspiciousPhoneTransferDto();

        SuspiciousPhoneTransferEntity entity = mapper.toEntity(dto);

        assertThat(entity).isNotNull();
    }

    @Test
    @DisplayName("Маппинг в энтити, на вход подан null")
    public void toEntityNullTest() {

        SuspiciousPhoneTransferEntity entity = mapper.toEntity(null);

        assertThat(entity).isNull();
    }

    @Test
    @DisplayName("Маппинг листа в дто")
    public void toDtoListTest() {

        List<SuspiciousPhoneTransferEntity> listEntity = Arrays.asList(
                new SuspiciousPhoneTransferEntity(),
                new SuspiciousPhoneTransferEntity()
        );

        List<SuspiciousPhoneTransferDto> listDto = mapper.toListDto(listEntity);

        assertThat(listDto).isNotNull();
        assertThat(listDto).hasSize(listEntity.size());
    }

    @Test
    @DisplayName("Маппинг листа в дто, на вход подан null")
    public void toDtoListNullTest() {

        List<SuspiciousPhoneTransferDto> listDto = mapper.toListDto(null);

        assertThat(listDto).isNull();
    }

    @Test
    @DisplayName("Слияние в энтити")
    public void mergeToEntityTest() {

        SuspiciousPhoneTransferEntity entity = new SuspiciousPhoneTransferEntity();

        SuspiciousPhoneTransferDto dto = new SuspiciousPhoneTransferDto();

        SuspiciousPhoneTransferEntity mergedEntity = mapper.mergeToEntity(dto, entity);

        assertThat(mergedEntity).isNotNull();
    }

    @Test
    @DisplayName("Слияние в энтити, на вход поданы null")
    public void mergeToEntityNullTest() {

        SuspiciousPhoneTransferEntity mergedEntity = mapper.mergeToEntity(null, null);

        assertThat(mergedEntity).isNull();
    }
}
