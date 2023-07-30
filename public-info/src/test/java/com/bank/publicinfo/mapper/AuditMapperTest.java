package com.bank.publicinfo.mapper;

import com.bank.publicinfo.dto.AuditDto;
import com.bank.publicinfo.entity.AuditEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Тесты {@link AuditMapper}
 */
class AuditMapperTest {

    private final AuditMapper mapper = Mappers.getMapper(AuditMapper.class);

    @Test
    @DisplayName("Маппинг в dto")
    void toDtoTest() {
        AuditEntity auditEntity = new AuditEntity();

        AuditDto auditDto = mapper.toDto(auditEntity);

        assertNotNull(auditDto);
    }

    @Test
    @DisplayName("Маппинг в dto, на вход подан null")
    void toDtoNullTest() {
        AuditDto auditDto = mapper.toDto(null);

        assertNull(auditDto);
    }
}