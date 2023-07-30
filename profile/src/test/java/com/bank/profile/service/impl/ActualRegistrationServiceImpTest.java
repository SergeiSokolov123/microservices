package com.bank.profile.service.impl;

import com.bank.profile.dto.ActualRegistrationDto;
import com.bank.profile.entity.ActualRegistrationEntity;
import com.bank.profile.mapper.ActualRegistrationMapper;
import com.bank.profile.repository.ActualRegistrationRepository;
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
class ActualRegistrationServiceImpTest {
    @Mock
    private ActualRegistrationRepository repository;
    @Mock
    private ActualRegistrationMapper mapper;

    @InjectMocks
    private ActualRegistrationServiceImp actualRegistrationServiceImp;

    @Test
    @DisplayName("Позитивный сценарий findById()")
    void successFindById() {
        ActualRegistrationEntity actualRegistration = EntityTestData.getActualRegistrationEntity();
        ActualRegistrationDto actualRegistrationDto = DtoTestData.getActualRegistrationDto();
        doReturn(Optional.of(actualRegistration)).when(repository).findById(actualRegistration.getId());
        doReturn(actualRegistrationDto).when(mapper).toDto(actualRegistration);

        ActualRegistrationDto actualResult = actualRegistrationServiceImp.findById(actualRegistration.getId());

        assertThat(actualResult).isEqualTo(actualRegistrationDto);
    }

    @Test
    @DisplayName("Негативный сценарий findById() c исключением")
    void shouldThrowExceptionIfEntityNotFound() {
        doReturn(Optional.empty()).when(repository).findById(any());

        assertThrows(EntityNotFoundException.class, () -> actualRegistrationServiceImp.findById(1L));
        verifyNoInteractions(mapper);
    }

    @Test
    @DisplayName("Позитивный сценарий save()")
    void successSave() {
        ActualRegistrationEntity actualRegistration = EntityTestData.getActualRegistrationEntity();
        ActualRegistrationDto actualRegistrationDto = DtoTestData.getActualRegistrationDto();
        doReturn(actualRegistration).when(mapper).toEntity(actualRegistrationDto);
        doReturn(actualRegistration).when(repository).save(actualRegistration);
        doReturn(actualRegistrationDto).when(mapper).toDto(actualRegistration);

        ActualRegistrationDto actualResult = actualRegistrationServiceImp.save(actualRegistrationDto);
        assertThat(actualResult).isEqualTo(actualRegistrationDto);
    }

    @Test
    @DisplayName("Негативный сценарий save() c Null на входе")
    void failSave() {
        ActualRegistrationDto actualResult = actualRegistrationServiceImp.save(null);

        assertThat(actualResult).isNull();
    }


    @Test
    @DisplayName("Позитивный сценарий update()")
    void successUpdate() {
        ActualRegistrationEntity actualRegistration = EntityTestData.getActualRegistrationEntity();
        ActualRegistrationDto actualRegistrationDto = DtoTestData.getActualRegistrationDto();
        doReturn(Optional.of(actualRegistration)).when(repository).findById(actualRegistration.getId());
        doReturn(actualRegistration)
                .when(mapper)
                .mergeToEntity(actualRegistrationDto, Optional.of(actualRegistration).get());
        doReturn(actualRegistration).when(repository).save(actualRegistration);
        doReturn(actualRegistrationDto).when(mapper).toDto(actualRegistration);

        ActualRegistrationDto actualResult = actualRegistrationServiceImp.update(actualRegistration.getId(), actualRegistrationDto);

        assertThat(actualResult).isEqualTo(actualRegistrationDto);
    }

    @Test
    @DisplayName("Негативный сценарий update() с исключением")
    void shouldThrowExceptionIfEntityNotUpdated() {
        ActualRegistrationDto actualRegistrationDto = DtoTestData.getActualRegistrationDto();
        doReturn(Optional.empty()).when(repository).findById(any());

        assertThrows(EntityNotFoundException.class,
                () -> actualRegistrationServiceImp.update(10L, actualRegistrationDto));
        verify(repository, times(0)).save(any());
        verifyNoInteractions(mapper);
    }

    @Test
    @DisplayName("Позитивный сценарий findAllById()")
    void successFindAllById() {
        List<ActualRegistrationEntity> actualRegistrationEntities = EntityTestData.getListOfActualRegistrationEntity();
        List<ActualRegistrationDto> listOfActualRegistrationDto = DtoTestData.getListOfActualRegistrationDto();
        doReturn(actualRegistrationEntities).when(repository).findAllById(List.of(1L, 2L));
        doReturn(listOfActualRegistrationDto).when(mapper).toDtoList(actualRegistrationEntities);

        List<ActualRegistrationDto> actualResult = actualRegistrationServiceImp.findAllById(List.of(1L, 2L));

        assertThat(actualResult).isEqualTo(listOfActualRegistrationDto);
    }

    @Test
    @DisplayName("Негативный сценарий findAllById() c Null на входе")
    void failFindAllById() {
        List<ActualRegistrationDto> actualResult = actualRegistrationServiceImp.findAllById(null);

        assertThat(actualResult).hasSize(0);
    }

}