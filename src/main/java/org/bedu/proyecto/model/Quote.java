package org.bedu.proyecto.model;

import org.bedu.proyecto.model_enums.StatusQuote;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Table(name = "quotes")
@Entity
public class Quote {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private double totalCost;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable=false)
    private StatusQuote status;

    @ManyToOne
    @JoinColumn(name="request_id",referencedColumnName = "id")
    @JsonBackReference
    private ServiceRequest serviceRequest;

    @ManyToOne
    @JoinColumn(name="supplier_id",referencedColumnName="id")
    @JsonBackReference
    private Supplier supplier;
}
