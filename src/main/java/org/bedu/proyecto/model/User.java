package org.bedu.proyecto.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import java.util.Date;

@Getter
@Setter

@ToString
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(nullable = false, length = 50)
    private String name;
    @Column(nullable = false, length = 50)
    private String lastname;
    @Email
    @Column(nullable = false, length = 50, unique = true)
    private String email;
    @Column(nullable = false, length = 50)
    private String phone;
    private Date date;
}
