package com.bank.publicinfo.mapper;

import com.bank.publicinfo.dto.AtmDto;
import com.bank.publicinfo.entity.AtmEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Тесты {@link AtmMapper}
 */
class AtmMapperTest {

    private final AtmMapper mapper = Mappers.getMapper(AtmMapper.class);

    @Test
    @DisplayName("Маппинг в entity")
    void toEntityTest() {
        AtmDto atmDto = new AtmDto();

        AtmEntity atmEntity = mapper.toEntity(atmDto);

        assertNotNull(atmEntity);
    }

    @Test
    @DisplayName("Маппинг в entity, на вход подан null")
    void toEntityNullTest() {
        AtmEntity atmEntity = mapper.toEntity(null);

        assertNull(atmEntity);
    }

    @Test
    @DisplayName("Маппинг в dto")
    void toDtoTest() {
        AtmEntity atmEntity = new AtmEntity();

        AtmDto atmDto = mapper.toDto(atmEntity);

        assertNotNull(atmDto);
    }

    @Test
    @DisplayName("Маппинг в dto, на вход подан null")
    void toDtoNullTest() {
        AtmDto atmDto = mapper.toDto(null);

        assertNull(atmDto);
    }

    @Test
    @DisplayName("Слияние в entity")
    void mergeToEntityTest() {
        AtmDto atmDto = new AtmDto();
        atmDto.setAddress("ATM address");
        AtmEntity atmEntity = new AtmEntity();

        AtmEntity updatedEntity = mapper.mergeToEntity(atmDto, atmEntity);

        assertEquals(atmDto.getAddress(), updatedEntity.getAddress());
    }

    @Test
    @DisplayName("Слияние в entity, на вход подан null")
    void mergeToEntityNullTest() {
        AtmEntity updatedEntity = mapper.mergeToEntity(null, null);

        assertNull(updatedEntity);
    }

    @Test
    @DisplayName("Маппинг в list dto")
    void toDtoListTest() {
        AtmEntity atmEntity1 = new AtmEntity();
        AtmEntity atmEntity2 = new AtmEntity();
        List<AtmEntity> atmEntityList = new ArrayList<>();
        atmEntityList.add(atmEntity1);
        atmEntityList.add(atmEntity2);

        List<AtmDto> atmDtos = mapper.toDtoList(atmEntityList);

        assertNotNull(atmDtos);
    }

    @Test
    @DisplayName("Маппинг в list dto, на вход подан null")
    void toDtoListNullTest() {
        List<AtmDto> atmDtos = mapper.toDtoList(null);

        assertNull(atmDtos);
    }
}