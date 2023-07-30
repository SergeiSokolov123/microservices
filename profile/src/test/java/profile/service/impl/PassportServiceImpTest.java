package profile.service.impl;

import com.bank.profile.dto.PassportDto;
import com.bank.profile.entity.PassportEntity;
import com.bank.profile.mapper.PassportMapper;
import com.bank.profile.repository.PassportRepository;
import com.bank.profile.service.impl.PassportServiceImp;
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
class PassportServiceImpTest {
    @Mock
    private PassportRepository repository;
    @Mock
    private PassportMapper mapper;
    @InjectMocks
    private PassportServiceImp passportServiceImp;

    @Test
    @DisplayName("Поиск по id, позитивный сценарий")
    void findByIdPositiveTest() {
        PassportEntity passport = EntityTestData.getPassportEntity();
        PassportDto passportDto = DtoTestData.getPassportDto();
        doReturn(Optional.of(passport)).when(repository).findById(passport.getId());
        doReturn(passportDto).when(mapper).toDto(passport);

        PassportDto actualResult = passportServiceImp.findById(passport.getId());

        assertThat(actualResult).isEqualTo(passportDto);
    }

    @Test
    @DisplayName("Поиск по несуществующему id, негативный сценарий")
    void findByNonExistIdNegativeTest() {
        doReturn(Optional.empty()).when(repository).findById(any());

        assertThrows(EntityNotFoundException.class, () -> passportServiceImp.findById(1L));
        verifyNoInteractions(mapper);
    }

    @Test
    @DisplayName("Сохранение, позитивный сценарий")
    void savePositiveTest() {
        PassportEntity passport = EntityTestData.getPassportEntity();
        PassportDto passportDto = DtoTestData.getPassportDto();
        doReturn(passport).when(mapper).toEntity(passportDto);
        doReturn(passport).when(repository).save(passport);
        doReturn(passportDto).when(mapper).toDto(passport);

        PassportDto actualResult = passportServiceImp.save(passportDto);
        assertThat(actualResult).isEqualTo(passportDto);
    }

    @Test
    @DisplayName("Сохранение, на вход подан null, позитивный сценарий")
    void saveNullPositiveTest() {
        PassportDto actualResult = passportServiceImp.save(null);
        assertThat(actualResult).isNull();
    }

    @Test
    @DisplayName("Сохранение, негативный сценарий")
    void saveNegativeTest() {
        PassportEntity passport = EntityTestData.getPassportEntity();
        PassportDto passportDto = DtoTestData.getPassportDto();
        doReturn(passport).when(mapper).toEntity(passportDto);
        doThrow(RuntimeException.class).when(repository).save(passport);

        assertThrows(RuntimeException.class, () -> passportServiceImp.save(passportDto));
    }

    @Test
    @DisplayName("Обновление по id, позитивный сценарий")
    void updateByIdPositiveTest() {
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
    @DisplayName("Обновление по несуществующему id, негативный сценарий")
    void updateByNonExistIdNegativeTest() {
        PassportDto passportDto = DtoTestData.getPassportDto();
        doReturn(Optional.empty()).when(repository).findById(any());

        assertThrows(EntityNotFoundException.class,
                () -> passportServiceImp.update(10L, passportDto));
        verify(repository, times(0)).save(any());
        verifyNoInteractions(mapper);
    }

    @Test
    @DisplayName("Поиск по нескольким id, позитивный сценарий")
    void findAllByIdPositiveTest() {
        List<PassportEntity> passportEntities = EntityTestData.getListOfPassportEntity();
        List<PassportDto> listOfPassportDto = DtoTestData.getListOfPassportDto();
        doReturn(passportEntities).when(repository).findAllById(List.of(1L, 2L));
        doReturn(listOfPassportDto).when(mapper).toDtoList(passportEntities);

        List<PassportDto> actualResult = passportServiceImp.findAllById(List.of(1L, 2L));

        assertThat(actualResult).isEqualTo(listOfPassportDto);
    }

    @Test
    @DisplayName("Поиск по нескольким id равным null, позитивный сценарий")
    void findAllByIdNullPositiveTest() {
        List<PassportDto> actualResult = passportServiceImp.findAllById(null);
        assertThat(actualResult).hasSize(0);
    }

    @Test
    @DisplayName("Поиск по нескольким id, негативный сценарий")
    void findAllByIdNegativeTest() {
        List<Long> ids = List.of(3L, 4L);
        doThrow(RuntimeException.class).when(repository).findAllById(ids);

        assertThrows(RuntimeException.class, () -> passportServiceImp.findAllById(ids));
    }
}