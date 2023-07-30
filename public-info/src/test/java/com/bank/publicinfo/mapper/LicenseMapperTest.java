package com.bank.publicinfo.mapper;

import com.bank.publicinfo.dto.LicenseDto;
import com.bank.publicinfo.entity.LicenseEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

/**
 * Тесты {@link LicenseMapper}
 */
class LicenseMapperTest {

    private final LicenseMapper mapper = Mappers.getMapper(LicenseMapper.class);

    @Test
    @DisplayName("Маппинг в entity")
    void toEntityTest() {
        LicenseDto licenseDto = new LicenseDto();

        LicenseEntity licenseEntity = mapper.toEntity(licenseDto);

        assertNotNull(licenseEntity);
    }

    @Test
    @DisplayName("Маппинг в entity, на вход подан null")
    void toEntityNullTest() {
        LicenseEntity licenseEntity = mapper.toEntity(null);

        assertNull(licenseEntity);
    }

    @Test
    @DisplayName("Маппинг в dto")
    void toDtoTest() {
        LicenseEntity licenseEntity = new LicenseEntity();

        LicenseDto licenseDto = mapper.toDto(licenseEntity);

        assertNotNull(licenseDto);
    }

    @Test
    @DisplayName("Маппинг в dto, на вход подан null")
    void toDtoNullTest() {
        LicenseDto licenseDto = mapper.toDto(null);

        assertNull(licenseDto);
    }

    @Test
    @DisplayName("Слияние в entity")
    void mergeToEntityTest() {
        LicenseDto licenseDto = new LicenseDto();
        licenseDto.setPhotoLicense(new Byte[] {1, 2, 3});
        LicenseEntity licenseEntity = new LicenseEntity();

        LicenseEntity updatedEntity = mapper.mergeToEntity(licenseDto, licenseEntity);

        assertArrayEquals(licenseDto.getPhotoLicense(), updatedEntity.getPhotoLicense());
    }

    @Test
    @DisplayName("Слияние в entity, на вход подан null")
    void mergeToEntityNullTest() {
        LicenseEntity updatedEntity = mapper.mergeToEntity(null, null);

        assertNull(updatedEntity);
    }

    @Test
    @DisplayName("Маппинг в list dto")
    void toDtoListTest() {
        LicenseEntity licenseEntity1 = new LicenseEntity();
        LicenseEntity licenseEntity2 = new LicenseEntity();
        List<LicenseEntity> licenseEntityList = new ArrayList<>();
        licenseEntityList.add(licenseEntity1);
        licenseEntityList.add(licenseEntity2);

        List<LicenseDto> licenseDtos = mapper.toDtoList(licenseEntityList);

        assertNotNull(licenseDtos);
    }

    @Test
    @DisplayName("Маппинг в list dto, на вход подан null")
    void toDtoListNullTest() {
        List<LicenseDto> licenseDtos = mapper.toDtoList(null);

        assertNull(licenseDtos);
    }
}