package com.bank.profile.service.impl;

import com.bank.profile.dto.ProfileDto;
import com.bank.profile.entity.ProfileEntity;
import com.bank.profile.mapper.ProfileMapper;
import com.bank.profile.repository.ProfileRepository;
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
class ProfileServiceImpTest {
    @Mock
    private ProfileRepository repository;
    @Mock
    private ProfileMapper mapper;
    @InjectMocks
    private ProfileServiceImp profileServiceImp;

    @Test
    @DisplayName("Позитивный сценарий findById()")
    void successFindById() {
        ProfileEntity profile = EntityTestData.getProfileEntity();
        ProfileDto profileDto = DtoTestData.getProfileDto();
        doReturn(Optional.of(profile)).when(repository).findById(profile.getId());
        doReturn(profileDto).when(mapper).toDto(profile);

        ProfileDto actualResult = profileServiceImp.findById(profile.getId());

        assertThat(actualResult).isEqualTo(profileDto);
    }

    @Test
    @DisplayName("Негативный сценарий findById() c исключением")
    void shouldThrowExceptionIfEntityNotFound() {
        doReturn(Optional.empty()).when(repository).findById(any());

        assertThrows(EntityNotFoundException.class, () -> profileServiceImp.findById(1L));
        verifyNoInteractions(mapper);
    }

    @Test
    @DisplayName("Позитивный сценарий save()")
    void successSave() {
        ProfileEntity profile = EntityTestData.getProfileEntity();
        ProfileDto profileDto = DtoTestData.getProfileDto();
        doReturn(profile).when(mapper).toEntity(profileDto);
        doReturn(profile).when(repository).save(profile);
        doReturn(profileDto).when(mapper).toDto(profile);

        ProfileDto actualResult = profileServiceImp.save(profileDto);

        assertThat(actualResult).isEqualTo(profileDto);
    }

    @Test
    @DisplayName("Негативный сценарий save() c Null на входе")
    void failSave() {
        ProfileDto actualResult = profileServiceImp.save(null);

        assertThat(actualResult).isNull();
    }

    @Test
    @DisplayName("Позитивный сценарий update()")
    void successUpdate() {
        ProfileEntity profile = EntityTestData.getProfileEntity();
        ProfileDto profileDto = DtoTestData.getProfileDto();
        doReturn(Optional.of(profile)).when(repository).findById(profile.getId());
        doReturn(profile)
                .when(mapper)
                .mergeToEntity(profileDto, Optional.of(profile).get());
        doReturn(profile).when(repository).save(profile);
        doReturn(profileDto).when(mapper).toDto(profile);

        ProfileDto actualResult = profileServiceImp.update(profile.getId(), profileDto);

        assertThat(actualResult).isEqualTo(profileDto);
    }

    @Test
    @DisplayName("Негативный сценарий update() с исключением")
    void shouldThrowExceptionIfEntityNotUpdated() {
        ProfileDto profileDto = DtoTestData.getProfileDto();
        doReturn(Optional.empty()).when(repository).findById(any());

        assertThrows(EntityNotFoundException.class,
                () -> profileServiceImp.update(10L, profileDto));
        verify(repository, times(0)).save(any());
        verifyNoInteractions(mapper);
    }

    @Test
    @DisplayName("Позитивный сценарий findAllById()")
    void successFindAllById() {
        List<ProfileEntity> profileEntities = EntityTestData.getListOfProfileEntity();
        List<ProfileDto> listOfProfileDto = DtoTestData.getListOfProfileDto();
        doReturn(profileEntities).when(repository).findAllById(List.of(1L, 2L));
        doReturn(listOfProfileDto).when(mapper).toDtoList(profileEntities);

        List<ProfileDto> actualResult = profileServiceImp.findAllById(List.of(1L, 2L));

        assertThat(actualResult).isEqualTo(listOfProfileDto);
    }

    @Test
    @DisplayName("Негативный сценарий findAllById() c Null на входе")
    void failFindAllById() {
        List<ProfileDto> actualResult = profileServiceImp.findAllById(null);

        assertThat(actualResult).hasSize(0);
    }

}