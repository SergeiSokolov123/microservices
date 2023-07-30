package com.bank.profile.mapper;

import com.bank.profile.dto.ActualRegistrationDto;
import com.bank.profile.entity.ActualRegistrationEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import utils.DtoTestData;
import utils.EntityTestData;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ActualRegistrationMapperImplTest {

    private ActualRegistrationMapperImpl actualRegistrationMapperImpl = new ActualRegistrationMapperImpl();

    @Test
    @DisplayName("Позитивный сценарий toEntity()")
    void successToEntity() {
        ActualRegistrationDto actualRegistrationDto = DtoTestData.getActualRegistrationDto();
        ActualRegistrationEntity actualRegistrationEntity = EntityTestData.getActualRegistrationEntity();

        ActualRegistrationEntity actualResult = actualRegistrationMapperImpl.toEntity(actualRegistrationDto);

        assertThat(actualResult).isEqualTo(actualRegistrationEntity);
    }

    @Test
    @DisplayName("Негативный сценарий toEntity() c Null на входе")
    void failToEntity() {
        ActualRegistrationEntity actualResult = actualRegistrationMapperImpl.toEntity(null);

        assertThat(actualResult).isNull();
    }

    @Test
    @DisplayName("Позитивный сценарий toDto()")
    void successToDto() {
        ActualRegistrationDto actualRegistrationDto = DtoTestData.getActualRegistrationDto();
        ActualRegistrationEntity actualRegistrationEntity = EntityTestData.getActualRegistrationEntity();

        ActualRegistrationDto actualResult = actualRegistrationMapperImpl.toDto(actualRegistrationEntity);

        assertThat(actualResult).isEqualTo(actualRegistrationDto);
    }

    @Test
    @DisplayName("Негативный сценарий toDto() c Null на входе")
    void failToDto() {
        ActualRegistrationDto actualResult = actualRegistrationMapperImpl.toDto(null);

        assertThat(actualResult).isNull();
    }

    @Test
    @DisplayName("Позитивный сценарий mergeToEntity()")
    void successMergeToEntity() {
        ActualRegistrationDto actualRegistrationDto = DtoTestData.getActualRegistrationDto();
        ActualRegistrationEntity actualRegistrationEntity = EntityTestData.getActualRegistrationEntity();

        ActualRegistrationEntity actualResult = actualRegistrationMapperImpl.mergeToEntity(actualRegistrationDto, actualRegistrationEntity);

        assertThat(actualResult).isEqualTo(actualRegistrationEntity);
    }

    @Test
    @DisplayName("Негативный сценарий mergeToEntity() на входе Dto с Null")
    void failMergeToEntityWhenDtoIsNull() {
        ActualRegistrationEntity actualRegistrationEntity = EntityTestData.getActualRegistrationEntity();
        ActualRegistrationEntity actualResult = actualRegistrationMapperImpl.mergeToEntity(null, actualRegistrationEntity);

        assertThat(actualResult).isEqualTo(actualRegistrationEntity);
    }

    @Test
    @DisplayName("Позитивный сценарий toDtoList()")
    void successToDtoList() {
        List<ActualRegistrationDto> actualRegistrationDtoList = DtoTestData.getListOfActualRegistrationDto();
        List<ActualRegistrationEntity> actualRegistrationEntityList = EntityTestData.getListOfActualRegistrationEntity();

        List<ActualRegistrationDto> actualResult = actualRegistrationMapperImpl.toDtoList(actualRegistrationEntityList);

        assertThat(actualResult).isEqualTo(actualRegistrationDtoList);
    }

    @Test
    @DisplayName("Негативный сценарий toDtoList() c Null на входе")
    void failToDtoList() {
        List<ActualRegistrationDto> actualResult = actualRegistrationMapperImpl.toDtoList(null);

        assertThat(actualResult).isNull();
    }
}