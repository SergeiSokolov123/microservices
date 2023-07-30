package profile.service.impl;

import com.bank.profile.dto.RegistrationDto;
import com.bank.profile.entity.RegistrationEntity;
import com.bank.profile.mapper.RegistrationMapper;
import com.bank.profile.repository.RegistrationRepository;
import com.bank.profile.service.impl.RegistrationServiceImp;
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
class RegistrationServiceImpTest {
    @Mock
    private RegistrationRepository repository;
    @Mock
    private RegistrationMapper mapper;
    @InjectMocks
    private RegistrationServiceImp registrationServiceImp;

    @Test
    @DisplayName("Поиск по id, позитивный сценарий")
    void findByIdPositiveTest() {
        RegistrationEntity registration = EntityTestData.getRegistrationEntity();
        RegistrationDto registrationDto = DtoTestData.getRegistrationDto();
        doReturn(Optional.of(registration)).when(repository).findById(registration.getId());
        doReturn(registrationDto).when(mapper).toDto(registration);

        RegistrationDto actualResult = registrationServiceImp.findById(registration.getId());

        assertThat(actualResult).isEqualTo(registrationDto);
    }

    @Test
    @DisplayName("Поиск по несуществующему id, негативный сценарий")
    void findByNonExistIdNegativeTest() {
        doReturn(Optional.empty()).when(repository).findById(any());

        assertThrows(EntityNotFoundException.class, () -> registrationServiceImp.findById(1L));
        verifyNoInteractions(mapper);
    }

    @Test
    @DisplayName("Сохранение, позитивный сценарий")
    void savePositiveTest() {
        RegistrationEntity registration = EntityTestData.getRegistrationEntity();
        RegistrationDto registrationDto = DtoTestData.getRegistrationDto();
        doReturn(registration).when(mapper).toEntity(registrationDto);
        doReturn(registration).when(repository).save(registration);
        doReturn(registrationDto).when(mapper).toDto(registration);

        RegistrationDto actualResult = registrationServiceImp.save(registrationDto);
        assertThat(actualResult).isEqualTo(registrationDto);
    }

    @Test
    @DisplayName("Сохранение, на вход подан null, позитивный сценарий")
    void saveNullPositiveTest() {
        RegistrationDto actualResult = registrationServiceImp.save(null);

        assertThat(actualResult).isNull();
    }

    @Test
    @DisplayName("Сохранение, негативный сценарий")
    void saveNegativeTest() {
        RegistrationEntity registration = EntityTestData.getRegistrationEntity();
        RegistrationDto registrationDto = DtoTestData.getRegistrationDto();
        doReturn(registration).when(mapper).toEntity(registrationDto);
        doThrow(RuntimeException.class).when(repository).save(registration);

        assertThrows(RuntimeException.class, () -> registrationServiceImp.save(registrationDto));
    }

    @Test
    @DisplayName("Обновление по id, позитивный сценарий")
    void updateByIdPositiveTest() {
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
    @DisplayName("Обновление по несуществующему id, негативный сценарий")
    void updateByNonExistIdNegativeTest() {
        RegistrationDto registrationDto = DtoTestData.getRegistrationDto();
        doReturn(Optional.empty()).when(repository).findById(any());

        assertThrows(EntityNotFoundException.class,
                () -> registrationServiceImp.update(10L, registrationDto));
        verify(repository, times(0)).save(any());
        verifyNoInteractions(mapper);
    }

    @Test
    @DisplayName("Поиск по нескольким id, позитивный сценарий")
    void findAllByIdPositiveTest() {
        List<RegistrationEntity> registrationEntities = EntityTestData.getListOfRegistrationEntity();
        List<RegistrationDto> listOfRegistrationDto = DtoTestData.getListOfRegistrationDto();
        doReturn(registrationEntities).when(repository).findAllById(List.of(1L, 2L));
        doReturn(listOfRegistrationDto).when(mapper).toDtoList(registrationEntities);

        List<RegistrationDto> actualResult = registrationServiceImp.findAllById(List.of(1L, 2L));

        assertThat(actualResult).isEqualTo(listOfRegistrationDto);
    }

    @Test
    @DisplayName("Поиск по нескольким id равным null, позитивный сценарий")
    void findAllByIdNullPositiveTest() {
        List<RegistrationDto> actualResult = registrationServiceImp.findAllById(null);

        assertThat(actualResult).hasSize(0);
    }

    @Test
    @DisplayName("Поиск по нескольким id, негативный сценарий")
    void findAllByIdNegativeTest() {
        List<Long> ids = List.of(3L, 4L);
        doThrow(RuntimeException.class).when(repository).findAllById(ids);

        assertThrows(RuntimeException.class, () -> registrationServiceImp.findAllById(ids));
    }
}