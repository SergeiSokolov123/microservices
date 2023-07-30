package com.bank.profile.service.impl;

import com.bank.profile.dto.RegistrationDto;
import com.bank.profile.entity.RegistrationEntity;
import com.bank.profile.mapper.RegistrationMapper;
import com.bank.profile.repository.RegistrationRepository;
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
class RegistrationServiceImpTest {
    @Mock
    private RegistrationRepository repository;
    @Mock
    private RegistrationMapper mapper;
    @InjectMocks
    private RegistrationServiceImp registrationServiceImp;

    @Test
    @DisplayName("Позитивный сценарий findById()")
    void successFindById() {
        RegistrationEntity registration = EntityTestData.getRegistrationEntity();
        RegistrationDto registrationDto = DtoTestData.getRegistrationDto();
        doReturn(Optional.of(registration)).when(repository).findById(registration.getId());
        doReturn(registrationDto).when(mapper).toDto(registration);

        RegistrationDto actualResult = registrationServiceImp.findById(registration.getId());

        assertThat(actualResult).isEqualTo(registrationDto);
    }

    @Test
    @DisplayName("Негативный сценарий findById() c исключением")
    void shouldThrowExceptionIfEntityNotFound() {
        doReturn(Optional.empty()).when(repository).findById(any());

        assertThrows(EntityNotFoundException.class, () -> registrationServiceImp.findById(1L));
        verifyNoInteractions(mapper);
    }

    @Test
    @DisplayName("Позитивный сценарий save()")
    void successSave() {
        RegistrationEntity registration = EntityTestData.getRegistrationEntity();
        RegistrationDto registrationDto = DtoTestData.getRegistrationDto();
        doReturn(registration).when(mapper).toEntity(registrationDto);
        doReturn(registration).when(repository).save(registration);
        doReturn(registrationDto).when(mapper).toDto(registration);

        RegistrationDto actualResult = registrationServiceImp.save(registrationDto);
        assertThat(actualResult).isEqualTo(registrationDto);
    }

    @Test
    @DisplayName("Негативный сценарий save() c Null на входе")
    void failSave() {
        RegistrationDto actualResult = registrationServiceImp.save(null);

        assertThat(actualResult).isNull();
    }

    @Test
    @DisplayName("Позитивный сценарий update()")
    void successUpdate() {
        RegistrationEntity registration = EntityTestData.getRegistrationEntity();
        RegistrationDto registrationDto = DtoTestData.getRegistrationDto();
        doReturn(Optional.of(registration)).when(repository).findById(registration.getId());
        doReturn(registration)
                .when(mapper)
                .mergeToEntity(registrationDto, Optional.of(registration).get());
        doReturn(registration).when(repository).save(registration);
        doReturn(registrationDto).when(mapper).toDto(registration);

        RegistrationDto actualResult = registrationServiceImp.update(registration.getId(), registrationDto);

        assertThat(actualResult).isEqualTo(registrationDto);
    }

    @Test
    @DisplayName("Негативный сценарий update() с исключением")
    void shouldThrowExceptionIfEntityNotUpdated() {
        RegistrationDto registrationDto = DtoTestData.getRegistrationDto();
        doReturn(Optional.empty()).when(repository).findById(any());

        assertThrows(EntityNotFoundException.class,
                () -> registrationServiceImp.update(10L, registrationDto));
        verify(repository, times(0)).save(any());
        verifyNoInteractions(mapper);
    }

    @Test
    @DisplayName("Позитивный сценарий findAllById()")
    void successFindAllById() {
        List<RegistrationEntity> registrationEntities = EntityTestData.getListOfRegistrationEntity();
        List<RegistrationDto> listOfRegistrationDto = DtoTestData.getListOfRegistrationDto();
        doReturn(registrationEntities).when(repository).findAllById(List.of(1L, 2L));
        doReturn(listOfRegistrationDto).when(mapper).toDtoList(registrationEntities);

        List<RegistrationDto> actualResult = registrationServiceImp.findAllById(List.of(1L, 2L));

        assertThat(actualResult).isEqualTo(listOfRegistrationDto);
    }

    @Test
    @DisplayName("Негативный сценарий findAllById() c Null на входе")
    void failFindAllById() {
        List<RegistrationDto> actualResult = registrationServiceImp.findAllById(null);

        assertThat(actualResult).hasSize(0);
    }
}