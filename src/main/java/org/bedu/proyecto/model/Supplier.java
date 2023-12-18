package org.bedu.proyecto.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
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

    @Column(nullable = false,unique=true)
    @NotNull
    private String businessName;
    @Column(nullable = false, length = 15,unique=true)
    @NotNull
    private String phone;

    @Column(nullable = true, length = 15,unique=true)
    private String phone_2;

    @Column(nullable = false)
    @NotNull
    private String address;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @ManyToMany
    @JoinTable(
     name = "supplier_service", 
    joinColumns = @JoinColumn(name = "supplier_id"), 
    inverseJoinColumns = @JoinColumn(name = "service_id"))
    @JsonBackReference
    List<AppService> services;

}
