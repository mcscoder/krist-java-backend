package com.krist.mapper.user;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.krist.dto.user.UserOverview;
import com.krist.entity.user.User;

@Mapper
public interface UserMapper {
    final UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserOverview toUserOverview(User user);
}
