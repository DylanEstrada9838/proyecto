package org.bedu.proyecto.mapper;

import java.util.List;

import org.bedu.proyecto.dto.quote.ChangeStatusQuoteDTO;
import org.bedu.proyecto.dto.quote.CreateQuoteDTO;
import org.bedu.proyecto.dto.quote.QuoteDTO;
import org.bedu.proyecto.model.Quote;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR,nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface QuoteMapper {
    @Mapping(source = "quoteRequest.id", target = "quoteRequestId")
    QuoteDTO toDTO(Quote model);

    @Mapping(source = "quoteRequest.id", target = "quoteRequestId")
    List<QuoteDTO> toDTOs(List <Quote> model);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "quoteRequest", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "appointment", ignore = true)
    Quote toModel(CreateQuoteDTO dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "quoteRequest", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "appointment", ignore = true)
    @Mapping(target = "totalCost", ignore = true)
    void update(@MappingTarget Quote quote,ChangeStatusQuoteDTO status);
}
