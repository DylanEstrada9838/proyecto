package org.bedu.proyecto.mapper;
import java.util.List;

import org.bedu.proyecto.dto.quote_request.CreateQuoteRequestDTO;
import org.bedu.proyecto.dto.quote_request.QuoteRequestDTO;
import org.bedu.proyecto.model.QuoteRequest;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR,nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface QuoteRequestMapper {
    @Mapping(source = "serviceRequest.id", target = "serviceRequestId")
    @Mapping(source = "supplier.id", target = "supplierId")
    QuoteRequestDTO toDTO(QuoteRequest model);

    @Mapping(source = "serviceRequest.id", target = "serviceRequestId")
    @Mapping(source = "supplier.id", target = "supplierId")
    List<QuoteRequestDTO> toDTOs(List <QuoteRequest> model);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "serviceRequest", ignore = true)
    @Mapping(target = "supplier", ignore = true)
    @Mapping(target = "quote", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    QuoteRequest toModel(CreateQuoteRequestDTO dto);
}
