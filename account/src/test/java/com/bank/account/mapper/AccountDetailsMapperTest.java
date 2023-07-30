package com.bank.account.mapper;

import com.bank.account.dto.AccountDetailsDto;
import com.bank.account.entity.AccountDetailsEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Тесты {@link AccountDetailsMapper}
 */
public class AccountDetailsMapperTest {

    private final AccountDetailsMapper mapper = Mappers.getMapper(AccountDetailsMapper.class);

    @Test
    @DisplayName("маппинг в энтити")
    public void toEntityTest() {

        AccountDetailsDto accountDetailsDto = new AccountDetailsDto();

        AccountDetailsEntity accountDetailsEntity = mapper.toEntity(accountDetailsDto);

        assertThat(accountDetailsEntity).isNotNull();
    }

    @Test
    @DisplayName("Маппинг в энтити, на вход подан null")
    public void toEntityNullTest() {

        AccountDetailsEntity accountDetailsEntity = mapper.toEntity(null);

        assertThat(accountDetailsEntity).isNull();
    }

    @Test
    @DisplayName("Маппинг в дто")
    public void toDtoTest() {

        AccountDetailsEntity accountDetailsEntity = new AccountDetailsEntity();

        AccountDetailsDto accountDetailsDto = mapper.toDto(accountDetailsEntity);

        assertThat(accountDetailsDto).isNotNull();
    }

    @Test
    @DisplayName("Маппинг в дто, на вход подан null")
    public void toDtoNullTest() {

        AccountDetailsDto accountDetailsDto = mapper.toDto(null);

        assertThat(accountDetailsDto).isNull();
    }

    @Test
    @DisplayName("Маппинг листа в дто")
    public void toDtoListTest() {

        List<AccountDetailsEntity> entityList = Arrays.asList(
                new AccountDetailsEntity(),
                new AccountDetailsEntity(),
                new AccountDetailsEntity()
        );

        List<AccountDetailsDto> dtoList = mapper.toDtoList(entityList);

        assertThat(dtoList).isNotNull();
        assertThat(dtoList).hasSize(entityList.size());
    }

    @Test
    @DisplayName("Маппинг листа в дто, на вход подан null")
    public void toDtoListNullTest() {

        List<AccountDetailsDto> dtoList = mapper.toDtoList(null);

        assertThat(dtoList).isNull();
    }

    @Test
    @DisplayName("Слияние в энтити")
    public void mergeToEntityTest() {

        AccountDetailsEntity accountDetailsEntity = new AccountDetailsEntity();

        AccountDetailsDto accountDetailsDto = new AccountDetailsDto();

        AccountDetailsEntity mergedEntity = mapper.mergeToEntity(accountDetailsEntity, accountDetailsDto);

        assertThat(mergedEntity).isNotNull();
    }

    @Test
    @DisplayName("Слияние в энтити, на вход поданы null")
    public void mergeToEntityNullTest() {

        AccountDetailsEntity mergedEntity = mapper.mergeToEntity(null, null);

        assertThat(mergedEntity).isNull();
    }
}
