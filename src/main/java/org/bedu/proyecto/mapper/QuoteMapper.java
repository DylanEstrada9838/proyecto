package org.bedu.proyecto.mapper;

import java.util.List;

import org.bedu.proyecto.dto.quote.CreateQuoteDTO;
import org.bedu.proyecto.dto.quote.QuoteDTO;
import org.bedu.proyecto.model.Quote;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR,nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface QuoteMapper {
    @Mapping(source = "quoteRequest.id", target = "quoteRequestId")
    QuoteDTO toDTO(Quote model);

    @Mapping(source = "serviceRequest.id", target = "requestId")
    List<QuoteDTO> toDTOs(List <Quote> model);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "quoteRequest", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    Quote toModel(CreateQuoteDTO dto);
}
