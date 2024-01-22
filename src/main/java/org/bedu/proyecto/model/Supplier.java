package org.bedu.proyecto.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
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
@Table(name = "suppliers")
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Supplier {
    @Id
    @Column(name = "id")
    // @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique=true,length = 100)
    @NotNull
    private String businessName;

    @Column(length = 20,unique=true)
    @NotNull
    private String phone;

    @Column(length = 20,unique=true)
    private String phone_2;

    @Column(length=100)
    @NotNull
    private String address;

    @OneToOne
    @MapsId
    @JoinColumn(name = "id")
    // @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @OneToMany(mappedBy = "supplier")
    @JsonBackReference
    List<SupplierServiceJoin> services;

}
