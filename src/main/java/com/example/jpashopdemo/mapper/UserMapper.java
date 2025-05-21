package com.example.jpashopdemo.mapper;

import com.example.jpashopdemo.domain.entity.entity.User;
import com.example.jpashopdemo.dto.UserDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto toUserDto(User user);
    User toEntity(UserDto dto);
}
