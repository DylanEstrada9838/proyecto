package org.bedu.proyecto.dto;



import java.time.LocalDateTime;

import org.bedu.proyecto.model.Status;
import org.bedu.proyecto.model.Urgency;

import lombok.Data;

@Data
public class CreateReqServiceDTO {
    private Urgency urgency;
    private long serviceId;
    private LocalDateTime createdAt= LocalDateTime.now();
    private Status status = Status.OPEN;
}
