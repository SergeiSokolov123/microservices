package com.bank.profile.service.impl;

import com.bank.profile.dto.PassportDto;
import com.bank.profile.entity.PassportEntity;
import com.bank.profile.mapper.PassportMapper;
import com.bank.profile.repository.PassportRepository;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;

@ExtendWith(MockitoExtension.class)
class PassportServiceImpTest {
    @Mock
    private PassportRepository repository;
    @Mock
    private PassportMapper mapper;
    @InjectMocks
    private PassportServiceImp passportServiceImp;

    @Test
    @DisplayName("Позитивный сценарий findById()")
    void successFindById() {
        PassportEntity passport = EntityTestData.getPassportEntity();
        PassportDto passportDto = DtoTestData.getPassportDto();
        doReturn(Optional.of(passport)).when(repository).findById(passport.getId());
        doReturn(passportDto).when(mapper).toDto(passport);

        PassportDto actualResult = passportServiceImp.findById(passport.getId());

        assertThat(actualResult).isEqualTo(passportDto);
    }

    @Test
    @DisplayName("Негативный сценарий findById() c исключением")
    void shouldThrowExceptionIfEntityNotFound() {
        doReturn(Optional.empty()).when(repository).findById(any());

        assertThrows(EntityNotFoundException.class, () -> passportServiceImp.findById(1L));
        verifyNoInteractions(mapper);
    }

    @Test
    @DisplayName("Позитивный сценарий save()")
    void successSave() {
        PassportEntity passport = EntityTestData.getPassportEntity();
        PassportDto passportDto = DtoTestData.getPassportDto();
        doReturn(passport).when(mapper).toEntity(passportDto);
        doReturn(passport).when(repository).save(passport);
        doReturn(passportDto).when(mapper).toDto(passport);

        PassportDto actualResult = passportServiceImp.save(passportDto);
        assertThat(actualResult).isEqualTo(passportDto);
    }

    @Test
    @DisplayName("Негативный сценарий save() c Null на входе")
    void failSave() {
        PassportDto actualResult = passportServiceImp.save(null);
        assertThat(actualResult).isNull();
    }

    @Test
    @DisplayName("Позитивный сценарий update()")
    void successUpdate() {
        PassportEntity passport = EntityTestData.getPassportEntity();
        PassportDto passportDto = DtoTestData.getPassportDto();
        doReturn(Optional.of(passport)).when(repository).findById(passport.getId());
        doReturn(passport)
                .when(mapper)
                .mergeToEntity(passportDto, Optional.of(passport).get());
        doReturn(passport).when(repository).save(passport);
        doReturn(passportDto).when(mapper).toDto(passport);

        PassportDto actualResult = passportServiceImp.update(passport.getId(), passportDto);

        assertThat(actualResult).isEqualTo(passportDto);
    }

    @Test
    @DisplayName("Негативный сценарий update() с исключением")
    void shouldThrowExceptionIfEntityNotUpdated() {
        PassportDto passportDto = DtoTestData.getPassportDto();
        doReturn(Optional.empty()).when(repository).findById(any());

        assertThrows(EntityNotFoundException.class,
                () -> passportServiceImp.update(10L, passportDto));
        verify(repository, times(0)).save(any());
        verifyNoInteractions(mapper);
    }

    @Test
    @DisplayName("Позитивный сценарий findAllById()")
    void successFindAllById() {
        List<PassportEntity> passportEntities = EntityTestData.getListOfPassportEntity();
        List<PassportDto> listOfPassportDto = DtoTestData.getListOfPassportDto();
        doReturn(passportEntities).when(repository).findAllById(List.of(1L, 2L));
        doReturn(listOfPassportDto).when(mapper).toDtoList(passportEntities);

        List<PassportDto> actualResult = passportServiceImp.findAllById(List.of(1L, 2L));

        assertThat(actualResult).isEqualTo(listOfPassportDto);
    }

    @Test
    @DisplayName("Негативный сценарий findAllById() c Null на входе")
    void failFindAllById() {
        List<PassportDto> actualResult = passportServiceImp.findAllById(null);
        assertThat(actualResult).hasSize(0);
    }

}