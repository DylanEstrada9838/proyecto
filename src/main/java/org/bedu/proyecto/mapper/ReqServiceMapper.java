package org.bedu.proyecto.mapper;
import java.util.List;

import org.bedu.proyecto.dto.CreateReqServiceDTO;
import org.bedu.proyecto.dto.ReqServiceDTO;
import org.bedu.proyecto.model.ReqService;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface ReqServiceMapper {
    ReqServiceDTO toDTO(ReqService model);

    List<ReqServiceDTO> toDTOs(List <ReqService> model);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "client", ignore = true)
    @Mapping(target = "service", ignore = true)
    @Mapping(source = "serviceId", target = "service.id")
    ReqService toModel(CreateReqServiceDTO dto);
    
}
