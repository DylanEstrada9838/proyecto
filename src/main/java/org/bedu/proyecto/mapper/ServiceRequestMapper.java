package org.bedu.proyecto.mapper;
import java.util.List;

import org.bedu.proyecto.dto.servicerequest.CreateServiceRequestDTO;
import org.bedu.proyecto.dto.servicerequest.ServiceRequestDTO;
import org.bedu.proyecto.model.ServiceRequest;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR,nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ServiceRequestMapper {
    @Mapping(source = "supplier.id", target = "supplierId")
    @Mapping(source = "service.id", target = "serviceId")
    @Mapping(source = "client.id", target = "clientId")
    ServiceRequestDTO toDTO(ServiceRequest model);

    @Mapping(source = "supplier.id", target = "supplierId")
    @Mapping(source = "service.id", target = "serviceId")
    @Mapping(source = "client.id", target = "clientId")
    List<ServiceRequestDTO> toDTOs(List <ServiceRequest> model);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "client", ignore = true)
    @Mapping(target = "service", ignore = true)
    @Mapping(target = "supplier", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "quote", ignore = true)
    @Mapping(source = "serviceId", target = "service.id")
    ServiceRequest toModel(CreateServiceRequestDTO dto);
    
}
