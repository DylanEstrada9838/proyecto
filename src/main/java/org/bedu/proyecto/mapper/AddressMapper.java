package org.bedu.proyecto.mapper;

import java.util.List;

import org.bedu.proyecto.dto.address.AddressDTO;
import org.bedu.proyecto.dto.address.CreateAddressDTO;
import org.bedu.proyecto.dto.address.UpdateAddressDTO;
import org.bedu.proyecto.model.Address;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface AddressMapper {

    AddressDTO toDTO(Address model);
    List<AddressDTO> toDTOs(List<Address> model);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "client", ignore = true)
    Address toModel(CreateAddressDTO dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "client", ignore = true)
    void update(@MappingTarget Address address,UpdateAddressDTO dto);

}