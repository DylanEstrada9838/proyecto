package org.bedu.proyecto.mapper;

import java.util.List;

import org.bedu.proyecto.dto.client.ClientDTO;
import org.bedu.proyecto.dto.client.CreateClientDTO;
import org.bedu.proyecto.dto.client.UpdateClientDTO;
import org.bedu.proyecto.model.Client;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR,nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ClientMapper {
    ClientDTO toDTO(Client model);

    List<ClientDTO> toDTOs(List<Client> model);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(source = "userId", target = "user.id")
    Client toModel(CreateClientDTO dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    void update(@MappingTarget Client client, UpdateClientDTO data);
}
