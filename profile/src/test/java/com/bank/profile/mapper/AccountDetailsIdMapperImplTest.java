package com.bank.profile.mapper;

import com.bank.profile.dto.AccountDetailsIdDto;
import com.bank.profile.entity.AccountDetailsIdEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import utils.DtoTestData;
import utils.EntityTestData;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class AccountDetailsIdMapperImplTest {

    private AccountDetailsIdMapperImpl accountDetailsIdMapperImpl = new AccountDetailsIdMapperImpl();

    @Test
    @DisplayName("Позитивный сценарий toEntity()")
    void successToEntity() {
        AccountDetailsIdDto accountDetailsIdDto = DtoTestData.getAccountDetailsIdDto();
        AccountDetailsIdEntity accountDetailsIdEntity = EntityTestData.getAccountDetailsIdEntity();

        AccountDetailsIdEntity actualResult = accountDetailsIdMapperImpl.toEntity(accountDetailsIdDto);

        assertThat(actualResult).isEqualTo(accountDetailsIdEntity);
    }

    @Test
    @DisplayName("Негативный сценарий toEntity() c Null на входе")
    void failToEntity() {
        AccountDetailsIdEntity actualResult = accountDetailsIdMapperImpl.toEntity(null);

        assertThat(actualResult).isNull();
    }

    @Test
    @DisplayName("Позитивный сценарий toDto()")
    void successToDto() {
        AccountDetailsIdDto accountDetailsIdDto = DtoTestData.getAccountDetailsIdDto();
        AccountDetailsIdEntity accountDetailsIdEntity = EntityTestData.getAccountDetailsIdEntity();

        AccountDetailsIdDto actualResult = accountDetailsIdMapperImpl.toDto(accountDetailsIdEntity);

        assertThat(actualResult).isEqualTo(accountDetailsIdDto);
    }

    @Test
    @DisplayName("Негативный сценарий toDto() c Null на входе")
    void failToDto() {
        AccountDetailsIdDto actualResult = accountDetailsIdMapperImpl.toDto(null);

        assertThat(actualResult).isNull();
    }

    @Test
    @DisplayName("Позитивный сценарий mergeToEntity()")
    void successMergeToEntity() {
        AccountDetailsIdDto accountDetailsIdDto = DtoTestData.getAccountDetailsIdDto();
        AccountDetailsIdEntity accountDetailsId = EntityTestData.getAccountDetailsIdEntity();

        AccountDetailsIdEntity actualResult = accountDetailsIdMapperImpl.mergeToEntity(accountDetailsIdDto, accountDetailsId);

        assertThat(actualResult).isEqualTo(accountDetailsId);
    }

    @Test
    @DisplayName("Негативный сценарий mergeToEntity() на входе Entity c Null в Profile")
    void failMergeToEntityWhenEntityWithNullProfile() {
        AccountDetailsIdDto accountDetailsIdDto = DtoTestData.getAccountDetailsIdDto();
        AccountDetailsIdEntity accountDetailsId = EntityTestData.getAccountDetailsIdEntity();
        accountDetailsId.setProfile(null);

        AccountDetailsIdEntity actualResult = accountDetailsIdMapperImpl.mergeToEntity(accountDetailsIdDto, accountDetailsId);

        assertThat(actualResult).isEqualTo(accountDetailsId);
    }

    @Test
    @DisplayName("Негативный сценарий mergeToEntity() на входе Dto c Null в Profile")
    void failMergeToEntityWhenDtoWithNullProfile() {
        AccountDetailsIdDto accountDetailsIdDto = DtoTestData.getAccountDetailsIdDto();
        AccountDetailsIdEntity accountDetailsId = EntityTestData.getAccountDetailsIdEntity();
        accountDetailsIdDto.setProfile(null);

        AccountDetailsIdEntity actualResult = accountDetailsIdMapperImpl.mergeToEntity(accountDetailsIdDto, accountDetailsId);

        assertThat(actualResult).isEqualTo(accountDetailsId);
    }

    @Test
    @DisplayName("Негативный сценарий mergeToEntity() на входе Dto с Null")
    void failMergeToEntityWhenDtoIsNull() {
        AccountDetailsIdEntity accountDetailsId = EntityTestData.getAccountDetailsIdEntity();
        AccountDetailsIdEntity actualResult = accountDetailsIdMapperImpl.mergeToEntity(null, accountDetailsId);

        assertThat(actualResult).isEqualTo(accountDetailsId);
    }

    @Test
    @DisplayName("Позитивный сценарий toDtoList()")
    void successToDtoList() {
        List<AccountDetailsIdDto> accountDetailsIdDtoList = DtoTestData.getListOfAccountDetailsIdDto();
        List<AccountDetailsIdEntity> accountDetailsIdEntityList = EntityTestData.getListOfAccountDetailsIdEntity();

        List<AccountDetailsIdDto> actualResult = accountDetailsIdMapperImpl.toDtoList(accountDetailsIdEntityList);

        assertThat(actualResult).isEqualTo(accountDetailsIdDtoList);
    }

    @Test
    @DisplayName("Негативный сценарий toDtoList() c Null на входе")
    void failToDtoList() {
        List<AccountDetailsIdDto> actualResult = accountDetailsIdMapperImpl.toDtoList(null);

        assertThat(actualResult).isNull();
    }
}