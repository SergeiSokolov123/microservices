package com.bank.publicinfo.mapper;

import com.bank.publicinfo.dto.BranchDto;
import com.bank.publicinfo.entity.BranchEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Тесты {@link BranchMapper}
 */
class BranchMapperTest {

    private final BranchMapper mapper = Mappers.getMapper(BranchMapper.class);

    @Test
    @DisplayName("Маппинг в entity")
    void toEntityTest() {
        BranchDto branchDto = new BranchDto();

        BranchEntity branchEntity = mapper.toEntity(branchDto);

        assertNotNull(branchEntity);
    }

    @Test
    @DisplayName("Маппинг в entity, на вход подан null")
    void toEntityNullTest() {
        BranchEntity branchEntity = mapper.toEntity(null);

        assertNull(branchEntity);
    }

    @Test
    @DisplayName("Маппинг в dto")
    void toDtoTest() {
        BranchEntity branchEntity = new BranchEntity();

        BranchDto branchDto = mapper.toDto(branchEntity);

        assertNotNull(branchDto);
    }

    @Test
    @DisplayName("Маппинг в dto, на вход подан null")
    void toDtoNullTest() {
        BranchDto branchDto = mapper.toDto(null);

        assertNull(branchDto);
    }

    @Test
    @DisplayName("Слияние в entity")
    void mergeToEntityTest() {
        BranchDto branchDto = new BranchDto();
        branchDto.setAddress("Address");
        BranchEntity branchEntity = new BranchEntity();

        BranchEntity updatedEntity = mapper.mergeToEntity(branchDto, branchEntity);

        assertEquals(branchDto.getAddress(), updatedEntity.getAddress());
    }

    @Test
    @DisplayName("Слияние в entity, на вход подан null")
    void mergeToEntityNullTest() {
        BranchEntity updatedEntity = mapper.mergeToEntity(null, null);

        assertNull(updatedEntity);
    }

    @Test
    @DisplayName("Маппинг в list dto")
    void toDtoListTest() {
        BranchEntity branchEntity1 = new BranchEntity();
        BranchEntity branchEntity2 = new BranchEntity();
        List<BranchEntity> branchEntityList = new ArrayList<>();
        branchEntityList.add(branchEntity1);
        branchEntityList.add(branchEntity2);

        List<BranchDto> branchDtosDtos = mapper.toDtoList(branchEntityList);

        assertNotNull(branchDtosDtos);
    }

    @Test
    @DisplayName("Маппинг в list dto, на вход подан null")
    void toDtoListNullTest() {
        List<BranchDto> branchDtos = mapper.toDtoList(null);

        assertNull(branchDtos);
    }
}