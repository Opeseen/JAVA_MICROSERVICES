package com.ubaclone.eservice.mapper;

import com.ubaclone.eservice.dto.UserDTO;
import com.ubaclone.eservice.entity.Users;

public class UserMapper {
  public static UserDTO mapToUserDto(Users users, UserDTO userDTO){
    userDTO.setId(users.getId());
    userDTO.setUsername(users.getUsername());
    userDTO.setCustomerId(users.getCustomerId().toString());
    return userDTO;
  }

  public static Users mapToUser(UserDTO userDTO, Users users){
    users.setUsername(userDTO.getUsername());
    users.setPassword(userDTO.getPassword());
    return users;
  }
}
