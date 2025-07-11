package com.folkadev.folka_tasks.mappers;

import com.folkadev.folka_tasks.domain.dto.UserDto;
import com.folkadev.folka_tasks.domain.entities.User;

public interface UserMapper {
  UserDto toDto(User user);

  User toUser(UserDto userDto);
}
