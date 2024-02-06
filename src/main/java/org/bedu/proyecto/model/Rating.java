package org.bedu.proyecto.model;

import org.bedu.proyecto.keys.RatingKey;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinColumns;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "ratings")
public class Rating {
    @EmbeddedId
    private RatingKey id;

    @ManyToOne
    @MapsId("supplierServiceKey")
    @JoinColumns({
        @JoinColumn(name = "supplier_id", referencedColumnName = "supplier_id"),
        @JoinColumn(name = "service_id", referencedColumnName = "service_id")
    })
    @JsonBackReference
    private SupplierServiceJoin supplierServiceJoin;

    @ManyToOne
    @MapsId("clientId")
    @JoinColumn(name = "client_id",referencedColumnName = "id")
    @JsonBackReference
    private Client client;

    @NotNull
    @Min(value = 1)
    @Max(value = 5)
    private int clientRating;

}
