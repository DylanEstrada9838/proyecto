package org.bedu.proyecto.model;

import java.util.List;

import org.bedu.proyecto.model_enums.Gender;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Table(name = "clients")
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(length = 100)
    @NotNull
    private String name;

    @Column(length = 100)
    @NotNull
    private String lastName;

    @Column(length = 15, unique = true)
    @NotNull
    private String phone;

    @Column(length = 15, unique = true)
    private String phone_2;

    @NotNull
    @Min(value = 1)
    @Max(value = 99)
    private int age;

    @Enumerated(EnumType.STRING)
    @NotNull
    private Gender gender;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @OneToMany(mappedBy = "client")
    @JsonBackReference
    List<ServiceRequest> serviceRequests;

    @OneToMany(mappedBy = "client")
    @JsonBackReference
    List<Address> addresses;
}
