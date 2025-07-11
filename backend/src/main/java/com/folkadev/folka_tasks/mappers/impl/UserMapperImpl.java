package com.folkadev.folka_tasks.mappers.impl;

import com.folkadev.folka_tasks.domain.dto.UserDto;
import com.folkadev.folka_tasks.domain.entities.User;
import com.folkadev.folka_tasks.mappers.UserMapper;

public class UserMapperImpl implements UserMapper {

  @Override
  public User toUser(UserDto userDto) {
    return new User(
        userDto.name(),
        userDto.username(),
        userDto.email(),
        null, // hashedPassword is not provided by the DTO
        null, // created is not provided by the DTO
        null // updated is not provided by the DTO
    );
  }

  @Override
  public UserDto toDto(User user) {
    return new UserDto(user.getId(), user.getName(), user.getUsername(), user.getEmail());
  }

}
