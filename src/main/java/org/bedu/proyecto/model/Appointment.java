package org.bedu.proyecto.model;

import org.bedu.proyecto.model_enums.StatusAppointment;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Table(name = "appointments")
@Entity
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private StatusAppointment status;

    @ManyToOne
    @JoinColumn(name = "supplier_id",referencedColumnName = "id")
    private Supplier supplier;

}
