package org.bedu.proyecto.dto;

import java.util.Date;
import lombok.Data;

@Data
public class UserDTO {
    private long id;
    private String name;
    private String lastname;
    private String email;
    private String phone;
    private Date date;
}
