package org.bedu.proyecto.model;

import java.time.Instant;
import java.util.List;

import org.bedu.proyecto.model_enums.StatusRequest;
import org.bedu.proyecto.model_enums.Urgency;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.SourceType;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;

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
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Table(name = "service_requests")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class ServiceRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(length = 300)
    @NotNull
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(length = 100)
    @NotNull
    private Urgency urgency;

    @Enumerated(EnumType.STRING)
    @Column(length = 100)
    @NotNull
    private StatusRequest status;

    @CreationTimestamp(source = SourceType.DB)
    @Column(updatable = false)
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private Instant createdAt;

    @UpdateTimestamp(source = SourceType.DB)
    private Instant updatedAt;

    @ManyToOne
    @JoinColumn(name = "address_id",referencedColumnName = "id")
    private Address address;

    @ManyToOne
    @JoinColumn(name = "client_id", referencedColumnName = "id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonBackReference
    private Client client;

    @ManyToOne
    @JoinColumn(name = "service_id", nullable = false,referencedColumnName = "id")
    private AppService service;

    @OneToMany(mappedBy = "serviceRequest")
    List<QuoteRequest> quoteRequests;
    
}
