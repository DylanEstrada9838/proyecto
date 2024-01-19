package org.bedu.proyecto.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "adresses")
@NoArgsConstructor
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    @Column(length = 100)
    private String line_1;

    @Column(length = 100)
    private String line_2;

    @NotNull
    @Column(length = 50)
    private String city;

    @NotNull
    @Column(length = 50)
    private String state;

    @Column(length = 50)
    private String postalCode;

    @Column(length = 200)
    private String notes;

    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false,referencedColumnName = "id")
    @JsonBackReference
    private Client client;

    public Address(@NotNull String line_1, String line_2, @NotNull String city, @NotNull String state,
            String postalCode, Client client) {
        this.line_1 = line_1;
        this.line_2 = line_2;
        this.city = city;
        this.state = state;
        this.postalCode = postalCode;
        this.client = client;
    }

    
}
