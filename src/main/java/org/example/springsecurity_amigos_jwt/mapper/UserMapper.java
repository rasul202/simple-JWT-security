package org.example.springsecurity_amigos_jwt.mapper;

import org.example.springsecurity_amigos_jwt.entity.UserEntity;
import org.example.springsecurity_amigos_jwt.model.request.RegisterRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    public UserEntity toEntity(RegisterRequest request);

}
