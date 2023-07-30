package profile.mapper;

import com.bank.profile.dto.AuditDto;
import com.bank.profile.entity.AuditEntity;
import com.bank.profile.mapper.AuditMapperImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import utils.DtoTestData;
import utils.EntityTestData;

import static org.assertj.core.api.Assertions.assertThat;

class AuditMapperImplTest {
    private final AuditMapperImpl auditMapperImpl = new AuditMapperImpl();

    @Test
    @DisplayName("Маппинг в дто")
    void toDtoTest() {
        AuditDto auditDto = DtoTestData.getAuditDto();
        AuditEntity auditEntity = EntityTestData.getAuditEntity();

        AuditDto actualResult = auditMapperImpl.toDto(auditEntity);

        assertThat(actualResult).isEqualTo(auditDto);
    }

    @Test
    @DisplayName("Маппинг в дто, на вход подан null")
    void toDtoNullTest() {
        AuditDto actualResult = auditMapperImpl.toDto(null);

        assertThat(actualResult).isNull();
    }
}