package com.bank.account.mapper;

import com.bank.account.dto.AuditDto;
import com.bank.account.entity.AuditEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Тесты {@link AccountAuditMapper}
 */
public class AccountAuditMapperTest {

    private final AccountAuditMapper mapper = Mappers.getMapper(AccountAuditMapper.class);

    @Test
    @DisplayName("Маппинг в дто")
    public void toDtoTest() {

        AuditEntity auditEntity = new AuditEntity();

        AuditDto auditDto = mapper.toDto(auditEntity);

        assertThat(auditDto).isNotNull();
    }

    @Test
    @DisplayName("Маппинг в дто, на вход подан null")
    public void toDtoNullTest() {

        AuditDto auditDto = mapper.toDto(null);

        assertThat(auditDto).isNull();
    }
}
