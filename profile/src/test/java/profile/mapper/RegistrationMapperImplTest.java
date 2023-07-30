package profile.mapper;

import com.bank.profile.dto.RegistrationDto;
import com.bank.profile.entity.RegistrationEntity;
import com.bank.profile.mapper.RegistrationMapperImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import utils.DtoTestData;
import utils.EntityTestData;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class RegistrationMapperImplTest {
    private final RegistrationMapperImpl registrationMapperImpl = new RegistrationMapperImpl();

    @Test
    @DisplayName("Маппинг в энтити")
    void toEntityTest() {
        RegistrationDto registrationDto = DtoTestData.getRegistrationDto();
        RegistrationEntity registrationEntity = EntityTestData.getRegistrationEntity();

        RegistrationEntity actualResult = registrationMapperImpl.toEntity(registrationDto);

        assertThat(actualResult).isEqualTo(registrationEntity);
    }

    @Test
    @DisplayName("Маппинг в энтити, на вход подан null")
    void toEntityNullTest() {
        RegistrationEntity actualResult = registrationMapperImpl.toEntity(null);

        assertThat(actualResult).isNull();
    }

    @Test
    @DisplayName("Маппинг в дто")
    void toDtoTest() {
        RegistrationDto registrationDto = DtoTestData.getRegistrationDto();
        RegistrationEntity registrationEntity = EntityTestData.getRegistrationEntity();

        RegistrationDto actualResult = registrationMapperImpl.toDto(registrationEntity);

        assertThat(actualResult).isEqualTo(registrationDto);
    }

    @Test
    @DisplayName("Маппинг в дто, на вход подан null")
    void toDtoNullTest() {
        RegistrationDto actualResult = registrationMapperImpl.toDto(null);

        assertThat(actualResult).isNull();
    }

    @Test
    @DisplayName("Слияние в энтити")
    void mergeToEntityTest() {
        RegistrationDto registrationDto = DtoTestData.getRegistrationDto();
        RegistrationEntity registrationEntity = EntityTestData.getRegistrationEntity();

        RegistrationEntity actualResult = registrationMapperImpl.mergeToEntity(registrationDto, registrationEntity);

        assertThat(actualResult).isEqualTo(registrationEntity);
    }

    @Test
    @DisplayName("Слияние в энтити, на вход подан null в дто")
    void mergeToEntityNullDtoTest() {
        RegistrationEntity registrationEntity = EntityTestData.getRegistrationEntity();

        RegistrationEntity actualResult = registrationMapperImpl.mergeToEntity(null, registrationEntity);

        assertThat(actualResult).isEqualTo(registrationEntity);
    }

    @Test
    @DisplayName("Маппинг листа в дто")
    void toDtoListTest() {
        List<RegistrationDto> registrationDtoList = DtoTestData.getListOfRegistrationDto();
        List<RegistrationEntity> registrationEntityList = EntityTestData.getListOfRegistrationEntity();

        List<RegistrationDto> actualResult = registrationMapperImpl.toDtoList(registrationEntityList);

        assertThat(actualResult).isEqualTo(registrationDtoList);
    }

    @Test
    @DisplayName("Маппинг листа в дто, на вход подан null")
    void toDtoListNullTest() {
        List<RegistrationDto> actualResult = registrationMapperImpl.toDtoList(null);

        assertThat(actualResult).isNull();
    }
}