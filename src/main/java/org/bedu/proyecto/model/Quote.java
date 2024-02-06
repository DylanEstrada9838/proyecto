package org.bedu.proyecto.model;

import java.math.BigDecimal;
import java.time.Instant;

import org.bedu.proyecto.model_enums.StatusQuote;
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
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Table(name = "quotes")
@Builder
@Entity
public class Quote {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long id;

    @NotNull
    @DecimalMin(value = "0.0", inclusive = false)
    @Digits(integer=5, fraction=2)
    private BigDecimal totalCost;
    
    @Enumerated(EnumType.STRING)
    @Column(length = 100)
    @NotNull
    private StatusQuote status;

    @CreationTimestamp(source = SourceType.DB)
    private Instant createdAt;

    @OneToOne
    @JoinColumn(name="quote_request_id",referencedColumnName = "id")
    @JsonBackReference
    private QuoteRequest quoteRequest;

    @OneToOne(mappedBy = "quote")
    private Appointment appointment;

}
