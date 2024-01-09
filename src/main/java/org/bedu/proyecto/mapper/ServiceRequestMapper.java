package org.bedu.proyecto.mapper;
import java.util.List;

import org.bedu.proyecto.dto.servicerequest.CreateServiceRequestDTO;
import org.bedu.proyecto.dto.servicerequest.ServiceRequestDTO;
import org.bedu.proyecto.dto.servicerequest.UpdateServiceRequestDTO;
import org.bedu.proyecto.model.ServiceRequest;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR,nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ServiceRequestMapper {
    
    @Mapping(source = "service.id", target = "serviceId")
    @Mapping(source = "client.id", target = "clientId")
    @Mapping(source = "address.id", target = "addressId")
    ServiceRequestDTO toDTO(ServiceRequest model);

    @Mapping(source = "service.id", target = "serviceId")
    @Mapping(source = "client.id", target = "clientId")
    @Mapping(source = "address.id", target = "addressId")
    List<ServiceRequestDTO> toDTOs(List <ServiceRequest> model);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "client", ignore = true)
    @Mapping(source = "serviceId", target = "service.id")
    @Mapping(source = "addressId", target = "address.id")
    @Mapping(target = "quoteRequests", ignore = true)
    ServiceRequest toModel(CreateServiceRequestDTO dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "client", ignore = true)
    @Mapping(target = "service", ignore = true)
    @Mapping(target = "quoteRequests", ignore = true)
    @Mapping(source = "addressId", target = "address.id")
    void update(@MappingTarget ServiceRequest request,UpdateServiceRequestDTO data);
    
}
