package com.bank.authorization.service;

import com.bank.authorization.dto.UserDto;
import com.bank.authorization.entity.UserEntity;
import com.bank.authorization.mapper.UserMapper;
import com.bank.authorization.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("поиск по id - позитивный сценарий")
    void FindByIdPositiveTest() {
        Long userId = 1L;
        UserEntity userEntity = new UserEntity(userId, "role", 1L, "password");
        UserDto expectedDto = new UserDto(userId, "role", "password", 1L);

        when(userRepository.findById(userId)).thenReturn(Optional.of(userEntity));
        when(userMapper.toDTO(userEntity)).thenReturn(expectedDto);

        UserDto actualDto = userService.findById(userId);

        assertEquals(expectedDto, actualDto);
        verify(userRepository, times(1)).findById(userId);
        verify(userMapper, times(1)).toDTO(userEntity);
    }

    @Test
    @DisplayName("поиск по не существующему id - негативный сценарий")
    void FindByIdNegativeTest() {
        Long userId = 1L;

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> userService.findById(userId));
        verify(userRepository, times(1)).findById(userId);
    }

    @Test
    @DisplayName("сохранение - позитивный сценарий")
    void SavePositiveTest() {
        UserDto userDto = new UserDto(null, "role", "password", 1L);
        UserEntity userEntity = new UserEntity(null, "role", 1L, "password");
        UserDto expectedDto = new UserDto(1L, "role", "password", 1L);

        when(userMapper.toEntity(userDto)).thenReturn(userEntity);
        when(userRepository.save(userEntity)).thenReturn(userEntity);
        when(userMapper.toDTO(userEntity)).thenReturn(expectedDto);

        UserDto actualDto = userService.save(userDto);

        assertEquals(expectedDto, actualDto);
        assertNotNull(userEntity.getPassword()); // Assert that the password is encoded
        verify(userMapper, times(1)).toEntity(userDto);
        verify(userRepository, times(1)).save(userEntity);
        verify(userMapper, times(1)).toDTO(userEntity);
    }

    @Test
    @DisplayName("сохранение - негативный сценарий")
    void SaveNegativeTest() {
        UserDto userDto = new UserDto(1L, "role", "password", 1L);
        UserEntity userEntity = new UserEntity(1L, "role", 1L, "password");

        when(userMapper.toEntity(userDto)).thenReturn(userEntity);
        when(userRepository.save(userEntity)).thenThrow(new RuntimeException("Failed to save user"));

        assertThrows(RuntimeException.class, () -> userService.save(userDto));

        verify(userMapper, times(1)).toEntity(userDto);
        verify(userRepository, times(1)).save(userEntity);
        verify(userMapper, never()).toDTO(userEntity);
    }

    @Test
    @DisplayName("обновление, позитивный сценарий")
    void UpdatePositiveTest() {
        Long userId = 1L;
        UserDto userDto = new UserDto(null, "updatedRole", "updatedPassword", 1L);
        UserEntity existingUserEntity = new UserEntity(userId, "role", 1L, "password");
        UserEntity updatedUserEntity = new UserEntity(userId, "updatedRole", 1L, "updatedPassword");
        UserDto expectedDto = new UserDto(userId, "updatedRole", "updatedPassword", 1L);

        when(userRepository.findById(userId)).thenReturn(Optional.of(existingUserEntity));
        when(userRepository.save(updatedUserEntity)).thenReturn(updatedUserEntity);
        when(userMapper.mergeToEntity(userDto, existingUserEntity)).thenReturn(updatedUserEntity);
        when(userMapper.toDTO(updatedUserEntity)).thenReturn(expectedDto);

        UserDto actualDto = userService.update(userId, userDto);

        assertEquals(expectedDto, actualDto);
        assertNotNull(updatedUserEntity.getPassword());
        verify(userRepository, times(1)).findById(userId);
        verify(userRepository, times(1)).save(updatedUserEntity);
        verify(userMapper, times(1)).mergeToEntity(userDto, existingUserEntity);
        verify(userMapper, times(1)).toDTO(updatedUserEntity);
    }

    @Test
    @DisplayName("обновление null, негативный сценарий")
    void updateNullNegativeTest() {
        Long userId = 1L;
        UserDto userDto = new UserDto(null, "updatedRole", "updatedPassword", 1L);

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> userService.update(userId, userDto));
        verify(userRepository, times(1)).findById(userId);
        verifyNoMoreInteractions(userRepository);
        verifyNoInteractions(userMapper);
    }

    @Test
    @DisplayName("поиск по нескольким id, позитивный сценарий")
    void findAllByIdPositiveTest() {
        List<Long> userIds = List.of(1L, 2L, 3L);
        List<UserEntity> userEntities = new ArrayList<>();
        userEntities.add(new UserEntity(1L, "role1", 1L, "password1"));
        userEntities.add(new UserEntity(2L, "role2", 2L, "password2"));
        userEntities.add(new UserEntity(3L, "role3", 3L, "password3"));
        List<UserDto> expectedDtos = new ArrayList<>();
        expectedDtos.add(new UserDto(1L, "role1", "password1", 1L));
        expectedDtos.add(new UserDto(2L, "role2", "password2", 2L));
        expectedDtos.add(new UserDto(3L, "role3", "password3", 3L));

        when(userRepository.findById(1L)).thenReturn(Optional.of(userEntities.get(0)));
        when(userRepository.findById(2L)).thenReturn(Optional.of(userEntities.get(1)));
        when(userRepository.findById(3L)).thenReturn(Optional.of(userEntities.get(2)));
        when(userMapper.toDtoList(userEntities)).thenReturn(expectedDtos);

        List<UserDto> actualDtos = userService.findAllByIds(userIds);

        assertEquals(expectedDtos, actualDtos);
        verify(userRepository, times(1)).findById(1L);
        verify(userRepository, times(1)).findById(2L);
        verify(userRepository, times(1)).findById(3L);
        verify(userMapper, times(1)).toDtoList(userEntities);
    }

    @Test
    @DisplayName("поиск по нескольким несуществующим id, негативный сценарий")
    void findAllByNonExistIdNegativeTest() {
        List<Long> userIds = List.of(1L, 2L, 3L);

        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> userService.findAllByIds(userIds));
        verify(userRepository, times(1)).findById(1L);
        verifyNoMoreInteractions(userRepository);
        verifyNoInteractions(userMapper);
    }

}
