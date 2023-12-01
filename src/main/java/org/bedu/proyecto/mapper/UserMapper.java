package org.bedu.proyecto.mapper;

import org.bedu.proyecto.dto.CreateUserDTO;
import org.bedu.proyecto.dto.UserDTO;
import org.bedu.proyecto.model.User;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface UserMapper {
    UserDTO toDTO(User model);

    @Mapping(target = "id", ignore = true)
    User toModel(CreateUserDTO dto);

}
