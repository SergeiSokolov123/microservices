package profile.service.impl;

import com.bank.profile.dto.ActualRegistrationDto;
import com.bank.profile.entity.ActualRegistrationEntity;
import com.bank.profile.mapper.ActualRegistrationMapper;
import com.bank.profile.repository.ActualRegistrationRepository;
import com.bank.profile.service.impl.ActualRegistrationServiceImp;
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
class ActualRegistrationServiceImpTest {
    @Mock
    private ActualRegistrationRepository repository;
    @Mock
    private ActualRegistrationMapper mapper;

    @InjectMocks
    private ActualRegistrationServiceImp actualRegistrationServiceImp;

    @Test
    @DisplayName("Поиск по id, позитивный сценарий")
    void findByIdPositiveTest() {
        ActualRegistrationEntity actualRegistration = EntityTestData.getActualRegistrationEntity();
        ActualRegistrationDto actualRegistrationDto = DtoTestData.getActualRegistrationDto();
        doReturn(Optional.of(actualRegistration)).when(repository).findById(actualRegistration.getId());
        doReturn(actualRegistrationDto).when(mapper).toDto(actualRegistration);

        ActualRegistrationDto actualResult = actualRegistrationServiceImp.findById(actualRegistration.getId());

        assertThat(actualResult).isEqualTo(actualRegistrationDto);
    }

    @Test
    @DisplayName("Поиск по несуществующему id, негативный сценарий")
    void findByNonExistIdNegativeTest() {
        doReturn(Optional.empty()).when(repository).findById(any());

        assertThrows(EntityNotFoundException.class, () -> actualRegistrationServiceImp.findById(1L));
        verifyNoInteractions(mapper);
    }

    @Test
    @DisplayName("Сохранение, позитивный сценарий")
    void savePositiveTest() {
        ActualRegistrationEntity actualRegistration = EntityTestData.getActualRegistrationEntity();
        ActualRegistrationDto actualRegistrationDto = DtoTestData.getActualRegistrationDto();
        doReturn(actualRegistration).when(mapper).toEntity(actualRegistrationDto);
        doReturn(actualRegistration).when(repository).save(actualRegistration);
        doReturn(actualRegistrationDto).when(mapper).toDto(actualRegistration);

        ActualRegistrationDto actualResult = actualRegistrationServiceImp.save(actualRegistrationDto);
        assertThat(actualResult).isEqualTo(actualRegistrationDto);
    }

    @Test
    @DisplayName("Сохранение, на вход подан null, позитивный сценарий")
    void saveNullPositiveTest() {
        ActualRegistrationDto actualResult = actualRegistrationServiceImp.save(null);

        assertThat(actualResult).isNull();
    }

    @Test
    @DisplayName("Сохранение, негативный сценарий")
    void saveNegativeTest() {
        ActualRegistrationEntity actualRegistration = EntityTestData.getActualRegistrationEntity();
        ActualRegistrationDto actualRegistrationDto = DtoTestData.getActualRegistrationDto();
        doReturn(actualRegistration).when(mapper).toEntity(actualRegistrationDto);
        doThrow(RuntimeException.class).when(repository).save(actualRegistration);

        assertThrows(RuntimeException.class, () -> actualRegistrationServiceImp.save(actualRegistrationDto));
    }


    @Test
    @DisplayName("Обновление по id, позитивный сценарий")
    void updateByIdPositiveTest() {
        ActualRegistrationEntity actualRegistration = EntityTestData.getActualRegistrationEntity();
        ActualRegistrationDto actualRegistrationDto = DtoTestData.getActualRegistrationDto();
        doReturn(Optional.of(actualRegistration)).when(repository).findById(actualRegistration.getId());
        doReturn(actualRegistration)
                .when(mapper)
                .mergeToEntity(actualRegistrationDto, Optional.of(actualRegistration).get());
        doReturn(actualRegistration).when(repository).save(actualRegistration);
        doReturn(actualRegistrationDto).when(mapper).toDto(actualRegistration);

        ActualRegistrationDto actualResult = actualRegistrationServiceImp
                .update(actualRegistration.getId(), actualRegistrationDto);

        assertThat(actualResult).isEqualTo(actualRegistrationDto);
    }

    @Test
    @DisplayName("Обновление по несуществующему id, негативный сценарий")
    void updateByNonExistIdNegativeTest() {
        ActualRegistrationDto actualRegistrationDto = DtoTestData.getActualRegistrationDto();
        doReturn(Optional.empty()).when(repository).findById(any());

        assertThrows(EntityNotFoundException.class,
                () -> actualRegistrationServiceImp.update(10L, actualRegistrationDto));
        verify(repository, times(0)).save(any());
        verifyNoInteractions(mapper);
    }

    @Test
    @DisplayName("Поиск по нескольким id, позитивный сценарий")
    void findAllByIdPositiveTest() {
        List<ActualRegistrationEntity> actualRegistrationEntities = EntityTestData.getListOfActualRegistrationEntity();
        List<ActualRegistrationDto> listOfActualRegistrationDto = DtoTestData.getListOfActualRegistrationDto();
        doReturn(actualRegistrationEntities).when(repository).findAllById(List.of(1L, 2L));
        doReturn(listOfActualRegistrationDto).when(mapper).toDtoList(actualRegistrationEntities);

        List<ActualRegistrationDto> actualResult = actualRegistrationServiceImp.findAllById(List.of(1L, 2L));

        assertThat(actualResult).isEqualTo(listOfActualRegistrationDto);
    }

    @Test
    @DisplayName("Поиск по нескольким id равным null, позитивный сценарий")
    void findAllByIdNullPositiveTest() {
        List<ActualRegistrationDto> actualResult = actualRegistrationServiceImp.findAllById(null);

        assertThat(actualResult).hasSize(0);
    }

    @Test
    @DisplayName("Поиск по нескольким id, негативный сценарий")
    void findAllByIdNegativeTest() {
        List<Long> ids = List.of(3L, 4L);
        doThrow(RuntimeException.class).when(repository).findAllById(ids);

        assertThrows(RuntimeException.class, () -> actualRegistrationServiceImp.findAllById(ids));
    }

}