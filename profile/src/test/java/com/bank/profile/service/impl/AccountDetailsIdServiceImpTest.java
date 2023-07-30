package com.bank.profile.service.impl;

import com.bank.profile.dto.AccountDetailsIdDto;
import com.bank.profile.entity.AccountDetailsIdEntity;
import com.bank.profile.mapper.AccountDetailsIdMapper;
import com.bank.profile.repository.AccountDetailsIdRepository;
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
    @DisplayName("Позитивный сценарий findById()")
    void successFindById() {
        AccountDetailsIdEntity accountDetailsId = EntityTestData.getAccountDetailsIdEntity();
        AccountDetailsIdDto accountDetailsIdDto = DtoTestData.getAccountDetailsIdDto();
        doReturn(Optional.of(accountDetailsId)).when(repository).findById(accountDetailsId.getId());
        doReturn(accountDetailsIdDto).when(mapper).toDto(accountDetailsId);

        AccountDetailsIdDto actualResult = accountDetailsIdServiceImp.findById(accountDetailsId.getId());

        assertThat(actualResult).isEqualTo(accountDetailsIdDto);
    }

    @Test
    @DisplayName("Негативный сценарий findById() c исключением")
    void shouldThrowExceptionIfEntityNotFound() {
        doReturn(Optional.empty()).when(repository).findById(any());

        assertThrows(EntityNotFoundException.class, () -> accountDetailsIdServiceImp.findById(1L));
        verifyNoInteractions(mapper);
    }

    @Test
    @DisplayName("Позитивный сценарий save()")
    void successSave() {
        AccountDetailsIdEntity accountDetailsId = EntityTestData.getAccountDetailsIdEntity();
        AccountDetailsIdDto accountDetailsIdDto = DtoTestData.getAccountDetailsIdDto();
        doReturn(accountDetailsId).when(mapper).toEntity(accountDetailsIdDto);
        doReturn(accountDetailsId).when(repository).save(accountDetailsId);
        doReturn(accountDetailsIdDto).when(mapper).toDto(accountDetailsId);

        AccountDetailsIdDto actualResult = accountDetailsIdServiceImp.save(accountDetailsIdDto);

        assertThat(actualResult).isEqualTo(accountDetailsIdDto);
    }

    @Test
    @DisplayName("Негативный сценарий save() c Null на входе")
    void failSave() {
        AccountDetailsIdDto actualResult = accountDetailsIdServiceImp.save(null);

        assertThat(actualResult).isNull();
    }

    @Test
    @DisplayName("Позитивный сценарий update()")
    void successUpdate() {
        AccountDetailsIdEntity accountDetailsId = EntityTestData.getAccountDetailsIdEntity();
        AccountDetailsIdDto accountDetailsIdDto = DtoTestData.getAccountDetailsIdDto();
        doReturn(Optional.of(accountDetailsId)).when(repository).findById(accountDetailsId.getId());
        doReturn(accountDetailsId)
                .when(mapper)
                .mergeToEntity(accountDetailsIdDto, Optional.of(accountDetailsId).get());
        doReturn(accountDetailsId).when(repository).save(accountDetailsId);
        doReturn(accountDetailsIdDto).when(mapper).toDto(accountDetailsId);

        AccountDetailsIdDto actualResult = accountDetailsIdServiceImp.update(accountDetailsId.getId(), accountDetailsIdDto);

        assertThat(actualResult).isEqualTo(accountDetailsIdDto);
    }

    @Test
    @DisplayName("Негативный сценарий update() с исключением")
    void shouldThrowExceptionIfEntityNotUpdated() {
        AccountDetailsIdDto accountDetailsIdDto = DtoTestData.getAccountDetailsIdDto();
        doReturn(Optional.empty()).when(repository).findById(any());

        assertThrows(EntityNotFoundException.class,
                () -> accountDetailsIdServiceImp.update(10L, accountDetailsIdDto));
        verify(repository, times(0)).save(any());
        verifyNoInteractions(mapper);
    }

    @Test
    @DisplayName("Позитивный сценарий findAllById()")
    void successFindAllById() {
        List<AccountDetailsIdEntity> accountDetailsIdEntities = EntityTestData.getListOfAccountDetailsIdEntity();
        List<AccountDetailsIdDto> listOfAccountDetailsIdDto = DtoTestData.getListOfAccountDetailsIdDto();
        doReturn(accountDetailsIdEntities).when(repository).findAllById(List.of(1L, 2L));
        doReturn(listOfAccountDetailsIdDto).when(mapper).toDtoList(accountDetailsIdEntities);

        List<AccountDetailsIdDto> actualResult = accountDetailsIdServiceImp.findAllById(List.of(1L, 2L));

        assertThat(actualResult).isEqualTo(listOfAccountDetailsIdDto);
    }

    @Test
    @DisplayName("Негативный сценарий findAllById() c Null на входе")
    void failFindAllById() {
        List<AccountDetailsIdDto> actualResult = accountDetailsIdServiceImp.findAllById(null);
        assertThat(actualResult).hasSize(0);
    }
}