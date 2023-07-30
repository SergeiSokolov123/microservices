package com.bank.antifraud.mappers;

import com.bank.antifraud.dto.AuditDto;
import com.bank.antifraud.entity.AuditEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Тесты {@link AuditMapper}
 */
@ExtendWith(MockitoExtension.class)
public class AuditMapperTest {
    private final AuditMapper auditMapper = Mappers.getMapper(AuditMapper.class);

    @Test
    @DisplayName("Маппинг в дто")
    public  void toDtoTest() {

        AuditEntity entity = new AuditEntity();

        AuditDto dto = auditMapper.toDto(entity);

        assertThat(dto).isNotNull();
    }

    @Test
    @DisplayName("Маппинг в дто, на вход подан null")
    public void toDtoNullTest() {

        AuditDto dto = auditMapper.toDto(null);

        assertThat(dto).isNull();
    }
}
