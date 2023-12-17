package org.bedu.proyecto.mapper;

import java.util.List;

import org.bedu.proyecto.dto.supplier.CreateSupplierDTO;
import org.bedu.proyecto.dto.supplier.SupplierDTO;
import org.bedu.proyecto.dto.supplier.UpdateSupplierDTO;
import org.bedu.proyecto.model.Supplier;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR,nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface SupplierMapper {
    SupplierDTO toDTO(Supplier model);

    List<SupplierDTO> toDTOs(List<Supplier> model);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "services", ignore = true)
    //@Mapping(target = "quotes", ignore = true)
    @Mapping(source = "userId", target = "user.id")
    Supplier toModel(CreateSupplierDTO dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "services", ignore = true)
   // @Mapping(target = "quotes", ignore = true)
    @Mapping(target = "user", ignore = true)
    void update(@MappingTarget Supplier supplier, UpdateSupplierDTO data);
}
