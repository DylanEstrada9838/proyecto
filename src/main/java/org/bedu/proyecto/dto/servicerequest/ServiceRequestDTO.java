package org.bedu.proyecto.dto.servicerequest;

import java.time.Instant;

import org.bedu.proyecto.model.StatusRequest;
import org.bedu.proyecto.model.Urgency;

import lombok.Data;

@Data
public class ServiceRequestDTO {
    private long id;
    private String address;
    private String description;
    private StatusRequest status;
    private Urgency urgency;
    private Instant createdAt ;
}
