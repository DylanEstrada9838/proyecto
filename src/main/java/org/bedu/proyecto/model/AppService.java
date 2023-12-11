package org.bedu.proyecto.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Table(name = "services")
@NoArgsConstructor

@Entity
public class AppService {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, length = 100)
    @NotNull
    private String service;

    @ManyToMany(mappedBy = "services")
    @JsonBackReference
    List<Supplier> suppliers;
    

    public AppService(String serviceName) {
        this.service = serviceName;
    }
}
