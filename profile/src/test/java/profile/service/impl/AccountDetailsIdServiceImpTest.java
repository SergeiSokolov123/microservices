package profile.service.impl;

import com.bank.profile.dto.AccountDetailsIdDto;
import com.bank.profile.entity.AccountDetailsIdEntity;
import com.bank.profile.mapper.AccountDetailsIdMapper;
import com.bank.profile.repository.AccountDetailsIdRepository;
import com.bank.profile.service.impl.AccountDetailsIdServiceImp;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import utils.DtoTestData;
import utils.EntityTestData;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;

@ExtendWith(MockitoExtension.class)
class AccountDetailsIdServiceImpTest {
    @Mock
    private AccountDetailsIdRepository repository;
    @Mock
    private AccountDetailsIdMapper mapper;
    @InjectMocks
    private AccountDetailsIdServiceImp accountDetailsIdServiceImp;

    @Test
    @DisplayName("Поиск по id, позитивный сценарий")
    void findByIdPositiveTest() {
        AccountDetailsIdEntity accountDetailsId = EntityTestData.getAccountDetailsIdEntity();
        AccountDetailsIdDto accountDetailsIdDto = DtoTestData.getAccountDetailsIdDto();
        doReturn(Optional.of(accountDetailsId)).when(repository).findById(accountDetailsId.getId());
        doReturn(accountDetailsIdDto).when(mapper).toDto(accountDetailsId);

        AccountDetailsIdDto actualResult = accountDetailsIdServiceImp.findById(accountDetailsId.getId());

        assertThat(actualResult).isEqualTo(accountDetailsIdDto);
    }

    @Test
    @DisplayName("Поиск по несуществующему id, негативный сценарий")
    void findByNonExistIdNegativeTest() {
        doReturn(Optional.empty()).when(repository).findById(any());

        assertThrows(EntityNotFoundException.class, () -> accountDetailsIdServiceImp.findById(1L));
        verifyNoInteractions(mapper);
    }

    @Test
    @DisplayName("Сохранение, позитивный сценарий")
    void savePositiveTest() {
        AccountDetailsIdEntity accountDetailsId = EntityTestData.getAccountDetailsIdEntity();
        AccountDetailsIdDto accountDetailsIdDto = DtoTestData.getAccountDetailsIdDto();
        doReturn(accountDetailsId).when(mapper).toEntity(accountDetailsIdDto);
        doReturn(accountDetailsId).when(repository).save(accountDetailsId);
        doReturn(accountDetailsIdDto).when(mapper).toDto(accountDetailsId);

        AccountDetailsIdDto actualResult = accountDetailsIdServiceImp.save(accountDetailsIdDto);

        assertThat(actualResult).isEqualTo(accountDetailsIdDto);
    }

    @Test
    @DisplayName("Сохранение, на вход подан null, позитивный сценарий")
    void saveNullPositiveTest() {
        AccountDetailsIdDto actualResult = accountDetailsIdServiceImp.save(null);

        assertThat(actualResult).isNull();
    }

    @Test
    @DisplayName("Сохранение, негативный сценарий")
    void saveNegativeTest() {
        AccountDetailsIdEntity accountDetailsId = EntityTestData.getAccountDetailsIdEntity();
        AccountDetailsIdDto accountDetailsIdDto = DtoTestData.getAccountDetailsIdDto();
        doReturn(accountDetailsId).when(mapper).toEntity(accountDetailsIdDto);
        doThrow(RuntimeException.class).when(repository).save(accountDetailsId);

        assertThrows(RuntimeException.class, () -> accountDetailsIdServiceImp.save(accountDetailsIdDto));
    }

    @Test
    @DisplayName("Обновление по id, позитивный сценарий")
    void updateByIdPositiveTest() {
        AccountDetailsIdEntity accountDetailsId = EntityTestData.getAccountDetailsIdEntity();
        AccountDetailsIdDto accountDetailsIdDto = DtoTestData.getAccountDetailsIdDto();
        doReturn(Optional.of(accountDetailsId)).when(repository).findById(accountDetailsId.getId());
        doReturn(accountDetailsId)
                .when(mapper)
                .mergeToEntity(accountDetailsIdDto, Optional.of(accountDetailsId).get());
        doReturn(accountDetailsId).when(repository).save(accountDetailsId);
        doReturn(accountDetailsIdDto).when(mapper).toDto(accountDetailsId);

        AccountDetailsIdDto actualResult = accountDetailsIdServiceImp
                .update(accountDetailsId.getId(), accountDetailsIdDto);

        assertThat(actualResult).isEqualTo(accountDetailsIdDto);
    }

    @Test
    @DisplayName("Обновление по несуществующему id, негативный сценарий")
    void updateByNonExistIdNegativeTest() {
        AccountDetailsIdDto accountDetailsIdDto = DtoTestData.getAccountDetailsIdDto();
        doReturn(Optional.empty()).when(repository).findById(any());

        assertThrows(EntityNotFoundException.class,
                () -> accountDetailsIdServiceImp.update(10L, accountDetailsIdDto));
        verify(repository, times(0)).save(any());
        verifyNoInteractions(mapper);
    }

    @Test
    @DisplayName("Поиск по нескольким id, позитивный сценарий")
    void findAllByIdPositiveTest() {
        List<AccountDetailsIdEntity> accountDetailsIdEntities = EntityTestData.getListOfAccountDetailsIdEntity();
        List<AccountDetailsIdDto> listOfAccountDetailsIdDto = DtoTestData.getListOfAccountDetailsIdDto();
        doReturn(accountDetailsIdEntities).when(repository).findAllById(List.of(1L, 2L));
        doReturn(listOfAccountDetailsIdDto).when(mapper).toDtoList(accountDetailsIdEntities);

        List<AccountDetailsIdDto> actualResult = accountDetailsIdServiceImp.findAllById(List.of(1L, 2L));

        assertThat(actualResult).isEqualTo(listOfAccountDetailsIdDto);
    }

    @Test
    @DisplayName("Поиск по нескольким id равным null, позитивный сценарий")
    void findAllByIdNullPositiveTest() {
        List<AccountDetailsIdDto> actualResult = accountDetailsIdServiceImp.findAllById(null);
        assertThat(actualResult).hasSize(0);
    }

    @Test
    @DisplayName("Поиск по нескольким id, негативный сценарий")
    void findAllByIdNegativeTest() {
        List<Long> ids = List.of(3L, 4L);
        doThrow(RuntimeException.class).when(repository).findAllById(ids);

        assertThrows(RuntimeException.class, () -> accountDetailsIdServiceImp.findAllById(ids));
    }
}