package org.bedu.proyecto.service;

import org.bedu.proyecto.dto.appointment.AppointmentDTO;
import org.bedu.proyecto.dto.appointment.CreateAppointmentDTO;
import org.bedu.proyecto.exception.appointment.AppointmentAlreadyExist;
import org.bedu.proyecto.exception.quote.QuoteNotFound;
import org.bedu.proyecto.mapper.AppointmentMapper;
import org.bedu.proyecto.model.Appointment;
import org.bedu.proyecto.model.Quote;
import org.bedu.proyecto.repository.AppointmentRepository;
import org.bedu.proyecto.repository.QuoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AppointmentService {
    @Autowired
    private AppointmentRepository repository;
    @Autowired
    private AppointmentMapper mapper;
    @Autowired
    private QuoteRepository quoteRepository;

    public AppointmentDTO save(long quoteId,CreateAppointmentDTO data) throws QuoteNotFound, AppointmentAlreadyExist{
        Quote quote = Validation.quoteExist(quoteRepository, quoteId);

        if(quote.getAppointment()!=null){
            throw new AppointmentAlreadyExist(quoteId);
        }
        Appointment entity = mapper.toModel(data);
        entity.setQuote(quote);
        repository.save(entity);

        return mapper.toDTO(entity);
    }
}
