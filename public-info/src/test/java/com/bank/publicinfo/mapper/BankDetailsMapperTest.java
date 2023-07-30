package com.bank.publicinfo.mapper;

import com.bank.publicinfo.dto.BankDetailsDto;
import com.bank.publicinfo.entity.BankDetailsEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Тесты {@link BankDetailsMapper}
 */
class BankDetailsMapperTest {

    private final BankDetailsMapper mapper = Mappers.getMapper(BankDetailsMapper.class);

    @Test
    @DisplayName("Маппинг в entity")
    void toEntityTest() {
        BankDetailsDto bankDetailsDto = new BankDetailsDto();

        BankDetailsEntity bankDetailsEntity = mapper.toEntity(bankDetailsDto);

        assertNotNull(bankDetailsEntity);
    }

    @Test
    @DisplayName("Маппинг в entity, на вход подан null")
    void toEntityNullTest() {
        BankDetailsEntity bankDetailsEntity = mapper.toEntity(null);

        assertNull(bankDetailsEntity);
    }

    @Test
    @DisplayName("Маппинг в dto")
    void toDtoTest() {
        BankDetailsEntity bankDetailsEntity = new BankDetailsEntity();

        BankDetailsDto bankDetailsDtoDto = mapper.toDto(bankDetailsEntity);

        assertNotNull(bankDetailsDtoDto);
    }

    @Test
    @DisplayName("Маппинг в dto, на вход подан null")
    void toDtoNullTest() {
        BankDetailsDto bankDetailsDto = mapper.toDto(null);

        assertNull(bankDetailsDto);
    }

    @Test
    @DisplayName("Слияние в entity")
    void mergeToEntityTest() {
        BankDetailsDto bankDetailsDto = new BankDetailsDto();
        bankDetailsDto.setCity("City");
        BankDetailsEntity bankDetailsEntity = new BankDetailsEntity();

        BankDetailsEntity updatedEntity = mapper.mergeToEntity(bankDetailsDto, bankDetailsEntity);

        assertEquals(bankDetailsDto.getCity(), updatedEntity.getCity());
    }

    @Test
    @DisplayName("Слияние в entity, на вход подан null")
    void mergeToEntityNullTest() {
        BankDetailsEntity updatedEntity = mapper.mergeToEntity(null, null);

        assertNull(updatedEntity);
    }

    @Test
    @DisplayName("Маппинг в list dto")
    void toDtoListTest() {
        BankDetailsEntity bankDetailsEntity1 = new BankDetailsEntity();
        BankDetailsEntity bankDetailsEntity2 = new BankDetailsEntity();
        List<BankDetailsEntity> bankDetailsEntityList = new ArrayList<>();
        bankDetailsEntityList.add(bankDetailsEntity1);
        bankDetailsEntityList.add(bankDetailsEntity2);

        List<BankDetailsDto> bankDetailsDtos = mapper.toDtoList(bankDetailsEntityList);

        assertNotNull(bankDetailsDtos);
    }

    @Test
    @DisplayName("Маппинг в list dto, на вход подан null")
    void toDtoListNullTest() {
        List<BankDetailsDto> bankDetailsDtos = mapper.toDtoList(null);

        assertNull(bankDetailsDtos);
    }
}