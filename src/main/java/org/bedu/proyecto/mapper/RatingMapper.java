package org.bedu.proyecto.mapper;

import org.bedu.proyecto.dto.rating.CreateRatingDTO;
import org.bedu.proyecto.model.Rating;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring",injectionStrategy = InjectionStrategy.CONSTRUCTOR,nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface RatingMapper {

    @Mapping(source="clientId",target="client.id")
    @Mapping(source="clientId",target="id.clientId")
    @Mapping(source="supplierId",target="id.supplierServiceKey.supplierId")
    @Mapping(source="supplierId",target="supplierServiceJoin.supplier.id")
    @Mapping(source="supplierId",target="supplierServiceJoin.id.supplierId")
    @Mapping(source="dto.serviceId",target="id.supplierServiceKey.serviceId")
    @Mapping(source="dto.serviceId",target="supplierServiceJoin.service.id")
    @Mapping(source="dto.serviceId",target="supplierServiceJoin.id.serviceId")
    @Mapping(target = "supplierServiceJoin", ignore = true)
    Rating toModel(Long clientId,Long supplierId,CreateRatingDTO dto);
}
