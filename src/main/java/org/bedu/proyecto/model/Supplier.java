package org.bedu.proyecto.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Table(name = "suppliers")
@Entity
public class Supplier {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @OneToMany(mappedBy = "supplier")
    @JsonBackReference
    List<SupplierServiceJoin> services;

    public Supplier() {
    }

    public Supplier(@NotNull String businessName, @NotNull String phone, @NotNull String address, User user) {
        this.businessName = businessName;
        this.phone = phone;
        this.address = address;
        this.user = user;
    }
    
    
}
