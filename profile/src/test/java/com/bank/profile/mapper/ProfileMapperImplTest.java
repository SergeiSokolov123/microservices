package com.bank.profile.mapper;

import com.bank.profile.dto.ProfileDto;
import com.bank.profile.entity.ProfileEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import utils.DtoTestData;
import utils.EntityTestData;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ProfileMapperImplTest {
    private ProfileMapperImpl profileMapperImpl = new ProfileMapperImpl();

    @Test
    @DisplayName("Позитивный сценарий toEntity()")
    void successToEntity() {
        ProfileDto profileDto = DtoTestData.getProfileDto();
        ProfileEntity profileEntity = EntityTestData.getProfileEntity();

        ProfileEntity actualResult = profileMapperImpl.toEntity(profileDto);

        assertThat(actualResult).isEqualTo(profileEntity);
    }

    @Test
    @DisplayName("Негативный сценарий toEntity() c Null на входе")
    void failToEntity() {
        ProfileEntity actualResult = profileMapperImpl.toEntity(null);

        assertThat(actualResult).isNull();
    }

    @Test
    @DisplayName("Позитивный сценарий toDto()")
    void successToDto() {
        ProfileDto profileDto = DtoTestData.getProfileDto();
        ProfileEntity profileEntity = EntityTestData.getProfileEntity();

        ProfileDto actualResult = profileMapperImpl.toDto(profileEntity);

        assertThat(actualResult).isEqualTo(profileDto);
    }

    @Test
    @DisplayName("Негативный сценарий toDto() c Null на входе")
    void failToDto() {
        ProfileDto actualResult = profileMapperImpl.toDto(null);

        assertThat(actualResult).isNull();
    }

    @Test
    @DisplayName("Позитивный сценарий mergeToEntity()")
    void successMergeToEntity() {
        ProfileDto profileDto = DtoTestData.getProfileDto();
        ProfileEntity profileEntity = EntityTestData.getProfileEntity();

        ProfileEntity actualResult = profileMapperImpl.mergeToEntity(profileDto, profileEntity);

        assertThat(actualResult).isEqualTo(profileEntity);
    }

    @Test
    @DisplayName("Негативный сценарий mergeToEntity() на входе Entity c Null в Passport")
    void failMergeToEntityWhenEntityWithNullPassport() {
        ProfileDto profileDto = DtoTestData.getProfileDto();
        ProfileEntity profileEntity = EntityTestData.getProfileEntity();
        profileEntity.setPassport(null);

        ProfileEntity actualResult = profileMapperImpl.mergeToEntity(profileDto, profileEntity);

        assertThat(actualResult).isEqualTo(profileEntity);
    }

    @Test
    @DisplayName("Негативный сценарий mergeToEntity() на входе Entity c Null в ActualRegistration")
    void failMergeToEntityWhenEntityWithNullActualRegistration() {
        ProfileDto profileDto = DtoTestData.getProfileDto();
        ProfileEntity profileEntity = EntityTestData.getProfileEntity();
        profileEntity.setActualRegistration(null);

        ProfileEntity actualResult = profileMapperImpl.mergeToEntity(profileDto, profileEntity);

        assertThat(actualResult).isEqualTo(profileEntity);
    }

    @Test
    @DisplayName("Негативный сценарий mergeToEntity() на входе Dto c Null в Passport")
    void failMergeToEntityWhenDtoWithNullPassport() {
        ProfileDto profileDto = DtoTestData.getProfileDto();
        ProfileEntity profileEntity = EntityTestData.getProfileEntity();
        profileDto.setPassport(null);

        ProfileEntity actualResult = profileMapperImpl.mergeToEntity(profileDto, profileEntity);

        assertThat(actualResult).isEqualTo(profileEntity);
    }

    @Test
    @DisplayName("Негативный сценарий mergeToEntity() на входе Dto c Null в ActualRegistration")
    void failMergeToEntityWhenDtoWithNullActualRegistration() {
        ProfileDto profileDto = DtoTestData.getProfileDto();
        ProfileEntity profileEntity = EntityTestData.getProfileEntity();
        profileDto.setActualRegistration(null);

        ProfileEntity actualResult = profileMapperImpl.mergeToEntity(profileDto, profileEntity);

        assertThat(actualResult).isEqualTo(profileEntity);
    }

    @Test
    @DisplayName("Негативный сценарий mergeToEntity() на входе Dto с Null")
    void failMergeToEntityWhenDtoIsNull() {
        ProfileEntity profileEntity = EntityTestData.getProfileEntity();

        ProfileEntity actualResult = profileMapperImpl.mergeToEntity(null, profileEntity);

        assertThat(actualResult).isEqualTo(profileEntity);
    }

    @Test
    @DisplayName("Позитивный сценарий toDtoList()")
    void successToDtoList() {
        List<ProfileDto> profileDtoList = DtoTestData.getListOfProfileDto();
        List<ProfileEntity> profileEntityList = EntityTestData.getListOfProfileEntity();

        List<ProfileDto> actualResult = profileMapperImpl.toDtoList(profileEntityList);

        assertThat(actualResult).isEqualTo(profileDtoList);
    }

    @Test
    @DisplayName("Негативный сценарий toDtoList() c Null на входе")
    void failToDtoList() {
        List<ProfileDto> actualResult = profileMapperImpl.toDtoList(null);

        assertThat(actualResult).isNull();
    }
}