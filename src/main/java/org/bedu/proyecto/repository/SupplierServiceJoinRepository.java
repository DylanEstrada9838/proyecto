package org.bedu.proyecto.repository;

import java.util.List;
import java.util.Optional;

import org.bedu.proyecto.keys.SupplierServiceKey;
import org.bedu.proyecto.model.AppService;
import org.bedu.proyecto.model.SupplierServiceJoin;
import org.bedu.proyecto.model.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SupplierServiceJoinRepository extends JpaRepository <SupplierServiceJoin,SupplierServiceKey>{
    @Query("SELECT ss.service FROM SupplierServiceJoin ss WHERE ss.supplier.id = :supplierId")
    List<AppService> findServicesBySupplier(Long supplierId);
    @Query("SELECT ss.supplier FROM SupplierServiceJoin ss WHERE ss.service.id = :serviceId")
    List<Supplier> findSuppliersByService(Long serviceId);

    Optional<SupplierServiceJoin> findById(SupplierServiceKey id);
}
