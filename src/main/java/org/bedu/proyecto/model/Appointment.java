package org.bedu.proyecto.model;

import java.time.LocalDateTime;

import org.bedu.proyecto.model_enums.StatusAppointment;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
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

    @Enumerated(EnumType.STRING)
    @Column(length = 100)
    @NotNull
    private StatusAppointment status;

    @NotNull
    private LocalDateTime startDate;

    @OneToOne
    @JoinColumn(name = "quote_id", referencedColumnName = "id")
    private Quote quote;

}
