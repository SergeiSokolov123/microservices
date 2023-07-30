package profile.mapper;

import com.bank.profile.dto.PassportDto;
import com.bank.profile.entity.PassportEntity;
import com.bank.profile.mapper.PassportMapperImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import utils.DtoTestData;
import utils.EntityTestData;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class PassportMapperImplTest {
    private final PassportMapperImpl passportMapperImpl = new PassportMapperImpl();

    @Test
    @DisplayName("Маппинг в энтити")
    void toEntityTest() {
        PassportDto passportDto = DtoTestData.getPassportDto();
        PassportEntity passportEntity = EntityTestData.getPassportEntity();

        PassportEntity actualResult = passportMapperImpl.toEntity(passportDto);

        assertThat(actualResult).isEqualTo(passportEntity);
    }

    @Test
    @DisplayName("Маппинг в энтити, на вход подан null")
    void toEntityNullTest() {
        PassportEntity actualResult = passportMapperImpl.toEntity(null);

        assertThat(actualResult).isNull();
    }

    @Test
    @DisplayName("Маппинг в дто")
    void toDtoTest() {
        PassportDto passportDto = DtoTestData.getPassportDto();
        PassportEntity passportEntity = EntityTestData.getPassportEntity();

        PassportDto actualResult = passportMapperImpl.toDto(passportEntity);

        assertThat(actualResult).isEqualTo(passportDto);
    }

    @Test
    @DisplayName("Маппинг в дто, на вход подан null")
    void toDtoNullTest() {
        PassportDto actualResult = passportMapperImpl.toDto(null);

        assertThat(actualResult).isNull();
    }

    @Test
    @DisplayName("Слияние в энтити")
    void mergeToEntityTest() {
        PassportDto passportDto = DtoTestData.getPassportDto();
        PassportEntity passportEntity = EntityTestData.getPassportEntity();

        PassportEntity actualResult = passportMapperImpl.mergeToEntity(passportDto, passportEntity);

        assertThat(actualResult).isEqualTo(passportEntity);
    }

    @Test
    @DisplayName("Слияние в энтити, на вход подан энтити c null в Registration")
    void mergeToEntityNullRegistrationInEntityTest() {
        PassportDto passportDto = DtoTestData.getPassportDto();
        PassportEntity passportEntity = EntityTestData.getPassportEntity();
        passportEntity.setRegistration(null);

        PassportEntity actualResult = passportMapperImpl.mergeToEntity(passportDto, passportEntity);

        assertThat(actualResult).isEqualTo(passportEntity);
    }

    @Test
    @DisplayName("Слияние в энтити, на вход подан дто c null в Registration")
    void mergeToEntityNullRegistrationInDtoTest() {
        PassportDto passportDto = DtoTestData.getPassportDto();
        PassportEntity passportEntity = EntityTestData.getPassportEntity();
        passportDto.setRegistration(null);

        PassportEntity actualResult = passportMapperImpl.mergeToEntity(passportDto, passportEntity);

        assertThat(actualResult).isEqualTo(passportEntity);
    }

    @Test
    @DisplayName("Слияние в энтити, на вход подан null в дто")
    void mergeToEntityNullDtoTest() {
        PassportEntity passportEntity = EntityTestData.getPassportEntity();

        PassportEntity actualResult = passportMapperImpl.mergeToEntity(null, passportEntity);

        assertThat(actualResult).isEqualTo(passportEntity);
    }

    @Test
    @DisplayName("Маппинг листа в дто")
    void toDtoListTest() {
        List<PassportDto> passportDtoList = DtoTestData.getListOfPassportDto();
        List<PassportEntity> passportEntityList = EntityTestData.getListOfPassportEntity();

        List<PassportDto> actualResult = passportMapperImpl.toDtoList(passportEntityList);

        assertThat(actualResult).isEqualTo(passportDtoList);
    }

    @Test
    @DisplayName("Маппинг листа в дто, на вход подан null")
    void toDtoListNullTest() {
        List<PassportDto> actualResult = passportMapperImpl.toDtoList(null);

        assertThat(actualResult).isNull();
    }
}