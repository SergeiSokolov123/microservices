package profile.service.impl;

import com.bank.profile.dto.ProfileDto;
import com.bank.profile.entity.ProfileEntity;
import com.bank.profile.mapper.ProfileMapper;
import com.bank.profile.repository.ProfileRepository;
import com.bank.profile.service.impl.ProfileServiceImp;
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
import static org.mockito.Mockito.doThrow;
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
    @DisplayName("Поиск по id, позитивный сценарий")
    void findByIdPositiveTest() {
        ProfileEntity profile = EntityTestData.getProfileEntity();
        ProfileDto profileDto = DtoTestData.getProfileDto();
        doReturn(Optional.of(profile)).when(repository).findById(profile.getId());
        doReturn(profileDto).when(mapper).toDto(profile);

        ProfileDto actualResult = profileServiceImp.findById(profile.getId());

        assertThat(actualResult).isEqualTo(profileDto);
    }

    @Test
    @DisplayName("Поиск по несуществующему id, негативный сценарий")
    void findByNonExistIdNegativeTest() {
        doReturn(Optional.empty()).when(repository).findById(any());

        assertThrows(EntityNotFoundException.class, () -> profileServiceImp.findById(1L));
        verifyNoInteractions(mapper);
    }

    @Test
    @DisplayName("Сохранение, позитивный сценарий")
    void savePositiveTest() {
        ProfileEntity profile = EntityTestData.getProfileEntity();
        ProfileDto profileDto = DtoTestData.getProfileDto();
        doReturn(profile).when(mapper).toEntity(profileDto);
        doReturn(profile).when(repository).save(profile);
        doReturn(profileDto).when(mapper).toDto(profile);

        ProfileDto actualResult = profileServiceImp.save(profileDto);

        assertThat(actualResult).isEqualTo(profileDto);
    }

    @Test
    @DisplayName("Сохранение, на вход подан null, позитивный сценарий")
    void saveNullPositiveTest() {
        ProfileDto actualResult = profileServiceImp.save(null);

        assertThat(actualResult).isNull();
    }

    @Test
    @DisplayName("Сохранение, негативный сценарий")
    void saveNegativeTest() {
        ProfileEntity profile = EntityTestData.getProfileEntity();
        ProfileDto profileDto = DtoTestData.getProfileDto();
        doReturn(profile).when(mapper).toEntity(profileDto);
        doThrow(RuntimeException.class).when(repository).save(profile);

        assertThrows(RuntimeException.class, () -> profileServiceImp.save(profileDto));
    }

    @Test
    @DisplayName("Обновление по id, позитивный сценарий")
    void updateByIdPositiveTest() {
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
    @DisplayName("Обновление по несуществующему id, негативный сценарий")
    void updateByNonExistIdNegativeTest() {
        ProfileDto profileDto = DtoTestData.getProfileDto();
        doReturn(Optional.empty()).when(repository).findById(any());

        assertThrows(EntityNotFoundException.class,
                () -> profileServiceImp.update(10L, profileDto));
        verify(repository, times(0)).save(any());
        verifyNoInteractions(mapper);
    }

    @Test
    @DisplayName("Поиск по нескольким id, позитивный сценарий")
    void findAllByIdPositiveTest() {
        List<ProfileEntity> profileEntities = EntityTestData.getListOfProfileEntity();
        List<ProfileDto> listOfProfileDto = DtoTestData.getListOfProfileDto();
        doReturn(profileEntities).when(repository).findAllById(List.of(1L, 2L));
        doReturn(listOfProfileDto).when(mapper).toDtoList(profileEntities);

        List<ProfileDto> actualResult = profileServiceImp.findAllById(List.of(1L, 2L));

        assertThat(actualResult).isEqualTo(listOfProfileDto);
    }

    @Test
    @DisplayName("Поиск по нескольким id равным null, позитивный сценарий")
    void findAllByIdNullPositiveTest() {
        List<ProfileDto> actualResult = profileServiceImp.findAllById(null);

        assertThat(actualResult).hasSize(0);
    }

    @Test
    @DisplayName("Поиск по нескольким id, негативный сценарий")
    void findAllByIdNegativeTest() {
        List<Long> ids = List.of(3L, 4L);
        doThrow(RuntimeException.class).when(repository).findAllById(ids);

        assertThrows(RuntimeException.class, () -> profileServiceImp.findAllById(ids));
    }
}