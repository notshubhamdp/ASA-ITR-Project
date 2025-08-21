package com.attendence.Student.Attendence.mapper;
import org.mapstruct.Mapper;

import com.attendence.Student.Attendence.dto.Userdto;

import com.attendence.Student.Attendence.entity.User;

@Mapper(componentModel = "spring")
public interface UserConversion {
    Userdto toDto(User user);
    User toEntity(Userdto userDto);
}
