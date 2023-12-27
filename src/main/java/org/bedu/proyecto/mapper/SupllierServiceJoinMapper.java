package org.bedu.proyecto.mapper;

import org.bedu.proyecto.dto.service.AddServiceDTO;
import org.bedu.proyecto.model.SupplierServiceJoin;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring",injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface SupllierServiceJoinMapper {

    //Los mapper no aceptan tipos de datos primitivos como entrada
    //Se cambia de long a Long
    @Mapping(source="supplierId",target="id.supplierId")
    @Mapping(source="supplierId",target="supplier.id")
    @Mapping(source="dto.serviceId",target="id.serviceId")
    @Mapping(source="dto.serviceId",target="service.id")
    @Mapping(target = "averageRating", ignore = true)
    SupplierServiceJoin toModel(Long supplierId,AddServiceDTO dto);

    // @Mapping(source="supplierId",target="id.supplierId")
    // @Mapping(source="supplierId",target="supplier.id")
    // @Mapping(source="serviceId",target="id.serviceId")
    // @Mapping(source="serviceId",target="service.id")
    // @Mapping(target = "yearsExperience", ignore = true)
    // @Mapping(target = "averageRating", ignore = true)
    // Interpretation toModel(Long supplierId,Long serviceId);
}