package org.bedu.proyecto.model;

import java.time.Instant;
import java.util.List;

import org.bedu.proyecto.model_enums.StatusRequest;
import org.bedu.proyecto.model_enums.Urgency;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SourceType;

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
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Table(name = "service_requests")
@NoArgsConstructor

@Entity
public class ServiceRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(length = 300)
    @NotNull
    private String description;

    @Column(length = 100)
    @NotNull
    private String address;

    @Enumerated(EnumType.STRING)
    @Column(length = 100)
    @NotNull
    private Urgency urgency;

    @Enumerated(EnumType.STRING)
    @Column(length = 100)
    @NotNull
    private StatusRequest status;

    @CreationTimestamp(source = SourceType.DB)
    private Instant createdAt;

    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false,referencedColumnName = "id")
    @JsonBackReference
    private Client client;

    @ManyToOne
    @JoinColumn(name = "service_id", nullable = false,referencedColumnName = "id")
    private AppService service;

    @OneToMany(mappedBy = "serviceRequest")
    List<QuoteRequest> quoteRequests;
}
