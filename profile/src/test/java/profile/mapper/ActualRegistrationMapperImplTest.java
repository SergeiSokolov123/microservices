package profile.mapper;

import com.bank.profile.dto.ActualRegistrationDto;
import com.bank.profile.entity.ActualRegistrationEntity;
import com.bank.profile.mapper.ActualRegistrationMapperImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import utils.DtoTestData;
import utils.EntityTestData;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ActualRegistrationMapperImplTest {

    private final ActualRegistrationMapperImpl actualRegistrationMapperImpl = new ActualRegistrationMapperImpl();

    @Test
    @DisplayName("Маппинг в энтити")
    void toEntityTest() {
        ActualRegistrationDto actualRegistrationDto = DtoTestData.getActualRegistrationDto();
        ActualRegistrationEntity actualRegistrationEntity = EntityTestData.getActualRegistrationEntity();

        ActualRegistrationEntity actualResult = actualRegistrationMapperImpl.toEntity(actualRegistrationDto);

        assertThat(actualResult).isEqualTo(actualRegistrationEntity);
    }

    @Test
    @DisplayName("Маппинг в энтити, на вход подан null")
    void toEntityNullTest() {
        ActualRegistrationEntity actualResult = actualRegistrationMapperImpl.toEntity(null);

        assertThat(actualResult).isNull();
    }

    @Test
    @DisplayName("Маппинг в дто")
    void toDtoTest() {
        ActualRegistrationDto actualRegistrationDto = DtoTestData.getActualRegistrationDto();
        ActualRegistrationEntity actualRegistrationEntity = EntityTestData.getActualRegistrationEntity();

        ActualRegistrationDto actualResult = actualRegistrationMapperImpl.toDto(actualRegistrationEntity);

        assertThat(actualResult).isEqualTo(actualRegistrationDto);
    }

    @Test
    @DisplayName("Маппинг в дто, на вход подан null")
    void toDtoNullTest() {
        ActualRegistrationDto actualResult = actualRegistrationMapperImpl.toDto(null);

        assertThat(actualResult).isNull();
    }

    @Test
    @DisplayName("Слияние в энтити")
    void mergeToEntityTest() {
        ActualRegistrationDto actualRegistrationDto = DtoTestData.getActualRegistrationDto();
        ActualRegistrationEntity actualRegistrationEntity = EntityTestData.getActualRegistrationEntity();

        ActualRegistrationEntity actualResult = actualRegistrationMapperImpl
                .mergeToEntity(actualRegistrationDto, actualRegistrationEntity);

        assertThat(actualResult).isEqualTo(actualRegistrationEntity);
    }

    @Test
    @DisplayName("Слияние в энтити, на вход подан null в дто")
    void mergeToEntityNullDtoTest() {
        ActualRegistrationEntity actualRegistrationEntity = EntityTestData.getActualRegistrationEntity();

        ActualRegistrationEntity actualResult = actualRegistrationMapperImpl
                .mergeToEntity(null, actualRegistrationEntity);

        assertThat(actualResult).isEqualTo(actualRegistrationEntity);
    }

    @Test
    @DisplayName("Маппинг листа в дто")
    void toDtoListTest() {
        List<ActualRegistrationDto> actualRegistrationDtoList = DtoTestData.getListOfActualRegistrationDto();

        List<ActualRegistrationEntity> actualRegistrationEntityList = EntityTestData
                .getListOfActualRegistrationEntity();

        List<ActualRegistrationDto> actualResult = actualRegistrationMapperImpl.toDtoList(actualRegistrationEntityList);

        assertThat(actualResult).isEqualTo(actualRegistrationDtoList);
    }

    @Test
    @DisplayName("Маппинг листа в дто, на вход подан null")
    void toDtoListNullTest() {
        List<ActualRegistrationDto> actualResult = actualRegistrationMapperImpl.toDtoList(null);

        assertThat(actualResult).isNull();
    }
}