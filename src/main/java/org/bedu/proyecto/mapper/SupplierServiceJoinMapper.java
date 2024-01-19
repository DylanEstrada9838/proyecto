package org.bedu.proyecto.mapper;

import java.util.List;

import org.bedu.proyecto.dto.service.AddServiceDTO;
import org.bedu.proyecto.dto.supplier_service.ChangeStatusDTO;
import org.bedu.proyecto.dto.supplier_service.ServicesBySupplierDTO;
import org.bedu.proyecto.dto.supplier_service.SuppliersByServicesDTO;
import org.bedu.proyecto.model.SupplierServiceJoin;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
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

    @Mapping(source = "service.service", target = "service")
    ServicesBySupplierDTO toServicesBySupplierDTO(SupplierServiceJoin model);

    @Mapping(source = "service.service", target = "service")
    List<ServicesBySupplierDTO> toServicesBySupplierDTO(List<SupplierServiceJoin> model);

    @Mapping(source = "supplier.businessName", target = "supplier")
    SuppliersByServicesDTO toSuppliersByServicesDTO(SupplierServiceJoin model);

    @Mapping(source = "supplier.businessName", target = "supplier")
    List<SuppliersByServicesDTO> toSuppliersByServicesDTO(List<SupplierServiceJoin> model);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "supplier", ignore = true)
    @Mapping(target = "service", ignore = true)
    @Mapping(target = "yearsExperience", ignore = true)
    @Mapping(target = "averageRating", ignore = true)
    @Mapping(target = "countRating", ignore = true)
    void update(@MappingTarget SupplierServiceJoin supplierServiceJoin,ChangeStatusDTO status);
}