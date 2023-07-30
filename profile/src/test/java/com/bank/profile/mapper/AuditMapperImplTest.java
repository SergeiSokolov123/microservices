package com.bank.profile.mapper;

import com.bank.profile.dto.AuditDto;
import com.bank.profile.entity.AuditEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import utils.DtoTestData;
import utils.EntityTestData;

import static org.assertj.core.api.Assertions.assertThat;

class AuditMapperImplTest {
    private AuditMapperImpl auditMapperImpl = new AuditMapperImpl();

    @Test
    @DisplayName("Позитивный сценарий toDto()")
    void successToDto() {
        AuditDto auditDto = DtoTestData.getAuditDto();
        AuditEntity auditEntity = EntityTestData.getAuditEntity();

        AuditDto actualResult = auditMapperImpl.toDto(auditEntity);

        assertThat(actualResult).isEqualTo(auditDto);
    }

    @Test
    @DisplayName("Негативный сценарий toDto() c Null на входе")
    void failToDto() {
        AuditDto actualResult = auditMapperImpl.toDto(null);

        assertThat(actualResult).isNull();
    }
}