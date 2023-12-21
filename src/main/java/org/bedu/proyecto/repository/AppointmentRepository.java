package org.bedu.proyecto.repository;

import org.bedu.proyecto.model.Appointment;
import org.bedu.proyecto.model.Quote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository <Appointment,Long>{
    List<Appointment> findAll();
    List<Appointment> findByQuote(Quote quote);
}
