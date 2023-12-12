package org.bedu.proyecto.model;


import java.time.Instant;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SourceType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
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
import lombok.ToString;

@Getter
@Setter
@ToString
@Table(name = "service_req")
@NoArgsConstructor

@Entity
public class ServiceRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    @NotNull
    private String address;

    @Column(nullable = false)
    @NotNull
    private String description;

    @Column(nullable = false, length = 100)
    @NotNull
    @Enumerated(EnumType.STRING)
    private Urgency urgency;

     @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 100)
    @NotNull
    private Status status;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "client_id",nullable = false)
    private Client client;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="service_id",nullable=false)
    private AppService service;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="supplier_id",nullable = false)
    private Supplier supplier;
   
    @CreationTimestamp(source = SourceType.DB)
    private Instant createdAt;
     
}