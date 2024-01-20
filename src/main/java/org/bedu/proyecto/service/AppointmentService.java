package org.bedu.proyecto.service;

import org.bedu.proyecto.dto.appointment.AppointmentDTO;
import org.bedu.proyecto.dto.appointment.ChangeStatusAppointmentDTO;
import org.bedu.proyecto.dto.appointment.CreateAppointmentDTO;
import org.bedu.proyecto.exception.appointment.AppointmentAlreadyCompleted;
import org.bedu.proyecto.exception.appointment.AppointmentAlreadyExist;
import org.bedu.proyecto.exception.appointment.AppointmentCreationNotAllowed;
import org.bedu.proyecto.exception.appointment.AppointmentNotFound;
import org.bedu.proyecto.exception.appointment.CannotChangeStatus;
import org.bedu.proyecto.exception.quote.QuoteNotFound;
import org.bedu.proyecto.mapper.AppointmentMapper;
import org.bedu.proyecto.model.Appointment;
import org.bedu.proyecto.model.Quote;
import org.bedu.proyecto.model.ServiceRequest;
import org.bedu.proyecto.model_enums.StatusAppointment;
import org.bedu.proyecto.model_enums.StatusQuote;
import org.bedu.proyecto.model_enums.StatusRequest;
import org.bedu.proyecto.repository.AppointmentRepository;
import org.bedu.proyecto.repository.QuoteRepository;
import org.bedu.proyecto.repository.ServiceRequestRepository;
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
    @Autowired
    private ServiceRequestRepository serviceRequestRepository;

    public AppointmentDTO save(long quoteId,CreateAppointmentDTO data) throws QuoteNotFound, AppointmentAlreadyExist, AppointmentCreationNotAllowed{
        Quote quote = Validation.quoteExists(quoteRepository, quoteId);

        //Validation Quote have ACCEPTED status
        if(quote.getStatus() != StatusQuote.ACCEPTED){
            throw new AppointmentCreationNotAllowed(quoteId);
        }

        //Validation Appointment is not already created for Quote
        if(quote.getAppointment()!=null){
            throw new AppointmentAlreadyExist(quoteId);
        }

        Appointment entity = mapper.toModel(data);
        entity.setQuote(quote);
        repository.save(entity);

        //Change Quote status to scheduled
        quote.setStatus(StatusQuote.SCHEDULED);
        quoteRepository.save(quote);

        //Change ServiceRequest status to scheduled
        ServiceRequest serviceRequest = quote.getQuoteRequest().getServiceRequest();
        serviceRequest.setStatus(StatusRequest.SCHEDULED);
        serviceRequestRepository.save(serviceRequest);


        return mapper.toDTO(entity);
    }

    public AppointmentDTO findByQuote(long quoteId) throws QuoteNotFound, AppointmentNotFound{
        Quote quote = Validation.quoteExists(quoteRepository, quoteId);
        if(quote.getAppointment()==null){
            throw new AppointmentNotFound(quoteId);
        }
        return mapper.toDTO(quote.getAppointment());

    }

    public void update (long appointmentId, ChangeStatusAppointmentDTO data) throws AppointmentNotFound, AppointmentAlreadyCompleted, CannotChangeStatus{
        Appointment appointment=Validation.appointmentExists(repository, appointmentId);

        //Validation, Supplier can only change status to COMPLETED
        if(data.getStatus()!= StatusAppointment.COMPLETED){
            throw new CannotChangeStatus(appointmentId);
        }

        //Validation appointment is not already with COMPLETED Status
        if(appointment.getStatus()==StatusAppointment.COMPLETED){
            throw new AppointmentAlreadyCompleted(appointmentId);
        }

        mapper.update(appointment, data);
        repository.save(appointment);

        //Change Quote status to COMPLETED
        Quote quote = appointment.getQuote();
        quote.setStatus(StatusQuote.COMPLETED);
        quoteRepository.save(quote);

        //Change ServiceRequest to COMPLETED
        ServiceRequest serviceRequest = appointment.getQuote().getQuoteRequest().getServiceRequest();
        serviceRequest.setStatus(StatusRequest.COMPLETED);
        serviceRequestRepository.save(serviceRequest);
    }
}
