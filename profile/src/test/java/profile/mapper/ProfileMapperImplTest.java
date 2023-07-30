package profile.mapper;

import com.bank.profile.dto.ProfileDto;
import com.bank.profile.entity.ProfileEntity;
import com.bank.profile.mapper.ProfileMapperImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import utils.DtoTestData;
import utils.EntityTestData;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ProfileMapperImplTest {
    private final ProfileMapperImpl profileMapperImpl = new ProfileMapperImpl();

    @Test
    @DisplayName("Маппинг в энтити")
    void toEntityTest() {
        ProfileDto profileDto = DtoTestData.getProfileDto();
        ProfileEntity profileEntity = EntityTestData.getProfileEntity();

        ProfileEntity actualResult = profileMapperImpl.toEntity(profileDto);

        assertThat(actualResult).isEqualTo(profileEntity);
    }

    @Test
    @DisplayName("Маппинг в энтити, на вход подан null")
    void toEntityNullTest() {
        ProfileEntity actualResult = profileMapperImpl.toEntity(null);

        assertThat(actualResult).isNull();
    }

    @Test
    @DisplayName("Маппинг в дто")
    void toDtoTest() {
        ProfileDto profileDto = DtoTestData.getProfileDto();
        ProfileEntity profileEntity = EntityTestData.getProfileEntity();

        ProfileDto actualResult = profileMapperImpl.toDto(profileEntity);

        assertThat(actualResult).isEqualTo(profileDto);
    }

    @Test
    @DisplayName("Маппинг в дто, на вход подан null")
    void toDtoNullTest() {
        ProfileDto actualResult = profileMapperImpl.toDto(null);

        assertThat(actualResult).isNull();
    }

    @Test
    @DisplayName("Слияние в энтити")
    void mergeToEntityTest() {
        ProfileDto profileDto = DtoTestData.getProfileDto();
        ProfileEntity profileEntity = EntityTestData.getProfileEntity();

        ProfileEntity actualResult = profileMapperImpl.mergeToEntity(profileDto, profileEntity);

        assertThat(actualResult).isEqualTo(profileEntity);
    }

    @Test
    @DisplayName("Слияние в энтити, на вход подан энтити c null в Passport")
    void mergeToEntityNullPassportInEntityTest() {
        ProfileDto profileDto = DtoTestData.getProfileDto();
        ProfileEntity profileEntity = EntityTestData.getProfileEntity();
        profileEntity.setPassport(null);

        ProfileEntity actualResult = profileMapperImpl.mergeToEntity(profileDto, profileEntity);

        assertThat(actualResult).isEqualTo(profileEntity);
    }

    @Test
    @DisplayName("Слияние в энтити, на вход подан энтити c null в ActualRegistration")
    void mergeToEntityNullActualRegistrationInEntityTest() {
        ProfileDto profileDto = DtoTestData.getProfileDto();
        ProfileEntity profileEntity = EntityTestData.getProfileEntity();
        profileEntity.setActualRegistration(null);

        ProfileEntity actualResult = profileMapperImpl.mergeToEntity(profileDto, profileEntity);

        assertThat(actualResult).isEqualTo(profileEntity);
    }

    @Test
    @DisplayName("Слияние в энтити, на вход подан дто c null в Passport")
    void mergeToEntityNullPassportInDtoTest() {
        ProfileDto profileDto = DtoTestData.getProfileDto();
        ProfileEntity profileEntity = EntityTestData.getProfileEntity();
        profileDto.setPassport(null);

        ProfileEntity actualResult = profileMapperImpl.mergeToEntity(profileDto, profileEntity);

        assertThat(actualResult).isEqualTo(profileEntity);
    }

    @Test
    @DisplayName("Слияние в энтити, на вход подан дто c null в ActualRegistration")
    void mergeToEntityNullActualRegistrationInDtoTest() {
        ProfileDto profileDto = DtoTestData.getProfileDto();
        ProfileEntity profileEntity = EntityTestData.getProfileEntity();
        profileDto.setActualRegistration(null);

        ProfileEntity actualResult = profileMapperImpl.mergeToEntity(profileDto, profileEntity);

        assertThat(actualResult).isEqualTo(profileEntity);
    }

    @Test
    @DisplayName("Слияние в энтити, на вход подан null в дто")
    void mergeToEntityNullDtoTest() {
        ProfileEntity profileEntity = EntityTestData.getProfileEntity();

        ProfileEntity actualResult = profileMapperImpl.mergeToEntity(null, profileEntity);

        assertThat(actualResult).isEqualTo(profileEntity);
    }

    @Test
    @DisplayName("Маппинг листа в дто")
    void toDtoListTest() {
        List<ProfileDto> profileDtoList = DtoTestData.getListOfProfileDto();
        List<ProfileEntity> profileEntityList = EntityTestData.getListOfProfileEntity();

        List<ProfileDto> actualResult = profileMapperImpl.toDtoList(profileEntityList);

        assertThat(actualResult).isEqualTo(profileDtoList);
    }

    @Test
    @DisplayName("Маппинг листа в дто, на вход подан null")
    void toDtoListNullTest() {
        List<ProfileDto> actualResult = profileMapperImpl.toDtoList(null);

        assertThat(actualResult).isNull();
    }
}