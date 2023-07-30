package com.bank.authorization.controller;

import com.bank.authorization.dto.UserDto;
import com.bank.authorization.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Создание пользователя - позитивный сценарий")
    void createPositiveTest() {
        UserDto userDto = new UserDto();
        userDto.setRole("user");
        userDto.setProfileId(1L);
        userDto.setPassword("password");

        UserDto savedUser = new UserDto();
        savedUser.setId(1L);
        savedUser.setRole("user");
        savedUser.setProfileId(1L);

        when(userService.save(any(UserDto.class))).thenReturn(savedUser);

        ResponseEntity<UserDto> response = userController.create(userDto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(savedUser, response.getBody());
    }

    @Test
    @DisplayName("Создание null - негативный сценарий")
    void createNullNegativeTest() {
        UserDto userDto = new UserDto();
        userDto.setRole("user");
        userDto.setProfileId(1L);
        userDto.setPassword("password");

        when(userService.save(any(UserDto.class))).thenReturn(null);

        ResponseEntity<UserDto> response = userController.create(userDto);

        assertNull(response.getBody());
    }

    @Test
    @DisplayName("чтение пользователя по id - позитивный сценарий")
    void readPositiveTest() {
        Long userId = 1L;

        UserDto userDto = new UserDto();
        userDto.setId(userId);
        userDto.setRole("user");
        userDto.setProfileId(1L);

        when(userService.findById(userId)).thenReturn(userDto);

        ResponseEntity<UserDto> response = userController.read(userId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(userDto, response.getBody());
    }

    @Test
    @DisplayName("чтение пользователя по id равному null - негативный сценарий")
    void readNullNegativeTest() {
        Long userId = 1L;

        when(userService.findById(userId)).thenReturn(null);

        ResponseEntity<UserDto> response = userController.read(userId);

        assertNull(response.getBody());
    }

    @Test
    @DisplayName("Обновление пользователя - позитивный сценарий")
    void updatePositiveTest() {
        Long userId = 1L;

        UserDto userDto = new UserDto();
        userDto.setRole("admin");
        userDto.setProfileId(2L);

        UserDto updatedUser = new UserDto();
        updatedUser.setId(userId);
        updatedUser.setRole("admin");
        updatedUser.setProfileId(2L);

        when(userService.update(eq(userId), any(UserDto.class))).thenReturn(updatedUser);

        ResponseEntity<UserDto> response = userController.update(userId, userDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedUser, response.getBody());
    }

    @Test
    @DisplayName("Обновление несуществующего пользователя - негативный сценарий")
    void updateNullNegativeTest() {
        Long userId = 1L;

        UserDto userDto = new UserDto();
        userDto.setRole("admin");
        userDto.setProfileId(2L);

        when(userService.update(eq(userId), any(UserDto.class))).thenReturn(null);

        ResponseEntity<UserDto> response = userController.update(userId, userDto);

        assertNull(response.getBody());
    }

    @Test
    @DisplayName("чтение по нескольким id - позитивный сценарий")
    void readAllPositiveTest() {
        List<Long> userIds = new ArrayList<>();
        userIds.add(1L);
        userIds.add(2L);

        List<UserDto> userDtos = new ArrayList<>();
        userDtos.add(new UserDto());
        userDtos.add(new UserDto());

        when(userService.findAllByIds(userIds)).thenReturn(userDtos);

        ResponseEntity<List<UserDto>> response = userController.readAll(userIds);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(userDtos, response.getBody());
    }

    @Test
    @DisplayName("чтение по нескольким id равным null - негативный сценарий")
    void readAllByNullNegativeTest() {
        List<Long> userIds = new ArrayList<>();
        userIds.add(1L);
        userIds.add(2L);

        when(userService.findAllByIds(userIds)).thenReturn(null);

        ResponseEntity<List<UserDto>> response = userController.readAll(userIds);

        assertNull(response.getBody());
    }

}
