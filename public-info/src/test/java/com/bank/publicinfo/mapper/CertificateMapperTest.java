package com.bank.publicinfo.mapper;

import com.bank.publicinfo.dto.CertificateDto;
import com.bank.publicinfo.entity.CertificateEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

/**
 * Тесты {@link CertificateMapper}
 */
class CertificateMapperTest {

    private final CertificateMapper mapper = Mappers.getMapper(CertificateMapper.class);

    @Test
    @DisplayName("Маппинг в entity")
    void toEntityTest() {
        CertificateDto certificateDto = new CertificateDto();

        CertificateEntity certificateEntity = mapper.toEntity(certificateDto);

        assertNotNull(certificateEntity);
    }

    @Test
    @DisplayName("Маппинг в entity, на вход подан null")
    void toEntityNullTest() {
        CertificateEntity certificateEntity = mapper.toEntity(null);

        assertNull(certificateEntity);
    }

    @Test
    @DisplayName("Маппинг в dto")
    void toDtoTest() {
        CertificateEntity certificateEntity = new CertificateEntity();

        CertificateDto certificateDto = mapper.toDto(certificateEntity);

        assertNotNull(certificateDto);
    }

    @Test
    @DisplayName("Маппинг в dto, на вход подан null")
    void toDtoNullTest() {
        CertificateDto certificateDto = mapper.toDto(null);

        assertNull(certificateDto);
    }

    @Test
    @DisplayName("Слияние в entity")
    void mergeToEntityTest() {
        CertificateDto certificateDto = new CertificateDto();
        certificateDto.setPhotoCertificate(new Byte[] {1, 2, 3});
        CertificateEntity certificateEntity = new CertificateEntity();

        CertificateEntity updatedEntity = mapper.mergeToEntity(certificateDto, certificateEntity);

        assertArrayEquals(certificateDto.getPhotoCertificate(), updatedEntity.getPhotoCertificate());
    }

    @Test
    @DisplayName("Слияние в entity, на вход подан null")
    void mergeToEntityNullTest() {
        CertificateEntity updatedEntity = mapper.mergeToEntity(null, null);

        assertNull(updatedEntity);
    }

    @Test
    @DisplayName("Маппинг в list dto")
    void toDtoListTest() {
        CertificateEntity certificateEntity1 = new CertificateEntity();
        CertificateEntity certificateEntity2 = new CertificateEntity();
        List<CertificateEntity> certificateEntityList = new ArrayList<>();
        certificateEntityList.add(certificateEntity1);
        certificateEntityList.add(certificateEntity2);

        List<CertificateDto> certificateDtos = mapper.toDtoList(certificateEntityList);

        assertNotNull(certificateDtos);
    }

    @Test
    @DisplayName("Маппинг в list dto, на вход подан null")
    void toDtoListNullTest() {
        List<CertificateDto> certificateDtos = mapper.toDtoList(null);

        assertNull(certificateDtos);
    }
}