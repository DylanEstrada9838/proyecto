package org.bedu.proyecto.mapper;

import java.util.List;

import org.bedu.proyecto.dto.CreateUserDTO;
import org.bedu.proyecto.dto.UpdateUserDTO;
import org.bedu.proyecto.dto.UserDTO;
import org.bedu.proyecto.model.User;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface UserMapper {
    UserDTO toDTO(User model);

    List<UserDTO> toDTOs(List<User> model);

    @Mapping(target = "id", ignore = true)
    User toModel(CreateUserDTO dto);
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "email", ignore = true)
    void update(@MappingTarget User user,UpdateUserDTO data);

}
