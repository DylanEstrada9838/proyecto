package org.bedu.proyecto.dto;

import java.time.LocalDateTime;

import org.bedu.proyecto.model.Status;
import org.bedu.proyecto.model.Urgency;

import lombok.Data;

@Data
public class ServiceRequestDTO {
    private long id;
    private Status status;
    private Urgency urgency;
    private LocalDateTime createdAt ;
}
