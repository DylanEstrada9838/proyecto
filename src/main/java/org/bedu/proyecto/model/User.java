package org.bedu.proyecto.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Email
    @Column(length = 50, unique = true)
    @NotNull
    @Size(min=5,max=50,message = "Email must no be less than 5 characters")
    private String email;
    @Column(length = 50)
    @NotNull
    @Size(min=3,max=50,message = "Password must not be less tha 3 characters")
    private String password;
}
