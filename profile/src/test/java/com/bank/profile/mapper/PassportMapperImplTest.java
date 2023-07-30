package com.bank.profile.mapper;

import com.bank.profile.dto.PassportDto;
import com.bank.profile.entity.PassportEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import utils.DtoTestData;
import utils.EntityTestData;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class PassportMapperImplTest {
    private PassportMapperImpl passportMapperImpl = new PassportMapperImpl();

    @Test
    @DisplayName("Позитивный сценарий toEntity()")
    void successToEntity() {
        PassportDto passportDto = DtoTestData.getPassportDto();
        PassportEntity passportEntity = EntityTestData.getPassportEntity();

        PassportEntity actualResult = passportMapperImpl.toEntity(passportDto);

        assertThat(actualResult).isEqualTo(passportEntity);
    }

    @Test
    @DisplayName("Негативный сценарий toEntity() c Null на входе")
    void failToEntity() {
        PassportEntity actualResult = passportMapperImpl.toEntity(null);

        assertThat(actualResult).isNull();
    }

    @Test
    @DisplayName("Позитивный сценарий toDto()")
    void successToDto() {
        PassportDto passportDto = DtoTestData.getPassportDto();
        PassportEntity passportEntity = EntityTestData.getPassportEntity();

        PassportDto actualResult = passportMapperImpl.toDto(passportEntity);

        assertThat(actualResult).isEqualTo(passportDto);
    }

    @Test
    @DisplayName("Негативный сценарий toDto() c Null на входе")
    void failToDto() {
        PassportDto actualResult = passportMapperImpl.toDto(null);

        assertThat(actualResult).isNull();
    }

    @Test
    @DisplayName("Позитивный сценарий mergeToEntity()")
    void successMergeToEntity() {
        PassportDto passportDto = DtoTestData.getPassportDto();
        PassportEntity passportEntity = EntityTestData.getPassportEntity();

        PassportEntity actualResult = passportMapperImpl.mergeToEntity(passportDto, passportEntity);

        assertThat(actualResult).isEqualTo(passportEntity);
    }

    @Test
    @DisplayName("Негативный сценарий mergeToEntity() на входе Entity c Null в RegistrationEntity")
    void failMergeToEntityWhenEntityWithNullRegistration() {
        PassportDto passportDto = DtoTestData.getPassportDto();
        PassportEntity passportEntity = EntityTestData.getPassportEntity();
        passportEntity.setRegistration(null);

        PassportEntity actualResult = passportMapperImpl.mergeToEntity(passportDto, passportEntity);

        assertThat(actualResult).isEqualTo(passportEntity);
    }


    @Test
    @DisplayName("Негативный сценарий mergeToEntity() на входе Dto c Null в RegistrationEntity")
    void failMergeToEntityWhenDtoWithNullRegistration() {
        PassportDto passportDto = DtoTestData.getPassportDto();
        PassportEntity passportEntity = EntityTestData.getPassportEntity();
        passportDto.setRegistration(null);

        PassportEntity actualResult = passportMapperImpl.mergeToEntity(passportDto, passportEntity);

        assertThat(actualResult).isEqualTo(passportEntity);
    }

    @Test
    @DisplayName("Негативный сценарий mergeToEntity() на входе Dto с Null")
    void failMergeToEntityWhenDtoIsNull() {
        PassportEntity passportEntity = EntityTestData.getPassportEntity();

        PassportEntity actualResult = passportMapperImpl.mergeToEntity(null, passportEntity);

        assertThat(actualResult).isEqualTo(passportEntity);
    }

    @Test
    @DisplayName("Позитивный сценарий toDtoList()")
    void successToDtoList() {
        List<PassportDto> passportDtoList = DtoTestData.getListOfPassportDto();
        List<PassportEntity> passportEntityList = EntityTestData.getListOfPassportEntity();

        List<PassportDto> actualResult = passportMapperImpl.toDtoList(passportEntityList);

        assertThat(actualResult).isEqualTo(passportDtoList);
    }

    @Test
    @DisplayName("Негативный сценарий toDtoList() c Null на входе")
    void failToDtoList() {
        List<PassportDto> actualResult = passportMapperImpl.toDtoList(null);

        assertThat(actualResult).isNull();
    }
}