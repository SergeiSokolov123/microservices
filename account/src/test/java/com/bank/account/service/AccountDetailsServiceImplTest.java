package com.bank.account.service;

import com.bank.account.dto.AccountDetailsDto;
import com.bank.account.entity.AccountDetailsEntity;
import com.bank.account.mapper.AccountDetailsMapper;
import com.bank.account.repository.AccountDetailsRepository;
import com.bank.account.service.common.ExceptionReturner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.when;

/**
 * Тесты {@link AccountDetailsServiceImpl}
 */
@ExtendWith(MockitoExtension.class)
public class AccountDetailsServiceImplTest {

    private static final String MESSAGE_PREFIX = "Не существующий id = ";

    @Mock
    private AccountDetailsMapper mapper;

    @Mock
    private AccountDetailsRepository repository;

    @Mock
    private ExceptionReturner exceptionReturner;

    @InjectMocks
    private AccountDetailsServiceImpl accountDetailsService;

    @BeforeEach
    public void setup() {
        accountDetailsService = new AccountDetailsServiceImpl(mapper, repository, exceptionReturner);
    }

    @Test
    @DisplayName("Поиск по id, позитивный сценарий")
    public void findByIdPositiveTest() {

        Long accountId = 1L;
        AccountDetailsEntity accountDetailsEntity = new AccountDetailsEntity();
        AccountDetailsDto accountDetailsDto = new AccountDetailsDto();
        accountDetailsEntity.setId(accountId);
        accountDetailsDto.setId(accountId);

        when(repository.findById(accountId)).thenReturn(Optional.of(accountDetailsEntity));
        when(mapper.toDto(accountDetailsEntity)).thenReturn(accountDetailsDto);

        AccountDetailsDto result = accountDetailsService.findById(accountId);

        assertThat(result).isNotNull();
        assertThat(accountDetailsDto).isEqualTo(result);
    }

    @Test
    @DisplayName("Поиск по несуществующему id, негативный сценарий")
    public void findByNonExistIdNegativeTest() {
        Long accountId = 1L;

        when(repository.findById(accountId)).thenReturn(Optional.empty());
        when(exceptionReturner.getEntityNotFoundException(MESSAGE_PREFIX + accountId))
                .thenReturn(new EntityNotFoundException());

        assertThatExceptionOfType(EntityNotFoundException.class)
                .isThrownBy(() -> accountDetailsService.findById(accountId));
    }

    @Test
    @DisplayName("Поиск по нескольким id, позитивный сценарий")
    public void findAllByIdPositiveTest() {

        Long accountId1 = 1L;
        Long accountId2 = 2L;

        List<Long> ids = Arrays.asList(accountId1, accountId2);

        AccountDetailsEntity accountDetailsEntity1 = new AccountDetailsEntity();
        accountDetailsEntity1.setId(accountId1);
        AccountDetailsEntity accountDetailsEntity2 = new AccountDetailsEntity();
        accountDetailsEntity2.setId(accountId2);

        AccountDetailsDto accountDetailsDto1 = new AccountDetailsDto();
        accountDetailsDto1.setId(accountId1);
        AccountDetailsDto accountDetailsDto2 = new AccountDetailsDto();
        accountDetailsDto2.setId(accountId2);

        List<AccountDetailsEntity> entityList = Arrays.asList(accountDetailsEntity1, accountDetailsEntity2);
        List<AccountDetailsDto> dtoList = Arrays.asList(accountDetailsDto1, accountDetailsDto2);

        when(repository.findById(accountId1)).thenReturn(Optional.of(accountDetailsEntity1));
        when(repository.findById(accountId2)).thenReturn(Optional.of(accountDetailsEntity2));
        when(mapper.toDtoList(entityList)).thenReturn(dtoList);

        List<AccountDetailsDto> result = accountDetailsService.findAllById(ids);

        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(dtoList);
    }


    @Test
    @DisplayName("Поиск по нескольким несуществующим id, негативный сценарий")
    public void findAllByNonExistIdsNegativeTest() {

        Long accountId1 = 1L;
        Long accountId2 = 2L;
        List<Long> ids = Arrays.asList(accountId1, accountId2);

        when(repository.findById(accountId1)).thenReturn(Optional.empty());
        when(exceptionReturner.getEntityNotFoundException(MESSAGE_PREFIX + accountId1))
                .thenReturn(new EntityNotFoundException());

        assertThatExceptionOfType(EntityNotFoundException.class)
                .isThrownBy(() -> accountDetailsService.findAllById(ids));
    }

    @Test
    @DisplayName("Сохранение, позитивный сценарий")
    public void savePositiveTest() {
        AccountDetailsEntity accountDetailsEntity = new AccountDetailsEntity(
                1L,
                123456L,
                12345L,
                1123432L,
                BigDecimal.valueOf(1000.00),
                false,
                1L);
        AccountDetailsDto accountDetailsDto = new AccountDetailsDto(
                1L,
                123456L,
                12345L,
                1123432L,
                BigDecimal.valueOf(1000.00),
                false,
                1L);

        when(mapper.toEntity(accountDetailsDto)).thenReturn(accountDetailsEntity);
        when(repository.save(accountDetailsEntity)).thenReturn(accountDetailsEntity);
        when(mapper.toDto(accountDetailsEntity)).thenReturn(accountDetailsDto);

        AccountDetailsDto result = accountDetailsService.save(accountDetailsDto);

        assertThat(result).isNotNull();
        assertThat(accountDetailsDto.getId()).isEqualTo(result.getId());
    }

    @Test
    @DisplayName("Сохранение, негативный сценарий")
    public void saveNegativeTest() {
        AccountDetailsEntity accountDetailsEntity = new AccountDetailsEntity(
                1L,
                123456L,
                12345L,
                1123432L,
                BigDecimal.valueOf(1000.00),
                false,
                1L);
        AccountDetailsDto accountDetailsDto = new AccountDetailsDto(
                1L,
                123456L,
                12345L,
                1123432L,
                BigDecimal.valueOf(1000.00),
                false,
                1L);

        when(mapper.toEntity(accountDetailsDto)).thenReturn(accountDetailsEntity);
        when(repository.save(accountDetailsEntity)).thenThrow(new RuntimeException());

        assertThatExceptionOfType(RuntimeException.class)
                .isThrownBy(() -> accountDetailsService.save(accountDetailsDto));
    }

    @Test
    @DisplayName("Обновление по id, позитивный сценарий")
    public void updatePositiveTest() {

        Long accountId = 1L;
        AccountDetailsEntity accountDetailsEntity = new AccountDetailsEntity(
                accountId,
                123456L,
                12345L,
                1123432L,
                BigDecimal.valueOf(1000.00),
                false,
                1L);
        AccountDetailsDto accountDetailsDto = new AccountDetailsDto(accountId, 123456L, 12345L,
                1123432L, BigDecimal.valueOf(1000.00), false, 1L);

        when(repository.findById(accountId)).thenReturn(Optional.of(accountDetailsEntity));
        when(repository.save(accountDetailsEntity)).thenReturn(accountDetailsEntity);
        when(mapper.mergeToEntity(accountDetailsEntity, accountDetailsDto)).thenReturn(accountDetailsEntity);
        when(mapper.toDto(accountDetailsEntity)).thenReturn(accountDetailsDto);

        AccountDetailsDto result = accountDetailsService.update(accountId, accountDetailsDto);

        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(accountDetailsDto);
    }

    @Test
    @DisplayName("Обновление по несуществующему id, негативный сценарий")
    public void updateByNonExistIdNegativeTest() {

        Long accountId = 1L;
        AccountDetailsDto accountDetailsDto = new AccountDetailsDto(
                accountId,
                123456L,
                12345L,
                1123432L,
                BigDecimal.valueOf(1000.00),
                false,
                1L);

        when(repository.findById(accountId)).thenReturn(Optional.empty());
        when(exceptionReturner.getEntityNotFoundException(MESSAGE_PREFIX + accountId))
                .thenReturn(new EntityNotFoundException());

        assertThatExceptionOfType(EntityNotFoundException.class)
                .isThrownBy(() -> accountDetailsService.update(accountId, accountDetailsDto));
    }
}
