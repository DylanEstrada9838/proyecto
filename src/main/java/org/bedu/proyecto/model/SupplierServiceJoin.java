package org.bedu.proyecto.model;

import java.math.BigDecimal;

import org.bedu.proyecto.keys.SupplierServiceKey;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "supplier_service")
public class SupplierServiceJoin {
    @EmbeddedId
    private SupplierServiceKey id;

    @ManyToOne
    @MapsId("supplierId")
    @JoinColumn(name = "supplier_id")
    @JsonBackReference
    private Supplier supplier;

    @ManyToOne
    @MapsId("serviceId")
    @JoinColumn(name = "service_id")
    @JsonBackReference
    private AppService service;

    @NotNull
    @Min(value = 0)
    @Max(value = 99)
    private int yearsExperience;

    @DecimalMin(value = "0.0", inclusive = false)
    @Digits(integer = 1, fraction = 1)
    private BigDecimal averageRating;
    
    @Min(value = 0)
    private int countRating;
}
