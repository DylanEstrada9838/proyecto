package org.bedu.proyecto.mapper;

import org.bedu.proyecto.dto.service.AddServiceDTO;
import org.bedu.proyecto.model.SupplierServiceJoin;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring",injectionStrategy = InjectionStrategy.CONSTRUCTOR,nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface SupplierServiceJoinMapper {

    //Los mapper no aceptan tipos de datos primitivos como entrada
    //Se cambia de long a Long
    @Mapping(source="supplierId",target="id.supplierId")
    @Mapping(source="supplierId",target="supplier.id")
    @Mapping(source="dto.serviceId",target="id.serviceId")
    @Mapping(source="dto.serviceId",target="service.id")
    @Mapping(target = "averageRating", ignore = true)
    SupplierServiceJoin toModel(Long supplierId,AddServiceDTO dto);
}