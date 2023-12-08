package org.bedu.proyecto.dto;


import lombok.Data;

@Data
public class UserDTO {
    private long id;
    private String email;
    private String password;
}
