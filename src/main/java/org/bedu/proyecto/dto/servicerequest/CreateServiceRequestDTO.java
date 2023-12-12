package org.bedu.proyecto.dto.servicerequest;



import org.bedu.proyecto.model.StatusRequest;
import org.bedu.proyecto.model.Urgency;

import lombok.Data;

@Data
public class CreateServiceRequestDTO {
    private String address;
    private String description;
    private Urgency urgency;
    private long serviceId;
    private long supplierId;
    private StatusRequest status = StatusRequest.OPEN;
}
