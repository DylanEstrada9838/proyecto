package org.bedu.proyecto.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
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
    private String email;

    @Column(length = 50)
    @NotNull
    private String password;

    public User() {
    }

    public User(@Email @NotNull String email, @NotNull String password) {
        this.email = email;
        this.password = password;
    }

    
}
