package org.bedu.proyecto.mapper;

import java.util.List;

import org.bedu.proyecto.dto.SupplierDTO;
import org.bedu.proyecto.dto.CreateSupplierDTO;
import org.bedu.proyecto.dto.UpdateSupplierDTO;
import org.bedu.proyecto.model.Supplier;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface SupplierMapper {
    SupplierDTO toDTO(Supplier model);

    List<SupplierDTO> toDTOs(List<Supplier> model);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "services", ignore = true)
    @Mapping(source = "userId", target = "user.id")
    Supplier toModel(CreateSupplierDTO dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "services", ignore = true)
    @Mapping(target = "user", ignore = true)
    void update(@MappingTarget Supplier supplier, UpdateSupplierDTO data);
}
