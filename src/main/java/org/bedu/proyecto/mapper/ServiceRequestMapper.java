package org.bedu.proyecto.mapper;
import java.util.List;

import org.bedu.proyecto.dto.CreateServiceRequestDTO;
import org.bedu.proyecto.dto.ServiceRequestDTO;
import org.bedu.proyecto.model.ServiceRequest;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface ServiceRequestMapper {
    ServiceRequestDTO toDTO(ServiceRequest model);

    List<ServiceRequestDTO> toDTOs(List <ServiceRequest> model);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "client", ignore = true)
    @Mapping(target = "service", ignore = true)
    @Mapping(source = "serviceId", target = "service.id")
    ServiceRequest toModel(CreateServiceRequestDTO dto);
    
}
