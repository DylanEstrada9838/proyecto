package org.bedu.proyecto.mapper;

import java.util.List;

import org.bedu.proyecto.dto.ClientDTO;
import org.bedu.proyecto.dto.CreateClientDTO;
import org.bedu.proyecto.dto.UpdateClientDTO;
import org.bedu.proyecto.model.Client;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface ClientMapper {
    ClientDTO toDTO(Client model);
    
    List<ClientDTO> toDTOs(List<Client> model);
    @Mapping(target = "id", ignore = true)
    @Mapping(source = "userId",target = "user.id")
    Client toModel(CreateClientDTO dto);

    void update (@MappingTarget Client client, UpdateClientDTO data);
}
