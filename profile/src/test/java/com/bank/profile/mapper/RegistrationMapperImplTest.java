package com.bank.profile.mapper;

import com.bank.profile.dto.RegistrationDto;
import com.bank.profile.entity.RegistrationEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import utils.DtoTestData;
import utils.EntityTestData;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class RegistrationMapperImplTest {
    private RegistrationMapperImpl registrationMapperImpl = new RegistrationMapperImpl();

    @Test
    @DisplayName("Позитивный сценарий toEntity()")
    void successToEntity() {
        RegistrationDto registrationDto = DtoTestData.getRegistrationDto();
        RegistrationEntity registrationEntity = EntityTestData.getRegistrationEntity();

        RegistrationEntity actualResult = registrationMapperImpl.toEntity(registrationDto);

        assertThat(actualResult).isEqualTo(registrationEntity);
    }

    @Test
    @DisplayName("Негативный сценарий toEntity() c Null на входе")
    void failToEntity() {
        RegistrationEntity actualResult = registrationMapperImpl.toEntity(null);

        assertThat(actualResult).isNull();
    }

    @Test
    @DisplayName("Позитивный сценарий toDto()")
    void successToDto() {
        RegistrationDto registrationDto = DtoTestData.getRegistrationDto();
        RegistrationEntity registrationEntity = EntityTestData.getRegistrationEntity();

        RegistrationDto actualResult = registrationMapperImpl.toDto(registrationEntity);

        assertThat(actualResult).isEqualTo(registrationDto);
    }

    @Test
    @DisplayName("Негативный сценарий toDto() c Null на входе")
    void failToDto() {
        RegistrationDto actualResult = registrationMapperImpl.toDto(null);

        assertThat(actualResult).isNull();
    }

    @Test
    @DisplayName("Позитивный сценарий mergeToEntity()")
    void successMergeToEntity() {
        RegistrationDto registrationDto = DtoTestData.getRegistrationDto();
        RegistrationEntity registrationEntity = EntityTestData.getRegistrationEntity();

        RegistrationEntity actualResult = registrationMapperImpl.mergeToEntity(registrationDto, registrationEntity);

        assertThat(actualResult).isEqualTo(registrationEntity);
    }

    @Test
    @DisplayName("Негативный сценарий mergeToEntity() на входе Dto с Null")
    void failMergeToEntityWhenDtoIsNull() {
        RegistrationEntity registrationEntity = EntityTestData.getRegistrationEntity();

        RegistrationEntity actualResult = registrationMapperImpl.mergeToEntity(null, registrationEntity);

        assertThat(actualResult).isEqualTo(registrationEntity);
    }

    @Test
    @DisplayName("Позитивный сценарий toDtoList()")
    void successToDtoList() {
        List<RegistrationDto> registrationDtoList = DtoTestData.getListOfRegistrationDto();
        List<RegistrationEntity> registrationEntityList = EntityTestData.getListOfRegistrationEntity();

        List<RegistrationDto> actualResult = registrationMapperImpl.toDtoList(registrationEntityList);

        assertThat(actualResult).isEqualTo(registrationDtoList);
    }

    @Test
    @DisplayName("Негативный сценарий toDtoList() c Null на входе")
    void failToDtoList() {
        List<RegistrationDto> actualResult = registrationMapperImpl.toDtoList(null);

        assertThat(actualResult).isNull();
    }
}