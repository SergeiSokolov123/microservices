package com.bank.authorization.mapper;

import com.bank.authorization.dto.UserDto;
import com.bank.authorization.entity.UserEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class UserMapperTest {

    private UserMapper userMapper;

    @BeforeEach
    public void setup() {
        userMapper = Mappers.getMapper(UserMapper.class);
    }

    @Test
    @DisplayName("маппинг в дто")
    public void ToDtoTest() {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(1L);
        userEntity.setRole("ADMIN");
        userEntity.setPassword("password");
        userEntity.setProfileId(1L);

        UserDto userDto = userMapper.toDTO(userEntity);

        Assertions.assertEquals(userEntity.getId(), userDto.getId());
        Assertions.assertEquals(userEntity.getRole(), userDto.getRole());
        Assertions.assertEquals(userEntity.getPassword(), userDto.getPassword());
        Assertions.assertEquals(userEntity.getProfileId(), userDto.getProfileId());
    }

    @Test
    @DisplayName("маппинг в дто, на вход подан null")
    public void ToDtoNullTest() {
        UserEntity userEntity = new UserEntity();

        UserDto userDto = userMapper.toDTO(userEntity);

        Assertions.assertNull(userDto.getId());
        Assertions.assertNull(userDto.getRole());
        Assertions.assertNull(userDto.getPassword());
        Assertions.assertNull(userDto.getProfileId());
    }

    @Test
    @DisplayName("маппинг в энтити")
    public void ToEntityTest() {
        UserDto userDto = new UserDto();
        userDto.setRole("ADMIN");
        userDto.setPassword("password");
        userDto.setProfileId(1L);

        UserEntity userEntity = userMapper.toEntity(userDto);

        Assertions.assertEquals(userDto.getRole(), userEntity.getRole());
        Assertions.assertEquals(userDto.getPassword(), userEntity.getPassword());
        Assertions.assertEquals(userDto.getProfileId(), userEntity.getProfileId());
    }

    @Test
    @DisplayName("маппинг в энтити, на вход подан null")
    public void toEntityNullTest() {
        UserDto userDto = new UserDto();

        UserEntity userEntity = userMapper.toEntity(userDto);

        Assertions.assertNull(userEntity.getId());
        Assertions.assertNull(userEntity.getPassword());
        Assertions.assertNull(userEntity.getProfileId());
    }

    @Test
    @DisplayName("маппинг в список дто")
    public void ToDtoListTest() {
        UserEntity userEntity1 = new UserEntity(1L, "ADMIN", 1L, "password");
        UserEntity userEntity2 = new UserEntity(2L, "USER", 2L, "password");
        List<UserEntity> userEntityList = Arrays.asList(userEntity1, userEntity2);

        List<UserDto> userDtoList = userMapper.toDtoList(userEntityList);

        Assertions.assertEquals(userEntityList.size(), userDtoList.size());

        for (int i = 0; i < userEntityList.size(); i++) {
            UserEntity userEntity = userEntityList.get(i);
            UserDto userDto = userDtoList.get(i);

            Assertions.assertEquals(userEntity.getId(), userDto.getId());
            Assertions.assertEquals(userEntity.getRole(), userDto.getRole());
            Assertions.assertEquals(userEntity.getPassword(), userDto.getPassword());
            Assertions.assertEquals(userEntity.getProfileId(), userDto.getProfileId());
        }
    }

    @Test
    @DisplayName("маппинг в список дто, на вход подан null")
    public void ToDtoListNullTest() {
        List<UserEntity> userEntityList = new ArrayList<>();

        List<UserDto> userDtoList = userMapper.toDtoList(userEntityList);

        Assertions.assertEquals(userEntityList.size(), userDtoList.size());

        for (int i = 0; i < userEntityList.size(); i++) {
            UserEntity userEntity = userEntityList.get(i);
            UserDto userDto = userDtoList.get(i);

            Assertions.assertNull(userDto.getId());
            Assertions.assertNull(userDto.getRole());
            Assertions.assertNull(userDto.getPassword());
            Assertions.assertNull(userDto.getProfileId());
        }
    }

    @Test
    @DisplayName("маппинг в энтити")
    public void mergeToEntityTest() {
        UserEntity user = new UserEntity(1L, "ADMIN", 1L, "password");
        UserDto updatedUserDto = new UserDto();
        updatedUserDto.setRole("USER");
        updatedUserDto.setPassword("newPassword");
        updatedUserDto.setProfileId(2L);

        UserEntity mergedUser = userMapper.mergeToEntity(updatedUserDto, user);

        Assertions.assertEquals(user.getId(), mergedUser.getId());
        Assertions.assertEquals(updatedUserDto.getRole(), mergedUser.getRole());
        Assertions.assertEquals(updatedUserDto.getPassword(), mergedUser.getPassword());
        Assertions.assertEquals(updatedUserDto.getProfileId(), mergedUser.getProfileId());
    }

    @Test
    @DisplayName("маппинг в энтити, на вход подан null")
    public void mergeToEntityNullTest() {
        UserMapper userMapperMock = Mockito.mock(UserMapper.class);
        UserEntity user = null;
        UserDto updatedUserDto = new UserDto();
        updatedUserDto.setRole("USER");
        updatedUserDto.setPassword("newPassword");
        updatedUserDto.setProfileId(2L);

        UserEntity mergedUser = userMapperMock.mergeToEntity(updatedUserDto, user);

        Assertions.assertNull(mergedUser);
    }

}
