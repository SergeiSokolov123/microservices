package com.bank.authorization.mapper;

import com.bank.authorization.dto.AuditDto;
import com.bank.authorization.entity.AuditEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.sql.Timestamp;

public class AuditMapperTest {

    private AuditMapper auditMapper;

    @BeforeEach
    public void setup() {
        auditMapper = Mappers.getMapper(AuditMapper.class);
    }

    @Test
    @DisplayName("маппинг в дто")
    public void toDtoTest() {
        AuditEntity auditEntity = new AuditEntity();
        auditEntity.setId(1L);
        auditEntity.setEntityType("User");
        auditEntity.setOperationType("CREATE");
        auditEntity.setCreatedBy("Ivan");
        auditEntity.setModifiedBy("Ivanov");
        auditEntity.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        auditEntity.setModifiedAt(new Timestamp(System.currentTimeMillis()));
        auditEntity.setNewEntityJson("{}");
        auditEntity.setEntityJson("{}");


        AuditDto auditDto = auditMapper.toDto(auditEntity);

        Assertions.assertEquals(auditEntity.getId(), auditDto.getId());
        Assertions.assertEquals(auditEntity.getEntityType(), auditDto.getEntityType());
        Assertions.assertEquals(auditEntity.getOperationType(), auditDto.getOperationType());
        Assertions.assertEquals(auditEntity.getCreatedBy(), auditDto.getCreatedBy());
        Assertions.assertEquals(auditEntity.getModifiedBy(), auditDto.getModifiedBy());
        Assertions.assertEquals(auditEntity.getCreatedAt(), auditDto.getCreatedAt());
        Assertions.assertEquals(auditEntity.getModifiedAt(), auditDto.getModifiedAt());
        Assertions.assertEquals(auditEntity.getNewEntityJson(), auditDto.getNewEntityJson());
        Assertions.assertEquals(auditEntity.getEntityJson(), auditDto.getEntityJson());
    }

    @Test
    @DisplayName("маппинг в дто, на вход подан null")
    public void toDtoNullTest() {
        AuditEntity auditEntity = new AuditEntity();

        AuditDto auditDto = auditMapper.toDto(auditEntity);

        Assertions.assertNull(auditDto.getId());
        Assertions.assertNull(auditDto.getEntityType());
        Assertions.assertNull(auditDto.getOperationType());
        Assertions.assertNull(auditDto.getCreatedBy());
        Assertions.assertNull(auditDto.getModifiedBy());
        Assertions.assertNull(auditDto.getCreatedAt());
        Assertions.assertNull(auditDto.getModifiedAt());
        Assertions.assertNull(auditDto.getNewEntityJson());
        Assertions.assertNull(auditDto.getEntityJson());
    }
}
