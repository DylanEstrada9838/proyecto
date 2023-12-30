package org.bedu.proyecto.mapper;

import org.bedu.proyecto.dto.appointment.AppointmentDTO;
import org.bedu.proyecto.dto.appointment.ChangeStatusAppointmentDTO;
import org.bedu.proyecto.dto.appointment.CreateAppointmentDTO;
import org.bedu.proyecto.model.Appointment;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import java.util.List;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR,nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface AppointmentMapper {
    @Mapping(source = "quote.id", target = "quoteId")
    AppointmentDTO toDTO(Appointment model);
    @Mapping(source = "quote.id", target = "quoteId")
    List<AppointmentDTO> toDTOs(List <Appointment> model);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "quote", ignore = true)
    Appointment toModel(CreateAppointmentDTO dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "quote", ignore = true)
    @Mapping(target = "startDate", ignore = true)
    void update(@MappingTarget Appointment appointment,ChangeStatusAppointmentDTO status);
}
