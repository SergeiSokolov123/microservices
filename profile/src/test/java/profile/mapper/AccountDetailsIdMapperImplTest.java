package profile.mapper;

import com.bank.profile.dto.AccountDetailsIdDto;
import com.bank.profile.entity.AccountDetailsIdEntity;
import com.bank.profile.mapper.AccountDetailsIdMapperImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import utils.DtoTestData;
import utils.EntityTestData;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class AccountDetailsIdMapperImplTest {

    private final AccountDetailsIdMapperImpl accountDetailsIdMapperImpl = new AccountDetailsIdMapperImpl();

    @Test
    @DisplayName("Маппинг в энтити")
    void toEntityTest() {
        AccountDetailsIdDto accountDetailsIdDto = DtoTestData.getAccountDetailsIdDto();
        AccountDetailsIdEntity accountDetailsIdEntity = EntityTestData.getAccountDetailsIdEntity();

        AccountDetailsIdEntity actualResult = accountDetailsIdMapperImpl.toEntity(accountDetailsIdDto);

        assertThat(actualResult).isEqualTo(accountDetailsIdEntity);
    }

    @Test
    @DisplayName("Маппинг в энтити, на вход подан null")
    void toEntityNullTest() {
        AccountDetailsIdEntity actualResult = accountDetailsIdMapperImpl.toEntity(null);

        assertThat(actualResult).isNull();
    }

    @Test
    @DisplayName("Маппинг в дто")
    void toDtoTest() {
        AccountDetailsIdDto accountDetailsIdDto = DtoTestData.getAccountDetailsIdDto();
        AccountDetailsIdEntity accountDetailsIdEntity = EntityTestData.getAccountDetailsIdEntity();

        AccountDetailsIdDto actualResult = accountDetailsIdMapperImpl.toDto(accountDetailsIdEntity);

        assertThat(actualResult).isEqualTo(accountDetailsIdDto);
    }

    @Test
    @DisplayName("Маппинг в дто, на вход подан null")
    void toDtoNullTest() {
        AccountDetailsIdDto actualResult = accountDetailsIdMapperImpl.toDto(null);

        assertThat(actualResult).isNull();
    }

    @Test
    @DisplayName("Слияние в энтити")
    void mergeToEntityTest() {
        AccountDetailsIdDto accountDetailsIdDto = DtoTestData.getAccountDetailsIdDto();
        AccountDetailsIdEntity accountDetailsId = EntityTestData.getAccountDetailsIdEntity();

        AccountDetailsIdEntity actualResult = accountDetailsIdMapperImpl
                .mergeToEntity(accountDetailsIdDto, accountDetailsId);

        assertThat(actualResult).isEqualTo(accountDetailsId);
    }

    @Test
    @DisplayName("Слияние в энтити, на вход подан энтити c null в Profile")
    void mergeToEntityNullProfileInEntityTest() {
        AccountDetailsIdDto accountDetailsIdDto = DtoTestData.getAccountDetailsIdDto();
        AccountDetailsIdEntity accountDetailsId = EntityTestData.getAccountDetailsIdEntity();
        accountDetailsId.setProfile(null);

        AccountDetailsIdEntity actualResult = accountDetailsIdMapperImpl
                .mergeToEntity(accountDetailsIdDto, accountDetailsId);

        assertThat(actualResult).isEqualTo(accountDetailsId);
    }

    @Test
    @DisplayName("Слияние в энтити, на вход подан дто c null в Profile")
    void mergeToEntityNullProfileInDtoTest() {
        AccountDetailsIdDto accountDetailsIdDto = DtoTestData.getAccountDetailsIdDto();
        AccountDetailsIdEntity accountDetailsId = EntityTestData.getAccountDetailsIdEntity();
        accountDetailsIdDto.setProfile(null);

        AccountDetailsIdEntity actualResult = accountDetailsIdMapperImpl
                .mergeToEntity(accountDetailsIdDto, accountDetailsId);

        assertThat(actualResult).isEqualTo(accountDetailsId);
    }

    @Test
    @DisplayName("Слияние в энтити, на вход подан null в дто")
    void mergeToEntityNullDtoTest() {
        AccountDetailsIdEntity accountDetailsId = EntityTestData.getAccountDetailsIdEntity();

        AccountDetailsIdEntity actualResult = accountDetailsIdMapperImpl
                .mergeToEntity(null, accountDetailsId);

        assertThat(actualResult).isEqualTo(accountDetailsId);
    }

    @Test
    @DisplayName("Маппинг листа в дто")
    void toDtoListTest() {
        List<AccountDetailsIdDto> accountDetailsIdDtoList = DtoTestData.getListOfAccountDetailsIdDto();
        List<AccountDetailsIdEntity> accountDetailsIdEntityList = EntityTestData.getListOfAccountDetailsIdEntity();

        List<AccountDetailsIdDto> actualResult = accountDetailsIdMapperImpl.toDtoList(accountDetailsIdEntityList);

        assertThat(actualResult).isEqualTo(accountDetailsIdDtoList);
    }

    @Test
    @DisplayName("Маппинг листа в дто, на вход подан null")
    void toDtoListNullTest() {
        List<AccountDetailsIdDto> actualResult = accountDetailsIdMapperImpl.toDtoList(null);

        assertThat(actualResult).isNull();
    }
}